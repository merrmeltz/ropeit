import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class ArchiveScreen extends CenterScreen {

	private JButton TrophyRoomButton;
	private JButton OverallStats;
	private JButton clearData;
	private JPanel entries;
	private JScrollPane scroller;
	private ArrayList<JButton> SessionButtons;

	/**
	 * This is the constructor for the ArchiveScreen.
	 */
	public ArchiveScreen(){
		setLayout(new BorderLayout());
		entries = new JPanel();
		ImageIcon ostat = new ImageIcon(getClass().getResource("images/ostatbutton.png"));
		ImageIcon tbut = new ImageIcon(getClass().getResource("/images/wafbutton.png"));
		ImageIcon cdata = new ImageIcon(getClass().getResource("images/cleardata.png"));

		OverallStats = new JButton(ostat);
		OverallStats.setOpaque(false);
		OverallStats.setContentAreaFilled(false);
		OverallStats.setBorderPainted(false);

		TrophyRoomButton = new JButton(tbut);
		TrophyRoomButton.setOpaque(false);
		TrophyRoomButton.setContentAreaFilled(false);
		TrophyRoomButton.setBorderPainted(false);

		clearData = new JButton(cdata);
		clearData.setOpaque(false);
		clearData.setContentAreaFilled(false);
		clearData.setBorderPainted(false);
		clearData.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				RopeItProGUI.getController().getArchive().clear();
				redisplay();
				CenterScreen.getCurrent().setCenter(RopeItProGUI.getCenter("Archive"));
			}
			
		});
		
		
		TrophyRoomButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getScreens().get("Archive").setVisible(false);
				CenterScreen trophyScreen = getScreens().get("TrophyRoom");
				setCenter(trophyScreen);
			}
		});

		OverallStats.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getScreens().get("Archive").setVisible(false);
				CenterScreen overallScreen = getScreens().get("OverallGraph");
				setCenter(overallScreen);
				((OverallGraphs)overallScreen).redisplay();
			}
		});

		add(clearData, BorderLayout.LINE_END);
		add(TrophyRoomButton,BorderLayout.PAGE_END);
		add(OverallStats,BorderLayout.PAGE_START);

		displaySessionDataEntries();

	}


	/**
	 * Called to update the ArchiveScreen when the user
	 * returns to the ArchiveScreen so that the screen
	 * displays any changes in the data.
	 */
	public void redisplay(){
		for (JButton button : SessionButtons){
			entries.remove(button);
		}
		remove(scroller);
		displaySessionDataEntries();
	}

	/**
	 * Displays the buttons corresponding to each
	 * session in the application's archive.
	 */
	public void displaySessionDataEntries(){
		Controller ctrl = RopeItProGUI.getController();
		SessionButtons = new ArrayList<JButton>();
		entries.setLayout(new BoxLayout(entries,BoxLayout.PAGE_AXIS));
		if (ctrl != null){
			Collection<Session> archive = ctrl.getArchive();
			for (Session session : archive){
				JButton sessionButton = makeButton(session);
				SessionButtons.add(sessionButton);	
			}
		}
		for (JButton button : SessionButtons){
			entries.add(button);
		}

		entries.setPreferredSize(new Dimension(250,5000));
		scroller = new JScrollPane(entries);
		scroller.setPreferredSize(new Dimension(250,300));
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scroller,BorderLayout.CENTER);

	}

	/**
	 * A private method called in displaySessionDataEntries
	 * that is used to make the buttons for individual sessions.
	 * @param session
	 * @return
	 */
	private JButton makeButton(final Session session){
		Shot first = session.getFirstShot();
		ImageIcon icon = new ImageIcon();
		String date = first.getDate();
		if (first.getMode()){
			icon = new ImageIcon(getClass().getResource("images/DRArchive.png"));
		}
		else {
			icon = new ImageIcon(getClass().getResource("images/PinArchive.png"));
		}

		JButton button = new JButton();
		button.setIcon(icon);
		button.setText(date);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);

		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent evt) {
				getScreens().get("Archive").setVisible(false);
				SessionGraphs sessionGraph = new SessionGraphs(session);
				setCenter(sessionGraph);
			}
		});
		
		return button;
	}
}
