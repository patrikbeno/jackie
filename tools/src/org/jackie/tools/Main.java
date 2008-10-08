package org.jackie.tools;

import static org.jackie.utils.Assert.doAssert;
import org.jackie.utils.CyclicBuffer;
import org.jackie.utils.IOHelper;
import org.jackie.utils.Assert;
import org.jackie.compilerimpl.CompilerImpl;
import org.jackie.compilerimpl.filemanager.InMemoryFileManager;
import org.jackie.compilerimpl.filemanager.JarFileManager;
import org.jackie.compilerimpl.filemanager.DirFileManager;
import org.jackie.compilerimpl.filemanager.FilteredJarFileManager;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;

import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.jar.JarOutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;

/**
 * @author Patrik Beno
 */
public class Main {

	static public void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: compile {sourceDirectory} {outputJarName}");
			System.exit(-1);
		}

		String srcdir = args[0];
		String jarname = args[1];

		Main main = new Main(new File(srcdir), new File(jarname));
		main.run();
	}

	File srcdir;
	File jarname;

	public Main(File srcdir, File jarname) {
		this.srcdir = srcdir;
		this.jarname = jarname;
	}

	FileManager _sources;
	List<FileManager> _dependencies;

	void run() {
		doAssert(srcdir.exists(), "Missing sources directory: %s", srcdir);
//		doAssert(!jarname.exists(), "Ouput file already exists: %s", jarname);

		String jhome = System.getProperty("java.home");
		final File rtjar = new File(jhome, "lib/rt.jar");
		assert rtjar.exists();

		CompilerImpl compiler = new CompilerImpl() {
			{
				sources = _sources != null ? _sources : (_sources=new DirFileManager(srcdir));
				workspace = new InMemoryFileManager();
				dependencies = _dependencies != null ? _dependencies : (_dependencies = Arrays.asList(
						(FileManager) new FilteredJarFileManager(rtjar,
																			  // warning: temporary solution to allow javac sources compilation (conflict with classes already in rt.jar)
																			  "com.sun.(source|tools.javac).*",
																			  "javax.(annotation.processing|lang.model|tools).*")));
			}

			protected void save() {
				try {
					JarOutputStream out = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(jarname)));
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
