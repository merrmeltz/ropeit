import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public abstract class CenterScreen extends JPanel{
	private final static Hashtable<String, CenterScreen> screens = makeScreens();
	private static CenterScreen current;
	protected JButton backButton;
	protected static String ballColorFile = "images/GolfBall.png";
	protected static boolean englishUnits = true;
	protected static double distanceOfPin = 30;
	protected static double courseLength = distanceOfPin + 1.5;
	protected GameScreen gameScreen;
	
	/**
	 * makes the hash table of all the center screens we use in the game
	 * so we don't need to make new screens every time we move through the app
	 * @return Hashtable of CenterScreens 
	 */
	public static Hashtable<String,CenterScreen> makeScreens(){
		Hashtable<String,CenterScreen> screens1 = new Hashtable<String, CenterScreen>();
		HomeScreen home = new HomeScreen(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Image back = new ImageIcon(getClass().getResource("images/background.png")).getImage();
				g.drawImage(back, 0, 0, this.getWidth(), this.getHeight(), this);

			}
		};
		screens1.put("Home", home);
		screens1.put("Pin", new PinScreen());
		screens1.put("DrivingRange", new DrivingRangeScreen());
		screens1.put("Archive", new ArchiveScreen());
		TrophyRoomScreen trophyRoom = new TrophyRoomScreen(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Image back = new ImageIcon(getClass().getResource("images/bullitin.png")).getImage();
				g.drawImage(back, 0, 0, this.getWidth(), this.getHeight(), this);

			}
		};
		screens1.put("TrophyRoom", trophyRoom);
		screens1.put("OverallGraph", new OverallGraphs());
//		screens1.put("SessionGraph", new SessionGraphs());
		screens1.put("pinReplay", new PinReplayScreen(screens1.get("Pin")));
		screens1.put("DRReplay", new DrivingReplayScreen(screens1.get("DrivingRange")));
		return screens1;
	}
	
	/**
	 * returns the hashtable of centerscreens
	 * @return
	 */
	public Hashtable<String, CenterScreen> getScreens() {
		return screens;
	}

	/**
	 * returns the screen currently used in the center of the Border layout
	 * @return CenterScreen
	 */
	public static CenterScreen getCurrent(){ 
		if (current == null){
			current = screens.get("Home");
		}
		return current; 
		}
	
	/**
	 * sets the center of the Border Screen to a new Center Screen 
	 * @param screen
	 */
	public void setCenter(CenterScreen screen){
		screen.setVisible(true);
		RopeItProGUI.frame.getContentPane().removeAll();
		RopeItProGUI.frame.getContentPane().add(RopeItProGUI.getMenuBar(),BorderLayout.NORTH);
		RopeItProGUI.frame.getContentPane().add(screen);
		RopeItProGUI.frame.getContentPane().validate();
		current = screen;	
	}
	/**
	 * sets the color of the ball  
	 * @param fileName
	 */
	public static void setBallColorFile(String fileName){
		ballColorFile = fileName;
	}
	
	/**
	 * gets the current color of this ball
	 * @return ballColorFile
	 */
	public static String getBallColorFile(){
		return ballColorFile;
	}
	
	/**
	 * Used to see what units need to be used 
	 * true is english units and false is metric units
	 * @param b
	 */
	public static void setEnglishUnits(boolean b){
		englishUnits = b;
	}
	
	/**
	 * 
	 * @return englishUnits
	 */
	public static boolean getEnglishUnits(){
		System.out.println(englishUnits);
		return englishUnits;
	}
	
	/**
	 * gets the distance of pin that is set from the settings menu
	 * @return
	 */
	public static double getDistanceOfPin(){
		return distanceOfPin;
	}
	
	/**
	 * sets the distance of the pin
	 * set from the settings menu
	 * @param distance
	 */
	public static void setDistanceOfPin(double distance){
		distanceOfPin = distance;
	}
	
	/**
	 * returns the length of the course
	 * @return courseLength
	 */
	public static double getCourseLength(){
		return courseLength;
	}
	
	/**
	 * Sets the settings to the given parameters.
	 * @param engUnits
	 * @param ballFile
	 * @param distOfPin
	 */
	public static void setSettings(boolean engUnits, String ballFile, double distOfPin){
		englishUnits = engUnits;
		ballColorFile = ballFile;
		distanceOfPin = distOfPin;
		
	}
	
}
