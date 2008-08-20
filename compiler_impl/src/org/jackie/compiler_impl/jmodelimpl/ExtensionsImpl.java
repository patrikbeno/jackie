package org.jackie.compiler_impl.jmodelimpl;

import org.jackie.compiler.extension.ExtensionManager;
import org.jackie.compiler.spi.Compilable;
import static org.jackie.context.ContextManager.context;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.Extensions;
import static org.jackie.utils.Assert.typecast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class ExtensionsImpl implements Extensions, Compilable {

	JNode jnode;

	Map<Class<? extends Extension>, Extension> extensions;

	public ExtensionsImpl(JNode jnode) {
		this.jnode = jnode;
	}

	{
		extensions = new HashMap<Class<? extends Extension>, Extension>();
	}

	public <T extends Extension> Set<Class<T>> supported() {
		//noinspection unchecked
		return (Set) extensions.keySet();
	}

	public <T extends Extension> boolean supports(Class<T> type) {
		return extensions.containsKey(type) || get(type) != null;
	}

	public <T extends Extension> T get(Class<T> type) {
		Extension ext = extensions.get(type);
		if (ext != null) {
			return typecast(ext, type);
		}

		// todo optimize extension lookup: positive lookups fall here only once; false lookups (nonexistent or unsupported extensions) will repeat this on every lookup

		ext = context(ExtensionManager.class).apply(jnode, type);
		if (ext == null) {
			return null;
		}

		extensions.put(type, ext);

		return typecast(ext, type);
	}

	public Iterator<Extension> iterator() {
		return new Iterator<Extension>() {

			Iterator<Map.Entry<Class<? extends Extension>, Extension>> it
					= extensions.entrySet().iterator();

			public boolean hasNext() {
				return it.hasNext();
			}

			public Extension next() {
				return it.next().getValue();
			}

			public void remove() {
				it.remove();
			}
		};
	}

	public void compile() {
		for (Extension ext : this) {
			((Compilable) ext).compile();
		}
	}
}
