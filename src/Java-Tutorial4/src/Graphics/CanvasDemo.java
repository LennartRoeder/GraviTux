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


@SuppressWarnings("serial")
public class CanvasDemo extends JFrameDemo {

    public CanvasDemo() {
        initCanvas("Canvas Demo", 800, 600);
        setVisible(true);
    }

    @Override
    public void paint(Graphics canvas) {
        clearCanvas(canvas);

        canvas.setColor(Color.WHITE);
        // coordinates of the starting and end point
        canvas.drawLine(100, 100, 600, 600);

        // RBG (additive) color definition:
        // 0.0f minimal intensity
        // 1.0f maximal intensity
        Color c = NumberGenerator.getColor();
        canvas.setColor(c);
        // rectangle definition: coordinates of the left upper corner +
        // dimensions
        canvas.fillRect(200, 200, 100, 200);

        // text display
        canvas.setColor(Color.RED);
        canvas.setFont(new Font("Tahoma", Font.ITALIC, 30));
        canvas.drawString("Hello World!", 30, 60);
    }

    public static void main(String[] args) {
        new CanvasDemo();
    }
}
