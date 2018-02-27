/**
 * Clasa corespunzatoare comenzii "chuser".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class ChangeUser implements Command {
	/**
	 *<p> Metoda care schimba utilizatorul curent din sistem, precum si folderul
	 * curent la folderul asignat userului. Daca userul nu exista se afiseaza
	 * eroarea corespunzatoare.
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		if (!myfs.containUser(param)) {
			System.out.println("-8: " + commandName + ": User does not exist");
		} else {
			myfs.setCurrentUser(myfs.getUser(param));
			myfs.setCurrentFolder(myfs.getUser(param).getAssignedFolder());

		}

	}
}