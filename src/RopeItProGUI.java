import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class RopeItProGUI extends JPanel{ 
	
	private static Controller ctrlr;
	private static CenterScreen center;
	private static MenuBar mBar;
	private static Club currentClubType;
	private static ArrayList<Club> clubList;
	static JFrame frame;
	static int enteredGame = 0;
	static int enteredReplay = 0;

	
	
	public RopeItProGUI(){ 
		setOpaque(true);
		setLayout(new BorderLayout());
		HomeScreen home = new HomeScreen(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Image back = new ImageIcon(getClass().getResource("images/background.png")).getImage();
				g.drawImage(back, 0, 0, this.getWidth(), this.getHeight(), this);

			}
		};
		center = home;
		Hashtable<String,CenterScreen> screens = center.getScreens();
		mBar = new MenuBar(){
			 public void paintComponent(Graphics g){
				  g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/Toolbar.png")),0,0,this);
				  g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/ropeit_logo.png")),431,5,this);
			 }
		};
		add(mBar,BorderLayout.NORTH);
		center = screens.get("Home");
		add(center);
		createClubList();
		
	}

	/**
	 * Called in the controller. Used to actually create and display
	 * the GUI (actually creates the JFrame for the app).
	 * This method also handles the saving of data upon closing the app.
	 * @param controller
	 */
	public void createAndShowGUI(Controller controller){
		ctrlr = controller;       
		frame = new JFrame("RopeIt Pro");
		frame.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
	            	
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					FileOutputStream fos = new FileOutputStream("SaveData.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					for (Session session : ctrlr.getArchive()){
						oos.writeObject(session);
					}
					oos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				ObjectInputStream ois;
				try {
					FileInputStream fis = new FileInputStream("SaveData.ser");
					ois = new ObjectInputStream(fis);
					Session first = (Session)ois.readObject();
					ctrlr.archiveSession(first);
					while (first != null){
						Session session = (Session) ois.readObject();
						ctrlr.archiveSession(session);
					}
		            ois.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
				
					
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
			}
			
		});
		
        frame.setBackground(Color.WHITE);
        frame.add(this);		
        frame.pack();
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
	}
	
	

	/**
	 * Returns a particular center screen given a String name
	 * @return the CenterScreen corresponding to the given key
	 */
	public static CenterScreen getCenter(String key){
		return center.getScreens().get(key);
	}
	
	/**
	 * Used to access the app's Controller.
	 * @return the Controller
	 */
	public static Controller getController(){
		return ctrlr;
	}


	/**
	 * Used to find the current club type,
	 * as shown on the menu bar's drop-down menu.
	 * @return The current club type.
	 */
	public static Club getCurrentClubType() {
		return currentClubType;
	}

	/**
	 * Used to change the club type to the given club.
	 * @param club
	 */
	public static void setCurrentClubType(Club club) {
		currentClubType = club;
	}
	
	/**
	 * 
	 * @return The app's MenuBar.
	 */
	public static MenuBar getMenuBar(){
		return mBar;
	}
	
	/**
	 * Used to create the list of clubs for the MenuBar's drop-down
	 * menu. 
	 */
	public void createClubList(){
		clubList = new ArrayList<Club>();
		clubList.add(new Club("Rescue Club",new Color(150,124,85)));
		clubList.add(new Club("5 Iron",new Color(255,128,0)));
		clubList.add(new Club("6 Iron",new Color(115,30,143)));
		clubList.add(new Club("7 Iron",new Color(255,204,51)));
		clubList.add(new Club("8 Iron",new Color(0,127,255)));
		clubList.add(new Club("9 Iron",new Color(50, 177, 65)));
		clubList.add(new Club("Pitch Wedge",new Color(54,117,136)));
		clubList.add(new Club("Sand Wedge",new Color(200,8,21)));
	}


	/**
	 * 
	 * @return The list of all clubs to be shown in the menu bar's
	 * choose club drop-down menu.
	 */
	public static ArrayList<Club> getClubList() {
		return clubList;
	}
	
	
}
