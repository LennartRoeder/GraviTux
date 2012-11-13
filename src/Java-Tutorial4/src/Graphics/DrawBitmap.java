package Graphics;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 *
 * Courtesy of the castle image to Reiner Prokein
 * @see http://www.reinerstilesets.de/de/2d-grafiken/2d-buildings/
 *
 * @version October 28, 2012
 */

import GUI.JFrameDemo;
import Tools.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class DrawBitmap extends JFrameDemo {

    private AssetLoader assetLoader = null;
    private BufferedImage image = null;

    /**
     * the loadImage method streams the image ... so force a repaint
     */
    public DrawBitmap() {
        initCanvas("Draw Bitmap Demo", 800, 600);
        assetLoader = new AssetLoader(this);
        image = assetLoader.loadImage("Images/castle.png");
        repaint();
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        // Graphics2D is a more powerful version of the Graphics class
        Graphics2D canvas = (Graphics2D) g;
        clearCanvas(canvas);
        if (image != null) {
            canvas.drawImage(image, 0, 40, this);
        } else {
            System.out.println("frame not yet loaded");
        }
    }

    public static void main(String[] args) {
        new DrawBitmap();
    }
}
