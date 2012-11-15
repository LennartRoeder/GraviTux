package Game;

/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version November 10, 2012
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import Tools.GraphTools;
import Tools.NumberGenerator;
import Tools.Traces.TraceFlag;
import static Tools.Traces.*;

public class PhysicalObject {

	private Stage stage;

	/**
	 * attributes just for debugging purposes
	 */
	// class variable to construct a unique ID for every thread
	private static int idCounter = 1;
	private int objectID;

	/**
	 * physical attributes
	 */
	// unit pixel
	private Point2D.Double position = new Point2D.Double(0.0d, 0.0d);
	private Point2D.Double size = new Point2D.Double(0.0d, 0.0d);
	private double orientation = 0.0d;
	private double spin = 0.0d;

	private final double MAX_SPIN = 100.0d;

	private Point2D.Double center = new Point2D.Double(0.0d, 0.0d);
	private double radius = 0.0d;
	private Rectangle2D.Double boundingBox = new Rectangle2D.Double(0.0d, 0.0d,
			1.0d, 1.0d);

	// unit: pixels per millisecond
	private Point2D.Double velocity = new Point2D.Double(0.0d, 0.0d);
	private Point2D.Double acceleration = new Point2D.Double(0.0d, 0.0d);
	private Point2D.Double momentum = new Point2D.Double(0.0d, 0.0d);
	private double density = 0.0d; // unit g per cm^3

	private final double MAX_VELOCITY = 100.0d;
	private final double MAX_ACCELERATION = 100.0d;
	private final double MAX_MOMENTUM = 100.0d;

	private double age = 0.0d;
	private double lifeTime = 0.0d;	
	private final double MAX_LIFETIME = 100.0;

	public PhysicalObject(Stage stage) {
		this.stage = stage;
		this.objectID = idCounter++;
	}

	/**
	 * guess values of all object attributes
	 */
	public void setRandomParameters() {
		// put the object somewhere on the screen
		GameDemo frame = stage.getFrame();
		int screenWidth = frame.getWidth();
		int screenHeight = frame.getHeight();
		double x = NumberGenerator.getPositiveDouble(screenWidth);
		double y = NumberGenerator.getPositiveDouble(screenHeight);
		setPosition(x, y);
		// limit the size of the object
		double width = NumberGenerator.getPositiveDouble(screenWidth / 2);
		double height = NumberGenerator.getPositiveDouble(screenHeight / 2);
		setSize(width, height);
		// orientation and spin
		double degree = NumberGenerator.getPositiveDouble(360);
		setOrientation(degree);
		double rotationSpeed = NumberGenerator.getSign()
				* NumberGenerator.getPositiveDouble(MAX_SPIN);
		setSpin(rotationSpeed);
		// set physical attributes
		double dx = NumberGenerator.getSign()
				* NumberGenerator.getPositiveDouble(MAX_VELOCITY);
		double dy = NumberGenerator.getSign()
				* NumberGenerator.getPositiveDouble(MAX_VELOCITY);
		setVelocity(dx, dy);
		dx = NumberGenerator.getSign()
				* NumberGenerator.getPositiveDouble(MAX_ACCELERATION);
		dy = NumberGenerator.getSign()
				* NumberGenerator.getPositiveDouble(MAX_ACCELERATION);
		setAcceleration(dx, dy);
		dx = NumberGenerator.getSign()
				* NumberGenerator.getPositiveDouble(MAX_MOMENTUM);
		dy = NumberGenerator.getSign()
				* NumberGenerator.getPositiveDouble(MAX_MOMENTUM);
		setMomentum(dx, dy);
		setDensity(0.8); // wood [0.4 .. 0.8]
		// parameter for particle system
		double msec = NumberGenerator.getPositiveDouble(MAX_LIFETIME);
		setLifeTime(msec);
	}

	public int getID() {
		return objectID;
	}

	/**
	 * summarize the object's attributes
	 */
	public void debugInfo() {
		System.out.format("  object %d\n", objectID);
		System.out.format("\tposition: (%3.0f, %3.0f)", position.x, position.y);
		System.out.format("  center: (%3.0f, %3.0f)", center.x, center.y);
		System.out.format("  size: (%3.0f, %3.0f)", size.x, size.y);
		System.out.format("  radius: %3.0f\n", radius);
		System.out.format("\tvelocity: (%3.0f, %3.0f)", velocity.x, velocity.y);
		System.out.format("  acceleration: (%3.0f, %3.0f)\n", acceleration.x,
				acceleration.y);
		System.out.format("\tmomentum: (%3.0f, %3.0f)", momentum.x, momentum.y);
		System.out.format("  mass: %3.1f\n", getMass());
		System.out.format("\torientation: %3.1f", orientation);
		System.out.format("  spin: %3.1f\n", spin);
		System.out.format("\tage: %3.1f  lifeTime: %3.1f\n", age, lifeTime);		
	}

	/**
	 * getter and setter function to spatial attributes
	 */

	public void setPosition(double x, double y) {
		position.x = x;
		position.y = y;
		center.x = position.x + (size.x / 2.0);
		center.y = position.y + (size.y / 2.0);
	}

	public Point2D getPosition() {
		return position;
	}

	public double getX() {
		return position.x;
	}

	public double getY() {
		return position.y;
	}

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

	public Point2D getSize() {
		return size;
	}

	public double getWidth() {
		return size.x;
	}

	public double getHeight() {
		return size.y;
	}

	public void setOrientation(double degree) {
		orientation = Math.toRadians(degree);
	}

	public double getOrientation() {
		return orientation;
	}

	public void setSpin(double speed) {
		spin = speed;
	}

	public double getSpin() {
		return spin;
	}

	/**
	 * getter and setter function to physical attributes
	 */

	public void setVelocity(double dx, double dy) {
		velocity.x = dx;
		velocity.y = dy;
	}

	public Point2D.Double getVelocity() {
		return velocity;
	}

	public void setAcceleration(double dx, double dy) {
		acceleration.x = dx;
		acceleration.y = dy;
	}

	public Point2D.Double getAcceleration() {
		return acceleration;
	}

	public void setMomentum(double dx, double dy) {
		momentum.x = dx;
		momentum.y = dy;
	}

	public Point2D.Double getMomentum() {
		return momentum;
	}

	public double getVolume() {
		// volume of the bounding sphere
		return 4.0 / 3.0 * Math.PI * radius * radius * radius;
	}

	// mass = volume * density
	public double getMass() {
		return getVolume() * getDensity();
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getDensity() {
		return density;
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
	 * parameters for particle system
	 */

	public void setLifeTime(double msec) {
		lifeTime = msec;
	}

	public double getLifeTime() {
		return lifeTime;
	}

	/**
	 * vector-based physics
	 */

	public void stopMoving() {
		velocity.x = 0.0d;
		velocity.y = 0.0d;
		acceleration.x = 0.0d;
		acceleration.y = 0.0d;
		momentum.x = 0.0d;
		momentum.y = 0.0d;
	}

	/**
	 * updates this sprite's animation and its position based on the velocity.
	 */
	public void update(double elapsedTime) {
		double factor = elapsedTime / 1000.0;
		double dx = velocity.x * factor;
		double dy = velocity.y * factor;
		if (is(TraceFlag.UPDATES)) {
			System.out.format("\tshift: (%1.1f, %1.1f)\n", dx, dy);
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
	public void draw(Graphics g) {
		Graphics2D canvas = (Graphics2D) g;
		GraphTools.drawBoundingBox(canvas, boundingBox);
		// display bounding sphere and a cross for the center
		GraphTools.drawBoundingCircle(canvas, center, radius);
		GraphTools.drawText(canvas, "o" + objectID, center);
	}
}
