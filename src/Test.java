
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clasa de testare.
 * 
 * @author Ilie Ana-Maria
 *
 */
public class Test {
	/**
	 * <p>
	 * Aici fiecare comanda este citita din fisier, prelucrata si in functie de
	 * parametri este luata din "fabrica de comenzi" si executata.
	 * <p>
	 * La final se apeleaza metoda de afisare a arborelui pentru folderul "/" ,
	 * incepand cu nivelul 0 de indentare.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		MyFileSystem myfs = new MyFileSystem();
		Command command;

		BufferedReader br;
		br = new BufferedReader(new FileReader(args[0]));
		String firstLine = br.readLine();

		CommandFactory commandFactory = new CommandFactory();
		String[] parts = firstLine.split(" ");

		command = commandFactory.getCommands("Mkdir");
		command.execute(parts[1], myfs, firstLine);

		while ((firstLine = br.readLine()) != null) {

			parts = firstLine.split(" ");
			if (parts[0].equals("mkdir")) {
				command = commandFactory.getCommands("Mkdir");
				command.execute(parts[1], myfs, firstLine);
			}

			if (parts[0].equals("adduser")) {
				command = commandFactory.getCommands("AddUser");
				command.execute(parts[1], myfs, firstLine);
			}

			if (parts[0].equals("chuser")) {
				command = commandFactory.getCommands("ChangeUser");
				command.execute(parts[1], myfs, firstLine);
			}

			if (parts[0].equals("deluser")) {
				command = commandFactory.getCommands("DelUser");
				command.execute(parts[1], myfs, firstLine);
			}

			if (parts[0].equals("cd")) {

				command = commandFactory.getCommands("Cd");
				command.execute(parts[1], myfs, firstLine);
			}

			if (parts[0].equals("ls")) {
				command = commandFactory.getCommands("Ls");
				command.execute(parts[1], myfs, firstLine);
			}

			if (parts[0].equals("chmod")) {
				command = commandFactory.getCommands("Chmod");
				command.execute(firstLine.substring(6), myfs, firstLine);
			}

			if (parts[0].equals("touch")) {
				command = commandFactory.getCommands("Touch");
				command.execute(parts[1], myfs, firstLine);
			}

			if (parts[0].equals("writetofile")) {
				command = commandFactory.getCommands("WriteToFile");
				command.execute(firstLine.substring(12), myfs, firstLine);
			}

			if (parts[0].equals("cat")) {
				command = commandFactory.getCommands("Cat");
				command.execute(parts[1], myfs, firstLine);
			}

			if (parts[0].equals("rmdir")) {
				command = commandFactory.getCommands("Rmdir");
				command.execute(parts[1], myfs, firstLine);
			}

			if (parts[0].equals("rm")) {
				command = commandFactory.getCommands("Rm");
				command.execute(firstLine.substring(3), myfs, firstLine);
			}
		}
		myfs.getRoot().getAssignedFolder().showHierarchy(0,
				myfs.getRoot().getAssignedFolder());

		br.close();
	}
}
