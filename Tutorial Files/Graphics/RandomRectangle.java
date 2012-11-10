/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 26, 2012
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class RandomRectangle extends JFrameDemo {

	// a single rectangle shape will be reused again and again
	// global variables to control the maximal size of a rectangle and the
	// number of rectangles
	Shape rectangleShape = new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0);
	private final double MAX_RECTANGLE_SIZE = 200.0;
	private final int MAX_RECTANGLES = 50;
	// affine transformations summarize translations, scalings and rotations
	// a identity transformation does not affect position, size or rotation
	private AffineTransform identity = new AffineTransform();

	public RandomRectangle() {
		initCanvas("Random Rectangle", 800, 600);
		setVisible(true);
	}
	
	/**
	 * Fills the background of the canvas with a color defined in
	 * backgroundColor.
	 * 
	 * @param canvas
	 *            a handle to the canvas object
	 */
	public void clearCanvas(Graphics2D canvas) {
		// fill background
		canvas.setColor(backgroundColor);
		canvas.fillRect(0, 0, canvasWidth, canvasHeight);
	}

	/**
	 * draw rectangles
	 * 
	 * @param canvas
	 *            a handle to the canvas object
	 */
	public void drawRectangle(Graphics2D canvas) {
		Color fillColor = Tools.getColor();
		int xPosition = Tools.getNaturalNumber(canvasWidth);
		int yPosition = Tools.getNaturalNumber(canvasHeight);
		double xScale = Tools.getPositiveDouble(MAX_RECTANGLE_SIZE);
		double yScale = Tools.getPositiveDouble(MAX_RECTANGLE_SIZE);
		double angle = Math.toRadians(Tools.getPositiveDouble(360));

		// reset previous changes 
		canvas.setTransform(identity);
		// apply transformation
		canvas.translate(xPosition, yPosition);
		canvas.scale(xScale, yScale);
		canvas.rotate(angle);
		canvas.setColor(fillColor);
		canvas.fill(rectangleShape);
	}

	@Override
	public void paint(Graphics g) {
		// Graphics2D is a more powerful version of the Graphics class
		Graphics2D canvas = (Graphics2D) g;
		clearCanvas(canvas);
		for (int counter = 0; counter < MAX_RECTANGLES; counter++) {
			drawRectangle(canvas);
		}
	}

	public static void main(String[] args) {
		new RandomRectangle();
	}
}
