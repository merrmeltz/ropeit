import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class Session implements java.io.Serializable{

	private Collection<Shot> sessionShots;


	/**
	 * Creates a new Session.
	 */
	public Session(){
		sessionShots = new ArrayList<Shot>();
	}

	/**
	 * Used to get the Collection of Shots stored in a Session.
	 * @return Collection<Shot> of Shots
	 */
	public Collection<Shot> getSessionShots(){ 
		return sessionShots; 
	}
	
	/**
	 * Used to get the first shot stored in the session's
	 * collection of shots.
	 * @return the first shot in the session's collection of Shots
	 */
	public Shot getFirstShot(){
		Iterator<Shot> iter = sessionShots.iterator();
		if (iter.hasNext()){
			return iter.next();
		}
		else return null;
	}

	/**
	 * Used to compute the average distance over all shots in
	 * the session's collection of shots.
	 * @return the average distance of all shots in this session
	 */
	public double getAverageDistance(){
		double sum = 0;
		for (Shot shot : sessionShots){
			sum += shot.getDistance();
		}
		return sum/sessionShots.size();
	}

	/**
	 * Used to compute the average speed over all shots in
	 * the session's collection of shots.
	 * @return the average speed of all shots in this session
	 */
	public double getAverageSpeed(){
		double sum = 0;
		for (Shot shot : sessionShots){
			sum += shot.getSpeed();
		}
		return sum/sessionShots.size();
	}

	/**
	 * Used to compute the average maximum height of all shots in
	 * the session's collection of shots.
	 * @return the average maximum height of all shots in this session
	 */
	public double getAverageHeight(){
		double sum = 0;
		for (Shot shot : sessionShots){
			sum += shot.getMaxHeight();
		}
		return sum/sessionShots.size();
	}


}
