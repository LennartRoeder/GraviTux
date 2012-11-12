package Tools;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 *
 * @version October 26, 2012
 */

import java.awt.*;
import java.util.Random;

public final class NumberGenerator {

    private static Random randomGenerator = new Random();

    public static Color getColor() {
        int red = getNaturalNumber(256);
        int green = getNaturalNumber(256);
        int blue = getNaturalNumber(256);
        return new Color(red, green, blue);
    }

    public static int getNaturalNumber() {
        int result = Math.abs(randomGenerator.nextInt());
        return result;
    }

    public static int getNaturalNumber(int maximum) {
        int result = Math.abs(randomGenerator.nextInt()) % maximum;
        return result;
    }

    public static int getCenteredNumber(int maximum) {
        int center = maximum / 2;
        int offset = randomGenerator.nextInt() % (maximum / 2);
        return center + offset;
    }

    public static int getInteger() {
        int result = randomGenerator.nextInt();
        return result;
    }

    public static int getInteger(int maximum) {
        int result = randomGenerator.nextInt() % maximum;
        return result;
    }

    /**
     * Random.nextDouble returns a double value in the interval [0.0, 1.0).
     *
     * @param scaleFactor maximal return value
     * @return result a pseudo random double in the interval [0.0, scaleFactor)
     */
    public static double getPositiveDouble(double scaleFactor) {
        double result = Math.abs(randomGenerator.nextDouble()) * scaleFactor;
        return result;
    }

    public static double getDouble(double scaleFactor) {
        double result = randomGenerator.nextDouble() * scaleFactor;
        return result;
    }

    public static int getSign() {
        double number = randomGenerator.nextDouble();
        if (number <= 0.5) {
            return 1;
        } else {
            return -1;
        }
    }
}
