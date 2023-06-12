import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.Box;
import javax.swing.JRadioButtonMenuItem;


public class MenuBar extends JMenuBar{

	private JMenu menu;
	private JButton settingsButton;
	private JButton tutorialButton;
	private JButton homeButton;
	private TutorialPopup tutorialPopup; 
	private String current;
	
	Color a = new Color(150,124,85);
	Color b = new Color(255,128,0);
	Color c = new Color(115,30,143);
	Color d = new Color(255,204,51);
	Color e = new Color(0,127,255);
	Color f = new Color(50, 177, 65);
	Color g = new Color(54,117,136);
	Color h = new Color(200,8,21);

	/**
	 * Used to get the tutorial's popup.
	 * @return the tutorialPopup
	 */
	public TutorialPopup getTutorial(){
		return tutorialPopup;
	}
	
	/**
	 * Menu bar constructor 
	 * creates the buttons and menu and places them evenly across the bar
	 */
	public MenuBar(){
		setPreferredSize(new Dimension(1000,60));
		createButton();

		add(Box.createHorizontalStrut(50)); // Fixed width invisible separator.
		add(homeButton);

		add(Box.createHorizontalStrut(100)); // Fixed width invisible separator.
		JMenu menu = createMenu("Choose Club");
		add(menu);


		add(Box.createHorizontalGlue());

		add(tutorialButton);
		add(Box.createHorizontalStrut(100)); // Fixed width invisible separator.

		add(settingsButton);
		add(Box.createHorizontalStrut(50)); // Fixed width invisible separator.


	}
	



	/**
	 * sets a new jMenu for the club drop down, 
	 * sets the radio buttons with text and colors for the clubs
	 * @param name
	 * @return JMenu
	 */
	public JMenu createMenu(String name){
		menu = new JMenu(name);
		menu.setOpaque(false);
		
		JRadioButtonMenuItem club = new JRadioButtonMenuItem("Rescue Club", new ImageIcon());
		club.setHorizontalTextPosition(JMenuItem.RIGHT);
		club.setForeground(a);
		club.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				RopeItProGUI.setCurrentClubType(RopeItProGUI.getClubList().get(0));
				current = "Rescue Club";
				menu.setText(current);
			}
		});
		
		JRadioButtonMenuItem iron5 = new JRadioButtonMenuItem("5 Iron", new ImageIcon());
		iron5.setHorizontalTextPosition(JMenuItem.RIGHT);
		iron5.setForeground(b);
		iron5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				RopeItProGUI.setCurrentClubType(RopeItProGUI.getClubList().get(1));
				current = "5 Iron";
				menu.setText(current);
			}
		});

		JRadioButtonMenuItem iron6 = new JRadioButtonMenuItem("6 Iron", new ImageIcon());
		iron6.setHorizontalTextPosition(JMenuItem.RIGHT);
		iron6.setForeground(c);
		iron6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				RopeItProGUI.setCurrentClubType(RopeItProGUI.getClubList().get(2));
				current = "6 Iron";
				menu.setText(current);
			}
		});

		JRadioButtonMenuItem iron7 = new JRadioButtonMenuItem("7 Iron", new ImageIcon());
		iron7.setHorizontalTextPosition(JMenuItem.RIGHT);
		iron7.setForeground(d);
		iron7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				RopeItProGUI.setCurrentClubType(RopeItProGUI.getClubList().get(3));
				current = "7 Iron";
				menu.setText(current);
			}
		});
		JRadioButtonMenuItem iron8 = new JRadioButtonMenuItem("8 Iron", new ImageIcon());
		iron8.setHorizontalTextPosition(JMenuItem.RIGHT);
		iron8.setForeground(e);
		iron8.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				RopeItProGUI.setCurrentClubType(RopeItProGUI.getClubList().get(4));
				current = "8 Iron";
				menu.setText(current);
			}
		});
		JRadioButtonMenuItem iron9 = new JRadioButtonMenuItem("9 Iron", new ImageIcon());
		iron9.setHorizontalTextPosition(JMenuItem.RIGHT);
		iron9.setForeground(f);
		iron9.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				RopeItProGUI.setCurrentClubType(RopeItProGUI.getClubList().get(5));
				current = "9 Iron";
				menu.setText(current);
			}
		});
		
		JRadioButtonMenuItem wedge1 = new JRadioButtonMenuItem("Pitch Wedge", new ImageIcon());
		wedge1.setHorizontalTextPosition(JMenuItem.RIGHT);
		wedge1.setForeground(g);
		wedge1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				RopeItProGUI.setCurrentClubType(RopeItProGUI.getClubList().get(6));
				current = "Pitch Wedge";
				menu.setText(current);
			}
		});
		
		JRadioButtonMenuItem wedge2 = new JRadioButtonMenuItem("Sand Wedge", new ImageIcon());
		wedge2.setHorizontalTextPosition(JMenuItem.RIGHT);
		wedge2.setForeground(h);
		wedge2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				RopeItProGUI.setCurrentClubType(RopeItProGUI.getClubList().get(7));
				current = "Sand Wedge";
				menu.setText(current);
				
			}
		});

		ButtonGroup group = new ButtonGroup(  );
		group.add(club);
		group.add(iron5);
		group.add(iron6);
		group.add(iron7);
		group.add(iron8);
		group.add(iron9);
		group.add(wedge1);
		group.add(wedge2);

		menu.add(club);
		menu.add(iron5);
		menu.add(iron6);
		menu.add(iron7);
		menu.add(iron8);
		menu.add(iron9);
		menu.add(wedge1);
		menu.add(wedge2);
		menu.revalidate();
		
		return menu;

}
	/**
	 * creates new buttons for the menu bar 
	 * sets the images and action listeners 
	 */
	public void createButton(){
		homeButton = new JButton("Home");
		tutorialButton = new JButton("Tutorial");
		settingsButton = new JButton("Settings");

		ImageIcon homeIcon = new ImageIcon(getClass().getResource("images/Home Icon.png"));
		homeButton = new JButton(homeIcon);
		homeButton.setOpaque(false);
		homeButton.setContentAreaFilled(false);
		homeButton.setBorderPainted(false);

		ImageIcon tutorialIcon = new ImageIcon(getClass().getResource("images/Question Icon.png"));
		tutorialButton = new JButton(tutorialIcon);
		tutorialButton.setOpaque(false);
		tutorialButton.setContentAreaFilled(false);
		tutorialButton.setBorderPainted(false);

		ImageIcon settingsIcon = new ImageIcon(getClass().getResource("images/Setting Icon.png"));
		settingsButton = new JButton(settingsIcon);
		settingsButton.setOpaque(false);
		settingsButton.setContentAreaFilled(false);
		settingsButton.setBorderPainted(false);


		homeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				CenterScreen current = CenterScreen.getCurrent();

				if (current instanceof GameScreen){
					Session lastSession = new Session();
					GameScreen gamescreen = (GameScreen)current;
					if (gamescreen.temp.size() > 0){
						lastSession.getSessionShots().addAll(gamescreen.getSessionShots());
						RopeItProGUI.getController().archiveSession(lastSession);
						gamescreen.getSessionShots().clear();
						gamescreen.lastShot = null;
					}
					CenterScreen.getCurrent().setVisible(false);
					CenterScreen home = RopeItProGUI.getCenter("Home");
					CenterScreen.getCurrent().setCenter(home);
				}	
				else if (current instanceof ReplayScreen){
					Session lastSession = new Session();
					ReplayScreen replayscreen = (ReplayScreen)current;
					GameScreen gamescreen = replayscreen.gameScreen;
					if (gamescreen.temp.size() > 0){
						lastSession.getSessionShots().addAll(gamescreen.getSessionShots());
						RopeItProGUI.getController().archiveSession(lastSession);
						gamescreen.getSessionShots().clear();
						gamescreen.lastShot = null;
					}
					CenterScreen.getCurrent().setVisible(false);
					CenterScreen home = RopeItProGUI.getCenter("Home");
					CenterScreen.getCurrent().setCenter(home);
				}

				else {
					current.setVisible(false);
					CenterScreen home = RopeItProGUI.getCenter("Home");
					current.setCenter(home);
				}
			}
		});

		settingsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				SettingsPopup spopup = new SettingsPopup(RopeItProGUI.frame);
				spopup.setSize(new Dimension(250, 400));
				spopup.setResizable(false);
				spopup.setBackground(null);
				revalidate();
				spopup.setLocationRelativeTo(RopeItProGUI.frame);
				spopup.setAlwaysOnTop(true);
				spopup.setVisible(true);
			}
		});

		tutorialButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				tutorialPopup = new TutorialPopup(RopeItProGUI.frame);
				tutorialPopup.setLocationRelativeTo(RopeItProGUI.frame);
				tutorialPopup.setAlwaysOnTop(true);
				tutorialPopup.setVisible(true);
			}
		});	
	}
}
