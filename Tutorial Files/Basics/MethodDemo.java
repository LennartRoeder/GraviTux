/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 14, 2012
 */

public class MethodDemo {

	// instance variable
	public int magicNumber = 42;

	// method with input and return values
	public int minimum(int x, int y) {
		int result;
		if (x <= y) {
			result = x;
		} else {
			result = y;
		}
		return result;
	}

	/**
	 * The entry point of the program. There must be exactly one class with a
	 * main method that initiate the control flow of the the program. Note that
	 * the main-method is static, i.e., that this is a class method. Hence, it
	 * can only access static (class) variables and NOT instance variables.
	 * Therefore, this method often just contains a call of the constructor of
	 * the class.
	 */
	public static void main(String[] args) {
		MethodDemo m = new MethodDemo();
		int i = m.minimum(1, 2);
		System.out.println(i);

		/*
		 * Error message: Cannot make a static reference to the non-static field
		 * magicNumber
		 */
		// System.out.println(magicNumber);
	}

}
