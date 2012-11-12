package GUI;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 19, 2012
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;

/**
 * JFrame inherit the interface Serializable and implements methods to save the
 * current status of the window on a hard disc. Therefore, we need an unique
 * identifier for the window. Note, that this implementation is not very useful
 * in this respect, it simply prevent compiler hints.
 */

@SuppressWarnings("serial")
public class JFrameDemo extends JFrame {
	// convenience functions to access the dimensions of the current render
	// context and the definition of the background color used in the canvas
	protected int canvasWidth, canvasHeight;
	protected Color backgroundColor = Color.BLACK;

	public int getCanvasWidth() {
		return canvasWidth;
	}

	public int getCanvasHeight() {
		return canvasHeight;
	}

	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}

	public JFrameDemo() {
		initCanvas("JFrame Demo", 800, 600);
		setVisible(true);
	}

	/**
	 * This method defines the dimensions of the canvas. As the the GUI manager
	 * / the operation system must not follow hints, we retrieve the current
	 * window size later on.
	 */
	public void initCanvas(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		canvasWidth = getSize().width;
		canvasHeight = getSize().height;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Fills the background of the canvas with a color defined in
	 * backgroundColor.
	 * 
	 * @param graphicContext
	 *            a handle to the canvas object
	 */
	protected void clearCanvas(Graphics graphicContext) {
		// fill background
		graphicContext.setColor(backgroundColor);
		graphicContext.fillRect(0, 0, canvasWidth, canvasHeight);
	}

	/**
	 * Fills the background of the canvas with a color defined in
	 * backgroundColor.
	 * 
	 * @param graphicContext
	 *            a handle to the canvas object
	 */
	protected void clearCanvas(Graphics2D graphicContext) {
		// fill background
		graphicContext.setColor(backgroundColor);
		graphicContext.fillRect(0, 0, canvasWidth, canvasHeight);
	}

	/**
	 * The function paint is called by the GUI event handler directly. Some
	 * examples:
	 * <OL>
	 * <LI>the window has been created for the first time,
	 * <LI>the window has been hidden, ...
	 * <LI>some function had asked for a new rendering with the repaint method
	 * </OL>
	 * Do not call this function in our application program, but inform the GUI
	 * event manager to redraw the window with the repaint method!
	 * 
	 * The graphicContext implements a finite state machine, i.e., changes will
	 * effect the rendering until they are redefined.
	 */

	public void paint(Graphics graphicContext) {
	}

	public static void main(String[] args) {
		new JFrameDemo();
	}


}
