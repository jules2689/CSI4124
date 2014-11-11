package simModel;

public class Debugger {
	
	private static final boolean debug = true;
	private static final int log_level = 2;

	public static void debug(String message) {
		debug(message, 1);
	}
	
	public static void debug(String message, int level) {
		if (debug && level >= log_level) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			StackTraceElement element = stackTraceElements[2];
			System.out.println("DEBUG: class=" + element.getClassName() + " : " + message);
		}
	}
	
}
