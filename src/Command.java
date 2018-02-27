/**
 * Interfata pe care o va implementa fiecare comanda.
 * 
 * @author Ilie Ana-Maria
 *
 */
public interface Command {
	/**
	 * 
	 * @param param
	 *            parametrul comenzii.
	 * @param myfs
	 *            sistem de fisiere.
	 * @param commandName
	 *            numele comenzii.
	 */
	public void execute(String param, MyFileSystem myfs, String commandName);
}
