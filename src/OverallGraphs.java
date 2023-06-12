import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.SeriesLineStyle;
import com.xeiam.xchart.XChartPanel;


public class OverallGraphs extends GraphScreen{

	private XChartPanel distPanel;
	private XChartPanel speedPanel;
	private XChartPanel heightPanel;
	private ImageIcon toggle;
	private ImageIcon backToArchive;
	private JButton backButton;
	
	/**
	 * The constructor for the OverallGraphs screen.
	 */
	public OverallGraphs(){
		distPanel = new XChartPanel(null);
		speedPanel = new XChartPanel(null);
		heightPanel = new XChartPanel(null);
		regression = false;		
		toggle = new ImageIcon(getClass().getResource("images/togglebutton.png"));
		backToArchive = new ImageIcon(getClass().getResource("images/backtoarchive.png"));
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		trendlineButton = new JButton(toggle);
		trendlineButton.setPreferredSize(new Dimension(1000,40));
		trendlineButton.setOpaque(false);
		trendlineButton.setContentAreaFilled(false);
		trendlineButton.setBorderPainted(false);
		trendlineButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				regression = !regression;
				redisplay();
			}
		});
		
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
		add(trendlineButton);
		add(backButton);
		}
	
	
	/**
	 * Called every time the graphs need to be changed.
	 */
	public void redisplay(){
		
		remove(distPanel);
		displayDistanceGraph();
		remove(speedPanel);
		displaySpeedGraph();
		remove(heightPanel);
		displayHeightGraph();
		repaint();
		revalidate();
	}

	/**
	 * This method is called to display the distanceGraph (using XChart).
	 * It shows average shot speed versus the session.
	 */
	public void displayDistanceGraph(){
		if (RopeItProGUI.getController() != null){
			Chart chart = new ChartBuilder().width(500).height(500).build();
			int index = 0;
			Collection<Double> averageDistances = new ArrayList<Double>();
			Collection<Integer> indices = new ArrayList<Integer>();
			for (Session session : RopeItProGUI.getController().getArchive()){
				indices.add(index);
				averageDistances.add(session.getAverageDistance());
				index++;
			}  
			if (regression){
				double[] regressionInfo = getRegressionInfo(averageDistances);
				double Beta0 = regressionInfo[0];
				double Beta1 = regressionInfo[1];
				Collection<Double> regressionDistances = new ArrayList<Double>();
				for (int i : indices){
					regressionDistances.add(Beta0 + Beta1*i);
				} 
				chart.addSeries("Trend Line", indices, regressionDistances).setLineStyle(SeriesLineStyle.DASH_DASH).setLineColor(Color.MAGENTA).setMarkerColor(Color.MAGENTA);
			}
			
			
			chart.addSeries("Distance", indices, averageDistances).setLineColor(Color.BLUE).setMarkerColor(Color.BLUE);
			chart.setChartTitle("Average Distance vs Session");
			chart.setXAxisTitle("Session");
			chart.setYAxisTitle("Average Distance");
			distPanel = new XChartPanel(chart);
			add(distPanel);
		}
	}

	/**
	 * This method is called to display the overall speed graph (using XChart).
	 * It shows average shot speed versus the session.
	 */
	public void displaySpeedGraph(){
		if (RopeItProGUI.getController() != null){
			Chart chart = new ChartBuilder().width(500).height(500).build();
			int index = 0;
			Collection<Double> averageSpeeds = new ArrayList<Double>();
			Collection<Integer> indices = new ArrayList<Integer>();
			for (Session session : RopeItProGUI.getController().getArchive()){
				indices.add(index);
				averageSpeeds.add(session.getAverageSpeed());
				index++;
			}  
			
			if (regression){
				double[] regressionInfo = getRegressionInfo(averageSpeeds);
				double Beta0 = regressionInfo[0];
				double Beta1 = regressionInfo[1];
				Collection<Double> regressionDistances = new ArrayList<Double>();
				for (int i : indices){
					regressionDistances.add(Beta0 + Beta1*i);
				} 
				chart.addSeries("Trend Line", indices, regressionDistances).setLineStyle(SeriesLineStyle.DASH_DASH).setLineColor(Color.MAGENTA).setMarkerColor(Color.MAGENTA);
			}
			

			chart.addSeries("Speed", indices, averageSpeeds).setLineColor(Color.BLUE).setMarkerColor(Color.BLUE);
			chart.setChartTitle("Average Speed vs Session");
			chart.setXAxisTitle("Session");
			chart.setYAxisTitle("Average Speed");
			speedPanel = new XChartPanel(chart);
			add(speedPanel);
			
		}

	}

	/**
	 * This method is called to display the maximum height graph (using XChart)
	 * Displays the average maximum height versus session. 
	 */
	public void displayHeightGraph(){
		if (RopeItProGUI.getController() != null){
			Chart chart = new ChartBuilder().width(500).height(500).build();
			int index = 0;
			Collection<Double> averageHeights = new ArrayList<Double>();
			Collection<Integer> indices = new ArrayList<Integer>();
			for (Session session : RopeItProGUI.getController().getArchive()){
				indices.add(index);
				averageHeights.add(session.getAverageHeight());
				index++;
			}  
			
			if (regression){
				double[] regressionInfo = getRegressionInfo(averageHeights);
				double Beta0 = regressionInfo[0];
				double Beta1 = regressionInfo[1];
				Collection<Double> regressionDistances = new ArrayList<Double>();
				for (int i : indices){
					regressionDistances.add(Beta0 + Beta1*i);
				} 
				chart.addSeries("Trend Line", indices, regressionDistances).setLineStyle(SeriesLineStyle.DASH_DASH).setLineColor(Color.MAGENTA).setMarkerColor(Color.MAGENTA);
			}

			chart.addSeries("Distance", indices, averageHeights).setLineColor(Color.BLUE).setMarkerColor(Color.BLUE);
			chart.setChartTitle("Average Distance vs Session");
			chart.setXAxisTitle("Session");
			chart.setYAxisTitle("Average Distance");
			heightPanel = new XChartPanel(chart);
			add(heightPanel);
		}

	}

}
