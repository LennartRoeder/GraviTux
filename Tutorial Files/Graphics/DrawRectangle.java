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
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class DrawRectangle extends JFrameDemo {

	public DrawRectangle() {
		initCanvas("Draw Rectangle Demo", 800, 600);
		setVisible(true);
	}

	private Rectangle2D.Double createRectangle() {
		int xPosition = Tools.getCenteredNumber(canvasWidth);
		int yPosition = Tools.getCenteredNumber(canvasHeight);
		int xSize = Tools.getNaturalNumber(canvasWidth / 2);
		int ySize = Tools.getNaturalNumber(canvasHeight / 2);
		return new Rectangle2D.Double(xPosition, yPosition, xSize, ySize);
	}

	/**
	 * draw rectangles
	 * 
	 * @param canvas
	 *            a handle to the canvas object
	 */
	public void drawRectangle(Graphics2D canvas) {
		Shape borderRectangle = createRectangle();
		Color borderColor = Tools.getColor();
		canvas.setColor(borderColor);
		canvas.fill(borderRectangle);

		Shape filledRectangle = createRectangle();
		Color fillColor = Tools.getColor();
		canvas.setColor(fillColor);
		canvas.draw(filledRectangle);
	}

	@Override
	public void paint(Graphics g) {
		// Graphics2D is a more powerful version of the Graphics class
		Graphics2D canvas = (Graphics2D) g;
		clearCanvas(canvas);
		drawRectangle(canvas);
	}

	public static void main(String[] args) {
		new DrawRectangle();
	}
}
