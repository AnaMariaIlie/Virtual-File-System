
import java.util.HashMap;
import java.util.Map;

/**
 * Clasa parinte pentru folder si fisier.
 * 
 * @author Ana
 *
 */
public class Entity {
	/**
	 * Numele entitatii.
	 */
	protected String name;
	/**
	 * Tipul, care poate fi "f" pentru fisiere sau "d" pentru directoare.
	 */
	protected char type;
	/**
	 * Permisiunea detinatorului entitatii.
	 */
	protected Integer ownerPermission;
	/**
	 * Permisiunea celoralti utilizatori.
	 */
	protected Integer otherPermission;
	/**
	 * Userul detinator.
	 */
	protected User owner;
	/**
	 * Folderul parinte.
	 */
	protected Folder parent;

	/**
	 * HashMap pentru permisiuni, folosit la afisare. Pentru fiecare permisiune
	 * de tip Integer se retine string-ul corespunzator.
	 */
	@SuppressWarnings("serial")
	public static final Map<Integer, String> mapPermission = new HashMap<Integer, String>() {
		{
			put(0, "---");
			put(1, "--x");
			put(2, "-w-");
			put(3, "-wx");
			put(4, "r--");
			put(5, "r-x");
			put(6, "rw-");
			put(7, "rwx");

		}
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public Integer getOwnerPermission() {
		return ownerPermission;
	}

	public void setOwnerPermission(Integer ownerPermision) {
		this.ownerPermission = ownerPermision;
	}

	public Integer getOtherPermission() {
		return otherPermission;
	}

	public void setOtherPermission(Integer otherPermision) {
		this.otherPermission = otherPermision;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Folder getParent() {
		return this.parent;
	}

	/**
	 * Metoda de afisare a caracteristicilor entitatii. Un utilizator poate
	 * accesa o entitate daca este root sau este detinator si are dreptul
	 * respectiv sau daca nu este detinator si are dreptul respectiv.
	 */
	public void listEntity() {

	}

	/**
	 * Metoda de verificare a drepturilor, pentru acces.
	 * 
	 * @param user
	 *            userul pentru care se verifica drepturile.
	 * @param right
	 *            cifra corespunzatoare, de la 0 pana la 7.
	 * @return true, daca userul are acces, false altfel.
	 */
	public boolean checkRights(User user, int right) {

		if (user.getName().equals("root")) {
			return true;
		} else {

			if (user.getName().equals(this.getOwner().getName())
					&& ((this.getOwnerPermission() & right) != 0)) {
				return true;
			} else {

				if (!user.getName().equals(this.getOwner().getName())
						&& ((this.getOtherPermission() & right) != 0)) {
					return true;
				}
			}
		}

		return false;

	}

}
