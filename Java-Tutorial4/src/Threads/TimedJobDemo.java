package Threads;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 * 
 * A simple demonstration for threads that are controlled by timers.
 * @see TimedJob
 */

import java.util.Timer;

public class TimedJobDemo {

	/***
	 * The demo creates two threads and starts them.
	 * A game loop controls the lifetime of the threads 
	 * and kill the jobs after a a predefined period of time.
	 */
	public static void main(String[] args) {
		Timer timer = new Timer();
		final long maxRunTime = 10000;
		TimedJob job1 = new TimedJob();
		TimedJob job2 = new TimedJob();

		// start job1 immediately and run it every second
		timer.schedule(job1, 0, 1000);
		// start job2 after 1 second, called every two seconds
		timer.schedule(job2, 1000, 2000);

		// game loop:
		// * control lifetime
		// * cancel jobs after a a predefined period of time
		long startTime = System.currentTimeMillis();
		while (true) {
			long now = System.currentTimeMillis();
			long diff = now - startTime;
			if (diff >= maxRunTime) {
				job1.cancel();
				job2.cancel();
				break;
			}
		}

		System.out.println("Finally got out of an infinite loop!");
	}
}
