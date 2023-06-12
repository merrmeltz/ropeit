import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class TutorialPopup extends JDialog{
	
	private JFrame frame;

	/**
	 * The constructor for the TutorialPopup. Takes a JFrame
	 * as a parameter.
	 * @param f
	 */
	public TutorialPopup(JFrame f){
		frame = f;
		setLayout(new FlowLayout());
		setSize(new Dimension(500, 350));
		
		JPanel instructionPanel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Image instructions = new ImageIcon(getClass().getResource("images/InstructionManual.png")).getImage();
				g.drawImage(instructions, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		instructionPanel.setPreferredSize(new Dimension(500,845));
		JScrollPane scroller = new JScrollPane(instructionPanel);
		scroller.setPreferredSize(new Dimension(500,300));
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scroller);
	}
}
