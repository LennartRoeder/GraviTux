/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 26, 2012
 */

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class AssetLoader {

	private MediaTracker mediaTracker = null;
	private JFrame frame = null;

	public AssetLoader(JFrame frame) {
		this.frame = frame;
		mediaTracker = new MediaTracker(frame);
	}

	public Clip loadSound(String fileName) {
		Clip clip = null;
		URL url;

		// file directory relative to the file structure of the class
		url = frame.getClass().getResource(fileName);
		System.out.println(url);
		AudioInputStream audioIn = null;
		try {
			audioIn = AudioSystem.getAudioInputStream(url);
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
		try {
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
		return clip;
	}

	/**
	 * the read-method streams the image ... so force a repaint
	 * 
	 * @param fileName
	 */
	public BufferedImage loadImage(String fileName) {
		BufferedImage image = null;
		URL url;
		try {
			// file directory relative to the file structure of the class
			url = frame.getClass().getResource(fileName);
			System.out.println(url);
			image = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
		mediaTracker.addImage(image, 1);
		try {
			mediaTracker.waitForAll();
		} catch (InterruptedException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
		return image;
	}

}
