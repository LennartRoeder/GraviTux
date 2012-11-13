package Graphics;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 *
 * @version October 26, 2012
 */

import Tools.AssetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StaticAnimation {

    private JFrame frame;
    private AssetLoader assetLoader = null;

    private ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
    private int currentSprite = 0;

    Point position = new Point(0, 0);

    public StaticAnimation(JFrame frame) {
        this.frame = frame;
        // center the sprite on canvas
        position.x = frame.getWidth() / 2;
        position.y = frame.getHeight() / 2;
        assetLoader = new AssetLoader(frame);
    }

    public void addSprite(String fileName) {
        BufferedImage sprite = assetLoader.loadImage(fileName);
        if (sprite == null) {
            System.out.println("Error: cant load image " + fileName);
        } else {
            sprites.add(sprite);
        }
    }

    public BufferedImage getCurrentSprite() {
        if (sprites.size() > 0) {
            return sprites.get(currentSprite);
        } else {
            return null;
        }
    }

    public void setNextSprite() {
        if (currentSprite < sprites.size() - 1) {
            currentSprite++;
        } else {
            currentSprite = 0;
        }
    }

    public void draw(Graphics2D canvas) {
        if (sprites.isEmpty()) {
            System.out.println("sprites not yet ready");
        } else {
            BufferedImage sprite = getCurrentSprite();
            if (sprite == null) {
                System.out.println("sprite #" + currentSprite
                        + " not yet ready");
            } else {
                // System.out.println("sprite: " + currentSprite );
                canvas.drawImage(sprite, position.x, position.y, frame);
                setNextSprite();
            }
        }
    }
}
