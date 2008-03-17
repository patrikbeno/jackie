package org.jackie.compiler_impl.typeregistry;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;
import org.jackie.compiler_impl.jmodelimpl.JClassImpl;
import org.jackie.compiler_impl.jmodelimpl.JPackageImpl;
import org.jackie.compiler.LoadLevel;
import org.jackie.utils.ClassName;
import org.jackie.utils.PackageName;
import org.jackie.compiler_impl.bytecode.JClassReader;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JPackage;
import org.jackie.utils.Assert;
import org.objectweb.asm.ClassReader;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.nio.channels.Channels;

/**
 * @author Patrik Beno
 */
public abstract class AbstractTypeRegistry implements TypeRegistry {

	protected boolean editable;

	protected FileManager fileManager;

	protected Map<String, JPackage> packages;

	protected Map<String, JClass> classes;

	protected ArrayRegistry arrays;

	protected JClass loading; // currently loading (avoid recursion)

	{
		packages = new HashMap<String, JPackage>();
		classes = new HashMap<String, JClass>();
		arrays = new ArrayRegistry(this);
	}

	protected AbstractTypeRegistry(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public JClass getJClass(final ClassName clsname) {
		// check if available (for arrays, check their component type)
		if (!hasJClass(clsname.isArray() ? clsname.getComponentType() : clsname)) {
			return null;
		}

		// arrays are created on demand
		if (clsname.isArray()) {
			return arrays.getArray(clsname);
		}

		JClass cls = classes.get(clsname.getFQName());
		if (cls != null) {
			return cls;
		}

		cls = EditAction.run(this, new EditAction<JClass>() {
			protected JClass run() {
				JClass cls = createJClass(clsname);
				classes.put(clsname.getFQName(), cls);
				return cls;
			}
		});

		return cls;
	}

	public JClass getJClass(Class cls) {
		return getJClass(new ClassName(cls));
	}

	public void loadJClass(final JClass jclass, final LoadLevel level) {

		assert jclass != null;
		assert level != null;

		if (loading != null) {
			return;
		}

		EditAction.run(this, new EditAction<Object>() {
			protected Object run() {
				try {
					loading = jclass;

					ClassName clsname = new ClassName(jclass.getFQName());
					assert fileManager != null;
					FileObject fo = fileManager.getFileObject(clsname.getPathName());
					ClassReader cr = new ClassReader(Channels.newInputStream(fo.getInputChannel()));
					JClassReader reader = new JClassReader(level);
					cr.accept(reader, 0); // todo setup flags!

					return null;

				} catch (IOException e) {
					throw Assert.notYetHandled(e);

				} finally {
					loading = null;
				}
			}
		});
	}

	public JPackage getJPackage(ClassName clsname) {
		JPackage p = packages.get(clsname.getPackageFQName());
		if (p != null) {
			return p;
		}

		p = createPackage(clsname.getPackageName());
		return p;
	}

	public JPackage getJPackage(PackageName pckgname) {
		JPackage p = (pckgname != null) ? packages.get(pckgname.getFQName()) : null;
		if (p != null) {
			return p;
		}

		p = createPackage(pckgname);
		packages.put(p.getFQName(), p);

		return p;
	}

	protected JPackage createPackage(PackageName pckgname) {
		if (pckgname == null) {
			JPackage p = new JPackageImpl();
			p.edit().setName("");
			return p;
		}

		JPackage parent = getJPackage(pckgname.getParent());

		JPackage jpackage = new JPackageImpl();
		jpackage.edit()
				.setName(pckgname.getName())
				.setParent(parent);

		return jpackage;
	}

	protected JClass createJClass(ClassName clsname) {
		JClass jclass = new JClassImpl(clsname.getName(), getJPackage(clsname.getPackageName()), this);
		return jclass;
	}

}
