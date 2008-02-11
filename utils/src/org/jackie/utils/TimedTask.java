package org.jackie.utils;

/**
 * @author Patrik Beno
 */
public class TimedTask {

	long started;
	long finished;


	public TimedTask() {
	}

	public long duration() {
		return finished - started;
	}

	public TimedTask start() {
		started = System.currentTimeMillis();
		return this;
	}

	public TimedTask stop() {
		finished = System.currentTimeMillis();
		return this;
	}
}
