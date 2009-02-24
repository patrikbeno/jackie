package org.jackie.test.compiler;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.jackie.utils.DependencyInfo;
import org.jackie.utils.JackieException;
import org.jackie.utils.Log;
import static org.jackie.utils.Assert.doAssert;
import static org.jackie.utils.Assert.expected;

import java.util.List;
import java.util.Arrays;

/**
 * @author Patrik Beno
 */
public class DependencySorterTest {

	DependencyInfo all;
	DependencyInfo a;
	DependencyInfo b;
	DependencyInfo c;
	DependencyInfo d;
	DependencyInfo e;
	DependencyInfo f;

	@BeforeMethod
	public void setup() {
		Log.info("setup()");
		all = new DependencyInfo("<all>");
		a = new DependencyInfo("a");
		b = new DependencyInfo("b");
		c = new DependencyInfo("c");
		d = new DependencyInfo("d");
		e = new DependencyInfo("e");
		f = new DependencyInfo("f");
	}

	@Test
	public void detectCycle() {
		a.depend(b);
		b.depend(a);
		all.depend(a,b);

		try {
			all.sortDependencies();
		} catch (JackieException ex) {
			doAssert(ex.getMessage().toLowerCase().contains("cycle"), "Unexpected exception?");
			Log.debug("Caught expected exception: %s", ex);
		}
	}

	@Test
	public void sortDependencies() {
		b.depend(a);
		c.depend(b);
		d.depend(e);
		e.depend(f);
		f.depend(a,b);

		all.depend(a,b,c,d,e,f);

		List<? extends DependencyInfo> sorted = all.sortDependencies();
		expected(Arrays.asList(a,b,c,f,e,d), sorted, "Invalid order of dependencies");
	}

}
