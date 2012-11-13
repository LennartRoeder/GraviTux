package Tools;

/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied STracesciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 *
 * @version October 26, 2012
 */

import java.util.EnumSet;

public final class Traces {

	private static boolean trace = true;

	public static enum TraceFlag {
		ASSETS, COLLISIONS, CONSTRUCTORS, DRAWS, EVENTS, TIMING, UPDATES
	};

	public static boolean traced() {
		return trace;
	}

	public static void traceOn() {
		trace = true;
	}

	public static void traceOff() {
		trace = false;
	}

	// private static EnumSet<TraceFlag> flags = EnumSet.allOf(TraceFlag.class);
	private static EnumSet<TraceFlag> flags = EnumSet.noneOf(TraceFlag.class);

	public static void set(TraceFlag flag) {
		flags.add(flag);
	}

	public static boolean is(TraceFlag flag) {
		return trace && flags.contains(flag);
	}

	public static void clearFlags() {
		flags.clear();
	}

	public static void listFlags() {
		for (TraceFlag flag : Traces.flags) {
			System.out.print(flag + " ");
		}
	}
}
