import java.util.Arrays;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;
import org.apache.commons.math3.analysis.solvers.PegasusSolver;

public class PhysicsCalculator implements java.io.Serializable{
	
	
	/*
	 * Club, ball, weight, rope, accelerometer constants
	 */
	private double frequency = 250;        //input accelerometer sample frequency (Hz)
	private double weightMassLbs = 5.42;   //input mass of weight (lbs)
	private double k = .7;                 //input spring constant of rope (N/m)
	private double ballMassKgs = .045;     //input mass of golf ball (kgs)
	private double ropeLengthYds = 10;     //input rope length (yds)
	private double g = 9.8;                //input acceleration due to gravity (m/s^2)
	private String club = "3iron";         //input club type
	private double cL = .3;                //input lift coefficient of golf ball
	private double cD = .3;                //input drag coefficient of golf ball
	private double ballArea = .00165;      //input cross-sectional area of golf ball (m^2)
	private double airDensity = 1.2;       //input density of air (kg/m^3)
	private double sensorThreshold = .33 ;  //sensor threshold
	private double period = 1/frequency;	//period
    private double weightMassKgs = weightMassLbs*.453592;	//weight 
	
	//acceleration arrays
    private double[][] data = new double[751][3];
	private double[] xAcceleration = new double[751];
	private double[] yAcceleration = new double[751];
	private double[] zAcceleration = new double[751];
	
	//acceleration time vectors
	private double tIncr = 0;
	private double[] tX = new double[751];
	private double[] tY = new double[751];
	private double[] tZ = new double[751];
	
	//delta velocity vectors
	private double[] xDeltaVelocity = new double[751];
	private double[] yDeltaVelocity = new double[751];
	private double[] zDeltaVelocity = new double[751];
	
	//force vectors
	private double[] xForce = new double[751];
	private double[] yForce = new double[751];
	private double[] zForce = new double[751];
	
	//velocity curve vectors
	private double xVincr = 0;
	private double[] xVelocity = new double[751];
	private double yVincr = 0;
	private double[] yVelocity = new double[751];
	private double zVincr = 0;
	private double[] zVelocity = new double[751];
	
	//power curve vectors
	private double[] xPower = new double[751];
	private double[] yPower = new double[751];
	private double[] zPower = new double[751];
	
	//delta work vectors
	private double[] xDeltaWork = new double[751];
	private double[] yDeltaWork = new double[751];
	private double[] zDeltaWork = new double[751];
	
	//work vectors
	private double xWincr = 0;
	private double[] xWork = new double[751];
	private double yWincr = 0;
	private double[] yWork = new double[751];
	private double zWincr = 0;
	private double[] zWork = new double[751];
	
	//net work values
	private double[] xWorkValue = new double[2];
	private double[] yWorkAbs = new double[751];
	private double[] yWorkValue = new double[2];
	private double[] zWorkValue = new double[2]; 
	
	/*
	 * Physics model variables
	 */
    private double phiRads;				//launch angle (radians)
    private double phiDegs;				//launch angle (degrees)
    private double ropeLengthMs;		//rope length in meters
    private double h1;					//ball height before the string is taught (meters)
    private double workWeight;			//work in a scalar value
	private double tSpring;				//time ball taught (s)
	
	//First set of simultaneous equation variables
	private double xValue;				//distance rope stretched
	private double v1Value;				//velocity value (m/s)
	private double v1ValueX;			//velocity value-x direction (m/s)
	private double v1ValueZ;			//velocity value-y direction (m/s)
	private double h2Value;				//ball height after rope is taught
	private double b;					//drag simplifying constant
	
	//Second set of simultaneous equation variable
	private double v0ValueX;			//initial x velocity (m/s)
	private double v0ValueZ;			//initial z velocity (m/s)
	private double v0Value;				//initial velocity (m/s)
	private double tRopeValue;			//time just before ball is taught (seconds)
	private double tFlightValue;		//time ball would be in flight
	private double tSwitchValue;		//time of ODE switch in x-direction
	private double distanceYds;			//distance ball would travel in yards
	private double maxHeight;			//maximum height ball goes
	
	private double roundEDistance;		//rounded max distance in yards
	private double roundMDistance;		//rounded max distance in meters
	private double roundESpeed;			//rounded ball speed in mph
	private double roundMSpeed;			//rounded ball speed in kph
	private double roundEHeight;		//rounded max height in yards
	private double roundMHeight;		//rounded max height in meters
	private double distanceOfPin;		//sets distance of pin in yards
	private double distanceToPin;		//distance to pin in yards
	private double roundEDistanceToPin;	//rounded distance to pin in yards
	private double roundMDistanceToPin;	//rounded distance to pin in meters
	private double roundLaunchAngle;	//rounded launch angle in degrees
	
	/**
	 * The constructor for a PhysicsCalculator. Using various helper methods
	 * and the acceleration data from the given shot, the PhysicsCalculator
	 * determines the shot's distance, speed, and maximum height.
	 * @param shot
	 */
	public PhysicsCalculator(Shot shot){
		
		//Reads shot data into double array
		data = shot.getAccelerations();
    	
		//creates acceleration arrays from shot data
		for(int i = 0; i < 751; i++){
			xAcceleration[i] = data[i][0];
		}
		
		for(int i = 0; i < 751; i++){
			yAcceleration[i] = data[i][1];
		}
		
		for(int i = 0; i < 751; i++){
			zAcceleration[i] = data[i][2];
		}
		
		//creates acceleration time vectors
		for(int i = 0; i < 751; i++){
			tX[i] = tIncr;
			tY[i] = tIncr;
			tZ[i] = tIncr;
			tIncr += 0.004;
		}
		
		//delta velocity rectangle area curve and force curves:
		for(int i = 0; i < 751; i++){
			xDeltaVelocity[i] = xAcceleration[i] * period;
		    yDeltaVelocity[i] = yAcceleration[i] * period; 
		    zDeltaVelocity[i] = zAcceleration[i] * period;
		    xForce[i] = xAcceleration[i]* weightMassKgs;
		    yForce[i] = yAcceleration[i]* weightMassKgs;
		    zForce[i] = zAcceleration[i]* weightMassKgs;
		}
		
	    //generate velocity curve using rectangle method on integration:
		for(int i = 0; i < 751; i++){
			xVincr += xDeltaVelocity[i];
			xVelocity[i] = xVincr;
			yVincr += yDeltaVelocity[i];
			yVelocity[i] = yVincr;
			zVincr += zDeltaVelocity[i];
			zVelocity[i] = zVincr;
		}
		
		//generate power curve:
		for(int i = 0; i < 751; i++){
			xPower[i] = xForce[i]*xVelocity[i];
			yPower[i] = yForce[i]*yVelocity[i];
			zPower[i] = zForce[i]*zVelocity[i];
		}
		
		//delta work rectangle area curve:
		for(int i = 0; i < 751; i++){
			xDeltaWork[i] = xPower[i] * period;
			yDeltaWork[i] = yPower[i] * period;
			zDeltaWork[i] = zPower[i] * period;
		}
		
		//generate work curve using rectangle method:
		for(int i = 0; i < 751; i++){
			
			xWincr += xDeltaWork[i];
			xWork[i] = xWincr;
			yWincr += yDeltaWork[i];
			yWork[i] = yWincr;
			zWincr += zDeltaWork[i];
			zWork[i] = zWincr;
		
		}
		
		
		
		//net work done on weight:
		xWorkValue = getMaxValue(xWork);
		yWorkAbs = absVal(yWork);
		yWorkValue = getMaxValue(yWorkAbs);
		zWorkValue = getMaxValue(zWork);
		
		//Physics model:
		phiRads = Math.atan(getMaxValue(zAcceleration)[0]/getMaxValue(xAcceleration)[0]);
		phiDegs = phiRads * 180/Math.PI;
		ropeLengthMs = 0.9144 * ropeLengthYds;
		h1 = ropeLengthMs * Math.sin(phiRads);		//ball height before rope is taught
		workWeight = xWorkValue[0]+ yWorkValue[0]+ zWorkValue[0];	//work in scalar
		tSpring = ((xWorkValue[1]/frequency)-(findGreaterThanOrEqual(xAcceleration, sensorThreshold)/frequency))/10;	//time ball taught
		//tThresh excluded-not used 
	
		/*
		 * Following solves conservation of energy system to obtain velocity of ball
		 * before taut by rope. Velocity value returned as 'v1Value'. X,Y, and Z components 
		 * returned as 'v1ValueX', 'v1ValueY', 'v1ValueZ' respectively.
		 */
		//quadratic coefficients to find x (distance rope stretched [meters])
		double aX = ((0.5*ballMassKgs)/((0.5*tSpring)*(0.5*tSpring)))-0.5*k;
		double bX = -ballMassKgs*g*Math.sin(phiRads);
		double cX = ballMassKgs*g*h1-ballMassKgs*g*ropeLengthMs*Math.sin(phiRads)-workWeight;
		//finds solutions to first system of equations and performs calculations on them
		xValue = quadraticSolver(aX,bX,cX);
		v1Value = xValue/(0.5*tSpring);							//velocity value
		v1ValueX = v1Value*Math.cos(phiRads);					//velocity value in x-direction
		v1ValueZ = v1Value*Math.sin(phiRads);					//velocity value in z-direction
		h2Value = (ropeLengthMs+xValue)*Math.sin(phiRads);		//height after rope is taught
		b = cD*airDensity*ballArea*.5/ballMassKgs;				//drag simplifying constant
		
		
		
		/*
		 * Following solves system of equations to get ball launch velocity. Velocity value
		 * returned as 'v0Value'. X,Y, and Z components returned as 'v0ValueX','v0ValueY', 
		 * 'v0ValueZ' respectively. Model incorporates drag force. 
		 */
		
		//Finds solutions to the second system of equations
		UnivariateFunction tRopeSolver = new UnivariateFunction(){

			@Override
			public double value(double x) {
				return 0.5*ballMassKgs*(Math.pow(1/((1/v1ValueX)-b*x), 2)+ (g/b)*Math.pow(Math.tan(x*Math.sqrt(b*g) + Math.tan(Math.atan(v1ValueZ*Math.sqrt(b/g)))), 2)) - ballMassKgs*g*h1 - 0.5*ballMassKgs*v1Value*v1Value +b*ballMassKgs*Math.pow(v1Value, 3)*x;
			}
			
		};
		PegasusSolver pegasus2 = new PegasusSolver();
		tRopeValue = pegasus2.solve(10000, tRopeSolver, 0,3);
		v0ValueX = 1/((1/v1ValueX)-b*tRopeValue);															//initial x velocity (m/s)
		v0ValueZ = Math.sqrt(g/b)*Math.tan(tRopeValue*Math.sqrt(b*g)+ Math.atan(Math.sqrt(b/g)*v1ValueZ));	//initial z velocity (m/s)
		v0Value = Math.sqrt(Math.pow(v0ValueX,2)+Math.pow(v0ValueZ,2));															//initial velocity (m/s)
		
		/*
		 * Following solves system of equations to get distance ball would travel
		 * if not restrained by rope. Distance returned as 'distanceYds'. Model
		 * incorporates drag force. Treats x-trajectory as system of piecewise 
		 * second order non-linear ODEs. 
		 */
		tSwitchValue = -Math.atan(-v0ValueZ*Math.sqrt(b/g))/Math.sqrt(b*g); 	
		//time of ODE switch in x-direction 
		double placeholder = Math.cos(-Math.sqrt(b*g)*tSwitchValue);
		if (Double.isNaN(tSwitchValue + Math.log(placeholder + Math.sqrt(Math.pow(placeholder,2) - 1))/Math.sqrt(b*g))) {
			double pha = Math.log(Math.sqrt(placeholder*placeholder+Math.sqrt((placeholder*placeholder-1)*(placeholder*placeholder-1))))/Math.sqrt(b*g);
			double phb = Math.atan(Math.pow((placeholder*placeholder-1)*(placeholder*placeholder-1),0.25)/placeholder)/Math.sqrt(b*g);
			tFlightValue = Math.sqrt(Math.pow((tSwitchValue+pha),2)+phb*phb);       //time ball would be in flight
		}
		else {
			tFlightValue = tSwitchValue + Math.log(placeholder + Math.sqrt(Math.pow(placeholder,2) - 1))/Math.sqrt(b*g);
		}		
		distanceYds = Math.abs(1.09361*Math.log((b*tFlightValue - 1/v0ValueX)/(-1/v0ValueX))/b);
		maxHeight = 1.09361*v0ValueZ*v0ValueZ/(2*g);
		roundEDistance = (double)Math.round((distanceYds*10))/10;
		roundMDistance = (double)Math.round(distanceYds*0.9144*10)/10;
		roundESpeed = (double)Math.round(v0Value*2.23694*10)/10;
		roundMSpeed = (double)Math.round(v0Value*3.6*10)/10;
		roundEHeight = (double)Math.round(maxHeight*10)/10;
		roundMHeight = (double)Math.round(maxHeight*0.9144*10)/10;
		roundLaunchAngle = (double)Math.round(phiDegs*10)/10;

	}

	
	/**
	 * Finds and returns the maximum value of the array along with its index.
	 * @return [maxVal, index]
	 */
	private double[] getMaxValue(double[] a){
		double maxVal = a[0];
		double index = 0;
		for(int i = 0; i < a.length; i++){
			if(a[i] > maxVal){
				maxVal = a[i];
				index = i;
			}
		}
		double[] maxValArray = {maxVal, index};
		return maxValArray;
	}
	
	/**
	 * Returns an array of the absolute values of the elements in the array.
	 * @return array of absolute values of given array
	 */
	private double[] absVal(double[] a){
		for(int i =0; i < a.length; i++){
			if(a[i] < 0){
				a[i] = a[i] * -1;
			}
		}
		return a;
	}
	
	/**
	 * Returns first index of array when greater than or equal to a scalar.
	 */
	private int findGreaterThanOrEqual(double[] array, double scalar){
			int index = 0;
			for(int i =0; i < array.length; i++){
				if(array[i] >= scalar){
					index = i;
					break;
				}
			}
			return index;
	}

	/**
	 * Calculates the roots of quadratic equation from the coefficients
	 * using the quadratic formula.
	 * @param Three doubles: a,b, and c. These are the coefficients of
	 * a quadratic polynomial of the form ax^2 + bx + c.
	 * @return The root of the polynomial.
	 */
	private double quadraticSolver(double a, double b, double c){
		double x;
		x = (-b + Math.sqrt(b*b-4*a*c))/(2*a);
		if(x > 0){
			return x;
		}
		else{
			x = (-b - Math.sqrt(b*b-4*a*c))/(2*a);
			return x;
		}
	}
	
	
	/**
	 * Sets the rounded values for the distance to pin in both unit systems.
	 */
	private void distanceToPinCalculator(){
		distanceToPin = distanceYds - distanceOfPin;
		roundEDistanceToPin = (double)Math.round(distanceToPin*10)/10;
		roundMDistanceToPin = (double)Math.round(distanceToPin * 0.9144*10)/10;
	}
	
	/**
	 * Calculates the x and z coordinates of a Shot given the current time.
	 * @param the current time
	 * @return the array [x,z] where x is the x-coordinate of the shot and
	 * z is the z-coordinate of the shot at the given time.
	 */
	public double[] getXZCoordinates(double time){
		if (time<tFlightValue) {
			double adjustTime=time*2*v0ValueZ/(g*tFlightValue);
			//System.out.println(distanceYds);
			double zCoordinate= 1.09361*(v0ValueZ*adjustTime-0.5*g*adjustTime*adjustTime);

			double xCoordinate = 1.09361*v0ValueX*adjustTime;
			double adjustxCoordinate = distanceYds*g*xCoordinate/(2*v0ValueX*v0ValueZ*1.09361);
			double[] answer = {adjustxCoordinate,zCoordinate};
			return answer;
		}
		else{
			double[] answer = {distanceYds,0};
			return answer;
		}
		
	}
	
	/**
	 * Used to get the total time the ball is in the air. 
	 * @return tFlightValue, the time the ball is airborne. 
	 */
	public double gettFlight(){
		return tFlightValue; 
	}
	
	/**
	 * Gets the calculated distance of the shot.
	 * @return distance
	 */
	public double getDistance(){
		return distanceYds;
	}
	
	/**
	 * Gets the calculated speed of the shot.
	 * @return speed
	 */
	public double getSpeed(){
		return v0Value;
	}
	
	/**
	 * Gets the calculated maximum height of the shot.
	 * Note that this value is based on a model that does
	 * not include drag.
	 * @return the maximum height of the shot
	 */
	public double getMaxHeight(){
		return maxHeight;
	}
	
	/**
	 * @return the rounded distance in yards
	 */
	public double getRoundEDistance(){
		return roundEDistance;
	}
	
	/**
	 * 
	 * @return the rounded distance in meters
	 */
	public double getRoundMDistance(){
		return roundMDistance;
	}
	
	/**
	 * 
	 * @return the rounded ball speed in miles per hour
	 */
	public double getRoundESpeed(){
		return roundESpeed;
	}
	
	/**
	 * 
	 * @return the rounded ball speed in kilometers per hour
	 */
	public double getRoundMSpeed(){
		return roundMSpeed;
	}
	
	/**
	 * 
	 * @return the rounded maximum height in yards
	 */
	public double getRoundEHeight(){
		return roundEHeight;
	}
	
	/**
	 * 
	 * @return the rounded maximum height in meters
	 */
	public double getRoundMHeight(){
		return roundMHeight;
	}
	
	/**
	 * 
	 * @return the launch angle in degrees
	 */
	public double getLaunchAngle(){
		return phiDegs;
	}
	
	/**
	 * Sets the distance to the pin to that of the given double.
	 * @param distance
	 */
	public void setDistanceOfPin(double distance){
		this.distanceOfPin = distance;
		distanceToPinCalculator();
	}
	
	/**
	 * 
	 * @return the current distance of the pin 
	 * from the tee
	 */
	public double getDistanceOfPin(){
		return distanceOfPin;
	}
	
	/**
	 * 
	 * @return the distance from the ball to the pin in yards, 
	 * as determined by the PhysicsCalculator
	 */
	public double getDistanceToPin(){
		return distanceToPin;
	}
	
	/**
	 * 
	 * @return the rounded distance to the pin in yards
	 */
	public double getRoundEDistanceToPin(){
		return roundEDistanceToPin;
	}
	
	/**
	 * 
	 * @return the rounded distance to the pin in meters
	 */
	public double getRoundMDistanceToPin(){
		return roundMDistanceToPin;
	}
	
	/**
	 * 
	 * @return the rounded launch angle
	 */
	public double getRoundLaunchAngle(){
		return roundLaunchAngle;
	}
	
}
