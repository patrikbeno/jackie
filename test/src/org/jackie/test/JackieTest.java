package org.jackie.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.jackie.tools.DumpClass;
import org.jackie.utils.Assert;
import org.jackie.utils.IOHelper;
import org.jackie.utils.Log;
import org.testng.annotations.AfterMethod;

/**
 * Test template and supporting infrastructure (tmp file/directory support, programatic support for
 * @ AfterMethod) etc.
 *
 * @author Patrik Beno
 */
public abstract class JackieTest {

	List<Runnable> afterMethod = new ArrayList<Runnable>();
	
	protected void onAfterMethod(Runnable runnable) {
		afterMethod.add(runnable);
	}

	@AfterMethod
	protected void afterMethod() {
		for (Runnable runnable : afterMethod) {
			runnable.run();
		}
	}

	static public enum DirOwner { METHOD, CLASS }

	protected File createTempDir(DirOwner scope) {
		try {
			final File dir = File.createTempFile("jackie", "");
			dir.delete();
			dir.mkdir();
			Log.info("Created temporary (scope %s) directory %s", scope, dir);

			switch (scope) {
				case METHOD: {
					onAfterMethod(new Runnable() {
						public void run() {
							Log.info("Deleting %s", dir);
							delete(dir);
						}
					});
					break;
				}
				default:
					throw Assert.notYetImplemented(); // todo implement this
			}

			return dir;

		} catch (IOException e) {
			throw Assert.unexpected(e);
		}
	}

	protected void delete(File f) {
		if (f.isDirectory()) {
			for (File child : f.listFiles()) {
				delete(child);
			}
		}
		if (!f.delete()) { f.deleteOnExit(); }
	}

	protected void dump(Class cls) {
		InputStream in = null;
		try {
			String sname = cls.getName();
			sname = sname.substring(sname.lastIndexOf('.')+1) + ".class";
			in = cls.getResourceAsStream(sname);
			DumpClass.dump(in, new PrintStream(System.out));
		} finally {
			IOHelper.close(in);
		}
	}

}
