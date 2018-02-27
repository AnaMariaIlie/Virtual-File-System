/**
 * Clasa corespunzatoare comenzii "chmod".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class Chmod implements Command {

	/**
	 * <p>Metoda care verifica daca ultima entitate din calea primita este valida
	 * si o putem accesa si schimba permisiunuile cu cele dorite. De
	 * asemenea, se revine la folderul din care am dat comanda.
	 * 
	 * @param myfs
	 *            sistemul de fisiere.
	 * @param name
	 *            numele ultimei entitati.
	 * @param commandName
	 *            numele comenzii.
	 * @param tempCurrentFolder
	 *            clona folderului initial.
	 * @param ownerPermission
	 *            permisiunile pentru detinator.
	 * @param otherPermission
	 *            permisiunile pentru ceilalti utilizatori.
	 * @return	true sau false, in functie de succesul executiei.
	 */
	public boolean checkSingleEntity(MyFileSystem myfs, String name,
			String commandName, Folder tempCurrentFolder, int ownerPermission,
			int otherPermission) {

		if (myfs.getCurrentFolder().getContent().isEmpty()
				|| (!myfs.getCurrentFolder().containsFolder(name)
						&& !myfs.getCurrentFolder().containsFile(name))) {

			System.out.println(
					"-12: " + commandName + ": No such file or directory");
			myfs.setCurrentFolder(tempCurrentFolder);
			return false;
		} else {
			if (!myfs.getCurrentFolder().getChild(name)
					.checkRights(myfs.getCurrentUser(), 2)) {
				System.out
						.println("-5: " + commandName + ": No rights to write");
				myfs.setCurrentFolder(tempCurrentFolder);
				return false;
			} else {
				myfs.getCurrentFolder().getChild(name)
						.setOwnerPermission(ownerPermission);
				myfs.getCurrentFolder().getChild(name)
						.setOtherPermission(otherPermission);
				myfs.setCurrentFolder(tempCurrentFolder);
				return true;
			}
		}
	}

	/**
	 * <p>
	 * Metoda care executa comanda "chmod".
	 * <p>
	 * Cu ajutorul acesteia se schimba permisiunile unei entitati.
	 * <p>
	 * Mai intai se prelucreaza sirul primit, astfel incat sa putem distinge
	 * care sunt permisiunile si care este calea, apoi cu ajutorul comenzii "cd"
	 * se intra in folderul dinaintea entitatii dorite si se apeleaza metoda
	 * "checkSingleEntity" care verifica daca se poate accesa entitatea si
	 * efectueaza modificarile dorite.
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		try {
			String[] partsPermisssion = param.split(" ");
			String[] parts = partsPermisssion[1].split("/");
			int ownerPermission = Integer
					.parseInt(partsPermisssion[0].substring(0, 1));
			int otherPermission = Integer
					.parseInt(partsPermisssion[0].substring(1, 2));
			CommandFactory commandFactory = new CommandFactory();
			Command command;
			Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder().clone();

			if (partsPermisssion[1].endsWith("/")
					&& partsPermisssion[1].lastIndexOf("/") != 0) {
				partsPermisssion[1] = partsPermisssion[1].substring(0,
						partsPermisssion[1].length() - 1);
			}

			if (myfs.isRelative(partsPermisssion[1])) {
				if (!partsPermisssion[1].contains("/")) {
					if (!this.checkSingleEntity(myfs, partsPermisssion[1],
							commandName, tempCurrentFolder, ownerPermission,
							otherPermission)) {
						return;
					}
				} else {
					command = commandFactory.getCommands("Cd");
					command.execute(
							partsPermisssion[1].substring(0,
									partsPermisssion[1].lastIndexOf("/")),
							myfs, commandName);

					if (myfs.isCheckIfCdIsWorking()) {
						if (!this.checkSingleEntity(myfs,
								parts[parts.length - 1], commandName,
								tempCurrentFolder, ownerPermission,
								otherPermission)) {
							return;
						}
					} else {
						return;
					}
				}
			} else {
				if (partsPermisssion[1].lastIndexOf("/") == 0) {
					command = commandFactory.getCommands("Cd");
					command.execute("/", myfs, commandName);

					if (myfs.isCheckIfCdIsWorking()) {
						if (!this.checkSingleEntity(myfs,
								parts[parts.length - 1], commandName,
								tempCurrentFolder, ownerPermission,
								otherPermission)) {
							return;
						}
					} else {
						return;
					}
				} else {
					command = commandFactory.getCommands("Cd");
					command.execute(
							partsPermisssion[1].substring(0,
									partsPermisssion[1].lastIndexOf("/")),
							myfs, commandName);

					if (myfs.isCheckIfCdIsWorking()) {
						if (!this.checkSingleEntity(myfs,
								parts[parts.length - 1], commandName,
								tempCurrentFolder, ownerPermission,
								otherPermission)) {
							return;
						}
					} else {
						return;
					}
				}
			}

		} catch (CloneNotSupportedException e) {
			System.out.println("Folder Cast exception : " + e);
		}
	}

}
