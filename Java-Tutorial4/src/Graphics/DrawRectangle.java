package Graphics;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 *
 * @version October 26, 2012
 */

import GUI.JFrameDemo;
import Tools.NumberGenerator;

import java.awt.*;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class DrawRectangle extends JFrameDemo {

    public DrawRectangle() {
        initCanvas("Draw Rectangle Demo", 800, 600);
        setVisible(true);
    }

    private Rectangle2D.Double createRectangle() {
        int xPosition = NumberGenerator.getCenteredNumber(canvasWidth);
        int yPosition = NumberGenerator.getCenteredNumber(canvasHeight);
        int xSize = NumberGenerator.getNaturalNumber(canvasWidth / 2);
        int ySize = NumberGenerator.getNaturalNumber(canvasHeight / 2);
        return new Rectangle2D.Double(xPosition, yPosition, xSize, ySize);
    }

    /**
     * draw rectangles
     *
     * @param canvas a handle to the canvas object
     */
    public void drawRectangle(Graphics2D canvas) {
        Shape borderRectangle = createRectangle();
        Color borderColor = NumberGenerator.getColor();
        canvas.setColor(borderColor);
        canvas.fill(borderRectangle);

        Shape filledRectangle = createRectangle();
        Color fillColor = NumberGenerator.getColor();
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
