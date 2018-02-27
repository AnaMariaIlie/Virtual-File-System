
/**
 * Clasa corespunzatoare comenzii "writetofile".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class WriteToFile implements Command {

	/**
	 * Metoda care verifica cele 3 conditii pentru a putea schimba continutul
	 * unui fisier si in caz afirmativ il modifica.
	 * 
	 * @param myfs
	 *            sistemul de fisiere.
	 * @param name
	 *            numele fisierului care va fi verificat.
	 * @param commandName
	 *            numele comenzii.
	 * @param tempCurrentFolder
	 *            clona folderului initial.
	 * @param content
	 * 			continutul care trebuie scris.
	 * @return true sau false, daca operatia de modificare a reusit sau nu.
	 */
	public boolean checkSingleEntity(MyFileSystem myfs, String name,
			String commandName, Folder tempCurrentFolder, String content) {

		if (myfs.getCurrentFolder().containsFolder(name)) {

			System.out.println("-1: " + commandName + ": Is a directory");
			myfs.setCurrentFolder(tempCurrentFolder);
			return false;
		} else {
			if (!myfs.getCurrentFolder().containsFile(name)) {

				System.out.println("-11: " + commandName + ": No such file");
				myfs.setCurrentFolder(tempCurrentFolder);
				return false;
			} else {

				if (!myfs.getCurrentFolder().getChildFile(name)
						.checkRights(myfs.getCurrentUser(), 2)) {

					System.out.println(
							"-5: " + commandName + ": No rights to write");
					myfs.setCurrentFolder(tempCurrentFolder);
					return false;
				} else {

					myfs.getCurrentFolder().getChildFile(name)
							.setContent(content);
					myfs.setCurrentFolder(tempCurrentFolder);
					return true;

				}
			}
		}
	}

	/**
	 * <p>
	 * Metoda prin care se schimba continutul unui fisier.
	 * <p>
	 * Cu ajutorul comenzii "cd", in functie de tipul caii(relativa sau
	 * absoluta) verific directorul/directoarele dinaintea fisierului si daca nu
	 * exista nicio eroare, ii modific continutul.
	 * 
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		try {
			String[] partsContent = param.split(" ");
			String[] partsPath = partsContent[0].split("/");
			String content = param.substring(param.indexOf("\""),
					param.lastIndexOf("\"") + 1);
			CommandFactory commandFactory = new CommandFactory();
			Command command;
			Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder().clone();

			if (myfs.isRelative(partsContent[0])) {
				if (!partsContent[0].contains("/")) {
					if (!this.checkSingleEntity(myfs, partsContent[0],
							commandName, tempCurrentFolder, content)) {
						return;
					}
				} else {
					command = commandFactory.getCommands("Cd");
					command.execute(
							partsContent[0].substring(0,
									partsContent[0].lastIndexOf("/")),
							myfs, commandName);

					if (!this.checkSingleEntity(myfs,
							partsPath[partsPath.length - 1], commandName,
							tempCurrentFolder, content)) {
						return;
					}
				}
			} else {

				if (partsContent[0].lastIndexOf("/") == 0) {

					command = commandFactory.getCommands("Cd");
					command.execute("/", myfs, commandName);

					if (!this.checkSingleEntity(myfs,
							partsPath[partsPath.length - 1], commandName,
							tempCurrentFolder, content)) {
						return;
					}

				} else {
					command = commandFactory.getCommands("Cd");
					command.execute(
							partsContent[0].substring(0,
									partsContent[0].lastIndexOf("/")),
							myfs, commandName);

					if (!this.checkSingleEntity(myfs,
							partsPath[partsPath.length - 1], commandName,
							tempCurrentFolder, content)) {
						return;
					}

				}
			}

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

}
