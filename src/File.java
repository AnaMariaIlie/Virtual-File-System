/**
 * Clasa corespunzatoare unui fisier, care extinde o entitate.
 * 
 * @author Ilie Ana-Maria
 *
 */
public class File extends Entity {
	/**
	 * Textul fisierului.
	 */
	private String content;

	/**
	 * Constructorul clasei.
	 * 
	 * @param name
	 *            numele care trebuie dat fisierului.
	 * @param owner
	 *            userul detinator.
	 * @param owner_permision
	 *            permisiunea(cifra) a detinatorului.
	 * @param other_permision
	 *            permisiunea(cifra) celorlalti utilizatori.
	 * @param parent
	 *            parintele fisierului.
	 */
	public File(String name, User owner, Integer owner_permision,
			Integer other_permision, Folder parent) {
		super();
		this.name = name;
		this.owner = owner;
		this.ownerPermission = owner_permision;
		this.otherPermission = other_permision;
		this.type = 'f';
	}

	public File() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Folder getParent() {
		return this.parent;
	}

	public void setParent(Folder parent) {
		this.parent = parent;
	}

	/**
	 * Metoda de afisare a carateristicilor unui fisier.
	 */
	public void listEntity() {
		System.out.println(this.getName() + " " + this.getType()
				+ mapPermission.get(this.getOwnerPermission())
				+ mapPermission.get(this.getOtherPermission()) + " "
				+ this.getOwner().getName());
	}

}
