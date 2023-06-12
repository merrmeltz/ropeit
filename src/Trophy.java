import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Trophy {

	private String name;
	private String info;
	private String requirements;
	private double value;
	private boolean obtained;
	private Icon image;
	private TrophyPopup popup;

	/**
	 * Creates a new Trophy.
	 * @param name
	 * @param info
	 * @param requirements
	 * @param value
	 * @param obtained
	 */
	public Trophy(String name, String info, String requirements, double value, boolean obtained){
		this.name = name;
		this.info = info;
		this.requirements = requirements;
		this.value = value;
		this.obtained = obtained;
		image = createImageIcon("images/award_ribbon_blue.png",name);
		popup = new TrophyPopup(name,requirements);
	}
	
	/**
	 * Creates an imageIcon given a path and description.
	 * @param path
	 * @param description
	 * @return an ImageIcon
	 */
	protected ImageIcon createImageIcon(String path,
			String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	/**
	 * Returns a boolean that says whether or not the user has obtained
	 * this trophy.
	 * @return true if obtained, false if not obtained
	 */
	public boolean isObtained(){
		return obtained;
	}

	/**
	 * Returns any numerical value associated with this trophy 
	 * (e.g. number of yards, etc.)
	 * @return double value
	 */
	public double getValue(){
		return value;
	}

	/**
	 * Used to obtain the requirements needed to get the trophy.
	 * @return String with requirements
	 */
	public String getRequirements(){
		return requirements;
	}

	
	/**
	 * Used to obtain information that will be displayed on
	 * the trophyPopups and Facebook.
	 * @return String info
	 */
	public String getInfo(){
		return info;
	}

	/**
	 * Returns the name of the trophy.
	 * @return String name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the trophy's picture (an Icon)
	 * @return Icon image
	 */
	public Icon getImage(){
		return image;
	}

	/**
	 * Returns the TrophyPopup associated with this trophy.
	 * Used in the TrophyRoomScreen (aka Wall of Fame).
	 * @return TrophyPopup
	 */
	public TrophyPopup getPopup(){
		return popup;
	}
	
	/**
	 * Used when the user obtains a trophy (setObtained to true).
	 * @param bool
	 */
	public void setObtained(boolean bool){
		this.obtained = bool;
	}


}
