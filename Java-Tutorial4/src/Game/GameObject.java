package Game;

/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version November 10, 2012
 */

import java.awt.event.MouseEvent;

public class GameObject extends PhysicalObject {

	/**
	 * reference to the visual representation (vector graphics, sprite, sprite
	 * animation)
	 */
	
	@SuppressWarnings("unused")
	private Stage stage = null;
	@SuppressWarnings("unused")
	private Skin skin = null;
	private boolean activeFlag = false;
	
	public GameObject(Stage stage) {
		// call constructor of PhysicalObject
		super(stage);
		this.stage = stage;
	}

	public void handleCollisionWith(GameObject collider) {
		stopMoving();
	}

	public boolean isActive() {
		return activeFlag;
	}

	public void setActive() {
		activeFlag = true;
	}
	
	public void setPassive() {
		activeFlag = false;
	}

	public void handleMouseEvent(MouseEvent e) {
		
	}
}
