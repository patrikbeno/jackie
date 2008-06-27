package org.jackie.test.compiler;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;
import org.jackie.compiler_impl.CompilerImpl;
import org.jackie.compiler_impl.filemanager.InMemoryFileManager;
import org.jackie.compiler_impl.filemanager.JarFileManager;
import org.jackie.utils.Assert;
import org.jackie.utils.CyclicBuffer;
import org.jackie.utils.IOHelper;
import org.testng.annotations.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

/**
 * @author Patrik Beno
 */
@Test
public class CompilerTest {

	public void compile() {

		String jhome = System.getProperty("java.home");
		final File rtjar = new File(jhome, "lib/rt.jar");
		assert rtjar.exists();

		CompilerImpl compiler = new CompilerImpl() {
			{
				sources = new InMemoryFileManager();
				workspace = new InMemoryFileManager();
				dependencies = Arrays.asList((FileManager) new JarFileManager(rtjar));

				TestSources.load(sources);
			}

			protected void save() {
				try {
					JarOutputStream out = new JarOutputStream(new BufferedOutputStream(new FileOutputStream("test.jar")));
					CyclicBuffer buf = new CyclicBuffer(1024*8);
					for (FileObject fo : workspace.getFileObjects()) {
						buf.reset();
						out.putNextEntry(new ZipEntry(fo.getPathName()));

						ReadableByteChannel chin = fo.getInputChannel();
						WritableByteChannel chout = Channels.newChannel(out);

						while (buf.readFrom(chin) != -1 || !buf.isEmpty()) {
							buf.writeTo(chout);
						}

						out.closeEntry();
					}
					IOHelper.close(out);

				} catch (IOException e) {
					throw Assert.notYetHandled(e);
				}

			}
		};
		compiler.compile();


	}

}
