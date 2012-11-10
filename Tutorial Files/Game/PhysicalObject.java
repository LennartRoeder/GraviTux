/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 28, 2012
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PhysicalObject {

	private JFrameDemo containerComponent;

	/**
	 * attributes just for debugging purposes
	 */
	// class variable to construct a unique ID for every thread
	private static int idCounter = 1;
	int objectID;
	private Stroke debugStroke = new BasicStroke(4.0f);
	private Color boundingBoxColor = Color.DARK_GRAY;
	private int crossSize = 3;
	private Color boundingSphereColor = Color.GREEN;
	private Color debugFontColor = Color.BLACK;
	private Font debugFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);

	/**
	 * physical attributes
	 */
	// unit pixel
	protected Point2D.Double position = new Point2D.Double(0.0, 0.0);
	protected Point2D.Double size = new Point2D.Double(0.0, 0.0);
	protected Point2D.Double center = new Point2D.Double(0.0, 0.0);
	protected double radius = 0.0;
	protected Rectangle2D.Double boundingBox = new Rectangle2D.Double(0.0, 0.0,
			1.0, 1.0);
	// unit: pixels per millisecond
	protected Point2D.Double velocity = new Point2D.Double(0.0, 0.0);
	protected double orientation = 0.0;
	protected double spin = 0.0;

	public PhysicalObject(JFrameDemo frame) {
		this.containerComponent = frame;
		this.objectID = idCounter++;
	}

	/**
	 * guess values of all object attributes
	 */
	public void setRandomParameters() {
		// put the object somewhere on the screen
		double x = Tools.getPositiveDouble(containerComponent.canvasWidth);
		double y = Tools.getPositiveDouble(containerComponent.canvasHeight);
		setPosition(x, y);
		// limit the size of the object
		double width = Tools
				.getPositiveDouble(containerComponent.canvasWidth / 2);
		double height = Tools
				.getPositiveDouble(containerComponent.canvasHeight / 2);
		setSize(width, height);
		// set speed
		final double MAX_VELOCITY = 100.0;
		double dx = Tools.getSign() * Tools.getPositiveDouble(MAX_VELOCITY);
		double dy = Tools.getSign() * Tools.getPositiveDouble(MAX_VELOCITY);
		setVelocity(dx, dy);
		// orientation and spin
		// final double MAX_SPIN = 10.0;
		// orientation = Math.toRadians(Tools.getPositiveDouble(360));
		// spin = Math.toRadians(Tools.getSign()
		// * Tools.getPositiveDouble(MAX_SPIN));
	}

	/**
	 * summarize the object's attributes
	 */
	public void debugInfo() {
		String msg = "object " + objectID + " position: ("
				+ Math.round(position.x) + "," + Math.round(position.y) + ")"
				+ " center: (" + Math.round(center.x) + ","
				+ Math.round(center.y) + ")" + " size: (" + Math.round(size.x)
				+ "," + Math.round(size.y) + ")" + " radius: "
				+ Math.round(radius) + " velocity: (" + Math.round(velocity.x)
				+ "," + Math.round(velocity.y) + ")";
		System.out.println(msg);
	}

	/**
	 * setter function to physical attributes
	 */

	public void setSize(double width, double height) {
		size.x = width;
		size.y = height;
		double halfX = size.x / 2.0;
		double halfY = size.y / 2.0;
		center.x = position.x + halfX;
		center.y = position.y + halfY;
		double diagonal = Math.sqrt(size.x * size.x + size.y * size.y);	
		radius = diagonal / 2.0;
	}

	public void setPosition(double x, double y) {
		position.x = x;
		position.y = y;
		center.x = position.x + (size.x / 2.0);
		center.y = position.y + (size.y / 2.0);
	}

	public void setVelocity(double dx, double dy) {
		velocity.x = dx;
		velocity.y = dy;
	}

	/**
	 * accessing bounding Box
	 */

	public Rectangle2D.Double getBoundingBox() {
		boundingBox.setRect(position.x, position.y, size.x, size.y);
		return boundingBox;
	}

	public void updateBoundingBox() {
		boundingBox.setRect(position.x, position.y, size.x, size.y);
	}

	/**
	 * vector-based physics
	 */

	public void stopMoving() {
		velocity.x = 0.0;
		velocity.y = 0.0;
	}

	/**
	 * updates this sprite's animation and its position based on the velocity.
	 */
	public void update(double elapsedTime, boolean verbose) {
		double factor = elapsedTime / 1000.0;
		double dx = velocity.x * factor;
		double dy = velocity.y * factor;
		if (verbose) {
			System.out.println(" shift: (" + dx + "," + dy + ")");
		}
		position.x = position.x + dx;
		position.y = position.y + dy;
		center.x = center.x + dx;
		center.y = center.y + dy;

		// double deltaSpin = spin * factor;
		// orientation = orientation + deltaSpin;
	}

	/**
	 * draw the default shape to debug the game
	 */
	public void draw(Graphics2D canvas) {
		canvas.setStroke(debugStroke);
		// display bounding boxes
		canvas.setColor(boundingBoxColor);
		canvas.drawRect((int) position.x, (int) position.y, (int) size.x,
				(int) size.y);
		// display bounding sphere and a cross for the center
		canvas.setColor(boundingSphereColor);
		canvas.drawLine((int) center.x - crossSize, (int) center.y,
				(int) center.x + crossSize, (int) center.y);
		canvas.drawLine((int) center.x, (int) center.y - crossSize,
				(int) center.x, (int) center.y + crossSize);
		double circleLeftUpperX = center.x - radius;
		double circleLeftUpperY = center.y - radius;
		canvas.drawOval((int) circleLeftUpperX, (int) circleLeftUpperY,
				(int) radius*2, (int) radius*2);
		// display object ID
		canvas.setColor(debugFontColor);
		canvas.setFont(debugFont);
		canvas.drawString("o" + objectID, (int) center.x, (int) center.y);
	}
}
