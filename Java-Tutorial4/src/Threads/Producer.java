package Threads;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 */

import java.util.Date;
import java.util.LinkedList;

public class Producer extends Thread {
	
	private LinkedList<Date> timeStamps;
	
	public Producer(LinkedList<Date> list) {
		timeStamps = list;
	}
	
	public void run() {
		Date tmp;
		
		for(int i=0; i<10; i++) {
			// simply wait 2 secs 
			try {
				sleep(2000);
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
			
			tmp = new Date();
			
			synchronized(timeStamps) {
				timeStamps.add(tmp);
				timeStamps.notify();
			}
		}
	}
}
