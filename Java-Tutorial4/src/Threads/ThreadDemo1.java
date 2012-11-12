package Threads;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 */

/**
 * Threads implements the interface Runnable, that requires the definition of
 * the function run. Do NOT call the run function directly! Use the start
 * function that inserts the thread into the list of active processes. The
 * scheduler (i.e., the operation system) manage the list of active threads.
 */
public class ThreadDemo1 {
	public static void main(String args[]) {
		MyThread1 job = new MyThread1("ThreadDemo1");
		job.start();
	}
}
