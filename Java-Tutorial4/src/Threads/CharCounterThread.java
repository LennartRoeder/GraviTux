package Threads;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 */

class CharCounterThread implements Runnable {
	public void run() {
		char c = 'a'; // characters are also numbers
		for (int i = 0; i < 26; i++)
			System.out.println(c + i);
	}
}