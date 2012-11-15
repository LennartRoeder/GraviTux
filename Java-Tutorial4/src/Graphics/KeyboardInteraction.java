package Graphics;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 *
 * @version October 26, 2012
 */

import GUI.JFrameDemo;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class KeyboardInteraction extends JFrameDemo implements KeyListener {

    private BufferedImage backBuffer = null;
    private Graphics2D backCanvas = null;

    private Rectangle2D rectangleShape;
    private Color fillColor;
    private int xPosition, yPosition;
    private int deltaX = 0;
    private int rectangleWidth, rectangleHeight;

    private boolean traceKeyEvents = false;

    // affine transformations summarize translations, scalings and rotations
    // a identity transformation does not affect position, size or rotation
    private AffineTransform identity = new AffineTransform();

    public KeyboardInteraction() {
        initBackBuffer();
        initCanvas("Keyboard Interaction Demo", 800, 600);
        initRectangle();
        addKeyListener(this);
        repaint();
        setVisible(true);
    }

    /**
     * The function create the double buffer, an image, on which all draw
     * operation work. In order to be used as a canvas, the backbuffer image has
     * to be converted into a Graphics2D object.
     */
    private void initBackBuffer() {
        backBuffer = new BufferedImage(canvasWidth, canvasHeight,
                BufferedImage.TYPE_INT_RGB);
        backCanvas = backBuffer.createGraphics();
    }

    private void initRectangle() {
        fillColor = Color.WHITE;
        rectangleWidth = 100;
        rectangleHeight = 100;
        // place they rectangle centered on the canvas
        xPosition = (canvasWidth - rectangleWidth / 2) / 2;
        yPosition = (canvasHeight - rectangleHeight / 2) / 2;
        // the translation is done with affine transformations
        rectangleShape = new Rectangle2D.Double(0, 0, rectangleWidth,
                rectangleHeight);
    }

    /**
     * draw rectangles
     *
     * @param canvas a handle to the canvas object
     */
    private void drawRectangle(Graphics2D canvas) {
        // reset previous changes
        canvas.setTransform(identity);
        // apply transformation
        canvas.translate(xPosition, yPosition);
        canvas.setColor(fillColor);
        canvas.fill(rectangleShape);
    }

    @Override
    public void paint(Graphics g) {
        // Graphics2D is a more powerful version of the Graphics class
        Graphics2D canvas = (Graphics2D) g;

        if (backCanvas != null && backBuffer != null) {
            clearCanvas(backCanvas);
            drawRectangle(backCanvas);
            canvas.drawImage(backBuffer, 0, 0, this);
        } else {
            System.out.println("BackBuffer not yet initialized");
        }
    }

    private void traceKeyEvents(KeyEvent e) {
        if (traceKeyEvents) {
            String msg = "keyCode: " + e.getKeyCode() + " keyChar: "
                    + e.getKeyChar();
            System.out.println(msg);
        }
    }

    /**
     * methods defined by the interfaces KeyListener and MouseListener
     */

    @Override
    public void keyPressed(KeyEvent e) {
        traceKeyEvents(e);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_NUMPAD4:
            case KeyEvent.VK_A:
                deltaX--;
                xPosition = xPosition + deltaX;
                if (xPosition < 0)
                    xPosition = canvasWidth;
                repaint();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_NUMPAD6:
            case KeyEvent.VK_D:
                deltaX++;
                xPosition = xPosition + deltaX;
                if (xPosition > canvasWidth)
                    xPosition = 0;
                repaint();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        deltaX = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * entry point
     */
    public static void main(String[] args) {
        new KeyboardInteraction();
    }
}
