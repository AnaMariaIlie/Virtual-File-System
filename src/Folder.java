
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa corespunzatoare unui folder, care extinde o entitate si implementeaza
 * interfata Cloneable, pentru a putea fi clonat.
 * 
 * @author ilie Ana-Maria
 *
 */
public class Folder extends Entity implements Cloneable {
	/**
	 * Lista cu entitatile continute de folder.
	 */
	private List<Entity> content = new ArrayList<Entity>();

	/**
	 * Constructorul clasei.
	 * 
	 * @param name
	 *            numele care trebuie dat folderului.
	 * @param owner
	 *            userul detinator.
	 * @param owner_permision
	 *            permisiunea(cifra) a detinatorului.
	 * @param other_permision
	 *            permisiunea(cifra) celorlalti utilizatori.
	 * @param parent
	 *            parintele folderului.
	 */
	public Folder(String name, User owner, Integer owner_permision,
			Integer other_permision, Folder parent) {
		this.name = name;
		this.owner = owner;
		this.ownerPermission = owner_permision;
		this.otherPermission = other_permision;
		this.parent = parent;
		this.type = 'd';
		this.content = new ArrayList<Entity>();
	}

	public Folder(String name) {
		this.name = name;
	}

	public Folder() {
	}

	public void setContent(List<Entity> content) {
		this.content = content;
	}

	public void addContent(Entity entity) {
		content.add(entity);
	}

	public void setParent(Folder parent) {
		this.parent = parent;
	}

	public Folder getParent() {
		return this.parent;
	}

	public List<Entity> getContent() {
		return content;
	}

	/**
	 * Metoda care verifica daca un folder contine un anumit fisier.
	 * 
	 * @param name
	 *            numele fisierului cautat.
	 * @return true daca exista, false altfel.
	 */
	public boolean containsFile(String name) {

		for (Entity en : this.getContent()) {
			if (en.getName().equals(name) && en instanceof File
					&& en.getType() == 'f') {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda care verifica daca un folder contine un alt folder.
	 * 
	 * @param name
	 *            numele folderului cautat.
	 * @return true daca exista, false altfel.
	 */
	public boolean containsFolder(String name) {

		for (Entity en : this.getContent()) {
			if (en.getName().equals(name) && en instanceof Folder) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda care cauta un anumit folder in continutul folderului curent si il
	 * returneaza.
	 * 
	 * @param name
	 *            numele folderului cautat.
	 * @return folderul cautat.
	 */
	public Folder getChildFolder(String name) {

		for (Entity en : this.getContent()) {
			if (en.getName().equals(name) && en instanceof Folder) {
				return (Folder) en;
			}
		}
		return null;
	}

	/**
	 * Metoda care cauta un anumit fisier in continutul folderului curent si il
	 * returneaza.
	 * 
	 * @param name
	 *            numele fisierului cautat.
	 * @return fisierul cautat.
	 */
	public File getChildFile(String name) {

		for (Entity en : this.getContent()) {
			if (en.getName().equals(name) && en instanceof File) {
				return (File) en;
			}
		}
		return null;
	}

	/**
	 * Metoda care cauta o anumita entitate, careia nu i se stie tipul, in
	 * continutul folderului curent si o returneaza.
	 * 
	 * @param name
	 *            numele entitatii cautate.
	 * @return entitatea cautata.
	 */
	public Entity getChild(String name) {

		for (Entity en : this.getContent()) {
			if (en.getName().equals(name)) {
				return en;
			}
		}
		return null;
	}

	/**
	 * Metoda care itereaza prin continutul folderului curent si pentru fiecare
	 * entitate, daca este folder si pentru continutul lui, modifica actualu
	 * owner.
	 * 
	 * @param owner
	 *            noul user detinator.
	 */
	public void iterate(User owner) {

		this.setOwner(owner);
		for (int i = 0; i < this.getContent().size(); i++) {

			if (this.getContent().get(i) instanceof Folder) {
				this.getContent().get(i).setOwner(owner);
				((Folder) this.getContent().get(i)).iterate(owner);
			} else {
				((File) this.getContent().get(i)).setOwner(owner);
			}
		}
	}

	/**
	 * Metoda de afisare a carateristicilor entitatilor detinute de un folder.
	 */
	public void listEntity() {

		for (Entity entity : this.getContent()) {
			System.out.println(entity.getName() + " " + entity.getType()
					+ mapPermission.get(entity.getOwnerPermission())
					+ mapPermission.get(entity.getOtherPermission()) + " "
					+ entity.getOwner().getName());
		}
	}

	/**
	 * Metoda de afisare a ierarhiei de entitati.
	 * 
	 * @param tab
	 *            nivelul de indentare. Va fi 0 la inceput, iar apoi cate un tab
	 *            relativ fata de parintele nodului curent.
	 * @param entity
	 *            entitatea de la care va incepe indentarea, in cazul de fata din
	 *            directorul "/".
	 */
	public void showHierarchy(int tab, Entity entity) {
		
		for (int i = 0; i < tab; i++){
			System.out.print("\t");
		}
		
		System.out.println(entity.getName() + " " + entity.getType()
				+ mapPermission.get(entity.getOwnerPermission())
				+ mapPermission.get(entity.getOtherPermission()) + " "
				+ entity.getOwner().getName());
		if (entity instanceof Folder) {
			List<Entity> entities = ((Folder) entity).getContent();
			for (int i = 0; i < entities.size(); i++)
				showHierarchy(tab + 1, entities.get(i));
		}
	}

	/**
	 * Metoda de clonare, din interfata Cloneable.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
}
