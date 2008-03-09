package org.jackie.compiler.typeregistry;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.JPackageImpl;
import org.jackie.compiler.jmodelimpl.LoadLevel;
import org.jackie.compiler.util.ClassName;
import org.jackie.compiler.util.PackageName;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JPackage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public abstract class AbstractTypeRegistry implements TypeRegistry {

	protected FileManager fileManager;

	protected Map<String, JPackage> packages;

	protected Map<String, JClass> classes;

	protected ArrayRegistry arrays;

	{
		packages = new HashMap<String, JPackage>();
		classes = new HashMap<String, JClass>();
		arrays = new ArrayRegistry(this);
	}

	public JClass getJClass(ClassName clsname) {
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

		cls = createJClass(clsname);
		classes.put(clsname.getFQName(), cls);

		return cls;
	}

	public JClass getJClass(Class cls) {
		return getJClass(new ClassName(cls));
	}

	public void loadJClass(JClass jclass, LoadLevel level) {
//		try {
//			ClassName clsname = new ClassName(jclass);
//			FileObject fo = fileManager.getFileObject(clsname.getPathName());
//			ClassReader cr = new ClassReader(Channels.newInputStream(fo.getInputChannel()));
//			JClassReader reader = new JClassReader(jclass, level);
//			cr.accept(reader, 0); // todo setup flags!
//		} catch (IOException e) {
//			throw Assert.notYetHandled(e);
//		}
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

		JClass jclass = new JClassImpl();
		jclass.edit()
				.setName(clsname.getName())
				.setPackage(getJPackage(clsname.getPackageName()));

		return jclass;
	}

}
