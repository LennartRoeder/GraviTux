package Game;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 *
 * @version November 10, 2012
 */

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import static Tools.Traces.*;

public class Stage {

	private GameDemo frame = null;
	private Game game = null;
	private String name = null;

	private ArrayList<GameObject> activeObjects = new ArrayList<GameObject>();
	private ArrayList<GameObject> inactiveObjects = new ArrayList<GameObject>();

	public Stage(Game game, String name) {
		this.game = game;
		this.name = name;
		this.frame = game.getFrame();
		if (is(TraceFlag.CONSTRUCTORS)) {
			System.out.println("Initialize Stage " + name);
			System.out.println("\tgame: " + game);
			System.out.println("\tframe: " + frame);
		}
	}

	public GameDemo getFrame() {
		return frame;
	}

	public String getName() {
		return name;
	}

	public void addGameObject(GameObject gameObject) {
		if (gameObject.isActive()) {
			activeObjects.add(gameObject);
		} else {
			inactiveObjects.add(gameObject);
		}
	}

	public void addPlayer(int limit) {
		for (int index = 0; index < limit; index++) {
			GameObject object = new GameObject(this);
			object.setRandomParameters();
			activeObjects.add(object);
			if (is(TraceFlag.ASSETS)) {
				System.out.println("\tadd object: " + object.getID());
				object.debugInfo();
				System.out.println("\tactiveObjects: " + activeObjects.size());
			}
		}
	}

	public void handleMouseEvent(MouseEvent e) {
		for (GameObject gameObject : activeObjects) {
			gameObject.handleMouseEvent(e);
		}
	}

	/**
	 * collision detection with bounding boxes
	 */
	public void handleCollisions() {
		Rectangle2D boundingBox1;
		Rectangle2D boundingBox2;
		int limit = activeObjects.size();
		if (is(TraceFlag.COLLISIONS)) {
			System.out.println("\tcollision detection with " + limit
					+ " objects");
		}
		for (int i1 = 0; i1 < limit; i1++) {
			GameObject o1 = activeObjects.get(i1);
			boundingBox1 = o1.getBoundingBox();
			for (int i2 = i1 + 1; i2 < limit; i2++) {
				GameObject o2 = activeObjects.get(i2);
				boundingBox2 = o2.getBoundingBox();
				if (boundingBox1.intersects(boundingBox2)) {
					if (is(TraceFlag.COLLISIONS)) {
						System.out.println("\tcollision: " + o1.getID() + " - "
								+ o2.getID());
					}
					o1.handleCollisionWith(o2);
					o2.handleCollisionWith(o1);
				}
			}
		}
	}

	/**
	 * update the physical and visual status of all game objects elapsedTime in
	 * msec since last frame
	 */
	public void updateGameStatus(double elapsedTime) {
		for (GameObject object : activeObjects) {
			if (is(TraceFlag.UPDATES)) {
				object.debugInfo();
			}
			object.update(elapsedTime);
		}
	}

	/**
	 * All draw / paint operations are double buffered
	 */
	public void draw(Graphics2D canvas) {
		for (GameObject object : inactiveObjects) {
			if (is(TraceFlag.DRAWS)) {
				System.out.println("\tdraw inactive object: " + object.getID());
			}
			object.draw(canvas);
		}
		for (GameObject object : activeObjects) {
			if (is(TraceFlag.DRAWS)) {
				System.out.println("\tdraw active object: " + object.getID());
			}
			object.draw(canvas);
		}
	}

	public void handleMousePressedEvent(MouseEvent e) {
		game.stopGame();
	}

	public void handleKeyPressedEvent(KeyEvent e) {
		game.stopGame();		
	}

	public void handleKeyReleasedEvent(KeyEvent e) {
		game.stopGame();
	}
}
