package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.type.ArrayTypeImpl;
import org.jackie.compiler.util.ClassName;

import java.util.Map;

/**
 * @author Patrik Beno
 */
public class ArrayRegistry {

	class Descriptor {

		String clsname;
		int dimensions;

		Descriptor(String clsname, int dimensions) {
			this.clsname = clsname;
			this.dimensions = dimensions;
		}

		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Descriptor that = (Descriptor) o;

			if (dimensions != that.dimensions) return false;
			if (!clsname.equals(that.clsname)) return false;

			return true;
		}

		public int hashCode() {
			int result;
			result = clsname.hashCode();
			result = 31 * result + dimensions;
			return result;
		}
	}

	TypeRegistry master;

	Map<Descriptor, JClassImpl> arrays;

	public ArrayRegistry(TypeRegistry master) {
		this.master = master;
	}

	public JClassImpl getArray(ClassName clsname) {
		return getArray(clsname.getComponentType().getFQName(), clsname.getDimensions());
	}

	public JClassImpl getArray(String fqname) {
		int i = fqname.indexOf("[]");
		assert i != -1;

		String componentType = fqname.substring(0, i);
		int dimensions = (fqname.length()-i)/2;

		return getArray(componentType, dimensions);
	}

	public JClassImpl getArray(String clsname, int dimensions) {

		Descriptor desc = new Descriptor(clsname, dimensions);

		JClassImpl cls = arrays.get(desc);
		if (cls != null) {
			return cls;
		}

		// create array on demand
		cls = new JClassImpl();
		cls.name = toClassName(desc);
		cls.superclass = master.getJClass(new ClassName(Object.class));
		cls.addCapability(new ArrayTypeImpl(cls, master.getJClass(new ClassName(clsname)), dimensions));

		arrays.put(desc, cls);
		return cls;
	}

	String toClassName(Descriptor desc) {
		ClassName clsname = new ClassName(desc.clsname, desc.dimensions);
		return clsname.getFQName();
	}


}
