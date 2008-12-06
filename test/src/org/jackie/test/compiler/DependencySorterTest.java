package org.jackie.test.compiler;

import org.testng.annotations.Test;
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

	DependencyInfo all = new DependencyInfo("<all>");
	DependencyInfo a = new DependencyInfo("a");
	DependencyInfo b = new DependencyInfo("b");
	DependencyInfo c = new DependencyInfo("c");
	DependencyInfo d = new DependencyInfo("d");
	DependencyInfo e = new DependencyInfo("e");
	DependencyInfo f = new DependencyInfo("f");

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

}
