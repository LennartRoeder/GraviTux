package Game;

/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 *         Courtesy of the ship image to Jonathan S. Harbour
 * @see http://jharbour.com/wordpress/portfolio/beginning-java-se-6-game-programming-3rd-ed/
 * 
 * @version November 10, 2012
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import GUI.JFrameDemo;
import Tools.Traces;
import Tools.Traces.TraceFlag;

@SuppressWarnings("serial")
public class GameDemo extends JFrameDemo {

	private BufferedImage backBuffer = null;
	private Graphics2D backCanvas = null;
	private Game game = null;

	public GameDemo() {
		setBackgroundColor(Color.WHITE);
		initCanvas("Game Demo", 800, 600);
		initBackBuffer();
		initTrace();
		game = new Game(this);
		createLevels();
		GameEventHandler eventHandler = new GameEventHandler(game);
		addKeyListener(eventHandler);
		addMouseListener(eventHandler);
		game.startGame();
		setVisible(true);
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

	/**
	 * All draw / paint operations must work with a hidden workingCanvas that
	 * contains the backbuffer image. Finally, the backbuffer image is
	 * displayed.
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D canvas = (Graphics2D) g;
		if (backCanvas != null && backBuffer != null && game != null) {
			clearCanvas(backCanvas);
			game.draw(backCanvas);
			canvas.drawImage(backBuffer, 0, 0, this);
		} else
			System.out.println("System not yet ready");
	}

	private void createLevels() {
		if (game != null) {
			Stage stage = game.addStage("Test Level");
			stage.addPlayer(5);
		} else {
			System.out.println("game not yet initialized");
		}
	}

	private void initTrace() {
		Traces.clearFlags();
		Traces.traceOn();
		//Traces.set(TraceFlag.ASSETS);	
		//Traces.set(TraceFlag.COLLISIONS);	
		//Traces.set(TraceFlag.CONSTRUCTORS);	
		//Traces.set(TraceFlag.DRAWS);	
		//Traces.set(TraceFlag.EVENTS);	
		Traces.set(TraceFlag.TIMING);
		Traces.set(TraceFlag.UPDATES);	
		System.out.print("trace flags: "); 
		Traces.listFlags();
		System.out.println();
	}

	/**
	 * The entry point
	 */
	public static void main(String[] args) {
		new GameDemo();
	}
}
