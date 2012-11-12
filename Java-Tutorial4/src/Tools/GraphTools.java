package Tools;

/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 *
 *         Courtesy of the ship image to Jonathan S. Harbour
 * @see http://jharbour.com/wordpress/portfolio/beginning-java-se-6-game-programming-3rd-ed/
 *
 * @version October 26, 2012
 */

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public final class GraphTools {

    private static Stroke boundingBoxStroke = new BasicStroke(4.0f);
    private static Color boundingBoxColor = Color.DARK_GRAY;
    private static Stroke boundingCircleStroke = new BasicStroke(4.0f);
    private static int crossSize = 3;
    private static Color boundingCircleColor = Color.GREEN;
    private static Color debugFontColor = Color.BLACK;
    private static Font debugFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);

    public static void drawBoundingBox(Graphics2D canvas, Rectangle2D rectangle) {
        canvas.setStroke(boundingBoxStroke);
        canvas.setColor(boundingBoxColor);
        int x =  (int) Math.round(rectangle.getX());
        int y =  (int) Math.round(rectangle.getY());
        int width = (int) Math.round(rectangle.getWidth());
        int height = (int) Math.round(rectangle.getHeight());
        canvas.drawRect(x, y, width, height);
    }

    private static void drawCross(Graphics2D canvas, Point2D center) {
        int x = (int) Math.round(center.getX());
        int y = (int) Math.round(center.getY());
        canvas.drawLine(x - crossSize, y, x + crossSize, y);
        canvas.drawLine(x, y - crossSize, x, y + crossSize);
    }

    // display bounding sphere and a cross for the center
    public static void drawBoundingCircle(Graphics2D canvas, Point2D center, double radius) {
        canvas.setStroke(boundingCircleStroke);
        canvas.setColor(boundingCircleColor);
        int x = (int) Math.round(center.getX() - radius);
        int y = (int) Math.round(center.getY() - radius);
        int size = (int) Math.round(radius * 2);
        drawCross(canvas, center);
        canvas.drawOval(x, y, size, size);
    }

    public static void drawText(Graphics2D canvas, String message, Point2D center) {
        // display object ID
        canvas.setColor(debugFontColor);
        canvas.setFont(debugFont);
        int x = (int) Math.round(center.getX());
        int y = (int) Math.round(center.getY());
        canvas.drawString(message, x, y);
    }
}
