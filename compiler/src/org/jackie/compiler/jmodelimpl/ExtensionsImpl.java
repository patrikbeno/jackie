package org.jackie.compiler.jmodelimpl;

import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.Extensions;
import org.jackie.jmodel.extension.Extensible;
import org.jackie.jmodel.JNode;
import static org.jackie.utils.Assert.typecast;
import static org.jackie.compiler.util.Context.context;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * @author Patrik Beno
 */
public class ExtensionsImpl implements Extensions {

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

		ext = context().extensionSupport().apply(jnode, type);
		if (ext == null) {
			return null;
		}

		extensions.put(type, ext);

		return typecast(ext, type);
	}
}
