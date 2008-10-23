package org.jackie.tools;

import static org.jackie.utils.Assert.doAssert;
import org.jackie.utils.CyclicBuffer;
import org.jackie.utils.IOHelper;
import org.jackie.utils.Assert;
import org.jackie.compilerimpl.CompilerImpl;
import org.jackie.compilerimpl.filemanager.InMemoryFileManager;
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

	Progress progress;

	FileManager sources;
	List<FileManager> dependencies;
	FileManager workspace;

	void run() {
		doAssert(srcdir.exists(), "Missing sources directory: %s", srcdir);
//		doAssert(!jarname.exists(), "Ouput file already exists: %s", jarname);

		String jhome = System.getProperty("java.home");
		final File rtjar = new File(jhome, "lib/rt.jar");
		assert rtjar.exists();

		progress = new Progress();

		if (sources == null) {
			sources = new DirFileManager(srcdir);
		}
		if (dependencies == null) {
			dependencies = Arrays.asList((FileManager) new FilteredJarFileManager(
					rtjar,
					// warning: temporary solution to allow javac sources compilation (conflict with classes already in rt.jar)
					"com.sun.(source|tools.javac).*",
					"javax.(annotation.processing|lang.model|tools).*"));
		}

		CompilerImpl compiler = new CompilerImpl() {
			{
				sources = Main.this.sources;
				dependencies = Main.this.dependencies;
				workspace = Main.this.workspace = new InMemoryFileManager();

				// wrap file managers with progress info stuff
				if (System.getProperty("jackie.showProgress", "true").equals("true")) {
					sources = progress.createReadableFileManager(sources);
					workspace = progress.createWritableFileManager(workspace);
				}
			}

			protected void save() {
				saveJar();
			}
		};

		compiler.compile();

		progress.close();
	}

	void saveJar() {
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

}
