package org.jackie.compilerimpl;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;
import org.jackie.utils.DependencyInfo;
import org.jackie.utils.CyclicBuffer;
import org.jackie.utils.IOHelper;
import org.jackie.utils.Assert;
import static org.jackie.utils.CollectionsHelper.iterable;

import java.util.List;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.jar.JarOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;

/**
 * @author Patrik Beno
 */
public class MultiModuleCompiler {

	static public void main(String[] args) {
	}


	class Module extends DependencyInfo {
		FileManager sources;
		FileManager binaries;

		Module(String name, FileManager sources, FileManager binaries) {
			super(name);
			this.sources = sources;
			this.binaries = binaries;
		}

		public void depend(Module ... dependencies) {
			super.depend(dependencies);
		}

		public List<Module> sortDependencies() {
			//noinspection unchecked
			return (List<Module>) super.sortDependencies();
		}
	}

	Module project;

	public void compile() {
		for (Module m : project.sortDependencies()) {
			compile(m);
		}
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
		compiler.compile();
	}

	void saveJar(String jarname, FileManager files) {
		try {
			JarOutputStream out = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(jarname)));
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


}
