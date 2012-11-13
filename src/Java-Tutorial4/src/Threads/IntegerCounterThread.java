package Threads;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 */

class IntegerCounterThread implements Runnable {
	public void run() {
		for (int i = 0; i < 20; i++)
			System.out.println(i);
	}
}