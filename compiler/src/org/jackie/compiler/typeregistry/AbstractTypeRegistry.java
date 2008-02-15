package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.JPackageImpl;
import org.jackie.compiler.util.ClassName;
import org.jackie.compiler.util.PackageName;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public abstract class AbstractTypeRegistry implements TypeRegistry {

	protected Map<String, JPackageImpl> packages;

	protected Map<String, JClassImpl> classes;

	protected ArrayRegistry arrays;

	{
		packages = new HashMap<String, JPackageImpl>();
		classes = new HashMap<String, JClassImpl>();
		arrays = new ArrayRegistry(this);
	}

	public JClassImpl getJClass(ClassName clsname) {
		// check if available (for arrays, check their component type)
		if (!hasJClass(clsname.isArray() ? clsname.getComponentType() : clsname)) {
			return null;
		}

		// arrays are created on demand
		if (clsname.isArray()) {
			return arrays.getArray(clsname);
		}

		JClassImpl cls = classes.get(clsname.getFQName());
		if (cls != null) {
			return cls;
		}

		cls = createJClass(clsname);
		classes.put(clsname.getFQName(), cls);

		return cls;
	}

	public JClassImpl getJClass(Class cls) {
		return getJClass(new ClassName(cls.getName()));
	}

	public JPackageImpl getJPackage(ClassName clsname) {
		JPackageImpl p = packages.get(clsname.getPackageFQName());
		if (p != null) {
			return p;
		}

		p = createPackage(clsname.getPackageName());
		return p;
	}

	public JPackageImpl getJPackage(PackageName pckgname) {
		JPackageImpl p = packages.get(pckgname.getFQName());
		if (p != null) {
			return p;
		}

		p = createPackage(pckgname);
		packages.put(p.getFQName(), p);

		return p;
	}

	protected JPackageImpl createPackage(PackageName pckgname) {
		if (pckgname == null) {
			JPackageImpl p = new JPackageImpl();
			p.name = "";
			return p;
		}

		JPackageImpl parent = getJPackage(pckgname.getParent());

		JPackageImpl p = new JPackageImpl();
		p.name = pckgname.getName();
		p.parent = parent.addSubPackage(p);

		return p;
	}

	protected JClassImpl createJClass(ClassName clsname) {

		JClassImpl c = new JClassImpl();
		c.name = clsname.getName();
		c.jpackage = getJPackage(clsname.getPackageName()).addClass(c);

		return c;
	}

}
