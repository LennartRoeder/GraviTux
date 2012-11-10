/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 14, 2012
 */

import java.io.IOException;

public class ErrorDemo {
	public void dangerous() throws IOException {
		throw new IOException();
	}

	public void tentative() {
		try {
			dangerous();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	public static void main(String[] args) {
		ErrorDemo m = new ErrorDemo();
		m.tentative();
		//m.dangerous();
	}
}
