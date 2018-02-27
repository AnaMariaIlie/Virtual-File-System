
/**
 * Clasa corespunzatoare comenzii "rmdir".
 *
 * @author Ilie Ana-Maria
 *
 */
public class Rmdir implements Command {

	/**
	 * <p>
	 * Metoda care verifica daca din folderul curent se poate sterge un anumit
	 * folder.
	 * <p>
	 * Inainte de al sterge, verific daca nu se sterge folderul curent, vreun
	 * parinte sau vreun stramos. Folderul curent este cel e dinaintea entitatii
	 * care se doreste a fi stearsa, iar clona si folderul temporar vor fi
	 * folderul initial, din care s-a dat comanda. Incepand din folderul
	 * temporar parcurg din parinte in parinte pana in "/" si verific daca in
	 * aceasta cale se intalneste folderul curent. Daca da, se afiseaza mesaj de
	 * eroare si se revine la folderul initial(acum "clona"). Daca nu, verific
	 * celelalte conditii si daca sunt indeplinite il sterg din lista folderului
	 * curent si revin la cel initial.
	 * 
	 * @param myfs
	 *            sistemul de fisiere.
	 * @param commandName
	 *            numele comenzii.
	 * @param name
	 *            numele ultimei entitati.
	 * @param tempCurrentFolder
	 *            clona folderului curent.
	 * @param clone
	 *            clona folderului din care s-a executat comanda.
	 * @return true sau false, in functie de succesul executiei.
	 */
	public boolean checkHelper(MyFileSystem myfs, String commandName,
			String name, Folder tempCurrentFolder, Folder clone) {

		if (myfs.isCheckIfCdIsWorking()) {

			if (myfs.getCurrentFolder().containsFile(name)) {
				System.out.println("-3: " + commandName + ": Not a directory");
				myfs.setCurrentFolder(clone);
			} else {
				if ((name.equals(".") || name.equals(".."))
						&& tempCurrentFolder.getName().equals("/")) {

					System.out.println("-13: " + commandName
							+ ": Cannot delete parent or current directory");
					myfs.setCurrentFolder(clone);
				} else {

					while (tempCurrentFolder.getParent() != null) {

						if (tempCurrentFolder.equals(myfs.getCurrentFolder())) {
							System.out.println("-13: " + commandName
									+ ": Cannot delete parent or current directory");
							myfs.setCurrentFolder(clone);
							return false;
						} else {
							tempCurrentFolder = tempCurrentFolder.getParent();
						}
					}

					if (!myfs.getCurrentFolder().getChildFolder(name)
							.getContent().isEmpty()) {
						System.out.println("-14: " + commandName
								+ ": Non empty directory");
						myfs.setCurrentFolder(clone);
					} else {
						if (!myfs.getCurrentFolder()
								.checkRights(myfs.getCurrentUser(), 2)) {
							System.out.println("-5: " + commandName
									+ ": No rights to write");
							myfs.setCurrentFolder(clone);
						} else {
							if (!myfs.getCurrentFolder().containsFolder(name)) {
								System.out.println("-2: " + commandName
										+ ": No such directory");
								myfs.setCurrentFolder(clone);
							} else {
								myfs.getCurrentFolder().getContent()
										.remove(myfs.getCurrentFolder()
												.getChildFolder(name));
								myfs.setCurrentFolder(clone);
							}
						}
					}
				}
			}

		}

		return false;
	}

	/**
	 * <p>
	 * Metoda care executa comanda "rmdir".
	 * <p>
	 * Cu ajutorul acesteia se sterge un folder din sistem.
	 * <p>
	 * Mai intai se prelucreaza sirul primit, apoi cu ajutorul comenzii "cd" se
	 * intra in folderul dinaintea entitatii dorite si se apeleaza metoda
	 * "checkSingleEntity" care verifica daca se poate sterge folderul si se
	 * sterge.
	 * <p>
	 * Daca stringul primit reprezinta un singur nume se sterge direct din folderul
	 * curent. De asemenea, daca este chiar folderul curent sau parintele lui se
	 * afiseaza eroarea corespunzatoare.
	 * <p>
	 * Se elimina din lista folderului curent.
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		try {
			String[] parts = param.split("/");
			CommandFactory commandFactory = new CommandFactory();
			Command command;
			Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder().clone();
			Folder clone = (Folder) myfs.getCurrentFolder().clone();

			if (param.endsWith("/") && param.lastIndexOf("/") != 0) {
				param = param.substring(0, param.length() - 1);
			}

			if (param.equals(".") || param.equals("..")) {
				System.out.println("-13: " + commandName
						+ ": Cannot delete parent or current directory");
				return;

			} else {
				if (myfs.isRelative(param)) {
					if (!param.contains("/")) {
						if (!myfs.getCurrentFolder().containsFolder(param)) {
							System.out.println("-2: " + commandName
									+ ": No such directory");
						} else {
							if (myfs.getCurrentFolder().containsFile(param)) {
								System.out.println("-3: " + commandName
										+ ": Not a directory");
							} else {
								if (!myfs.getCurrentFolder()
										.getChildFolder(param).getContent()
										.isEmpty()) {
									System.out.println("-14: " + commandName
											+ ": Non empty directory");
								} else {
									if (!myfs.getCurrentFolder().checkRights(
											myfs.getCurrentUser(), 2)) {
										System.out.println("-5: " + commandName
												+ ": No rights to write");
									} else {
										myfs.getCurrentFolder().getContent()
												.remove(myfs.getCurrentFolder()
														.getChildFolder(param));
									}
								}
							}
						}
					} else {
						command = commandFactory.getCommands("Cd");
						command.execute(
								param.substring(0, param.lastIndexOf("/")),
								myfs, commandName);

						if (!this.checkHelper(myfs, commandName,
								parts[parts.length - 1], tempCurrentFolder,
								clone)) {
							return;
						}
					}
				} else {
					if (param.lastIndexOf("/") == 0) {
						command = commandFactory.getCommands("Cd");
						command.execute("/", myfs, commandName);

						if (!this.checkHelper(myfs, commandName,
								parts[parts.length - 1], tempCurrentFolder,
								clone)) {
							return;
						}

					} else {
						command = commandFactory.getCommands("Cd");
						command.execute(
								param.substring(0, param.lastIndexOf("/")),
								myfs, commandName);

						if (!this.checkHelper(myfs, commandName,
								parts[parts.length - 1], tempCurrentFolder,
								clone)) {
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
