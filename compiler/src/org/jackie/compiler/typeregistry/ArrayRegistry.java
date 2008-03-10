package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.attribute.impl.Kind;
import org.jackie.compiler.jmodelimpl.attribute.impl.KindAttribute;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;
import org.jackie.jmodel.JClass;

import java.util.HashMap;
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

	Map<Descriptor, JClassImpl> arrays;  // todo use WeakRefs, automatic cleanup

	{
		arrays = new HashMap<Descriptor, JClassImpl>();
	}

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

		JClassImpl jclass = arrays.get(desc);
		if (jclass != null) {
			return jclass;
		}

		ClassName classname = new ClassName(clsname, dimensions);
		JClass component = master.getJClass(classname.getComponentType());

		// create array on demand
		jclass = new JClassImpl();
		jclass.edit()
				.setName(classname.getName())
				.setPackage(component.getJPackage())
				.setSuperClass(context().typeRegistry().getJClass(Object.class));
		jclass.attributes().edit()
				.addAttribute(KindAttribute.class, new KindAttribute(Kind.ARRAY));

		arrays.put(desc, jclass);
		return jclass;
	}


}
