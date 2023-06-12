import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class DrivingRangeScreen extends GameScreen {
	private double time;
	private Club c = null;
	private double distanceOfCourse;
	final ImageIcon notificationimg = new ImageIcon(getClass().getResource("images/newnotification.png"));

	/**
	 * This is the constructor for the DrivingRangeScreen.
	 */
	public DrivingRangeScreen(){
		setLayout(new BorderLayout());
		displayCourse();
		displayGraphs();
		addButtons();
		scroller = new JScrollPane(graphPanel);
		scroller.setPreferredSize(new Dimension(250,300));
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroller,BorderLayout.LINE_END);


	}



	/**
	 * This methods handles adding and implementing listeners for
	 * all buttons in the DrivingRangeScreen.
	 */
	public void addButtons(){
		ImageIcon replay = new ImageIcon(getClass().getResource("images/ReplayButton.png")); 
		replayButton = new JButton(replay);		
		replayButton.setPreferredSize(new Dimension(1000,40));
		replayButton.setOpaque(false);
		replayButton.setContentAreaFilled(false);
		replayButton.setBorderPainted(false);
		time = 0.0;


		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "enter");
		am.put("enter", new AbstractAction() {
			public void actionPerformed(ActionEvent evt) {
				String a = "none";
			}
		});

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0,true), "enter released");
		am.put("enter released", new AbstractAction() {
			public void actionPerformed(ActionEvent evt) {
				Date date = new Date();
				Shot shot = new Shot(true, ""+date.toString()+"",RopeItProGUI.getCurrentClubType(),new Position(400,400));
				shot.setClubType(RopeItProGUI.getCurrentClubType());
				lastShot = shot;
				temp.add(shot);
				shotCounter++;
				repaint();

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

				shottimer.setRepeats(true);
				shottimer.start();

				if (shotCounter >= 10){
					int scrollposition = scroller.getHorizontalScrollBar().getValue();
					scrollposition +=5;
					scroller.getHorizontalScrollBar().setValue(scrollposition);
				}


				/*
				 * This upcoming block of code makes the trophy notification appear.
				 */

				CenterScreen tRoom = CenterScreen.getCurrent().getScreens().get("TrophyRoom");
				TrophyRoomScreen trophyRoom = (TrophyRoomScreen)tRoom;
				if (!trophyRoom.getTrophies().get(3).isObtained()){
					trophyRoom.getTrophies().get(3).setObtained(true);
					final JDialog notification = new JDialog();
					notification.add(new JLabel(notificationimg));
					notification.setUndecorated(true);
					notification.setAlwaysOnTop(true);
					notification.setSize(244,208);
					notification.setLocationRelativeTo(RopeItProGUI.frame);
					notification.setVisible(true);
					Timer timer = new Timer(3000,new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							notification.setVisible(false);
							notification.invalidate();
						}

					});
					timer.setRepeats(false);
					timer.start();
					trophyRoom.redisplay();

					/*
					 * The above block of code makes the trophy notification appear.
					 */
				}

				/*
				 * This block of code makes it so a trophy notification appears for
				 * when a user shoots a ball over 50 yards
				 */
				if (shot.getDistance() >= 50 && !trophyRoom.getTrophies().get(5).isObtained()){
					trophyRoom.getTrophies().get(5).setObtained(true);
					final JDialog notification = new JDialog();
					notification.add(new JLabel(notificationimg));
					notification.setUndecorated(true);
					notification.setAlwaysOnTop(true);
					notification.setSize(244,208);
					notification.setLocationRelativeTo(RopeItProGUI.frame);
					notification.setVisible(true);
					Timer timer = new Timer(3000,new ActionListener(){
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

			}

		});


		replayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				RopeItProGUI.enteredReplay += 1;
				getScreens().get("DrivingRange").setVisible(false);
				CenterScreen drivingReplayScreen = getScreens().get("DRReplay");
				((DrivingReplayScreen)drivingReplayScreen).redisplay();
				setCenter(drivingReplayScreen);
			}
		});
		add(replayButton,BorderLayout.PAGE_END);
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


		final ArrayList<Club> clubList = new ArrayList<Club>();

		JPanel pictureLabel = new JPanel(){
			public void paintComponent(Graphics g){
				distanceOfPin = CenterScreen.getDistanceOfPin();
				courseLength = CenterScreen.getCourseLength();
				super.paintComponent(g);
				if (englishUnits == true){
					Image backEnglish = new ImageIcon(getClass().getResource("images/DrivingRangeBackEnglish.png")).getImage();
					g.drawImage(backEnglish, 0, 0, this.getWidth(), this.getHeight(), this);

				}
				else{
					Image backMetric = new ImageIcon(getClass().getResource("images/DrivingRangeBack.png")).getImage();
					g.drawImage(backMetric, 0, 0, this.getWidth(), this.getHeight(), this);

				}
				Image cluster = new ImageIcon(getClass().getResource("images/DRCluster.png")).getImage();
				g.drawImage(cluster,clusterX,clusterY, 175, 171, this);
				Image tee = new ImageIcon(getClass().getResource("images/GolfTee.png")).getImage();
				g.drawImage(tee, this.getWidth()/2-25, this.getHeight()-50, this.getWidth()/50, this.getHeight()/20, this);


				// draw the ball
				Image golfBall = new ImageIcon(getClass().getResource(ballColorFile)).getImage();
				if (lastShot != null){
					distanceOfCourse = 38;
					int startx = this.getWidth()/2-30;
					double yD = this.getHeight()/5+8;
					double yI = 29*this.getHeight()/30-50;
					double xcoord = lastShot.getXZCoordinates(time)[0];
					double zcoord = lastShot.getXZCoordinates(time)[1];
					double ycoord = ((yD - yI)*(xcoord)/(distanceOfCourse))-(200*zcoord/(Math.sqrt(xcoord) + 1))+yI;
					if ( ((yD - yI)*(xcoord)/(distanceOfCourse))+yI < 100) {
						ycoord = 100 -(200*zcoord/(Math.sqrt(xcoord))) ;
					}
					g.drawImage(golfBall, (int)(startx),(int)(0.98*ycoord), (int)(this.getWidth()/30-xcoord/3+zcoord/3),(int)(this.getHeight()/30-xcoord/3+zcoord/3),this);

					if (RopeItProGUI.getCurrentClubType() != c){
						c = RopeItProGUI.getCurrentClubType();
						int z = 0;
						for (Club club : clubList){
							if (club == c){z +=1;}
						}
						if (z == 0){clubList.add(c);}
						System.out.println(clubList);
					}
					if (clubList.size() != 0){
						g.setColor(Color.white);
						g.fillRect(getWidth()-152, 5, 150, (clubList.size()*15)+25);
						g.setColor(Color.black);
						g.drawRect(getWidth()-152, 5, 150, (clubList.size()*15)+25);
						g.drawString("Club Color Legend: ", getWidth()-150, 20);
						int a = 40;
						for (Club club : clubList){
							g.setColor(club.getColor());
							g.setFont(new Font("Arial", Font.PLAIN, 15));
							g.drawString(club.getName(), getWidth()-150, a);
							a+=15;
						}

					}
					if (RopeItProGUI.enteredReplay == 0){
						Image redArrow = new ImageIcon(getClass().getResource("images/red_down_arrow.png")).getImage();
						g.drawImage(redArrow, 115, getHeight() -70, 28, 60, this);
						g.setColor(Color.black);
						g.setColor(Color.white);
						g.fillRect(148, getHeight()-75, 150, 60);
						g.setColor(Color.black);
						g.drawRect(148, getHeight()-75, 150, 60);
						g.setFont(new Font("Arial", Font.PLAIN, 15));
						g.drawString("Great Shot! ", 180, getHeight()-60);
						g.drawString("to see it from the side", 150, getHeight()-40); 
						g.drawString("click the button below.",150, getHeight()-20);
					}

					if(englishUnits == true){
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

					else if(englishUnits == false){
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
				}


				else{
					//places prompt to take shot when you first enter the screen and choose club
					Image redArrow = new ImageIcon(getClass().getResource("images/red_up_arrow.png")).getImage();
					if (RopeItProGUI.enteredGame == 1){
						g.drawImage(redArrow, 215, 5, 28, 60, this);
						g.setColor(Color.black);
						g.setColor(Color.white);
						g.fillRect(248, 5, 245, 60);
						g.setColor(Color.black);
						g.drawRect(248, 5, 245, 60);
						g.setFont(new Font("Arial", Font.PLAIN, 15));
						g.drawString("You can click 'Choose Club' above ", 250, 20);
						g.drawString("to specify club type for your session!",250, 40);
						g.drawString("You can change club the at any point", 250, 60);
					}
					g.setColor(Color.black);
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("press 'Enter' to take shot", getWidth()/3 -50, getHeight()/2);
					//draw ball on tee
					g.drawImage(golfBall, this.getWidth()/2-30, this.getHeight()-68,this.getWidth()/30,this.getHeight()/30, this);	

					if(englishUnits == true){
						distanceUnits.setText("yards");
						distanceUnits.setLocation((int) (distanceUnitsX-distanceUnits.getPreferredSize().getWidth()/2),(int)(distanceUnitsY-distanceUnits.getPreferredSize().getHeight()/2));
						speedUnits.setText("mph");
						speedUnits.setLocation((int)(speedUnitsX-speedUnits.getPreferredSize().getWidth()/2),(int)(speedUnitsY-speedUnits.getPreferredSize().getHeight()/2));
						maxHeightUnits.setText("yards");
						maxHeightUnits.setLocation((int)(distanceToPinUnitsX-maxHeightUnits.getPreferredSize().getWidth()/2),(int)(distanceToPinUnitsY-maxHeightUnits.getPreferredSize().getHeight()/2));
						distance.setText(String.valueOf(0.0));
						distance.setLocation((int)(distanceX-distance.getPreferredSize().getWidth()/2),(int)(distanceY-distance.getPreferredSize().getHeight()/2));
						speed.setText(String.valueOf(0.0));
						speed.setLocation((int)(speedX-speed.getPreferredSize().getWidth()/2), (int)(speedY-speed.getPreferredSize().getHeight()/2));
						maxHeight.setText(String.valueOf(0.0));
						maxHeight.setLocation((int)(distanceToPinX-maxHeight.getPreferredSize().getWidth()/2),(int)(distanceToPinY-maxHeight.getPreferredSize().getHeight()/2));
					}
					if(englishUnits == false){
						distanceUnits.setText("meters");
						distanceUnits.setLocation((int) (distanceUnitsX-distanceUnits.getPreferredSize().getWidth()/2),(int)(distanceUnitsY-distanceUnits.getPreferredSize().getHeight()/2));
						speedUnits.setText("kph");
						speedUnits.setLocation((int)(speedUnitsX-speedUnits.getPreferredSize().getWidth()/2),(int)(speedUnitsY-speedUnits.getPreferredSize().getHeight()/2));
						maxHeightUnits.setText("meters");
						maxHeightUnits.setLocation((int)(distanceToPinUnitsX-maxHeightUnits.getPreferredSize().getWidth()/2),(int)(distanceToPinUnitsY-maxHeightUnits.getPreferredSize().getHeight()/2));
						distance.setText(String.valueOf(0.0));
						distance.setLocation((int)(distanceX-distance.getPreferredSize().getWidth()/2),(int)(distanceY-distance.getPreferredSize().getHeight()/2));
						speed.setText(String.valueOf(0.0));
						speed.setLocation((int)(speedX-speed.getPreferredSize().getWidth()/2), (int)(speedY-speed.getPreferredSize().getHeight()/2));
						maxHeight.setText(String.valueOf(0.0));
						maxHeight.setLocation((int)(distanceToPinX-maxHeight.getPreferredSize().getWidth()/2),(int)(distanceToPinY-maxHeight.getPreferredSize().getHeight()/2));
					}
				}
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

	@Override
	public void displayGraphs() {
		graphPanel = new JPanel(){

			@Override
			public Dimension getPreferredSize(){
				return new Dimension(2050,300);
			}

			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.BLACK);
				g.drawString("Distance", 100, 20);
				g.drawLine(0, 20, 0, getHeight()/3);
				g.drawLine(getWidth(), getHeight()/3, 0, getHeight()/3);
				g.drawString("Speed", 100, getHeight()/3 + 20);
				g.drawLine(0, getHeight()/3+20, 0, 2*getHeight()/3);
				g.drawLine(getWidth(), 2*getHeight()/3, 0, 2*getHeight()/3);
				g.drawString("Maximum Height", 75, 2*getHeight()/3+20);
				g.drawLine(0, 2*getHeight()/3+20,0,getHeight()-20);
				g.drawLine(getWidth(), getHeight()-20, 0, getHeight()-20);

				Position p_dist = new Position(0,getHeight()/3);
				Position p_speed = new Position(0,2*getHeight()/3);
				Position p_height = new Position(0,getHeight()-20);
				int xindex = 1;
				// plot the points on the graphs
				if (temp != null){
					for (Shot s : temp){
						if (s.getClubType() != null){g.setColor(s.getClubType().getColor());}

						// first two lines plot the distance
						g.drawLine((int)p_dist.getX(), (int)p_dist.getY(), xindex,getHeight()/3-(int) s.getDistance()-5);
						p_dist = new Position(xindex,getHeight()/3-(int) s.getDistance()-5);
						g.drawOval(xindex, getHeight()/3-(int) s.getDistance()-5, 3, 3);
						g.fillOval(xindex, getHeight()/3-(int) s.getDistance()-5, 3, 3);


						// next two lines plot the speed
						g.drawLine((int)p_speed.getX(), (int)p_speed.getY(), xindex,2*getHeight()/3-(int) s.getSpeed()-5);
						p_speed = new Position(xindex,2*getHeight()/3-(int) s.getSpeed()-5);
						g.drawOval(xindex, 2*getHeight()/3-(int) s.getSpeed()-5, 3, 3);
						g.fillOval(xindex, 2*getHeight()/3-(int) s.getSpeed()-5, 3, 3);


						// final two lines plot the maximum height
						g.drawLine((int)p_height.getX(), (int)p_height.getY(), xindex, getHeight()-(int)(s.getMaxHeight())-30);
						p_height = new Position(xindex,getHeight()-(int)(s.getMaxHeight())-30);
						g.drawOval(xindex, getHeight()-(int)(s.getMaxHeight())-30, 3, 3);
						g.fillOval(xindex, getHeight()-(int)(s.getMaxHeight())-30, 3, 3);
						xindex+=5;


					}
				}
			}

		};
		graphPanel.setPreferredSize(getPreferredSize());
		add(graphPanel,BorderLayout.LINE_END);
	}


}
