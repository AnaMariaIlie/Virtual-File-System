/**
 * Clasa corespunzatoare unui utilizator din sistem.
 * 
 * @author Ilie Ana-Maria.
 *
 */
public class User {
	/**
	 * Numele utilizatorului.
	 */
	private String name;
	/**
	 * Folderul asignat utilizatorului la creare.
	 */
	private Folder assignedFolder;

	/**
	 * Constructorul clasei.
	 * 
	 * @param name
	 *            numele utilizatorului.
	 */
	public User(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Folder getAssignedFolder() {
		return assignedFolder;
	}

	public void setAssignedFolder(Folder assignedFolder) {
		this.assignedFolder = assignedFolder;
	}

	@Override
	public boolean equals(Object obj) {

		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
