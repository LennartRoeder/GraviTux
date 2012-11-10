/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version october 13, 2012
 */

public class ThreadDemo3 {
	public static void main(String args[]) {
		Thread job1 = new Thread(new CharCounterThread());
		Thread job2 = new Thread(new IntegerCounterThread());
		job1.start();
		job2.start();
	}
}
