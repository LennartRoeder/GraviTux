package Threads;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 */

public class MyThread2 extends Thread {
	public void run() {
		System.out.println("Start");
		for (int i = 0; i < 10; i++) {
			System.out.print("+");

			// sleep: Argument in msec
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Error: " + e);
			}
		}
		System.out.println("\nTerminated");
	}
}