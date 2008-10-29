package org.jackie.tools;

import static org.jackie.utils.Assert.doAssert;
import org.jackie.utils.CyclicBuffer;
import org.jackie.utils.IOHelper;
import org.jackie.utils.Assert;
import static org.jackie.utils.CollectionsHelper.iterable;
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
import java.util.ArrayList;
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

		CmdLineSupport cmdline = new CmdLineSupport(Main.class);

		Option<File> sources = cmdline.createOption("srcdir", File.class)
				.description("Source tree directory")
				.mandatory(true);
		Option<File[]> classpath = cmdline.createOption("classpath", File[].class)
				.description("Class path");
		Option<File> jarname = cmdline.createOption("jarname", File.class)
				.description("Output jar file name")
				.mandatory(true);
		Option<Boolean> overwrite = cmdline.createOption("overwrite", Boolean.class)
				.description("Overwrite output file?")
				.dflt(false);
		Option<Boolean> showprogress = cmdline.createOption("showprogress", Boolean.class)
				.description("Show compilation progress?")
				.dflt(true);
		Option<Boolean> help = cmdline.createOption("help", Boolean.class)
				.description("Show usage help")
				.dflt(false);

		try {
			cmdline.parse(args);

		} catch (Throwable t) {
			System.out.printf("Error: %s%n", t.getMessage());
			cmdline.printcfg();
			System.exit(-1);
		}

		if (help.get()) {
			cmdline.printcfg();
			System.exit(0);
		}

		Main main = new Main();
		configure: {
			main.srcdir = sources.get();
			main.jarname = jarname.get();
			main.classpath = classpath.get() != null ? Arrays.asList(classpath.get()) : null;
			main.overwrite = overwrite.get();
			main.showprogress = showprogress.get();
		}

		main.run();
	}

	File srcdir;
	File jarname;
	List<File> classpath;
	boolean overwrite;
	boolean showprogress;

	Progress progress;

	FileManager sources;
	List<FileManager> dependencies;
	FileManager workspace;

	void run() {
		doAssert(srcdir != null && srcdir.exists(), "Missing sources directory: %s", srcdir);
		doAssert((jarname != null && !jarname.exists()) || overwrite, "Ouput file already exists: %s", jarname);

		progress = new Progress();

		if (sources == null) {
			sources = new DirFileManager(srcdir);
		}
		if (dependencies == null) {
			dependencies = new ArrayList<FileManager>();
			for (File f : iterable(classpath)) {
				dependencies.add(new FilteredJarFileManager(
						f,
						// warning: temporary solution to allow javac sources compilation (conflict with classes already in rt.jar)
						"com.sun.(source|tools.javac).*",
						"javax.(annotation.processing|lang.model|tools).*"));
			}
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

		System.out.printf("\rCompiling sources in %s to %s%n", srcdir, jarname);

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
