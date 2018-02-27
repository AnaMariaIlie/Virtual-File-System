/**
 * Clasa corespunzatoare "fabricii de comenzi".
 * 
 * @author Ilie Ana-Maria
 *
 */
public class CommandFactory {
	/**
	 * 
	 * @param commandType
	 *            numele comenzii.
	 * @return comanda corespunzatoare numelui primit.
	 */
	public Command getCommands(String commandType) {

		if (commandType == null) {
			return null;
		}
		if (commandType.equalsIgnoreCase("Mkdir")) {
			return new Mkdir();

		}

		if (commandType.equalsIgnoreCase("AddUser")) {
			return new AddUser();
		}

		if (commandType.equalsIgnoreCase("ChangeUser")) {
			return new ChangeUser();
		}

		if (commandType.equalsIgnoreCase("DelUser")) {
			return new DelUser();
		}

		if (commandType.equalsIgnoreCase("Cd")) {
			return new Cd();
		}

		if (commandType.equalsIgnoreCase("Ls")) {
			return new Ls();
		}

		if (commandType.equalsIgnoreCase("Chmod")) {
			return new Chmod();
		}

		if (commandType.equalsIgnoreCase("Touch")) {
			return new Touch();
		}

		if (commandType.equalsIgnoreCase("WriteToFile")) {
			return new WriteToFile();
		}

		if (commandType.equalsIgnoreCase("Cat")) {
			return new Cat();
		}

		if (commandType.equalsIgnoreCase("Rmdir")) {
			return new Rmdir();
		}

		if (commandType.equalsIgnoreCase("Rm")) {
			return new Rm();
		}

		if (commandType.equalsIgnoreCase("RmRecursive")) {
			return new RmRecursive();
		}
		return null;
	}

}
