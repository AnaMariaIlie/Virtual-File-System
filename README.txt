

						  README

	Pentru rezolvarea acestei teme am folosit design patter-urile Composite Pattern
si Factory Pattern.
	Composite Pattern este folosit pentru a crea ierarhia de entitati. Am creat
o clasa "Entitate" mostenita de clasele "Folder" si "File". La nivelul folderului
exista o lista de entitati continute, cu ajutorul acesteia mentinandu-se ideea de
ierarhie.
	Factory Pattern este folosit pentru a crea comenzile de care este nevoie. Ca
si in laboratorul 4 si in tutorialele recomandate, am creat o interfata "Command"
pentru toate comenzile, ea continand metoda "execute" suprascrisa de toate clasele.
	Fiecare comanda citita din fisier este prelucrata si in functie de ce utilitate
se doreste, se apeleaza metoda din "fabrica" care creeaza o instanta a acelei comenzi.
	Pe langa aceste clase mai exista clasa "User" pentru un utilizator si clasa
"MyFileSystem"(System si FileSystem existau deja in Java) si clasa de test.
 In clasa "MyFileSystem" exista o lista cu utilizatorii din sistem, utilizatorul 
root si cel curent , folderul curent( cel in care se lucreaza) si o variabila
 booleana cu ajutorul careia se verifica daca comanda "cd" s-a efectuat cu succes.
	Primele comenzi: "adduser", "chuser" si "deluser" nu depind de o cale si nici
de comanda "cd". Pentru celelalte, care depind de o cale, m-am ajutat de comanda "cd"
pentru a ajunge la capatul caii si acolo am efectuat modificarile cerute.

