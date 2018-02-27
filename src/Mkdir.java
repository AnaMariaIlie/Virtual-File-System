/**
 * Clasa corespunzatoare comenzii "mkdir".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class Mkdir implements Command {

	/**
	 * <p>
	 * Metoda care verifica daca in folderul curent se poate crea un nou folder.
	 * <p>
	 * Daca sunt indeplinite conditiile impuse, se creeaza un nou folder cu
	 * numele dorit, se adauga la lista folderului curent si se revine la
	 * folderul de unde a fost data comanda.
	 * 
	 * @param myfs
	 *            sistemul de fisiere.
	 * @param name
	 *            numele ultimei entitati.
	 * @param commandName
	 *            numele comenzii.
	 * @param tempCurrentFolder
	 *            clona folderului initial.
	 * @param newFolder
	 *            noul folder.
	 * @return true sau false, in functie de succesul executiei.
	 */
	public boolean checkSingleEntity(MyFileSystem myfs, String commandName,
			String name, Folder tempCurrentFolder, Folder newFolder) {

		if (myfs.isCheckIfCdIsWorking()) {
			
			if (myfs.getCurrentFolder().containsFile(name)) {
				System.out.println("-3: " + commandName + ": Not a directory");
				myfs.setCurrentFolder(tempCurrentFolder);
				return false;
			} else {
				if (myfs.getCurrentFolder().containsFolder(name)) {
					System.out
							.println("-1: " + commandName + ": Is a directory");
					myfs.setCurrentFolder(tempCurrentFolder);
					return false;
				} else {
					if (!myfs.getCurrentFolder()
							.checkRights(myfs.getCurrentUser(), 2)) {
						System.out.println(
								"-5: " + commandName + ": No rights to write");
						myfs.setCurrentFolder(tempCurrentFolder);
						return false;
					} else {
						newFolder = new Folder(name, myfs.getCurrentUser(), 7,
								0, myfs.getCurrentFolder());
						myfs.getCurrentFolder().addContent(newFolder);
						myfs.setCurrentFolder(tempCurrentFolder);
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * <p>
	 * Metoda care executa comanda "mkdir".
	 * <p>
	 * Cu ajutorul acesteia se creeaza un nou folder.
	 * <p>
	 * Mai intai se prelucreaza sirul primit, apoi cu ajutorul comenzii "cd" se
	 * intra in folderul dinaintea entitatii dorite si se apeleaza metoda
	 * "checkSingleEntity" care verifica daca se poate creea directorul si se creeaza.
	 * <p>
	 * Daca stringul primit reprezinta un singur nume se creeaza direct in
	 * folderul curent.
	 * <p>
	 * Se creeaza un nou folder cu numele dorit, se adauga la lista folderului
	 * curent.
	 * <p>
	 * Pentru folderul "/" procedura este separate deoarece acesta trebuie setat
	 * si ca folder curent.
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		try {
			Folder newFolder = new Folder();
			String[] parts = param.split("/");
			CommandFactory commandFactory = new CommandFactory();
			Command command;
			Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder().clone();

			if (param.endsWith("/") && param.lastIndexOf("/") != 0) {
				param = param.substring(0, param.length() - 1);
			}

			if (param.equals("/")) {

				if (myfs.getCurrentFolder().getContent().size() != 0) {
					System.out
							.println("-1: " + commandName + ": Is a directory");
				} else {
					newFolder = new Folder("/", myfs.getCurrentUser(), 7, 5,
							null);
					myfs.root.setAssignedFolder(newFolder);
					myfs.setCurrentFolder(newFolder);
				}

			} else {
				if (myfs.isRelative(param)) {

					if (!param.contains("/")) {

						if (myfs.getCurrentFolder().containsFolder(param)) {
							System.out.println(
									"-1: " + commandName + ": Is a directory");
						} else {
							if (myfs.getCurrentFolder().containsFile(param)) {
								System.out.println("-3: " + commandName
										+ ": Not a directory");
							} else {
								if (!myfs.getCurrentFolder().checkRights(
										myfs.getCurrentUser(), 2)) {
									System.out.println("-5: " + commandName
											+ ": No rights to write");
								} else {

									newFolder = new Folder(param,
											myfs.getCurrentUser(), 7, 0,
											myfs.getCurrentFolder());
									myfs.getCurrentFolder()
											.addContent(newFolder);
									myfs.setCurrentFolder(tempCurrentFolder);
								}
							}
						}

					} else {

						command = commandFactory.getCommands("Cd");
						command.execute(
								param.substring(0, param.lastIndexOf("/")),
								myfs, commandName);

						if (!this.checkSingleEntity(myfs, commandName,
								parts[parts.length - 1], tempCurrentFolder,
								newFolder)) {

							return;
						}

					}
				} else {
					if (param.lastIndexOf("/") == 0) {
						command = commandFactory.getCommands("Cd");
						command.execute("/", myfs, commandName);

						if (!this.checkSingleEntity(myfs, commandName,
								parts[parts.length - 1], tempCurrentFolder,
								newFolder)) {

							return;
						}

					} else {
						command = commandFactory.getCommands("Cd");
						command.execute(
								param.substring(0, param.lastIndexOf("/")),
								myfs, commandName);

						if (!this.checkSingleEntity(myfs, commandName,
								parts[parts.length - 1], tempCurrentFolder,
								newFolder)) {

							return;
						}

					}

				}

			}

		} catch (CloneNotSupportedException e) {
			System.out.println("Folder Cast exception : " + e);
		}

	}

}
