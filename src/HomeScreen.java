import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;


public class HomeScreen extends CenterScreen{
	private JButton pinButton;
	private JButton drivingRangeButton;
	private JButton progressButton;

	/**
	 * Constructor for the HomeScreen.
	 * Sets the three buttons in the center of the home screen 
	 * sets the images, background and action listeners 
	 */
	public HomeScreen(){

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		final ImageIcon notificationimg = new ImageIcon(getClass().getResource("images/newnotification.png"));
		
		ImageIcon pin = new ImageIcon(getClass().getResource("images/PinButton.png")); 
		pinButton = new JButton(pin);
		pinButton.setOpaque(false);
		pinButton.setContentAreaFilled(false);
		pinButton.setBorderPainted(false);


		ImageIcon iron = new ImageIcon(getClass().getResource("images/DrivingRangeButton.png"));
		drivingRangeButton = new JButton(iron);
		drivingRangeButton.setOpaque(false);
		drivingRangeButton.setContentAreaFilled(false);
		drivingRangeButton.setBorderPainted(false);

		ImageIcon progress = new ImageIcon(getClass().getResource("images/ProgressButton.png"));
		progressButton = new JButton(progress);
		progressButton.setOpaque(false);
		progressButton.setContentAreaFilled(false);
		progressButton.setBorderPainted(false);

		
		
		pinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getScreens().get("Home").setVisible(false);
				CenterScreen pinScreen = getScreens().get("Pin");
				setCenter(pinScreen);
				PinScreen actualPinScreen = (PinScreen)pinScreen;
				actualPinScreen.shotCounter = 0; // reset shot counter to zero
				actualPinScreen.getScrollPane().getHorizontalScrollBar().setValue(0);
				RopeItProGUI.enteredGame +=1;
				

			}
		});

		drivingRangeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getScreens().get("Home").setVisible(false);
				CenterScreen drivingRange = getScreens().get("DrivingRange");
				setCenter(drivingRange);
				DrivingRangeScreen drivingRangeScreen = (DrivingRangeScreen)drivingRange;
				drivingRangeScreen.shotCounter = 0; // reset the shot counter to 0 for a new session
				drivingRangeScreen.getScrollPane().getHorizontalScrollBar().setValue(0);
				RopeItProGUI.enteredGame +=1;



			}
		});

		progressButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				CenterScreen tRoom = CenterScreen.getCurrent().getScreens().get("TrophyRoom");
				TrophyRoomScreen trophyRoom = (TrophyRoomScreen)tRoom;
				if (!trophyRoom.getTrophies().get(1).isObtained()){
					trophyRoom.getTrophies().get(1).setObtained(true);
					final JDialog notification = new JDialog();
					notification.add(new JLabel(notificationimg));
					notification.setUndecorated(true);
					notification.setAlwaysOnTop(true);
					notification.setSize(244,208);
					notification.setLocationRelativeTo(RopeItProGUI.frame);
					notification.setLocationRelativeTo(RopeItProGUI.frame);
					notification.setVisible(true);
					Timer timer = new Timer(5000,new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							notification.setVisible(false);
							notification.invalidate();
						}

					});
					timer.setRepeats(false);
					timer.start();
					trophyRoom.redisplay();
				}
				getScreens().get("Home").setVisible(false);
				CenterScreen archiveScreen = getScreens().get("Archive");
				setCenter(archiveScreen);
				((ArchiveScreen) archiveScreen).redisplay();

			}

		});

		add(Box.createHorizontalStrut(170)); 
		add(pinButton);
		add(Box.createHorizontalStrut(50)); 
		add(drivingRangeButton);
		add(Box.createHorizontalStrut(50)); 
		add(progressButton);


	}



	
}
