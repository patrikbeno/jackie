package org.jackie.utils;

/**
 * @author Patrik Beno
 */
public class Log {

	enum Level {
		ALL, DBG, INF, WRN, ERR, NOTHING
	}

	static final Level MINLEVEL = Level.DBG;

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

		System.out.printf("[%s] ", level);
		System.out.printf(msg, args);
		System.out.println();
	}
}
