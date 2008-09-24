package org.jackie.utils;

import static org.jackie.utils.Assert.doAssert;

/**
 * @author Patrik Beno
 */
public class Chain<T extends Chain<T>> {

	private T previous;
	private T next;

	public T previous() {
		return previous;
	}

	public T next() {
		return next;
	}

	public T head() {
		T element = $this();
		while (element.previous() != null) {
			element = element.previous();
		}
		return element;
	}

	public T tail() {
		T element = $this();
		while (element.next() != null) {
			element = element.next();
		}
		return element;
	}

	public T append(T element) {
		doAssert(next == null, "cannot append(). Use truncate()+append() or insert()");
		link($this(), element);
		return tail();
	}

	/**
	 * inserts element at current position (element becomes this' predecessor /this.previous/);
	 * its' effectivelly prepend(), maybe that would be the better name
	 * @param element
	 * @return always this, allowing to do successive chained inserts
	 */
	public T insert(T element) {
		link(previous, element);
		link(element, $this());
		return $this();
	}

	public T truncate() {
		link($this(), null);
		return $this();
	}

	public T delete() {
		link(previous, next);
		return next != null ? next : previous;
	}

	public boolean isHead() {
		return previous == null;
	}

	public boolean isTail() {
		return next == null;
	}

	private void link(T previous, T next) {
		if (previous != null) { previous.next = next; }
		if (next != null) { next.previous = previous; }
	}

	private T $this() {
		//noinspection unchecked
		return (T) this;
	}


}
