package org.jackie.compiler_impl.typeregistry;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.event.TypeRegistryEvents;
import org.jackie.compiler_impl.bytecode.JClassParser;
import org.jackie.compiler_impl.jmodelimpl.JClassImpl;
import org.jackie.compiler_impl.jmodelimpl.JPackageImpl;
import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import static org.jackie.context.ServiceManager.service;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JPackage;
import org.jackie.utils.Assert;
import org.jackie.utils.ClassName;
import org.jackie.utils.PackageName;
import org.jackie.event.Events;
import static org.jackie.event.Events.events;

import java.io.IOException;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public abstract class AbstractTypeRegistry implements TypeRegistry, JClassLoader {

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
				register(cls);
				return cls;
			}
		});

		return cls;
	}

	public JClass getJClass(Class cls) {
		return getJClass(new ClassName(cls));
	}

	public void load(final JClass jclass, final LoadLevel level) {

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
					service(JClassParser.class).execute(
							Channels.newInputStream(fo.getInputChannel()),
							level);
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
			JPackage p = new JPackageImpl(null);
			p.edit().setName("");
			return p;
		}

		JPackage parent = getJPackage(pckgname.getParent());

		JPackage jpackage = new JPackageImpl(parent);
		jpackage.edit()
				.setName(pckgname.getName())
				.setParent(parent);

		return jpackage;
	}

	protected JClass createJClass(ClassName clsname) {
		JClass jclass = new JClassImpl(clsname.getName(), getJPackage(clsname.getPackageName()), this);
		return jclass;
	}

	protected void register(JClass jclass) {
		events(TypeRegistryEvents.class).created(jclass);
		classes.put(jclass.getFQName(), jclass);
	}

}
