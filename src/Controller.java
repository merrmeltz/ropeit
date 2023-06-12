import java.util.ArrayList;
import java.util.Collection;


public class Controller {
	
	private Collection<Session> archive;
	
	/**
	 * Adds a session to the application's archive.
	 * @param session
	 */
	void archiveSession(Session session){
		archive.add(session);
	}
	
	/**
	 * Used to access the archive of all Sessions in the application's
	 * archive.
	 * @return the archive, a Collection of Sessions
	 */
	Collection<Session> getArchive(){
		return archive;
	}
	
	/**
	 * Runs the application.
	 * @param args
	 */
	public static void main(String[] args){
		Controller ctrlr = new Controller();
		ctrlr.archive = new ArrayList<Session>();
		RopeItProGUI ropeIt = new RopeItProGUI();
		ropeIt.createAndShowGUI(ctrlr);
		
	}

}
