package org.jackie.test.utils;

import org.testng.annotations.Test;
import org.jackie.utils.Stack;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.expected;
import static org.jackie.utils.Assert.doAssert;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Patrik Beno
 */
public class StackTest {

	@Test
	public void pushpop() {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(1);
		expected(1, stack.peek(), "invalid value on stack");

		Integer i = stack.pop();
		expected(1, i, "pop?");
		expected(0, stack.size(), "size?");
		doAssert(stack.isEmpty(), "isEmpty?");
	}

	@Test
	public void iterator() {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		expected(3, stack.size(), "size?");
		Iterator<Integer> it = stack.iterator();
		expected(3, it.next(), "iterator?");
		expected(2, it.next(), "iterator?");
		expected(1, it.next(), "iterator?");
		expected(false, it.hasNext(), "iterator?");
	}

	@Test
	public void contains() {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		doAssert(stack.contains(1), "contains?");
		doAssert(stack.contains(2), "contains?");
		doAssert(stack.contains(3), "contains?");
		doAssert(!stack.contains(4), "contains?");
	}

}
