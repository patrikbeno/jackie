package org.jackie.compilerimpl.typeregistry;

import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.event.TypeRegistryEvents;
import org.jackie.compilerimpl.jmodelimpl.JClassImpl;
import org.jackie.compilerimpl.jmodelimpl.LoadLevel;
import org.jackie.compilerimpl.jmodelimpl.type.ArrayTypeImpl;
import static org.jackie.context.ContextManager.context;
import org.jackie.jvm.JClass;
import org.jackie.utils.ClassName;
import static org.jackie.event.Events.events;

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

				jclass.extensions().edit().add(new ArrayTypeImpl(jclass));

				events(TypeRegistryEvents.class).created(jclass);
				arrays.put(desc, jclass);

				return jclass;
			}
		});
	}


}
