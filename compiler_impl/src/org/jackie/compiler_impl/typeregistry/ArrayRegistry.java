package org.jackie.compiler_impl.typeregistry;

import org.jackie.compiler_impl.jmodelimpl.JClassImpl;
import org.jackie.compiler.LoadLevel;
import org.jackie.compiler.attribute.Kind;
import org.jackie.compiler.attribute.KindAttribute;
import org.jackie.utils.ClassName;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.jvm.JClass;import static org.jackie.context.ContextManager.context;

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

	public JClassImpl getArray(final String clsname, final int dimensions) {
		return EditAction.run(master, new EditAction<JClassImpl>() {
			protected JClassImpl run() {
				Descriptor desc = new Descriptor(clsname, dimensions);

				JClassImpl jclass = arrays.get(desc);
				if (jclass != null) {
					return jclass;
				}

				ClassName classname = new ClassName(clsname, dimensions);
				JClass component = master.getJClass(classname.getComponentType());

				// create array on demand
				jclass = new JClassImpl(classname.getName(), component.getJPackage(), master);
				jclass.loadLevel = LoadLevel.CODE; // array - no bytecode (consider fully loaded) 

				jclass.edit()
					.setSuperClass(context(TypeRegistry.class).getJClass(Object.class));
				jclass.attributes().edit()
					.addAttribute(KindAttribute.class, new KindAttribute(Kind.ARRAY));

				arrays.put(desc, jclass);

				return jclass;
			}
		});
	}


}
