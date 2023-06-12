/*
 * The TrophyRoomScreen class accesses the Wall of Fame in the RopeItPro Application, creating new trophies
 * (AKA ribbons) that the user has achieved.
 */

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class TrophyRoomScreen extends CenterScreen { 
	private static final ArrayList<Trophy> allTrophies = createTrophies();
	private ImageIcon back = new ImageIcon(getClass().getResource("images/backToArchivesmall.png"));

	/**
	 * Initial trophy button display on the TrophyRoomScreen. Sets ribbon image as trophy and
	 * adds the back button to the screen.
	 */
	public TrophyRoomScreen(){
		setLayout(new GridBagLayout());
		for (Trophy trophy : allTrophies){
			if (trophy.isObtained()){
				JButton button = makeButton(trophy);
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				button.setVerticalTextPosition(SwingConstants.BOTTOM);
				button.setHorizontalTextPosition(SwingConstants.CENTER);
				add(button);

			}
		}

		backButton = new JButton(back);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				/*
				 * Switches back and forth between TrophyRoomScreen and the ArchiveScreen based on user action
				 */
				getScreens().get("TrophyRoom").setVisible(false);
				CenterScreen ArchiveScreen = getScreens().get("Archive");
				setCenter(ArchiveScreen);

			}
		});
		add(backButton);
	}

	/**
	 * Method makeButton(trophy) calls for every button create a popup (TrophyPopup.java)
	 * that returns the information about the achievement as well as the ability the share 
	 * that achievement on Facebook.
	 * @param trophy
	 */
	private JButton makeButton(final Trophy trophy) {
		JButton button = new JButton(trophy.getName(),trophy.getImage());
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				trophy.getPopup().setVisible(true);
			}
		});
		return button;
	}

	/**
	 * Method createTrophies() contains the text associated with trophies earned and if they are automatically
	 * displayed on the TrophyRoomScreen or not.
	 * 
	 * pseudo: Trophy(trophy name, " ", trophy description, shot distance required, boolean onScreenOrNot)
	 */
	public static ArrayList<Trophy> createTrophies(){
		ArrayList<Trophy> trophies = new ArrayList<Trophy>();
		trophies.add(new Trophy("New User"," ","Great! You've now used the RopeIt Pro Application.",0,true));
		trophies.add(new Trophy("Tutorial"," ","Congratulations! You completed the Tutorial.",0,true));
		trophies.add(new Trophy("Sharing"," ","Awesome! Now your friends can see your progress.",0,false));
		trophies.add(new Trophy("Driving Range"," ","You have used the driving range!",0,false));
		trophies.add(new Trophy("Pin", " ", "You have used pin mode!",0,false));
		trophies.add(new Trophy("50 Yard Drive"," " , "Wow! You hit over 50 yards in the driving range!",50,false));
		trophies.add(new Trophy("Awesome Accuracy"," " , "Wow! You got within 10 yards of the pin!",10,false));

		return trophies;
	}

	/**
	 * Accesses the trophy arrayList from createTrophies()
	 */
	public ArrayList<Trophy> getTrophies(){
		return allTrophies;
	}

	/**
	 * Redisplays trophies after you've exited the Wall of Fame and earn other trophies
	 */
	public void redisplay(){
		removeAll();
		for (Trophy trophy : allTrophies){
			if (trophy.isObtained()){
				JButton button = makeButton(trophy);
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				button.setVerticalTextPosition(SwingConstants.BOTTOM);
				button.setHorizontalTextPosition(SwingConstants.CENTER);
				add(button);

			}

		}

		backButton = new JButton(back);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getScreens().get("TrophyRoom").setVisible(false);
				CenterScreen ArchiveScreen = getScreens().get("Archive");
				setCenter(ArchiveScreen);

			}
		});
		add(backButton);

	}




}








