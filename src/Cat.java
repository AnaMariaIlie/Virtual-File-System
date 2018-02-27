/**
 * Clasa corespunzatoare comenzii "cat".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class Cat implements Command {

	/**
	 * Metoda care verifica cele 3 conditii pentru a putea citi continutul unui
	 * fisier si in caz afirmativ il afiseaza.
	 * 
	 * @param myfs
	 *            sistemul de fisiere.
	 * @param name
	 *            numele fisierului care va fi verificat.
	 * @param commandName
	 *            numele comenzii.
	 * @param tempCurrentFolder
	 *            clona folderului initial.
	 * @return true sau false, daca operatia de afisare a reusit sau nu.
	 */
	public boolean checkSingleEntity(MyFileSystem myfs, String name,
			String commandName, Folder tempCurrentFolder) {

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
						.checkRights(myfs.getCurrentUser(), 4)) {
					System.out.println(
							"-4: " + commandName + ": No rights to read");
					myfs.setCurrentFolder(tempCurrentFolder);
					return false;
				} else {
					System.out.println(myfs.getCurrentFolder()
							.getChildFile(name).getContent().toString());
					myfs.setCurrentFolder(tempCurrentFolder);
					return true;

				}
			}
		}
	}

	/**
	 * <p>
	 * Metoda prin care se afiseaza continutul unui fisier.
	 * <p>
	 * Cu ajutorul comenzii "cd", in functie de tipul caii(relativa sau
	 * absoluta) verific directorul/directoarele dinaintea fisierului si daca nu
	 * exista nicio eroare, ii afisez continutul.
	 * 
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		try {
			String[] parts = param.split("/");
			CommandFactory commandFactory = new CommandFactory();
			Command command;
			Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder().clone();

			if (myfs.isRelative(param)) {
				if (!param.contains("/")) {
					if (!this.checkSingleEntity(myfs, param, commandName,
							tempCurrentFolder)) {
						return;
					}
				} else {
					command = commandFactory.getCommands("Cd");
					command.execute(param.substring(0, param.lastIndexOf("/")),
							myfs, commandName);

					if (myfs.isCheckIfCdIsWorking()) {
						if (!this.checkSingleEntity(myfs,
								parts[parts.length - 1], commandName,
								tempCurrentFolder)) {
							return;
						}
					} else {
						return;
					}
				}
			} else {
				if (param.lastIndexOf("/") == 0) {
					command = commandFactory.getCommands("Cd");
					command.execute("/", myfs, commandName);

					if (myfs.isCheckIfCdIsWorking()) {
						if (!this.checkSingleEntity(myfs,
								parts[parts.length - 1], commandName,
								tempCurrentFolder)) {
							return;
						}
					} else {
						return;
					}
				} else {
					command = commandFactory.getCommands("Cd");
					command.execute(param.substring(0, param.lastIndexOf("/")),
							myfs, commandName);

					if (myfs.isCheckIfCdIsWorking()) {
						if (!this.checkSingleEntity(myfs,
								parts[parts.length - 1], commandName,
								tempCurrentFolder)) {
							return;
						}
					} else {
						return;
					}
				}
			}

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

}
