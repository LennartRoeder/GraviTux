package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version November 10, 2012
 */

public class GameEventHandler implements KeyListener, MouseListener {

	private Game game = null;

	public GameEventHandler(Game game) {
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		game.handleKeyPressedEvent(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		game.handleKeyReleasedEvent(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		game.handleMousePressedEvent(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
