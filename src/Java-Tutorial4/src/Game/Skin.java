package Game;

/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version November 10, 2012
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Skin {

	// affine transformations summarize translations, scalings and rotations
	// a identity transformation does not affect position, size or rotation
	private AffineTransform identity = new AffineTransform();

	/**
	 * visual attributes just for debugging purposes
	 */
	private Shape shape = new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0);
	private Color fillColor = Color.GREEN;

	private boolean animated = false;
	private boolean visable = true;
	private int depth; // layer information

	PhysicalObject object = null;

	public Skin(PhysicalObject object) {
		this.object = object;
	}

	/**
	 * setter function to visual attributes
	 */

	public void setFillColor(Color color) {
		fillColor = color;
	}

	/**
	 * getter and setter functions
	 */

	public boolean getAnimated() {
		return animated;
	}

	public boolean getVisable() {
		return visable;
	}

	public void setVisable() {
		this.visable = true;
	}

	public void setHidden() {
		this.visable = false;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void draw(Graphics2D canvas) {
		if (object != null) {
			AffineTransform saved = canvas.getTransform();
			// reset previous changes
			canvas.setTransform(identity);
			// apply transformation
			canvas.translate(object.getX(), object.getY());
			canvas.scale(object.getWidth(), object.getHeight());
			canvas.rotate(object.getOrientation());
			canvas.setColor(fillColor);
			// draw the default shape to debug the game
			canvas.fill(shape);
			// reset transformations
			canvas.setTransform(saved);
		}
	}
}
