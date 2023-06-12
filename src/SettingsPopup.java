import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SettingsPopup extends JDialog {

	private JFrame frame;
	private int englishUnitsNum;
	private static boolean englishUnitsBoolean = CenterScreen.getEnglishUnits();
	private int ballColor;	// 0-white, 1-blue, 2-red, 3-green
	private static String ballColorFile = CenterScreen.getBallColorFile();
	private static double distanceOfPinNum = CenterScreen.getDistanceOfPin();
	
	public SettingsPopup(JFrame f){
		System.out.println(englishUnitsBoolean);
		this.frame = f;
		setModal(true);
		createSettingsPopup();
		setLocation(frame.getWidth()/2,frame.getHeight()/3);
		setTitle("Settings");
		setBackground(null);
		setUndecorated(true);
		
	}	
	
	public void createSettingsPopup(){
		SpringLayout layout = new SpringLayout();
		JPanel settingsPanel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Image background = new ImageIcon(getClass().getResource("images/Square Icon.png")).getImage();
				g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		
		
				settingsPanel.setLayout(layout);
				settingsPanel.setBackground(null);
				
		
				JLabel unitsLabel = new JLabel("Units:");
				
				JRadioButton english = new JRadioButton("English");
				english.addItemListener(new ItemListener(){
					@Override
					public void itemStateChanged(ItemEvent e){
						setEnglishUnitsNum(0);
					}
				});
				
				
				JRadioButton metric = new JRadioButton("Metric");
				metric.addItemListener(new ItemListener(){
					@Override
					public void itemStateChanged(ItemEvent e){
						setEnglishUnitsNum(1);
					}
				});
				
				ButtonGroup unitGroup = new ButtonGroup();
				unitGroup.add(english);
				unitGroup.add(metric);
				
				JLabel ballColorLabel = new JLabel("Ball Color:");
				
				JRadioButton white = new JRadioButton("White",false);
				white.addItemListener(new ItemListener(){
		
					@Override
					public void itemStateChanged(ItemEvent e) {
						setBallColor(0);
					}
					
				});
						
				JRadioButton blue = new JRadioButton("Blue");
				blue.addItemListener(new ItemListener(){
		
					@Override
					public void itemStateChanged(ItemEvent e) {
						setBallColor(1);
					}
					
				});
				
				JRadioButton red = new JRadioButton("Red");
				red.addItemListener(new ItemListener(){
		
					@Override
					public void itemStateChanged(ItemEvent e) {
						setBallColor(2);
					}
					
				});
				
				JRadioButton green = new JRadioButton("Green");
				green.addItemListener(new ItemListener(){
					@Override
					public void itemStateChanged(ItemEvent e) {
						setBallColor(3);
					}
					
				});
				
				JRadioButton yellow = new JRadioButton("Yellow");
				yellow.addItemListener(new ItemListener(){
					@Override
					public void itemStateChanged(ItemEvent e){
						setBallColor(4);
					}
				});
				
				ButtonGroup ballColorGroup = new ButtonGroup();
				ballColorGroup.add(white);
				ballColorGroup.add(blue);
				ballColorGroup.add(red);
				ballColorGroup.add(green);
				ballColorGroup.add(yellow);
				
				JLabel distanceOfPinLabel = new JLabel("Distance of Pin:");
				final JSpinner distanceOfPin = new JSpinner(new SpinnerNumberModel(distanceOfPinNum, 10, 100, 5));
				distanceOfPin.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e){
						distanceOfPinNum = (Double) distanceOfPin.getValue();
					}
				});
				
				
				JButton updateSettings = new JButton("Update Settings");
				updateSettings.addActionListener(new ActionListener(){
		
					@Override
					public void actionPerformed(ActionEvent e) {
						setEnglishUnitsState();
						CenterScreen.setEnglishUnits(getEnglishUnitsState());
						setBallColorString();
						CenterScreen.setBallColorFile(getBallColorFile());
						CenterScreen.setDistanceOfPin(distanceOfPinNum);
						CenterScreen.setSettings(getEnglishUnitsState(), getBallColorFile(), distanceOfPinNum);
						dispose();	
					}
					
				});
				
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						dispose();
					}
				});
				
				if(englishUnitsBoolean == true){
					english.setSelected(true);
				}
				
				if(englishUnitsBoolean == false){
					metric.setSelected(true);
				}
				
				if(ballColorFile == "images/GolfBall.png"){
					white.setSelected(true);
				}
				
				if(ballColorFile == "images/GolfBallBlue.png"){
					blue.setSelected(true);
				}
				
				if(ballColorFile == "images/GolfBallRed.png"){
					red.setSelected(true);
				}
				
				if(ballColorFile == "images/GolfBallGreen.png"){
					green.setSelected(true);
				}
				
				if(ballColorFile == "images/YellowBall.png"){
					yellow.setSelected(true);
				}
				
				settingsPanel.add(unitsLabel);
				layout.putConstraint(SpringLayout.WEST, unitsLabel, 25, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.NORTH, unitsLabel, 25, SpringLayout.NORTH, this);
				settingsPanel.add(english);
				layout.putConstraint(SpringLayout.WEST, english, 42, SpringLayout.EAST, unitsLabel);
				layout.putConstraint(SpringLayout.NORTH, english, 25, SpringLayout.NORTH, this);
				settingsPanel.add(metric);
				layout.putConstraint(SpringLayout.WEST, metric, 42, SpringLayout.EAST, unitsLabel);
				layout.putConstraint(SpringLayout.NORTH, metric, 5, SpringLayout.SOUTH, english);
				
				settingsPanel.add(ballColorLabel);
				layout.putConstraint(SpringLayout.WEST, ballColorLabel, 25, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.NORTH, ballColorLabel, 25, SpringLayout.SOUTH, metric);
				settingsPanel.add(white);
				layout.putConstraint(SpringLayout.WEST, white, 15, SpringLayout.EAST, ballColorLabel);
				layout.putConstraint(SpringLayout.NORTH,white, 25, SpringLayout.SOUTH, metric);
				settingsPanel.add(blue);
				layout.putConstraint(SpringLayout.WEST,  blue,  15, SpringLayout.EAST, ballColorLabel);
				layout.putConstraint(SpringLayout.NORTH, blue, 5, SpringLayout.SOUTH, white);
				settingsPanel.add(red);
				layout.putConstraint(SpringLayout.WEST,  red,  15, SpringLayout.EAST, ballColorLabel);
				layout.putConstraint(SpringLayout.NORTH, red, 5, SpringLayout.SOUTH, blue);
				settingsPanel.add(green);
				layout.putConstraint(SpringLayout.WEST,  green,  15, SpringLayout.EAST, ballColorLabel);
				layout.putConstraint(SpringLayout.NORTH, green, 5, SpringLayout.SOUTH, red);
				settingsPanel.add(yellow);
				layout.putConstraint(SpringLayout.WEST, yellow, 15, SpringLayout.EAST, ballColorLabel);
				layout.putConstraint(SpringLayout.NORTH, yellow, 5, SpringLayout.SOUTH, green);
				
				settingsPanel.add(distanceOfPinLabel);
				layout.putConstraint(SpringLayout.WEST, distanceOfPinLabel, 25, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.NORTH, distanceOfPinLabel, 25, SpringLayout.SOUTH, yellow);
				settingsPanel.add(distanceOfPin);
				layout.putConstraint(SpringLayout.WEST, distanceOfPin, 15, SpringLayout.EAST, distanceOfPinLabel);
				layout.putConstraint(SpringLayout.NORTH, distanceOfPin, 25, SpringLayout.SOUTH, yellow);
				
				
				settingsPanel.add(updateSettings);
				layout.putConstraint(SpringLayout.WEST, updateSettings, 55, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.NORTH, updateSettings, 25, SpringLayout.SOUTH, distanceOfPinLabel);
				settingsPanel.add(cancel);
				layout.putConstraint(SpringLayout.WEST, cancel, 80, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.NORTH, cancel, 5, SpringLayout.SOUTH,  updateSettings);
				
				add(settingsPanel);
	}
	
	public void setEnglishUnitsNum(int i){
		englishUnitsNum = i;
	}
	
	public void setEnglishUnitsState(){
		switch(englishUnitsNum){
		case 0: englishUnitsBoolean = true;
			break;
		case 1: englishUnitsBoolean = false;
			break;
		}
	}

	public boolean getEnglishUnitsState(){
		return englishUnitsBoolean;
	}
	
	public void setBallColor(int i){
		ballColor = i;
	}
	
	public void setBallColorString(){
		switch (ballColor){
			case 0: ballColorFile = "images/GolfBall.png";
				break;
			case 1: ballColorFile = "images/GolfBallBlue.png";
				break;
			case 2: ballColorFile = "images/GolfBallRed.png";
				break;
			case 3: ballColorFile = "images/GolfBallGreen.png";
				break;
			case 4: ballColorFile = "images/YellowBall.png";
				break;
		}
	}
	
	public static String getBallColorFile(){
		return ballColorFile;
	}
	
	public void setBallColorFile(String s){
		this.ballColorFile = s;
	}
	
	
}
