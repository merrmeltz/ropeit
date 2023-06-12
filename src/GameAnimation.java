

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.QuadCurve2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class GameAnimation extends JFrame{
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private double maxHeight;
	private double tSwitch;
	private double tSwitchX;
	private double tSwitchY;
	private double tFlight;

	public GameAnimation(){
		//Constructor
	}

	/**
	 * @param startX the startX to set
	 */
	public void setStartX(double startX) {
		this.startX = startX;
	}

	/**
	 * @param startY the startY to set
	 */
	public void setStartY(double startY) {
		this.startY = startY;
	}

	/**
	 * @param endX the endX to set
	 */
	public void setEndX(double endX) {
		this.endX = endX;
	}

	/**
	 * @param endY the endY to set
	 */
	public void setEndY(double endY) {
		this.endY = endY;
	}

	/**
	 * @param maxHeight the maxHeight to set
	 */
	public void setMaxHeight(double maxHeight) {
		this.maxHeight = maxHeight;
	}

	/**
	 * @param tSwitch the tSwitch to set
	 */
	public void settSwitch(double tSwitch) {
		this.tSwitch = tSwitch;
	}

	/**
	 * @param tSwitchX the tSwitchX to set
	 */
	public void settSwitchX(double tSwitchX) {
		this.tSwitchX = tSwitchX;
	}

	/**
	 * @param tSwitchY the tSwitchY to set
	 */
	public void settSwitchY(double tSwitchY) {
		this.tSwitchY = tSwitchY;
	}

	/**
	 * @param tFlight the tFlight to set
	 */
	public void settFlight(double tFlight) {
		this.tFlight = tFlight;
	}


	
	
	
	
}
