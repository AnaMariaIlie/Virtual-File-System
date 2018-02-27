/**
 * Clasa corespunzatoare comenzii "adduser".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class AddUser implements Command {
	/**
	 * <p>
	 * Metoda care adauga un utilizator in sistem.
	 * <p>
	 * Verific daca utilizatorul care adauga este "root" sau daca userul exista
	 * deja. Daca sunt indeplinite conditiile, creez noul user si folderul
	 * asignat lui, apoi adaug folderul asignat in lista folderului "/", iar userul
	 * in lista de useri din sistem.
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		User newUser = new User(param);

		if (!myfs.getCurrentUser().equals(myfs.root)) {
			System.out.println("-10: " + commandName
					+ ": No rights to change user status");
		} else {

			if (myfs.getUsers().contains(newUser)) {
				System.out.println(
						"-9: " + commandName + ": User already exists");
			} else {

				Folder newFolder = new Folder(param, newUser, 7, 0,
						myfs.root.getAssignedFolder());
				newUser.setAssignedFolder(newFolder);
				myfs.root.getAssignedFolder().addContent(newFolder);
				myfs.addUser(newUser);

			}
		}

	}

}
