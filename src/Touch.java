/**
 * Clasa corespunzatoare comenzii "touch".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class Touch implements Command {

	/**
	 * <p>
	 * Metoda care verifica daca in folderul curent se poate crea un nou fisier.
	 * <p>
	 * Daca sunt indeplinite conditiile impuse, se creeaza un nou fisier cu
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
	 * @param newFile
	 *            noul fisier.
	 * @return true sau false, in functie de succesul executiei.
	 */
	public boolean checkSingleEntity(MyFileSystem myfs, String commandName,
			String name, Folder tempCurrentFolder, File newFile) {

		if (myfs.isCheckIfCdIsWorking()) {
			if (!myfs.getCurrentFolder().checkRights(myfs.getCurrentUser(),
					2)) {
				System.out
						.println("-5: " + commandName + ": No rights to write");
				myfs.setCurrentFolder(tempCurrentFolder);
				return false;
			} else {
				if (myfs.getCurrentFolder().containsFolder(name)) {
					System.out
							.println("-1: " + commandName + ": Is a directory");
					myfs.setCurrentFolder(tempCurrentFolder);
					return false;
				} else {
					if (myfs.getCurrentFolder().containsFile(name)) {
						System.out.println(
								"-7: " + commandName + ": File already exists");
						myfs.setCurrentFolder(tempCurrentFolder);
						return false;
					} else {
						newFile = new File(name, myfs.getCurrentUser(), 7, 0,
								myfs.getCurrentFolder());
						myfs.getCurrentFolder().addContent(newFile);
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
	 * Metoda care executa comanda "touch".
	 * <p>
	 * Cu ajutorul acesteia se creeaza un nou fisier.
	 * <p>
	 * Mai intai se prelucreaza sirul primit, apoi cu ajutorul comenzii "cd" se
	 * intra in folderul dinaintea entitatii dorite si se apeleaza metoda
	 * "checkSingleEntity" care verifica daca se poate creea fisierul si se creeaza.
	 * <p>
	 * Daca stringul primit reprezinta un singur nume se creeaza direct in
	 * folderul curent.
	 * <p>
	 * Se creeaza un nou fisier cu numele dorit, se adauga la lista folderului
	 * curent.
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		try {
			File newFile = new File();
			String[] parts = param.split("/");
			CommandFactory commandFactory = new CommandFactory();
			Command command;
			Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder().clone();

			if (!param.contains("/")) {

				if (!myfs.getCurrentFolder().checkRights(myfs.getCurrentUser(),
						2)) {
					System.out.println(
							"-5: " + commandName + ": No rights to write");
				} else {
					if (myfs.getCurrentFolder().containsFolder(param)) {
						System.out.println(
								"-1: " + commandName + ": Is a directory");
					} else {
						if (myfs.getCurrentFolder().containsFile(param)) {
							System.out.println("-7: " + commandName
									+ ": File already exists");
						} else {

							newFile = new File(param, myfs.getCurrentUser(), 7,
									0, myfs.getCurrentFolder());
							myfs.getCurrentFolder().addContent(newFile);
							myfs.setCurrentFolder(tempCurrentFolder);
						}
					}
				}

			} else {

				command = commandFactory.getCommands("Cd");
				command.execute(param.substring(0, param.lastIndexOf("/")),
						myfs, commandName);

				if (!this.checkSingleEntity(myfs, commandName,
						parts[parts.length - 1], tempCurrentFolder, newFile)) {

					return;
				}

			}

		} catch (CloneNotSupportedException e) {
			System.out.println("Folder Cast exception : " + e);
		}

	}

}