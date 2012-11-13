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
public class MyThread1 extends Thread {

	public MyThread1() {
	}

	public MyThread1(String name) {
		setName(name);
	}

	public void run() {
		String msg = "Thread: " + getName() + " ID: " + getId() + " Priority: "
				+ getPriority();
		System.out.println(msg);
	}
}