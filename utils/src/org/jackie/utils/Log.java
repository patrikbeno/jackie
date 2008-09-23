package org.jackie.utils;

/**
 * @author Patrik Beno
 */
public class Log {

	static final long START = System.currentTimeMillis();

	enum Level {
		ALL, DBG, TRC, INF, WRN, ERR, NOTHING
	}

	static final Level MINLEVEL = Level.valueOf(System.getProperty("Log.MINLEVEL", Level.INF.name()));

	static public void enter() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		log(Level.TRC, ">>> at %s.%s(%s:%s)", ste.getClassName(), ste.getMethodName(), ste.getFileName(), ste.getLineNumber());
	}

	static public void leave() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		log(Level.TRC, "<<< at %s.%s(%s:%s)", ste.getClassName(), ste.getMethodName(), ste.getFileName(), ste.getLineNumber());
	}

	static public void debug(String msg, Object... args) {
		log(Level.DBG, msg, args);
	}

	static public void info(String msg, Object... args) {
		log(Level.INF, msg, args);
	}

	static public void warn(String msg, Object... args) {
		log(Level.WRN, msg, args);
	}

	static public void error(String msg, Object... args) {
		log(Level.ERR, msg, args);
	}

	static public void log(Level level, String msg, Object... args) {
		if (level.ordinal() < MINLEVEL.ordinal()) {
			return;
		}

//		System.out.printf("%5d [%s] ", (System.currentTimeMillis()-START), level);
		System.out.printf("[%s] ", level);
		System.out.printf(msg, args);
		System.out.println();
	}
}
