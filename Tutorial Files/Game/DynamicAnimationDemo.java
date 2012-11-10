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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DynamicAnimationDemo extends JFrameDemo implements Runnable,
		KeyListener, MouseListener {

	private BufferedImage backBuffer = null;
	private Graphics2D backCanvas = null;

	private Thread gameLoop;
	private boolean runGameLoop = true;
	private float animationFrequency = 60.0f;
	private int frameTime = Math.round(1000 / animationFrequency);

	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	long lastTime;

	boolean traceMovements = false;
	boolean traceStatus = true;
	boolean traceCollisions = true;
	boolean traces = traceMovements || traceStatus || traceCollisions;

	public DynamicAnimationDemo() {
		setBackgroundColor(Color.WHITE);
		initCanvas("Dynamic Animation Demo", 800, 600);
		initBackBuffer();
		addPlayer(3);
		addEventHandler();
		setVisible(true);
		startGameLoop();
	}

	/**
	 * The function create the double buffer, an image, on which all draw
	 * operation work. In order to be used as a canvas, the backbuffer image has
	 * to be converted into a Graphics2D object.
	 */
	private void initBackBuffer() {
		backBuffer = new BufferedImage(canvasWidth, canvasHeight,
				BufferedImage.TYPE_INT_RGB);
		backCanvas = backBuffer.createGraphics();
	}

	private void addEventHandler() {
		addKeyListener(this);
		addMouseListener(this);
	}

	public void addPlayer(int limit) {
		for (int index = 0; index < limit; index++) {
			GameObject object = new GameObject(this);
			object.setRandomParameters();
			gameObjects.add(object);
		}
	}

	private void startGameLoop() {
		gameLoop = new Thread(this);
		lastTime = System.currentTimeMillis();
		gameLoop.start();
	}

	/**
	 * gameLoop
	 */
	@Override
	public void run() {
		while (runGameLoop) {
			updateGameObjects();
			collisionDectection();
			repaint();
			try {
				Thread.sleep(frameTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void endGameLoop() {
		runGameLoop = false;
	}

	/**
	 * 
	 */
	private void collisionDectection() {
		Rectangle2D boundingBox1;
		Rectangle2D boundingBox2;
		int limit = gameObjects.size();
		for (int i1 = 0; i1 < limit; i1++) {
			GameObject o1 = gameObjects.get(i1);
			boundingBox1 = o1.getBoundingBox();
			for (int i2 = i1 + 1; i2 < limit; i2++) {
				GameObject o2 = gameObjects.get(i2);
				boundingBox2 = o2.getBoundingBox();
				if (boundingBox1.intersects(boundingBox2)) {
					if (traceCollisions) {
						System.out.println("collision: " + o1.objectID + " - "
								+ o2.objectID);
					}
					o1.handleCollisionWith(o2);
					o2.handleCollisionWith(o1);
				}
			}
		}
	}

	/**
	 * update the physical and visual status of all game objects
	 */
	private void updateGameObjects() {
		long currentTime = System.currentTimeMillis();
		double elapsedTime = (double) (currentTime - lastTime);

		if (traces) {
			System.out.println();
		}
		if (traceStatus) {
			System.out.println("elapsedTime: " + elapsedTime + " msec");
		}
		for (GameObject object : gameObjects) {
			if (traceStatus) {
				object.debugInfo();
			}
			object.update(elapsedTime, traceMovements);
		}
		lastTime = currentTime;
	}

	/**
	 * All draw / paint operations must work with a hidden workingCanvas that
	 * contains the backbuffer image. Finally, the backbuffer image is
	 * displayed.
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D canvas = (Graphics2D) g;
		if (backCanvas != null && backBuffer != null) {
			clearCanvas(backCanvas);
			for (PhysicalObject object : gameObjects) {
				object.draw(backCanvas);
			}
			canvas.drawImage(backBuffer, 0, 0, this);
		} else
			System.out.println("System not yet ready");
	}

	/**
	 * event handler
	 */

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		for (GameObject object : gameObjects) {
			object.stopMoving();
		}
		endGameLoop();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (GameObject object : gameObjects) {
			object.stopMoving();
		}
		endGameLoop();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * entry point
	 */

	public static void main(String[] args) {
		new DynamicAnimationDemo();
	}

}
