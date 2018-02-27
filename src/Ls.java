
/**
 * Clasa corespunzatoare comenzii "ls".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class Ls implements Command {
	/**
	 * <p>
	 * Metoda care verifica daca ultima entitate din calea primita este valida
	 * si o putem accesa si lista continutul. De asemenea, se revine la folderul
	 * din care am dat comanda.
	 * 
	 * @param myfs
	 *            sistemul de fisiere.
	 * @param name
	 *            numele ultimei entitati.
	 * @param commandName
	 *            numele comenzii.
	 * @param tempCurrentFolder
	 *            clona folderului initial.
	 * @return true sau false, in functie de succesul executiei.
	 */
	public boolean checkSingleEntity(MyFileSystem myfs, String name,
			String commandName, Folder tempCurrentFolder) {

		CommandFactory commandFactory = new CommandFactory();
		Command command;

		if (name.equals(".") || name.equals("..")) {

			command = commandFactory.getCommands("Cd");
			command.execute(name, myfs, commandName);

			if (!myfs.isCheckIfCdIsWorking()) {
				myfs.setCurrentFolder(tempCurrentFolder);
				return false;
			} else {
				myfs.getCurrentFolder().listEntity();
				myfs.setCurrentFolder(tempCurrentFolder);
				return true;
			}
		} else {

			if (myfs.getCurrentFolder().getContent().isEmpty()) {

				System.out.println(
						"-12: " + commandName + ": No such file or directory");
				myfs.setCurrentFolder(tempCurrentFolder);
				return false;
			} else {

				if (myfs.getCurrentFolder().getChildFolder(name) != null) {

					if (!myfs.getCurrentFolder().getChildFolder(name)
							.checkRights(myfs.getCurrentUser(), 4)) {
						System.out.println(
								"-4: " + commandName + ": No rights to read");
						myfs.setCurrentFolder(tempCurrentFolder);
						return false;
					} else {

						command = commandFactory.getCommands("Cd");
						command.execute(name, myfs, commandName);

						if (!myfs.isCheckIfCdIsWorking()) {
							myfs.setCurrentFolder(tempCurrentFolder);
							return false;
						} else {

							if (myfs.getCurrentFolder().getContent()
									.isEmpty()) {
								System.out.println("-12: " + commandName
										+ ": No such file or directory");
								myfs.setCurrentFolder(tempCurrentFolder);
								return false;
							} else {

								myfs.getCurrentFolder().listEntity();
								myfs.setCurrentFolder(tempCurrentFolder);
								return true;
							}
						}

					}
				} else {

					if (!myfs.getCurrentFolder()
							.checkRights(myfs.getCurrentUser(), 4)) {
						System.out.println(
								"-4: " + commandName + ": No rights to read");
						myfs.setCurrentFolder(tempCurrentFolder);
						return false;
					} else {

						myfs.getCurrentFolder().getChildFile(name).listEntity();
						myfs.setCurrentFolder(tempCurrentFolder);
						return true;
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * Metoda care executa comanda "ls".
	 * <p>
	 * Cu ajutorul acesteia se listeaza continutul unei entitati.
	 * <p>
	 * Mai intai se prelucreaza sirul primit, apoi cu ajutorul comenzii "cd" se
	 * intra in folderul dinaintea entitatii dorite si se apeleaza metoda
	 * "checkSingleEntity" care verifica daca se poate accesa entitatea si ii
	 * listeaza continutul.
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		try {
			String[] parts = param.split("/");
			CommandFactory commandFactory = new CommandFactory();
			Command command;
			Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder().clone();

			if (param.endsWith("/") && param.lastIndexOf("/") != 0) {
				param = param.substring(0, param.length() - 1);
			}

			if (param.equals("/")) {
				myfs.root.getAssignedFolder().listEntity();
				return;
			} else {

				if (myfs.isRelative(param)) {

					if (!param.contains("/")) {
						if (!this.checkSingleEntity(myfs, param, commandName,
								tempCurrentFolder)) {
							return;
						}
					} else {

						command = commandFactory.getCommands("Cd");
						command.execute(
								param.substring(0, param.lastIndexOf("/")),
								myfs, commandName);

						if (myfs.isCheckIfCdIsWorking()) {
							this.checkSingleEntity(myfs,
									parts[parts.length - 1], commandName,
									tempCurrentFolder);
							return;

						} else {
							return;
						}
					}
				} else {

					command = commandFactory.getCommands("Cd");
					command.execute(param, myfs, commandName);

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