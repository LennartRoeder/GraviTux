package Game;

/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version November 10, 2012
 */

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import static Tools.Traces.*;

public class Game implements Runnable {

	private GameDemo frame = null;
	private ArrayList<Stage> stages = new ArrayList<Stage>();
	private Stage activeStage = null;

	private Thread gameLoop;
	private boolean runGameLoop = true;
	private float animationFrequency = 60.0f;
	private int frameTime = Math.round(1000 / animationFrequency);
	long lastTime;

	public Game(GameDemo frame) {
		this.frame = frame;
		if (is(TraceFlag.CONSTRUCTORS)) {
			System.out.println("Initialize Game");
			System.out.println("\tframe: " + frame);
			System.out.println("\tactive stage: " + activeStage);
		}
	}

	public GameDemo getFrame() {
		return frame;
	}

	public Stage addStage(String name) {
		Stage stage = new Stage(this, name);
		stages.add(stage);
		activeStage = stage;
		return stage;
	}

	public Stage getActiveStage() {
		return activeStage;
	}

	public void setActiveStage(Stage stage) {
		activeStage = stage;
	}

	public void startGame() {
		gameLoop = new Thread(this);
		lastTime = System.currentTimeMillis();
		gameLoop.start();
	}

	public void stopGame() {
		runGameLoop = false;
	}

	/**
	 * gameLoop
	 */
	@Override
	public void run() {
		while (runGameLoop) {
			if (traced()) {
				System.out.println("game loop:");
			}
			updateGameStatus();
			handleCollisions();
			frame.repaint();
			if (is(TraceFlag.DRAWS)) {
				System.out.println("\trepaint");
			}
			try {
				Thread.sleep(frameTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * update the physical and visual status of all game objects
	 */
	public void updateGameStatus() {
		long currentTime = System.currentTimeMillis();
		double elapsedTime = (double) (currentTime - lastTime);
		if (is(TraceFlag.TIMING)) {
			System.out.println("\telapsedTime: " + elapsedTime + " msec");
		}
		if (activeStage != null) {
			if (is(TraceFlag.UPDATES)) {
				System.out.println("\tupdate objects at stage: "
						+ activeStage.getName());
			}
			activeStage.updateGameStatus(elapsedTime);
		} else {
			System.out.println("no active stage available");
		}
		lastTime = currentTime;
	}

	/**
	 * 
	 */
	public void handleCollisions() {
		if (activeStage != null) {
			if (is(TraceFlag.COLLISIONS)) {
				System.out.println("\thandle collisions at stage: "
						+ activeStage.getName());
			}
			activeStage.handleCollisions();
		} else {
			System.out.println("no active stage available");
		}
	}

	/**
	 * All draw / paint operations are double buffered
	 */
	public void draw(Graphics2D canvas) {
		if (activeStage != null) {
			if (is(TraceFlag.DRAWS)) {
				System.out.println("\tdraw stage: " + activeStage.getName());
			}
			activeStage.draw(canvas);
		} else {
			System.out.println("no active stage available");
		}
	}

	public void handleMousePressedEvent(MouseEvent e) {
		if (activeStage != null) {
			if (is(TraceFlag.EVENTS)) {
				System.out.println("\thandle mouse event at stage: "
						+ activeStage.getName());
			}
			activeStage.handleMousePressedEvent(e);
		} else {
			System.out.println("no active stage available");
		}
	}

	public void handleKeyPressedEvent(KeyEvent e) {
		if (activeStage != null) {
			if (is(TraceFlag.EVENTS)) {
				System.out.println("\thandle mouse event at stage: "
						+ activeStage.getName());
			}
			activeStage.handleKeyPressedEvent(e);
		} else {
			System.out.println("no active stage available");
		}
	}

	public void handleKeyReleasedEvent(KeyEvent e) {
		if (activeStage != null) {
			if (is(TraceFlag.EVENTS)) {
				System.out.println("\thandle mouse event at stage: "
						+ activeStage.getName());
			}
			activeStage.handleKeyReleasedEvent(e);
		} else {
			System.out.println("no active stage available");
		}
	}
}
