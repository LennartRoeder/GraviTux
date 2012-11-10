/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 14, 2012
 */

public class ClassDemo extends Object {
	// attributes
	private int onlyForInternalUsage;
	public int canBeUsedByExternalClasses;

	// constructor
	public ClassDemo() {
		onlyForInternalUsage = 0;
		canBeUsedByExternalClasses = 0;
		System.out.println("Hello Class " + onlyForInternalUsage + " "
				+ canBeUsedByExternalClasses);
	}

	// starting point ... simply hand over to the constructor
	public static void main(String[] args) {
		new ClassDemo();
	}

}
