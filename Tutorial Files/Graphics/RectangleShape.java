/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 26, 2012
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PhysicalObject {

	private JFrameDemo containerComponent;

	// class variable to construct a unique ID for every thread
	private static int idCounter = 1;
	int objectID;

	// affine transformations summarize translations, scalings and rotations
	// a identity transformation does not affect position, size or rotation
	private AffineTransform identity = new AffineTransform();

	/**
	 * visual attributes just for debugging purposes
	 */
	private Shape shape = new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0);
	private Color fillColor = Color.GREEN;
	private Color debugFontColor = Color.BLACK;
	private Font debugFont = new Font("Arial", Font.ITALIC, 24);

	/**
	 * physical attributes
	 */
	// unit pixel
	protected Point2D.Double position = new Point2D.Double(0.0, 0.0);
	protected Point2D.Double size = new Point2D.Double(0.0, 0.0);
	protected Point2D.Double center = new Point2D.Double(0.0, 0.0);
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
		// visual attribute
		setFillColor(Tools.getColor());
	}

	public void debugInfo() {
		String msg = "object " + objectID + " position: ("
				+ Math.round(position.x) + "," + Math.round(position.y) + ")"
				+ " size: (" + Math.round(size.x) + "," + Math.round(size.y)
				+ ")" + " velocity: (" + Math.round(velocity.x) + ","
				+ Math.round(velocity.y) + ")";
		System.out.println(msg);
	}

	/**
	 * setter function to physical attributes
	 */

	public void setSize(double width, double height) {
		size.x = width;
		size.y = height;
	}

	public void setPosition(double x, double y) {
		position.x = x;
		position.y = y;
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
		center.x = (size.x - position.x) / 2;
		center.y = (size.y - position.y) / 2;
	}

	/**
	 * setter function to visual attributes
	 */

	public void setFillColor(Color color) {
		fillColor = color;
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
		// double deltaSpin = spin * factor;
		// orientation = orientation + deltaSpin;
	}

	private void displayDebugText(Graphics2D canvas, String msg, int x, int y) {
		canvas.setTransform(identity);
		canvas.setColor(debugFontColor);
		canvas.setFont(debugFont);
		canvas.drawString(msg, x, y);
	}

	/**
	 * 
	 */
	public void draw(Graphics2D canvas) {
		AffineTransform saved = canvas.getTransform();
		// reset previous changes
		canvas.setTransform(identity);
		// apply transformation
		canvas.translate(position.x, position.y);
		canvas.scale(size.x, size.x);
		canvas.rotate(orientation);
		canvas.setColor(fillColor);
		// draw the default shape to debug the game
		canvas.fill(shape);
		displayDebugText(canvas, "o" + objectID, (int) center.x, (int) center.y);
		// reset transformations
		canvas.setTransform(saved);
	}
}
