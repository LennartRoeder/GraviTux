package GUI;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 19, 2012
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ActionListenerDemo extends JFrameDemo implements ActionListener {

	public ActionListenerDemo() {
		initCanvas("ActionListener Demo", 800, 600);
		// insert a button into the display container 
		JButton btn = new JButton("Du triffst mich NIE!");
		btn.addActionListener(this);
		add(btn);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		btn.setText("Das gildet nicht!");
	}

	public static void main(String[] args) {
		new ActionListenerDemo();
	}
}
