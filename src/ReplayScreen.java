import java.util.Iterator;
import javax.swing.JButton;


public abstract class ReplayScreen extends CenterScreen {

	protected JButton backButton;
	protected GameScreen gameScreen;
	
	
	protected double distanceNum;
	protected double speedNum;
	protected double distanceToPinNum;
	protected double launchAngleNum;
	protected double maxHeightNum;
	protected int leftShift = 250;
	protected int clusterX = 565 + leftShift;
	protected int clusterY = 397;
	protected int distanceUnitsX = 671+ leftShift;
	protected int distanceUnitsY = 335;
	protected int speedUnitsX = 620+ leftShift;
	protected int speedUnitsY = 390;
	protected int distanceToPinUnitsX = 690+ leftShift;
	protected int distanceToPinUnitsY = 400;
	protected int distanceX = 671+ leftShift;
	protected int distanceY = 315;
	protected int speedX = 615+ leftShift;
	protected int speedY = 375;
	protected int distanceToPinX = 692+ leftShift;
	protected int distanceToPinY = 388;

	/**
	 * Used to get the last shot that was taken in a gameScreen.
	 * @return the last shot taken by the user
	 */
	public Shot getLastShot(){

		Iterator<Shot> iterShot = gameScreen.getSessionShots().iterator();
		Shot lastShot = null;
		while (iterShot.hasNext()){
			lastShot = iterShot.next();
		}
		return lastShot;

	}

	/**
	 * This method will be implemented to display the course 
	 * from a different angle in the replay screen.
	 */
	public abstract void displayCourse();

	}
