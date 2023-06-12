import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;


public class TrophyPopup extends JDialog{

	private JButton share;
	private JLabel info;
	private JLabel placeholder;

	/**
	 * This is the constructor for the TrophyPopup.
	 * @param name
	 * @param requirements
	 */
	public TrophyPopup(String name, String requirements){
		JPanel trophyPopupPanel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Image background = new ImageIcon(getClass().getResource("images/trophypopup.png")).getImage();
				g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};

		trophyPopupPanel.setLayout(new BoxLayout(trophyPopupPanel,BoxLayout.Y_AXIS));

		placeholder = new JLabel("	");
		placeholder.setAlignmentX(CENTER_ALIGNMENT);
		placeholder.setAlignmentY(CENTER_ALIGNMENT);

		setName(name);
		info = new JLabel();		
		info.setText(requirements);
		this.setName(name);
		info.setFocusable(false);
		info.setAlignmentX(CENTER_ALIGNMENT);
		info.setAlignmentY(TOP_ALIGNMENT);

		share = new JButton("Share");
		share.setAlignmentX(CENTER_ALIGNMENT);
		share.setAlignmentY(CENTER_ALIGNMENT);
		share.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent evt) {
				share();
			}

		});	
		JButton close = new JButton("Close");
		close.setAlignmentX(CENTER_ALIGNMENT);
		close.setAlignmentY(BOTTOM_ALIGNMENT);
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});

		setUndecorated(true);
		setBounds(20, 50, 400, 110);
		setLocationRelativeTo(RopeItProGUI.frame);

		trophyPopupPanel.add(placeholder); //blank component helps organization
		trophyPopupPanel.add(info);
		trophyPopupPanel.add(share);
		trophyPopupPanel.add(close);
		this.add(trophyPopupPanel);
	}

	/**
	 * This method handles what happens when the user clicks "share" 
	 * on the TrophyPopup. It posts information on the award the user
	 * unlocked to a fake Facebook account.
	 */
	public void share(){
		String message = "";
		message += "I earned an award while playing RopeItPro!";
		message += "\n" + "Description: " + info.getText();
		/*
		 * The DefaultFacebookClient takes an access token string as a parameter. 
		 * The access token needs to be re-obtained after logging out of our
		 * Team8 Facebook account because we aren't a registered Facebook developer.
		 * 
		 * Facebook account name: Jim Bob
		 * Facebook email: cisc275team8@aol.com
		 * Facebook password: ropeit
		 * Website to obtain new access token: https://developers.facebook.com/
		 * --> Then go to tools -> graph explorer -> get access token and
		 * --> Make sure that publish_actions is checked under extended permissions!
		 */
		FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBAEKpZAvTVCbeApMMd1l3ckAvjdYpwiRT5d1EK8B3EKpDZCjIuXgXLgjPzaZBxgukNODaZA0sTN1jWIWb6Aexdig0I3u9Y5AkfcinvilO18iu3ZAZB62xjJYoRMwssn7FK8q7DrHxYWeA1FuTmQCtcY5wXvo8ilOvgaNaooafZCXTK1VQ5G4uGYZD");
		FacebookType publishPhotoResponse = facebookClient.publish("me/photos", FacebookType.class,
				BinaryAttachment.with("images/award_ribbon_blue.png", getClass().getResourceAsStream("images/award_ribbon_blue.png")),
				Parameter.with("message", message));

	}
}