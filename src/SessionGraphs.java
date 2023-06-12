import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;



public class SessionGraphs extends GraphScreen {

	private Session session;
	private JPanel graphPanel;

	/**
	 * The Constructor for a SessionGraphs screen.
	 * Needs to take in a session so it knows what data to 
	 * display in graphs.
	 * @param session
	 */
	public SessionGraphs(Session session){
		setLayout(new BorderLayout());
		this.session = session;
		regression = false;
		
		
		displaySessionGraphs();
		createLegend();
		
		
		ImageIcon backToArchive = new ImageIcon(getClass().getResource("images/backtoarchive.png"));
		backButton = new JButton(backToArchive);
		backButton.setPreferredSize(new Dimension(1000,40));
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				setCenter(getScreens().get("Archive"));

			}
			
		});
		
		ImageIcon toggle = new ImageIcon(getClass().getResource("images/togglebutton.png"));
		trendlineButton = new JButton(toggle);
		trendlineButton.setPreferredSize(new Dimension(1000,40));
		trendlineButton.setOpaque(false);
		trendlineButton.setContentAreaFilled(false);
		trendlineButton.setBorderPainted(false);	
		trendlineButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent evt) {
				regression = !regression;
				repaint();
				System.out.println(regression);
			}
			
			
		});
		add(backButton,BorderLayout.PAGE_END);
		add(trendlineButton,BorderLayout.PAGE_START);
	}

	/**
	 * The getter for the session that the SessionGraphs screen
	 * is displaying the data of.
	 * @return the session corresponding to this SessionGraphs screen
	 */
	public Session getSession(){ return session; }


	/**
	 * This is the method used to display the session graphs.
	 * Initializes the graphPanel and uses paintComponent to
	 * paint the graphs.
	 */
	public void displaySessionGraphs(){
			graphPanel = new JPanel(){
				
				public Dimension getPreferredSize(){
					return new Dimension(500,500);
				}
				
				public void paintComponent(Graphics g){
					int xscale = 90;
					int yscale = 30;
					
					super.paintComponent(g);
					g.setColor(Color.BLACK);
					System.out.println("h"+getHeight());
					g.drawString("Distance", getWidth()/3+200, 20);
					g.drawLine(getWidth()/xscale, getHeight()/yscale, 10, getHeight()/3);
					g.drawLine(getWidth(), getHeight()/3, 10, getHeight()/3);
					g.drawString("Speed", getWidth()/3+200, getHeight()/3+20);
					g.drawLine(getWidth()/xscale, getHeight()/3+getHeight()/yscale, 10, 2*getHeight()/3);
					g.drawLine(getWidth(), 2*getHeight()/3, 10, 2*getHeight()/3);
					
					if (!session.getFirstShot().getMode()){ // we are in pin mode
						g.drawString("Distance to Pin", getWidth()/3+175, 2*getHeight()/3+20);
						g.drawLine(getWidth(), getHeight()-60, 10, getHeight()-60);
					}
					else { // we are in driving range mode
						g.drawString("Maximum Height", getWidth()/3+175, 2*getHeight()/3+20);
					}
					g.drawLine(getWidth()/xscale, 2*getHeight()/3+getHeight()/yscale,10,getHeight()-20);
					g.drawLine(getWidth(), getHeight()-20, 10, getHeight()-20);
					
					
					

					Collection<Shot> temp = session.getSessionShots();
					Position p_dist = new Position(10,getHeight()/3);
					Position p_speed = new Position(10,2*getHeight()/3);
					Position p_height = new Position(10,getHeight()-20);
					Position p_disttopin = new Position(10,getHeight()-20);
					
					int xindex = 11;
					if (temp != null){
						for (Shot s : temp){
							// changes to club color, stays black if no club selected
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
							if (!session.getFirstShot().getMode()){ // this means we are in pin mode
								int pinDistance = 80;
								g.drawLine((int)p_disttopin.getX(), (int)p_disttopin.getY()-pinDistance, xindex, getHeight()-(int)s.getDistanceToPin()-pinDistance);
								p_disttopin = new Position(xindex,getHeight()-(int)s.getDistanceToPin());
								g.drawOval(xindex, getHeight()-(int)s.getDistanceToPin()-pinDistance, 3, 3);
								g.fillOval(xindex, getHeight()-(int)s.getDistanceToPin()-pinDistance, 3, 3);
								xindex+=10;
							}
							else {
								g.drawLine((int)p_height.getX(), (int)p_height.getY(), xindex, getHeight()-(int)(s.getMaxHeight())-30);
								p_height = new Position(xindex,getHeight()-(int)(s.getMaxHeight())-30);
								g.drawOval(xindex, getHeight()-(int)(s.getMaxHeight())-30, 5, 5);
								g.fillOval(xindex, getHeight()-(int)(s.getMaxHeight())-30, 5, 5);
								xindex+=10;
							}
						}
						
						if (regression){
							// Compute stats for regression line
							int pinDistance = 80;
							Collection<Double> distances = new ArrayList<Double>();
							Collection<Double> speeds = new ArrayList<Double>();
							Collection<Double> heights = new ArrayList<Double>();
							Collection<Double> distsToPin = new ArrayList<Double>();
							for (Shot shot: session.getSessionShots()){
								distances.add(shot.getDistance());
								speeds.add(shot.getSpeed());
								heights.add(shot.getMaxHeight());
								distsToPin.add(shot.getDistanceToPin());
							}
							
							double[] distInfo = getRegressionInfo(distances);
							double[] speedInfo = getRegressionInfo(speeds);
							double[] heightInfo = getRegressionInfo(heights);
							double[] distToPinInfo = getRegressionInfo(distsToPin);
							
							double Beta1dist = distInfo[1];
							double Beta1speed = speedInfo[1];
							double Beta1height = heightInfo[1];
							double Beta1pin = distToPinInfo[1];
							double Beta0dist = distInfo[0];
							double Beta0speed = speedInfo[0];
							double Beta0height = heightInfo[0];
							double Beta0pin = distToPinInfo[0];
							
							int n = session.getSessionShots().size();
							g.setColor(Color.MAGENTA);
							g.drawLine(10, (int)(getHeight()/3-Beta0dist), 10+xindex, (int)(getHeight()/3 - Beta0dist - Beta1dist*n));
							g.drawLine(10, (int)(2*getHeight()/3-Beta0speed), 10+xindex, (int)(2*getHeight()/3 - Beta0speed - Beta1speed*n));
							if (session.getFirstShot().getMode()){ // if we are in driving range mode
								g.drawLine(10, (int)(getHeight()-Beta0height)-20,10+xindex,(int)(getHeight() - Beta0height - Beta1height*n)-20);
							}
							else {
								// we are in pin mode
								System.out.println("Beta0pin = " + Beta0pin);
								System.out.println("Beta1pin = " + Beta1pin);
								if (Beta1pin < 0 && Beta0pin > 0){
									g.drawLine(10, (int)(getHeight()-Beta0pin)-20,10+xindex,(int)(getHeight() - Beta0pin + Beta1pin*n)-20);	
								}
								else if (Beta1pin > 0 && Beta0pin < 0){
									g.drawLine(10, (int)(getHeight()+Beta0pin)-20,10+xindex,(int)(getHeight() + Beta0pin - Beta1pin*n)-20);
								}
								else if (Beta1pin < 0 && Beta0pin < 0){
									g.drawLine(10, (int)(getHeight()+Beta0pin)-20,10+xindex,(int)(getHeight() + Beta0pin + Beta1pin*n)-20);
								}
								else {
									g.drawLine(10, (int)(getHeight()-Beta0pin)-20,10+xindex,(int)(getHeight() - Beta0pin - Beta1pin*n)-20);	
								}
							}
							
						}
						
						
						
					}
					
				}
			};
		
		add(graphPanel, BorderLayout.CENTER);

		
	}
	
	/**
	 * This is the method called to display the legend that shows
	 * how clubs are color-coded.
	 */
	public void createLegend(){
		JPanel legend = new JPanel(){
			public Dimension getPreferredSize(){
				return new Dimension(100,500);
			}
			public void paintComponent(Graphics g){
				g.setColor(RopeItProGUI.getClubList().get(0).getColor());
				g.drawString(RopeItProGUI.getClubList().get(0).getName(), 5, 100);

				g.setColor(RopeItProGUI.getClubList().get(1).getColor());
				g.drawString(RopeItProGUI.getClubList().get(1).getName(), 5, 150);

				g.setColor(RopeItProGUI.getClubList().get(2).getColor());
				g.drawString(RopeItProGUI.getClubList().get(2).getName(), 5, 200);

				g.setColor(RopeItProGUI.getClubList().get(3).getColor());
				g.drawString(RopeItProGUI.getClubList().get(3).getName(), 5, 250);

				g.setColor(RopeItProGUI.getClubList().get(4).getColor());
				g.drawString(RopeItProGUI.getClubList().get(4).getName(), 5, 300);

				g.setColor(RopeItProGUI.getClubList().get(5).getColor());
				g.drawString(RopeItProGUI.getClubList().get(5).getName(), 5, 350);

				g.setColor(RopeItProGUI.getClubList().get(6).getColor());
				g.drawString(RopeItProGUI.getClubList().get(6).getName(), 5, 400);
				
				g.setColor(RopeItProGUI.getClubList().get(7).getColor());
				g.drawString(RopeItProGUI.getClubList().get(7).getName(), 5, 450);
			}
		};
		add(legend, BorderLayout.LINE_END);
	}


}
