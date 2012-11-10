/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 */

import java.util.Date;
import java.util.LinkedList;

public class Consumer extends Thread {

	private LinkedList<Date> timeStamps;
	
	public Consumer(LinkedList<Date> list) {
		timeStamps = list;
	}
	
	public void run() {
		Date tmp;
		
		while (true) {
			synchronized(timeStamps) {
				try {
					// do NOTHING, wait for notification
					timeStamps.wait(5000);
				} catch (InterruptedException e) {
					System.out.println("Error: " + e);
				}
				
				// wow, we've got something to do
				if (timeStamps.size() > 0) {
					tmp = timeStamps.removeFirst();
					System.out.println("Zeit: " + tmp);
				} else {
					// Producer stoped working
					System.out.println("Done!");
					break;
				}
			}
		}
	}
}
