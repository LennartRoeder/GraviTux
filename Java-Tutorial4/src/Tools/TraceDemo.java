package Tools;

/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version November 11, 2012
 */

import static Tools.Traces.*;

public class TraceDemo {

	public static void main(String[] args) {
		Traces.listFlags();
		Traces.clearFlags();
		Traces.set(TraceFlag.COLLISIONS);
		Traces.listFlags();
		System.out.println(is(TraceFlag.COLLISIONS));
	}
}
