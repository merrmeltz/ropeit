
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public abstract class GameScreen extends CenterScreen {
	
	protected JPanel graphPanel;
	protected JButton replayButton;
	protected JButton shotSimulationButton;
	protected Collection<Shot> temp = new ArrayList<Shot>();
	protected JPanel statModule;
	protected Shot lastShot;
	protected JScrollPane scroller;
	protected int shotCounter = 0;
	
	protected double distanceNum;
	protected double speedNum;
	protected double distanceToPinNum;
	protected double launchAngleNum;
	protected double maxHeightNum;
	protected int clusterX = 565;
	protected int clusterY = 397;
	protected int distanceUnitsX = 671;
	protected int distanceUnitsY = 335;
	protected int speedUnitsX = 620;
	protected int speedUnitsY = 390;
	protected int distanceToPinUnitsX = 690;
	protected int distanceToPinUnitsY = 400;
	protected int distanceX = 671;
	protected int distanceY = 315;
	protected int speedX = 615;
	protected int speedY = 375;
	protected int distanceToPinX = 692;
	protected int distanceToPinY = 388;
	
	/**
	 * This method will be implemented in subclasses to display the graphs.
	 */
	public abstract void displayGraphs();
	
	/**
	 * This method will be implemented in subclasses to display the course.
	 */
	public abstract void displayCourse();
	
	
	
	/**
	 * 
	 * @return The temporary collection of Shots taken
	 * thus far in this game session.
	 */
	public Collection<Shot> getSessionShots(){
		return temp;
	}
	
	/**
	 * 
	 * @return  The last shot taken in the game.
	 */
	public Shot getLastShot(){
		return lastShot;

	}
	
	/**
	 * Used to get the JScrollPane used to implement
	 * the scrolling graphs on the GameScreens.
	 * @return the GameScreen's JScrollPane
	 */
	public JScrollPane getScrollPane(){
		return scroller;
	}

	

}
