import java.util.Collection;

import javax.swing.JButton;



public abstract class GraphScreen extends CenterScreen {

	protected boolean regression;
	protected JButton trendlineButton;
	
	
	/**
	 * Computes the coefficients for a regression line over a collection
	 * of doubles.
	 * Returns an array of two doubles:
	 * The first element is the y-intercept of the regression line
	 * The second element is the slope of the regression line
	 */
	public double[] getRegressionInfo(Collection<Double> data){
		double xsum = 0;
		double ysum = 0;
		int n = data.size();
		for (int i = 0; i < n; i++){
			xsum+=i;
		}
		double xbar = xsum/n;
		for (Double d: data){
			ysum += d;
		}
		double ybar = ysum/n;
		double Sxx = 0;
		double Sxy = 0;
		int index = 0;
		for (Double d: data){
			Sxx += (index - xbar)*(index-xbar);
			Sxy += (index - xbar)*(d-ybar);
			index++;
		}
		double Beta1 = Sxy/Sxx;
		double Beta0 = ybar - Beta1*xbar;
		double[] regressionInfo = new double[2];
		regressionInfo[0] = Beta0;
		regressionInfo[1] = Beta1;
		return regressionInfo;
	}
	
	
	
}
