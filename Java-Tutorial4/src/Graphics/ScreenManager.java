package Graphics;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 26, 2012
 */

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.DisplayMode;
import java.awt.Window;
import javax.swing.JFrame;

/**
 * The SimpleScreenManager class manages initializing and displaying full screen
 * graphics modes.
 */
public class ScreenManager {

	// device properties
	private GraphicsDevice screenDevice;

	public ScreenManager() {
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		screenDevice = environment.getDefaultScreenDevice();
	}

	public void setFullScreen(DisplayMode displayMode, JFrame window) {
		window.setUndecorated(true);
		window.setResizable(false);

		screenDevice.setFullScreenWindow(window);
		if (displayMode != null && screenDevice.isDisplayChangeSupported()) {
			try {
				screenDevice.setDisplayMode(displayMode);
			} catch (IllegalArgumentException ex) {
				// ignore - illegal mode for this device
			}
		}
	}

	/**
	 * Returns the window currently used in full screen mode.
	 */
	public Window getFullScreenWindow() {
		return screenDevice.getFullScreenWindow();
	}

	/**
	 * Restores the screen's display mode.
	 */
	public void restoreScreen() {
		Window window = screenDevice.getFullScreenWindow();
		if (window != null) {
			window.dispose();
		}
		screenDevice.setFullScreenWindow(null);
	}

}
