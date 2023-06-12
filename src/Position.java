
public class Position implements java.io.Serializable{
	
	private double x;
	private double y;
	
	/**
	 * Creates a new position with given x and y coordinates
	 * e.g. (x,y)
	 * @param x
	 * @param y
	 */
	public Position(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the x-coordinate of a position.
	 * @return double x-coordinate
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * Gets the y-coordinate of a position.
	 * @return double y-coordinate
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * Increments the x-coordinate by dx and increments
	 * the y-coordinate by dy.
	 * @param dx
	 * @param dy
	 */
	public void updatePosition(double dx, double dy){
		x += dx;
		y += dy;
	}
	
	
	/**
	 * Used to get the distance between this position and a given other
	 * position.
	 * @param other
	 * @return double distance
	 */
	public double getDistance(Position other){
		return Math.sqrt((x-other.getX())*(x-other.getX()) + (y-other.getY()*(y-other.getY())));
	}

}
