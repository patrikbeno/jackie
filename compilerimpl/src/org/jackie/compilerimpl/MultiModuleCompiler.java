package org.jackie.compilerimpl;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;
import org.jackie.utils.DependencyInfo;
import org.jackie.utils.CyclicBuffer;
import org.jackie.utils.IOHelper;
import org.jackie.utils.Assert;
import org.jackie.utils.Log;
import org.jackie.utils.TimedTask;
import static org.jackie.utils.CollectionsHelper.iterable;
import org.jackie.compilerimpl.filemanager.JarFileManager;
import org.jackie.compilerimpl.filemanager.DirFileManager;
import org.jackie.compilerimpl.filemanager.InMemoryFileManager;
import org.jackie.compilerimpl.filemanager.JavaRuntimeFileManager;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.jar.JarOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;

/**
 * @author Patrik Beno
 */
public class MultiModuleCompiler {

	static public void main(String[] args) {
		new MultiModuleCompiler().run();
	}

	void run() {
		configure();
		compile();
	}

	private void configure1() {
		Module java = new Module("java", null, new JavaRuntimeFileManager());
		Module a = Module.createSource("a", "test/data/TestProject/A/src");
		Module b = Module.createSource("b", "test/data/TestProject/B/src");
		a.depend(java);
		b.depend(java, a);
		root = Module.project(java, a, b);
	}

	protected void configure() {
		Module java = new Module("java", null, new JavaRuntimeFileManager());
		Module javac = Module.createSource("javac", "../javac/src");
		Module asm = Module.createSource("org.objectweb.asm", "../asm/src");

		Module utils = Module.createSource("org.jackie.utils", "utils/src");
		Module context = Module.createSource("org.jackie.context", "context/src");
		Module asmtools = Module.createSource("org.jackie.asmtools", "asmtools/src");
		Module event = Module.createSource("org.jackie.event", "event/src");
		Module jclassfile = Module.createSource("org.jackie.jclassfile", "jclassfile/src");

		Module jvm = Module.createSource("org.jackie.jvm", "jvm/src");
		Module compiler = Module.createSource("org.jackie.compiler", "compiler/src");
		Module compilerimpl = Module.createSource("org.jackie.compilerimpl", "compilerimpl/src");
		Module java5 = Module.createSource("org.jackie.java5", "java5/src");
		Module tools = Module.createSource("org.jackie.tools", "tools/src");

		javac.depend(java);
		asm.depend(java);
		utils.depend(java, javac, asm);
		asmtools.depend(java, asm, utils);
		context.depend(utils);
		event.depend(utils, context, asmtools);
		jclassfile.depend(java, utils, context);
		jvm.depend(java, utils);
		compiler.depend(java, jvm, context, event, jclassfile);
		compilerimpl.depend(java, javac, compiler, jclassfile, context, utils);
		java5.depend(java, compiler, utils, jclassfile, asm);
		tools.depend(java, compilerimpl, utils, java5);

		root = Module.project(java, javac, asm, utils, asmtools, context, event, jclassfile, compiler, compilerimpl, java5, tools);
	}

	static class Module extends DependencyInfo {
		FileManager sources;
		FileManager binaries;

		static Module createSource(String name, String srcdir) {
			return new Module(name, new DirFileManager(new File(srcdir)), new InMemoryFileManager());
		}

		static Module createBinary(String name, String jarname) {
			return new Module(name, null, new JarFileManager(new File(jarname)));
		}

		static Module project(Module ... modules) {
			Module project = new Module("<project>", null, null);
			project.depend(modules);
			return project;
		}

		Module(String name, FileManager sources, FileManager binaries) {
			super(name);
			this.sources = sources;
			this.binaries = binaries;
		}

		public void depend(Module ... dependencies) {
			super.depend(dependencies);
		}

		public Set<Module> dependencies() {
			return (Set<Module>) super.dependencies(); // todo implement this
		}

		public List<Module> sortDependencies() {
			//noinspection unchecked
			return (List<Module>) super.sortDependencies();
		}
	}

	Module root;

	public void compile() {
		TimedTask timer = TimedTask.started();

		printInfo();

		List<Module> sorted = root.sortDependencies();
		Log.info("Compiling %s modules: %s", sorted.size(), sorted);

		for (Module m : sorted) {
			if (m.sources == null && m.binaries != null) {
				Log.info("Binary module: %s (already compiled)", m.name());
				continue;
			}
			compile(m);
		}

		Log.info("Finished in %s msec", timer.duration());
	}

	protected void compile(final Module module) {
		CompilerImpl compiler = new CompilerImpl() {
			{
				sources = module.sources;
				workspace = module.binaries;
				dependencies = new ArrayList<FileManager>();
				for (Module m : module.sortDependencies()) {
					dependencies.add(m.binaries);
				}
			}

			protected void save() {
				saveJar(module.name(), module.binaries);
			}
		};
		TimedTask timer = TimedTask.started();

		Log.info("[%s] Compiling %s files. Dependencies: %s.",
					module.name(), module.sources.getPathNames().size(), module.dependencies());

		compiler.compile();

		File f = new File(module.name() + ".jar");
		Log.info("[%s] Compiled in %s msec. Saved %s files (%sK).",
					module.name(), f.length()/1024, timer.duration(), module.binaries.getPathNames().size());
	}

	void saveJar(String jarname, FileManager files) {
		try {
			File f = new File(String.format("%s.jar", jarname));
			Log.debug("Saving %s (%s files)", f, files.getPathNames().size());
			JarOutputStream out = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			CyclicBuffer buf = new CyclicBuffer(1024*8);
			for (FileObject fo : files.getFileObjects()) {
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

	private void printInfo() {
		Set<String> sources = new HashSet<String>();
		Set<String> binaries = new HashSet<String>();

		populateDependencies(root, sources, binaries);

		Log.info("Configured %s source modules: %s", sources.size(), sources);
		Log.info("There are total %s binary modules (dependencies): %s", binaries.size(), binaries);
	}

	void populateDependencies(Module module, Set<String> sources, Set<String> binaries) {
		Set<String> dst
				= module.sources != null ? sources
				: module.binaries != null ? binaries
				: null;
		if (dst != null) { dst.add(module.name()); }
		for (Module d : module.dependencies()) {
			populateDependencies(d, sources, binaries);
		}
	}


}
