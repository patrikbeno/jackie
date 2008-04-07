package org.jackie.test.compiler;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler_impl.CompilerImpl;
import org.jackie.compiler_impl.filemanager.InMemoryFileManager;
import org.jackie.compiler_impl.filemanager.JarFileManager;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author Patrik Beno
 */
@Test
public class CompilerTest {

	public void compile() {

		String jhome = System.getProperty("java.home");
		final File rtjar = new File(jhome, "lib/rt.jar");
		assert rtjar.exists();

		CompilerImpl compiler = new CompilerImpl() {{
			sources = new InMemoryFileManager();
			workspace = new InMemoryFileManager();
			dependencies = Arrays.asList((FileManager) new JarFileManager(rtjar));

			TestSources.load(sources);
		}};
		compiler.compile();


	}

}
