/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 */

import java.util.Date;
import java.util.LinkedList;

public class SynchronizationDemo {

	public static void main(String[] args) {
		LinkedList<Date> timeStamps = new LinkedList<Date>();
		
		Producer p = new Producer(timeStamps);
		Consumer c = new Consumer(timeStamps);
		
		p.start();
		c.start();

	}

}
