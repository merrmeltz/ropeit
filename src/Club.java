import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * Club that holds a string name and Color for each club we use
 *
 */
public class Club implements java.io.Serializable{
	
	
	private String name;
	private Color color;
	
	/**
	 * Creates a club, given a name (string) and a color (Color)
	 * @param name
	 * @param color
	 */
	public Club(String name, Color color){
		this.name = name;
		this.color = color;
	}
	
	/**
	 * Used to get the name of a club.
	 * @return String name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Used to get the club's color.
	 * @return Color color
	 */
	public Color getColor(){
		return color;
	}

}
