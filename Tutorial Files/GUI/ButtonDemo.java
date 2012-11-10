/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 19, 2012
 */

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ButtonDemo extends JFrameDemo {
	
	public ButtonDemo() {
		initCanvas("Inserts a button into a display container", 800, 600);
		// insert a button into the display container 
		JButton btn = new JButton("Hello World!");
		add(btn);
		setVisible(true);		
	}

	public static void main(String[] args) {
		new ButtonDemo();
	}
}
