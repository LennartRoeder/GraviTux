/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 * 
 * This class is based on the class TimerTask, that implements threads that are managed by a timer.
 */

import java.util.TimerTask;

public class TimedJob extends TimerTask {
	// class variable to construct a unique ID for every thread
	private static int idCounter = 1;
	int jobID;
	long startTime;

	
	/***
	 * The constructor use the class variable idCounter as an unique jobID
	 * and increase the value of idCounter to get a new unique ID.
	 * Moreover, it stores the time of the creation of the thread.
	 */
	public TimedJob() {
		this.jobID = idCounter++;
		this.startTime = System.currentTimeMillis();
	}
	
	/**
	 * The superclass TimerTask implements the interface Runnable 
	 * that requires the definition of the function run.
	 * The function run is called by the scheduler after the thread got control.
	 */
	public void run() {
		long now = System.currentTimeMillis();
		long diff = now - startTime;
		String msg = "> job" + jobID + " called at: " + diff + " msec";
		System.out.println(msg);
	}
}
