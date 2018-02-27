/**
 * Clasa corespunzatoare comenzii "cd".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class Cd implements Command {

	/**
	 * <p>Metoda care verifica cele 3 conditii pentru a putea citi accesa un folder
	 * si care il acceseaza. De ficare daca cand se verifica un nume din
	 * vectorul de nume dat ca parametru se verifica si daca s-a ajuns la
	 * finalul listei sau nu,fara erori. Daca da, se seteaza directorul curent
	 * din sistem la cel dorit, altfel se afiseaza eroarea si se revine la
	 * directorul din care am dat comanda.
	 * 
	 * @param myfs
	 *            sistemul de fisiere.
	 * @param tempCurrentFolder
	 *            clona	folderului initial.
	 * @param folderNames
	 *            vectorul de string-uri ce contine numele entitatilor.
	 * @param commandName
	 *            numele comenzii.
	 */
	public void changeFolderHelper(MyFileSystem myfs, Folder tempCurrentFolder,
			String[] folderNames, String commandName) {

		for (int i = 0; i < folderNames.length; i++) {

			if (folderNames[i].equals(".")) {
				if (i == folderNames.length - 1) {
					myfs.setCurrentFolder(tempCurrentFolder);
				} else {

				}
			} else if (folderNames[i].equals("..")) {
				if (tempCurrentFolder.getName().equals("/")) {
					if (i == (folderNames.length - 1)) {
						myfs.setCurrentFolder(tempCurrentFolder);
					} else {

					}
				} else {
					if (i == (folderNames.length - 1)) {
						if (tempCurrentFolder.getParent() != null) {
							myfs.setCurrentFolder(
									tempCurrentFolder.getParent());
						} else {
							System.out.println("-2: " + commandName
									+ ": No such directory");
							myfs.setCheckIfCdIsWorking(false);
						}
					} else {
						if (tempCurrentFolder.getParent() != null) {
							tempCurrentFolder = tempCurrentFolder.getParent();
						} else {
							System.out.println("-2: " + commandName
									+ ": No such directory");
							myfs.setCheckIfCdIsWorking(false);
							return;
						}
					}
				}
			} else if (tempCurrentFolder.containsFolder(folderNames[i])) {
				if (!tempCurrentFolder.getChildFolder(folderNames[i])
						.checkRights(myfs.getCurrentUser(), 1)) {
					System.out.println(
							"-6: " + commandName + ": No rights to execute");
					myfs.setCheckIfCdIsWorking(false);
				} else {
					if (i == (folderNames.length - 1)) {
						myfs.setCurrentFolder(tempCurrentFolder
								.getChildFolder(folderNames[i]));
					} else {
						tempCurrentFolder = tempCurrentFolder
								.getChildFolder(folderNames[i]);
					}
				}
			} else {
				if (tempCurrentFolder.containsFile(folderNames[i])) {
					System.out.println(
							"-3: " + commandName + ": Not a directory");
					myfs.setCheckIfCdIsWorking(false);
					;
				} else {
					System.out.println(
							"-2: " + commandName + ": No such directory");
					myfs.setCheckIfCdIsWorking(false);
				}
			}
		}

	}

	/**
	 * <p>
	 * Metoda care executa comanda "cd".
	 * <p>
	 * Cu ajutorul acestei comezi se ajunge intr-un anume folder din sistem.
	 * <p>
	 * Inainte de a incepe se seteaza variabila care determina daca s-a efectuat
	 * cu succes comanda "cd" si se face o clona a folderului curent pentru a
	 * reveni in el in caz vom intampina erori.
	 * <p>
	 * Daca parametrul primit este "/" atunci ne mutam direct directorul asignat
	 * lui root.
	 * <p>
	 * Daca parametrul reprezinta o cale relativa se separa numele folderelor
	 * dupa caracterul '/' si se apeleaza metoda "changeFolderHelper". Vom lucra
	 * pe clona directorului curent.
	 * <p>
	 * Daca parametrul primit reprezinta o cale absoluta, vom lucra pe clona
	 * directorului curent, iar pe acest folder ca incepe verificarea din
	 * directorul "/".
	 * 
	 */
	@Override
	public void execute(String param, MyFileSystem myfs, String commandName) {

		myfs.setCheckIfCdIsWorking(true);
		if (param.equals("/")) {

			myfs.setCurrentFolder(myfs.root.getAssignedFolder());
			myfs.setCheckIfCdIsWorking(true);
			return;
		} else {

			if (myfs.isRelative(param)) {
				try {
					Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder()
							.clone();
					String[] folderNames = param.split("/");

					this.changeFolderHelper(myfs, tempCurrentFolder,
							folderNames, commandName);

				} catch (CloneNotSupportedException e) {
					System.out.println("Folder Cast exception : " + e);
				}
			} else {
				try {
					Folder tempCurrentFolder = (Folder) myfs.getCurrentFolder()
							.clone();
					String[] folderNames = param.substring(1).split("/");

					while (tempCurrentFolder.getParent() != null) {
						tempCurrentFolder = tempCurrentFolder.getParent();
					}

					this.changeFolderHelper(myfs, tempCurrentFolder,
							folderNames, commandName);

				} catch (CloneNotSupportedException e) {
					System.out.println("Folder Cast exception : " + e);
				}

			}

		}

	}

}
