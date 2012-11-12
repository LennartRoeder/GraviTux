package Graphics;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 *
 * @version October 26, 2012
 */

import GUI.JFrameDemo;
import Tools.AssetLoader;

import javax.sound.sampled.Clip;
import java.awt.*;

@SuppressWarnings("serial")
public class SoundDemo extends JFrameDemo {

    private AssetLoader assetLoader = null;
    private Clip gongClip = null;

    public SoundDemo() {
        initCanvas("Sound Demo", 800, 600);
        assetLoader = new AssetLoader(this);
        gongClip = assetLoader.loadSound("Sounds/gong.wav");
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        // Graphics2D is a more powerful version of the Graphics class
        Graphics2D canvas = (Graphics2D) g;
        clearCanvas(canvas);
    }

    public static void main(String[] args) {
        SoundDemo demo = new SoundDemo();
        demo.gongClip.start();
    }

}
