/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 26, 2012
 */

public class GameObject extends PhysicalObject {

	/**
	 * reference to the visual representation (vector graphics, sprite, sprite
	 * animation)
	 */
	@SuppressWarnings("unused")
	private Skin skin = null;

	public GameObject(JFrameDemo frame) {
		super(frame);
	}

	public void handleCollisionWith(GameObject collider) {
		stopMoving();
	}
}
