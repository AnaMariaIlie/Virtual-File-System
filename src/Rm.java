
/**
 * Clasa corespunzatoare comenzii "rm".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class Rm implements Command {

	/**
	 * <p>
	 * Metoda care verifica daca din folderul curent se poate sterge un anumit
	 * fisier.
	 * <p>
	 * Daca sunt indeplinite conditiile impuse, se sterge fisierul din lista
	 * folderului curent si se revine la folderul initial.
	 * 
	 * @param myfs
	 *            sistemul de fisiere.
	 * @param commandName
	 *            numele comenzii.
	 * @param name
	 *            numele ultimei entitati.
	 * @param tempCurrentFolder
	 *            copia folderului curent.
	 * @return true sau false, in functie de succesul executiei.
	 */
	public boolean checkSingleEntity(MyFileSystem myfs, String commandName,
			String name, Folder tempCurrentFolder) {

		if (myfs.isCheckIfCdIsWorking()) {

			if (myfs.getCurrentFolder().containsFolder(name)) {
				System.out.println("-1: " + commandName + ": Is a directory");
				myfs.setCurrentFolder(tempCurrentFolder);
				return false;
			} else {
				if (!myfs.getCurrentFolder().containsFile(name)) {
					System.out
							.println("-11: " + commandName + ": No such file");
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
						myfs.getCurrentFolder().getContent().remove(
								myfs.getCurrentFolder().getChildFile(name));
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
	 * Metoda care executa comanda "rm".
	 * <p>
	 * Cu ajutorul acesteia se sterge un fisier sau o entitate(recursiv).
	 * <p>
	 * Mai intai se prelucreaza sirul primit. Daca parametrul primit contine si
	 * "-r" se apeleaza functia execute din clasa "RmRecursive". Daca nu, cu
	 * ajutorul comenzii "cd" se intra in folderul dinaintea entitatii dorite si
	 * se apeleaza metoda "checkSingleEntity" care verifica daca se poate sterge
	 * fisierul si se sterge.
	 * <p>
	 * Daca stringul primit reprezinta un singur nume se sterge direct din
	 * folderul curent.
	 * <p>
	 * Se elimina din lista folderului curent.
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		try {
			String[] parts = param.split(" ");
			String[] path;
			CommandFactory commandFactory = new CommandFactory();
			Command command;
			Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder().clone();

			// rm -r
			if (parts.length != 1) {

				path = parts[1].split("/");
				command = commandFactory.getCommands("RmRecursive");
				command.execute(parts[1], myfs, commandName);
			} else {

				if (myfs.isRelative(param)) {

					if (!param.contains("/")) {

						if (myfs.getCurrentFolder().containsFolder(param)) {
							System.out.println(
									"-1: " + commandName + ": Is a directory");
						} else {
							if (!myfs.getCurrentFolder().containsFile(param)) {
								System.out.println("-11: " + commandName
										+ ": No such file");
							} else {
								if (!myfs.getCurrentFolder().checkRights(
										myfs.getCurrentUser(), 2)) {
									System.out.println("-5: " + commandName
											+ ": No rights to write");
								} else {
									myfs.getCurrentFolder().getContent()
											.remove(myfs.getCurrentFolder()
													.getChildFile(param));
									myfs.setCurrentFolder(tempCurrentFolder);
								}
							}
						}
					} else {
						path = param.split("/");
						command = commandFactory.getCommands("Cd");
						command.execute(
								param.substring(0, param.lastIndexOf("/")),
								myfs, commandName);

						if (!this.checkSingleEntity(myfs, commandName,
								path[path.length - 1], tempCurrentFolder)) {
							return;
						}
					}
				} else {

					path = param.split("/");
					if (param.lastIndexOf("/") == 0) {
						command = commandFactory.getCommands("Cd");
						command.execute("/", myfs, commandName);

						if (!this.checkSingleEntity(myfs, commandName,
								path[path.length - 1], tempCurrentFolder)) {
							return;
						}

					} else {
						command = commandFactory.getCommands("Cd");
						command.execute(
								param.substring(0, param.lastIndexOf("/")),
								myfs, commandName);

						if (!this.checkSingleEntity(myfs, commandName,
								path[path.length - 1], tempCurrentFolder)) {
							return;
						}

					}

				}

			}

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}
