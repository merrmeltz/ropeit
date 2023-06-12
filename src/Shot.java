import java.io.Serializable;
import java.util.Random;


public class Shot implements java.io.Serializable{
	
	private double distance;
	private double speed;
	private double maxHeight;
	private boolean Mode;
	private String date;
	private Club clubType;
	private Position posn;
	private double[][] accelerations;
	private PhysicsCalculator calc;
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
	private double launchAngle;
	private double roundLaunchAngle;
	

	/**
	 * Creates a new Shot. The Shot reads in random acceleration data from 
	 * the dataReader and uses the physicsCalculator to determine the
	 * distance, distance to pin, speed, and maximum height of the shot, along
	 * with other parameters. 
	 * @param Mode
	 * @param date
	 * @param clubType
	 * @param posn
	 */
	public Shot(boolean Mode, String date, Club clubType, Position posn){
		this.Mode = Mode;
		this.date = date;
		this.clubType = clubType;
		this.posn = posn;
		DataReader dReader = new DataReader();
		Random r = new Random(); 
		int index = r.nextInt(28)+1; // The shot randomly decides what acceleration data it will use
		System.out.println(index);
		accelerations = dReader.getData(index);
		this.calc = new PhysicsCalculator(this);
		distance = calc.getDistance();
		speed = calc.getSpeed();
		maxHeight = calc.getMaxHeight();
		roundEDistance = calc.getRoundEDistance();
		roundMDistance = calc.getRoundMDistance();
		roundESpeed = calc.getRoundESpeed();
		roundMSpeed = calc.getRoundMSpeed();
		roundEHeight = calc.getRoundEHeight();
		roundMHeight = calc.getRoundMHeight();
		launchAngle = calc.getLaunchAngle();
		roundLaunchAngle = calc.getRoundLaunchAngle();
	}
	
	/**
	 * Used to get the Club associated with the shot.
	 * @return the type of club (Club)
	 */
	public Club getClubType(){
		return clubType;
	}
	
	/**
	 * Used to change the type of Club of the Shot.
	 * @param type
	 */
	public void setClubType(Club type){
		clubType = type;
	}
	
	/**
	 * Returns the date that the shot was taken.
	 * @return String date
	 */
	public String getDate(){
		return date;
	}
	
	/**
	 * Sets the date of the shot to the given String.
	 * @param date
	 */
	public void setDate(String date){
		this.date = date;
	}
	

	/**
	 * Returns a boolean that is true if the Shot was taken in driving range mode,
	 * or false if the Shot was taken in pin mode.
	 * @return true if drivingRange, false if pin
	 */
	public boolean getMode(){
		return Mode;
	}

	/**
	 * Returns the current position (x,y) of the Shot.
	 * @return Position (x,y) of the Shot
	 */
	public Position getPosition(){
		return posn;
	}
	
	/**
	 * Changes the current position (x,y) of the Shot
	 * to the given position.
	 * @param posn
	 */
	public void setPosition(Position posn){
		this.posn = posn;
	}
	
	/**
	 * Used to get a 3-column matrix of acceleration data that represents
	 * the accelerations of the Shot. The first column corresponds to 
	 * accelerations in the X direction, the second column corresponds
	 * to accelerations in the Y direction, and the third corresponds
	 * to the Z direction. 
	 * @return double[][] accelerations
	 */
	public double[][] getAccelerations(){
		return accelerations;
	}
	
	/**
	 * This method is purely for testing purposes. It sets the 
	 * acceleration data of the shot to the given accelerations.
	 * @param accelerations
	 */
	public void setAccelerations(double[][] accelerations){
		this.accelerations = accelerations;
	}
	
	/**
	 * Returns the distance the shot traveled, as calculated by the 
	 * physics calculator.
	 * @return double distance
	 */
	public double getDistance(){
		return distance;
	}
	
	/**
	 * Sets the distance of the Shot to the given double.
	 * @param distance
	 */
	public void setDistance(double distance){
		this.distance = distance;
	}
	
	/**
	 * Returns the speed of the Shot, as determined by the
	 * physics Calculator.
	 * @return
	 */
	public double getSpeed(){
		return speed;
	}
	
	/**
	 * Sets the speed of the Shot to the given double.
	 * @param speed
	 */
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	/**
	 * Returns the maximum height of the Shot, as determined 
	 * by the physics calculator.
	 * @return double maxHeight
	 */
	public double getMaxHeight(){
		return maxHeight;
	}
	
	/**
	 * Sets the maximum height of the Shot to the given double.
	 * @param maxHeight
	 */
	public void setMaxHeight(double maxHeight){
		this.maxHeight = maxHeight;
	}

	/**
	 * Returns the distance to the pin, as determined by the 
	 * physics calculator.
	 * @return double distanceToPin
	 */
	public double getDistanceToPin() {
		return calc.getDistanceToPin();
	}

	/**
	 * Sets the distance of the pin to the given double.
	 * @param distanceOfPin
	 */
	public void setDistanceOfPin(double distanceOfPin) {
		this.distanceOfPin = distanceOfPin;
		calc.setDistanceOfPin(distanceOfPin);
	}
	
	/**
	 * Using the physics calculator, this method returns the coordinates
	 * (x,z) of the Shot at a given time. It returns a double[] where the
	 * first entry is the x coordinate and the second is the z coordinate.
	 * @param time
	 * @return an array of doubles with two entries: an x-coordinate followed 
	 * by a z-coordinate
	 */
	public double[] getXZCoordinates(double time){
		return calc.getXZCoordinates(time);
	}
	
	/**
	 * Uses the physics calculator to determine the flight time of
	 * the ball (the total time the ball is in the air).
	 * @return double maxTime
	 */
	public double getMaxTime(){
		return calc.gettFlight();
	}

	/**
	 * @return the roundEDistance
	 */
	public double getRoundEDistance() {
		return roundEDistance;
	}

	/**
	 * @param roundEDistance the roundEDistance to set
	 */
	public void setRoundEDistance(double roundEDistance) {
		this.roundEDistance = roundEDistance;
	}

	/**
	 * @return the roundMDistance
	 */
	public double getRoundMDistance() {
		return roundMDistance;
	}

	/**
	 * @param roundMDistance the roundMDistance to set
	 */
	public void setRoundMDistance(double roundMDistance) {
		this.roundMDistance = roundMDistance;
	}

	/**
	 * @return the roundESpeed
	 */
	public double getRoundESpeed() {
		return roundESpeed;
	}

	/**
	 * @param roundESpeed the roundESpeed to set
	 */
	public void setRoundESpeed(double roundESpeed) {
		this.roundESpeed = roundESpeed;
	}

	/**
	 * @return the roundMSpeed
	 */
	public double getRoundMSpeed() {
		return roundMSpeed;
	}

	/**
	 * @param roundMSpeed the roundMSpeed to set
	 */
	public void setRoundMSpeed(double roundMSpeed) {
		this.roundMSpeed = roundMSpeed;
	}

	/**
	 * @return the roundEHeight
	 */
	public double getRoundEHeight() {
		return roundEHeight;
	}

	/**
	 * @param roundEHeight the roundEHeight to set
	 */
	public void setRoundEHeight(double roundEHeight) {
		this.roundEHeight = roundEHeight;
	}

	/**
	 * @return the roundMHeight
	 */
	public double getRoundMHeight() {
		return roundMHeight;
	}

	/**
	 * @param roundMHeight the roundMHeight to set
	 */
	public void setRoundMHeight(double roundMHeight) {
		this.roundMHeight = roundMHeight;
	}

	/**
	 * @return the roundEDistanceToPin
	 */
	public double getRoundEDistanceToPin() {
		return calc.getRoundEDistanceToPin();
	}

	/**
	 * @param roundEDistanceToPin the roundEDistanceToPin to set
	 */
	public void setRoundEDistanceToPin(double roundEDistanceToPin) {
		this.roundEDistanceToPin = roundEDistanceToPin;
	}

	/**
	 * @return the roundMDistanceToPin
	 */
	public double getRoundMDistanceToPin() {
		return calc.getRoundMDistanceToPin();
	}

	/**
	 * @param roundMDistanceToPin the roundMDistanceToPin to set
	 */
	public void setRoundMDistanceToPin(double roundMDistanceToPin) {
		this.roundMDistanceToPin = roundMDistanceToPin;
	}

	/**
	 * @param distanceToPin the distanceToPin to set
	 */
	public void setDistanceToPin(double distanceToPin) {
		this.distanceToPin = distanceToPin;
	}
	
	/**
	 * 
	 * @return the launch angle of the Shot.
	 */
	public double getLaunchAngle(){
		return launchAngle;
	}
	
	/**
	 * 
	 * @return the round Launch Angle
	 */
	public double getRoundLaunchAngle(){
		return roundLaunchAngle;
	}
	
}
