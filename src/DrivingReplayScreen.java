import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class DrivingReplayScreen extends ReplayScreen{
	
	private Shot lastShot;
	private double time;
	private double courseLength;
	
	/**
	 * This is the constructor for the DrivingReplayScreen.
	 * It takes a CenterScreen that corresponds to the
	 * GameScreen that the user was just looking at
	 * before accessing this replay screen.
	 * @param game
	 */
	public DrivingReplayScreen(CenterScreen game){
		gameScreen = (GameScreen) game;
		DrivingRangeScreen drivingRangeScreen = (DrivingRangeScreen)game;
		lastShot = drivingRangeScreen.getLastShot();
		time = 0.0;
		setLayout(new BorderLayout());
		displayCourse();
		
		ImageIcon back = new ImageIcon(getClass().getResource("images/Back.png")); 
		backButton = new JButton(back);
		backButton.setPreferredSize(new Dimension(1000,40));
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getScreens().get("DRReplay").setVisible(false);
				CenterScreen pinScreen = getScreens().get("DrivingRange");
				setCenter(pinScreen);

			}
		});
		add(backButton, BorderLayout.NORTH);
	}


	@Override
	public void displayCourse() {
		final JLabel distanceUnits = new JLabel();
		final JLabel speedUnits = new JLabel();
		final JLabel maxHeightUnits = new JLabel();
		final JLabel distance = new JLabel();
		final JLabel speed = new JLabel();
		final JLabel maxHeight = new JLabel();
		Font unitFont = new Font("Consolas", Font.BOLD, 12);
		Font statFont = new Font("Consolas", Font.PLAIN, 24);
		
		JPanel pictureLabel = new JPanel(){

			public void paintComponent(Graphics g){
				super.paintComponent(g);
				courseLength = 35;
				if (englishUnits == true){
					Image backEnglish = new ImageIcon(getClass().getResource("images/drivingreplaybackEnglish.png")).getImage();
					g.drawImage(backEnglish, 0, 0, this.getWidth(), this.getHeight(), this);
					distanceUnits.setText("yards");
					distanceUnits.setLocation((int) (distanceUnitsX-distanceUnits.getPreferredSize().getWidth()/2),(int)(distanceUnitsY-distanceUnits.getPreferredSize().getHeight()/2));
					speedUnits.setText("mph");
					speedUnits.setLocation((int)(speedUnitsX-speedUnits.getPreferredSize().getWidth()/2),(int)(speedUnitsY-speedUnits.getPreferredSize().getHeight()/2));
					maxHeightUnits.setText("yards");
					maxHeightUnits.setLocation((int)(distanceToPinUnitsX-maxHeightUnits.getPreferredSize().getWidth()/2),(int)(distanceToPinUnitsY-maxHeightUnits.getPreferredSize().getHeight()/2));
					distanceNum = lastShot.getRoundEDistance();
					speedNum = lastShot.getRoundESpeed();
					maxHeightNum = lastShot.getRoundEHeight();
					launchAngleNum = lastShot.getRoundLaunchAngle();
					distance.setText(String.valueOf(distanceNum));
					distance.setLocation((int)(distanceX-distance.getPreferredSize().getWidth()/2),(int)(distanceY-distance.getPreferredSize().getHeight()/2));
					speed.setText(String.valueOf(speedNum));
					speed.setLocation((int)(speedX-speed.getPreferredSize().getWidth()/2), (int)(speedY-speed.getPreferredSize().getHeight()/2));
					maxHeight.setText(String.valueOf(maxHeightNum));
					maxHeight.setLocation((int)(distanceToPinX-maxHeight.getPreferredSize().getWidth()/2),(int)(distanceToPinY-maxHeight.getPreferredSize().getHeight()/2));

				}
				else{
					Image backMetric = new ImageIcon(getClass().getResource("images/drivingreplayBack.png")).getImage();
					g.drawImage(backMetric, 0, 0, this.getWidth(), this.getHeight(), this);
					distanceUnits.setText("meters");
					distanceUnits.setLocation((int) (distanceUnitsX-distanceUnits.getPreferredSize().getWidth()/2),(int)(distanceUnitsY-distanceUnits.getPreferredSize().getHeight()/2));
					speedUnits.setText("kph");
					speedUnits.setLocation((int)(speedUnitsX-speedUnits.getPreferredSize().getWidth()/2),(int)(speedUnitsY-speedUnits.getPreferredSize().getHeight()/2));
					maxHeightUnits.setText("meters");
					maxHeightUnits.setLocation((int)(distanceToPinUnitsX-maxHeightUnits.getPreferredSize().getWidth()/2),(int)(distanceToPinUnitsY-maxHeightUnits.getPreferredSize().getHeight()/2));
					distanceNum = lastShot.getRoundMDistance();
					speedNum = lastShot.getRoundMSpeed();
					maxHeightNum = lastShot.getRoundMHeight();
					launchAngleNum = lastShot.getRoundLaunchAngle();
					distance.setText(String.valueOf(distanceNum));
					distance.setLocation((int)(distanceX-distance.getPreferredSize().getWidth()/2),(int)(distanceY-distance.getPreferredSize().getHeight()/2));
					speed.setText(String.valueOf(speedNum));
					speed.setLocation((int)(speedX-speed.getPreferredSize().getWidth()/2), (int)(speedY-speed.getPreferredSize().getHeight()/2));
					maxHeight.setText(String.valueOf(maxHeightNum));
					maxHeight.setLocation((int)(distanceToPinX-maxHeight.getPreferredSize().getWidth()/2),(int)(distanceToPinY-maxHeight.getPreferredSize().getHeight()/2));

				}
				Image golfBall = new ImageIcon(getClass().getResource(ballColorFile)).getImage();
				Image cluster = new ImageIcon(getClass().getResource("images/DRCluster.png")).getImage();
				g.drawImage(cluster,clusterX,clusterY, 175, 171, this);
				double adjustCourseLength = courseLength*35/50;
				double xcoord = lastShot.getXZCoordinates(time)[0];
				double zcoord = lastShot.getXZCoordinates(time)[1];
				g.drawImage(golfBall, (int)((Math.cos(45*Math.PI/180)*0.875*(xcoord/adjustCourseLength)*(2*this.getWidth()/3))+100), (int)(200 + (0.8*this.getHeight()/2)-((Math.sin(45*Math.PI/180)*(xcoord /adjustCourseLength)*0.8*(this.getHeight()/2))+ zcoord*30)), (int)(this.getWidth()/40-xcoord/5+zcoord/5),(int)(this.getHeight()/40+zcoord/5-xcoord/5),this);

			}
		};
		
		distanceUnits.setFont(unitFont);
		distanceUnits.setForeground(Color.RED);
		distanceUnits.setSize(100,300);
		distanceUnits.setVisible(true);
		
		speedUnits.setFont(unitFont);
		speedUnits.setForeground(Color.RED);
		speedUnits.setSize(100,300);
		speedUnits.setVisible(true);
		
		maxHeightUnits.setFont(unitFont);
		maxHeightUnits.setForeground(Color.RED);
		maxHeightUnits.setSize(100,300);
		maxHeightUnits.setVisible(true);
		
		distance.setFont(statFont);
		distance.setForeground(Color.BLUE);
		distance.setSize(100,300);
		distance.setVisible(true);
		
		speed.setFont(statFont);
		speed.setForeground(Color.blue);
		speed.setSize(100, 300);
		speed.setVisible(true);
		
		maxHeight.setFont(statFont);
		maxHeight.setForeground(Color.blue);
		maxHeight.setSize(100, 300);
		maxHeight.setVisible(true);

		
	
		
		pictureLabel.setLayout(null);
		pictureLabel.add(distanceUnits);
		pictureLabel.add(speedUnits);
		pictureLabel.add(maxHeightUnits);
		pictureLabel.add(distance);
		pictureLabel.add(speed);
		pictureLabel.add(maxHeight);
		
		add(pictureLabel,BorderLayout.CENTER);
	}
	
	/**
	 * This method is called whenever the DrivingReplayScreen needs to be updated
	 * and subsequently redisplayed.
	 */
	public void redisplay(){
		removeAll();
		DrivingRangeScreen DRscreen = (DrivingRangeScreen)gameScreen;
		lastShot = DRscreen.getLastShot();
		if (DRscreen.getLastShot() != null){
			time = 0.0;
			Timer shottimer = new Timer(10, new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent evt) {
					time += 0.010;
					repaint();
					if (time >= lastShot.getMaxTime()){
						((Timer)evt.getSource()).stop();
					}
				}

			});


			shottimer.start();
		}

		setLayout(new BorderLayout());
		if (DRscreen.getLastShot() != null){
			displayCourse();
		}
		ImageIcon back = new ImageIcon(getClass().getResource("images/Back.png")); 
		backButton = new JButton(back);
		backButton.setPreferredSize(new Dimension(1000,40));
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getScreens().get("DRReplay").setVisible(false);
				CenterScreen pinScreen = getScreens().get("DrivingRange");
				setCenter(pinScreen);

			}
		});
		add(backButton, BorderLayout.NORTH);

	}


}
