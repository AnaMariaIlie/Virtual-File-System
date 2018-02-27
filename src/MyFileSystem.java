
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa ce reprezinta un sistem virtual de fisiere.
 * 
 * @author Ilie Ana-Maria
 *
 */
public class MyFileSystem {
	/**
	 * Lista de utilizatori din sistem.
	 */
	private List<User> users = new ArrayList<User>();
	/**
	 * Utilizatorul "root".
	 */
	public User root;
	/**
	 * Utilizatorul curent.
	 */
	private User currentUser;
	/**
	 * Folderul curent.
	 */
	public Folder currentFolder;
	/**
	 * Variabila booleana cu ajutorul careia se verifica daca comanda "cd" a
	 * functionat cu succes sau nu.
	 */
	private boolean checkIfCdIsWorking;

	/**
	 * Constructorul clasei. Atunci cand cream un sistem nou de fisiere adaugam
	 * userul "root" si initializam folderul lui.
	 */
	public MyFileSystem() {
		root = new User("root");
		currentUser = new User("root");
		users.add(root);
		currentFolder = new Folder();
		checkIfCdIsWorking = true;
	}
	/**
	 * Metoda care adauga un user in lista de useri din sistem.
	 * @param user userul dorit.
	 */
	public void addUser(User user) {
		users.add(user);
	}

	public User getRoot() {
		return root;
	}

	public void setRoot(User root) {
		this.root = root;
	}

	public Folder getCurrentFolder() {
		return currentFolder;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setCurrentFolder(Folder currentFolder) {
		this.currentFolder = currentFolder;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isCheckIfCdIsWorking() {
		return checkIfCdIsWorking;
	}

	public void setCheckIfCdIsWorking(boolean checkIfCdIsWorking) {
		this.checkIfCdIsWorking = checkIfCdIsWorking;
	}

	/**
	 * Metoda care verifica daca o cale primita ca parametru este relativa sau
	 * nu. Se verifica primul caracter, daca este '/' atunci este relativa,
	 * altfel nu.
	 * 
	 * @param param
	 *            calea primita.
	 * @return true daca este relativa, false altfel.
	 */
	public boolean isRelative(String param) {

		if (param.startsWith("/")) {
			return false;
		}
		return true;
	}

	/**
	 * Metoda care verifica daca un utilizator exista sau nu in sistem. Se
	 * parcurge lista de useri si se cauta numele primit ca parametru.
	 * 
	 * @param name
	 *            numele userului cautat.
	 * @return true daca a fost gasit, false altfel.
	 */
	public boolean containUser(String name) {

		for (User user : this.getUsers()) {
			if (user.getName().equals(name)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Metoda care returneaza un anumit utilizator.
	 * 
	 * @param name
	 *            numele utilizatorului cautat.
	 * @return userul sau null daca nu a fost gasit.
	 */
	public User getUser(String name) {

		for (User user : this.getUsers()) {
			if (user.getName().equals(name)) {
				return user;
			}
		}

		return null;
	}

}
