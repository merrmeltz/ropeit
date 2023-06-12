import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import sun.util.calendar.LocalGregorianCalendar.Date;


public class PinReplayScreen extends ReplayScreen{

	private Shot lastShot;
	private double time;	

	/**
	 * This is the constructor for the PinReplayScreen.
	 * It takes a CenterScreen that corresponds to the
	 * GameScreen that the user was just looking at
	 * before accessing this replay screen.
	 * @param game
	 */
	public PinReplayScreen(CenterScreen game){
		gameScreen = (GameScreen) game;
		PinScreen pinScreen = (PinScreen)game;
		lastShot = pinScreen.getLastShot();
		if (pinScreen.getLastShot() != null){
			time = 0.0;
			Timer shottimer = new Timer(10, new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent evt) {
					time += 0.010;
					repaint();
					System.out.println(time);
					if (time >= lastShot.getMaxTime()){
						((Timer)evt.getSource()).stop();
					}
				}

			});


			shottimer.start();
		}

		gameScreen = (GameScreen) game;
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
				getScreens().get("pinReplay").setVisible(false);
				CenterScreen pinScreen = getScreens().get("Pin");
				setCenter(pinScreen);

			}
		});
		add(backButton, BorderLayout.NORTH);
	}


	@Override
	public void displayCourse() {
		final JLabel distanceUnits = new JLabel();
		final JLabel speedUnits = new JLabel();
		final JLabel distanceToPinUnits = new JLabel();
		final JLabel distance = new JLabel();
		final JLabel speed = new JLabel();
		final JLabel distanceToPin = new JLabel();
		Font unitFont = new Font("Consolas", Font.BOLD, 12);
		Font statFont = new Font("Consolas", Font.PLAIN, 24);
		JPanel pictureLabel = new JPanel(){
			public void paintComponent(Graphics g){
				if (lastShot != null){
					lastShot = gameScreen.getLastShot();
					
					super.paintComponent(g);
					Image back = new ImageIcon(getClass().getResource("images/background.png")).getImage();
					g.drawImage(back, 0, 0, this.getWidth(), this.getHeight(), this);

					Image course = new ImageIcon(getClass().getResource("images/GolfCourse2(iso).png")).getImage();
					g.drawImage(course, 100, 200, 2*(this.getWidth()/3), this.getHeight()/2, this);
					Image pin = new ImageIcon(getClass().getResource("images/Flag.png")).getImage();
					Image cluster = new ImageIcon(getClass().getResource("images/PinCluster.png")).getImage();
					g.drawImage(cluster,clusterX,clusterY, 175, 171, this);
					Image golfBall = new ImageIcon(getClass().getResource(ballColorFile)).getImage();
					
					//cluster units and numbers
					if(englishUnits == true){
						distanceUnits.setText("yards");
						distanceUnits.setLocation((int) (distanceUnitsX-distanceUnits.getPreferredSize().getWidth()/2),(int)(distanceUnitsY-distanceUnits.getPreferredSize().getHeight()/2));
						speedUnits.setText("mph");
						speedUnits.setLocation((int)(speedUnitsX-speedUnits.getPreferredSize().getWidth()/2),(int)(speedUnitsY-speedUnits.getPreferredSize().getHeight()/2));
						distanceToPinUnits.setText("yards");
						distanceToPinUnits.setLocation((int)(distanceToPinUnitsX-distanceToPinUnits.getPreferredSize().getWidth()/2),(int)(distanceToPinUnitsY-distanceToPinUnits.getPreferredSize().getHeight()/2));
						distanceNum = lastShot.getRoundEDistance();
						speedNum = lastShot.getRoundESpeed();
						distanceToPinNum = lastShot.getRoundEDistanceToPin();
						launchAngleNum = lastShot.getRoundLaunchAngle();
						distance.setText(String.valueOf(distanceNum));
						distance.setLocation((int)(distanceX-distance.getPreferredSize().getWidth()/2),(int)(distanceY-distance.getPreferredSize().getHeight()/2));
						speed.setText(String.valueOf(speedNum));
						speed.setLocation((int)(speedX-speed.getPreferredSize().getWidth()/2), (int)(speedY-speed.getPreferredSize().getHeight()/2));
						distanceToPin.setText(String.valueOf(distanceToPinNum));
						distanceToPin.setLocation((int)(distanceToPinX-distanceToPin.getPreferredSize().getWidth()/2),(int)(distanceToPinY-distanceToPin.getPreferredSize().getHeight()/2));
					}
					
					else if(englishUnits == false){
						distanceUnits.setText("meters");
						distanceUnits.setLocation((int) (distanceUnitsX-distanceUnits.getPreferredSize().getWidth()/2),(int)(distanceUnitsY-distanceUnits.getPreferredSize().getHeight()/2));
						speedUnits.setText("kph");
						speedUnits.setLocation((int)(speedUnitsX-speedUnits.getPreferredSize().getWidth()/2),(int)(speedUnitsY-speedUnits.getPreferredSize().getHeight()/2));
						distanceToPinUnits.setText("meters");
						distanceToPinUnits.setLocation((int)(distanceToPinUnitsX-distanceToPinUnits.getPreferredSize().getWidth()/2),(int)(distanceToPinUnitsY-distanceToPinUnits.getPreferredSize().getHeight()/2));
						distanceNum = lastShot.getRoundMDistance();
						speedNum = lastShot.getRoundMSpeed();
						distanceToPinNum = lastShot.getRoundMDistanceToPin();
						launchAngleNum = lastShot.getRoundLaunchAngle();
						distance.setText(String.valueOf(distanceNum));
						distance.setLocation((int)(distanceX-distance.getPreferredSize().getWidth()/2),(int)(distanceY-distance.getPreferredSize().getHeight()/2));
						speed.setText(String.valueOf(speedNum));
						speed.setLocation((int)(speedX-speed.getPreferredSize().getWidth()/2), (int)(speedY-speed.getPreferredSize().getHeight()/2));
						distanceToPin.setText(String.valueOf(distanceToPinNum));
						distanceToPin.setLocation((int)(distanceToPinX-distanceToPin.getPreferredSize().getWidth()/2),(int)(distanceToPinY-distanceToPin.getPreferredSize().getHeight()/2));
					}
					
					courseLength = distanceOfPin + 1.5*distanceOfPin/30;
					double adjustCourseLength = courseLength*35/50;
					g.drawImage(pin, (int)((Math.cos(45*Math.PI/180)*0.875*(distanceOfPin/adjustCourseLength)*(2*this.getWidth()/3))+100), (int)(200 + (0.8*this.getHeight()/2)-((Math.sin(45*Math.PI/180)*(distanceOfPin /adjustCourseLength)*0.8*(this.getHeight()/2)))), (int)(this.getWidth()/40),(int)(this.getHeight()/40),this);
					double xcoord = lastShot.getXZCoordinates(time)[0];
					double zcoord = lastShot.getXZCoordinates(time)[1];
					g.drawImage(golfBall, (int)((Math.cos(45*Math.PI/180)*0.875*(xcoord/adjustCourseLength)*(2*this.getWidth()/3))+100), (int)(200 + (0.8*this.getHeight()/2)-((Math.sin(45*Math.PI/180)*(xcoord /adjustCourseLength)*0.8*(this.getHeight()/2))+ zcoord*30)), (int)(this.getWidth()/40-xcoord/5+zcoord/5),(int)(this.getHeight()/40+zcoord/5-xcoord/5),this);

				}
				distanceOfPin = CenterScreen.getDistanceOfPin();
				courseLength = CenterScreen.getCourseLength();
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
		
		distanceToPinUnits.setFont(unitFont);
		distanceToPinUnits.setForeground(Color.RED);
		distanceToPinUnits.setSize(100,300);
		distanceToPinUnits.setVisible(true);
		
		distance.setFont(statFont);
		distance.setForeground(Color.BLUE);
		distance.setSize(100,300);
		distance.setVisible(true);
		
		speed.setFont(statFont);
		speed.setForeground(Color.blue);
		speed.setSize(100, 300);
		speed.setVisible(true);
		
		distanceToPin.setFont(statFont);
		distanceToPin.setForeground(Color.blue);
		distanceToPin.setSize(100, 300);
		distanceToPin.setVisible(true);
		
		pictureLabel.setLayout(null);
		pictureLabel.add(distanceUnits);
		pictureLabel.add(speedUnits);
		pictureLabel.add(distanceToPinUnits);
		pictureLabel.add(distance);
		pictureLabel.add(speed);
		pictureLabel.add(distanceToPin);
		pictureLabel.revalidate();
		
		add(pictureLabel, BorderLayout.CENTER);


	}

	/**
	 * This method is called whenever the PinReplayScreen needs to be updated
	 * and subsequently redisplayed.
	 */
	public void redisplay(){
		removeAll();
		PinScreen pinScreen = (PinScreen)gameScreen;
		lastShot = pinScreen.getLastShot();
		if (pinScreen.getLastShot() != null){
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
		if (pinScreen.getLastShot() != null){
			displayCourse();
		}
		displayCourse();
		ImageIcon back = new ImageIcon(getClass().getResource("images/Back.png")); 
		backButton = new JButton(back);
		backButton.setPreferredSize(new Dimension(1000,40));
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getScreens().get("pinReplay").setVisible(false);
				CenterScreen pinScreen = getScreens().get("Pin");
				setCenter(pinScreen);

			}
		});
		add(backButton, BorderLayout.NORTH);

	}

}
