/**
 * Clasa corespunzatoare comenzii "deluser".
 * 
 * @author Ilie Ana-Maria.
 *
 */
public class DelUser implements Command {
	/**
	 * <p>
	 * Metoda care sterge un utilizator din sistem. De asemenea, modifica si
	 * detinatorul entitatilor detinute de acest utilizator cu primul utilizator
	 * adaugat cu ajutorul comenzii "adduser", acesta fiind al doilea din lista
	 * de useri din sistem.
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		User oldUser = myfs.getUser(param);

		if (!myfs.getCurrentUser().equals(myfs.root)) {
			System.out.println("-10: " + commandName
					+ ": No rights to change user status");
		} else {
			if (!myfs.getUsers().contains(oldUser)) {
				System.out.println(
						"-8: " + commandName + ": User does not exist");
			} else {

				myfs.root.getAssignedFolder().getChildFolder(param)
						.iterate(myfs.getUsers().get(1));
				myfs.getUsers().remove(oldUser);

			}
		}
	}

}
