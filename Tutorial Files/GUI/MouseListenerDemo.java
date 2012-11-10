/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 19, 2012
 */

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MouseListenerDemo extends JFrameDemo implements MouseListener {

	public MouseListenerDemo() {
		initCanvas("Mouse Listener Demo", 300, 200);
		backgroundColor = Color.WHITE;
		// insert a button into the display container
		JButton btn = new JButton("Label");
		add(btn);
		btn.setToolTipText("Versuch mich doch zu treffen!");
		this.setVisible(true);
		btn.addMouseListener(this);
	}

	private void traceEvent(MouseEvent e, String msg) {
		int x = e.getX();
		int y = e.getY();
		System.out.println(msg + " at x:" + x + " y:" + y);
	}

	public void mouseClicked(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		btn.setText("Getroffen");
		btn.setToolTipText("Verdammt!");
		traceEvent(e, "MouseClicked");
	}

	public void mouseEntered(MouseEvent e) {
		traceEvent(e, "MouseEntered");
	}

	public void mouseExited(MouseEvent e) {
		traceEvent(e, "MouseExited");
	}

	public void mousePressed(MouseEvent e) {
		traceEvent(e, "MousePressed");
	}

	public void mouseReleased(MouseEvent e) {
		traceEvent(e, "MouseReleased");
	}

	public static void main(String[] args) {
		new MouseListenerDemo();
	}
}
