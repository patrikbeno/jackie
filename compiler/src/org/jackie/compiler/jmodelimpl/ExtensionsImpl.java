package org.jackie.compiler.jmodelimpl;

import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.Extensions;
import static org.jackie.utils.Assert.typecast;

import java.util.Map;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class ExtensionsImpl implements Extensions {

	Map<Class<? extends Extension>, Extension> extensions;


	public <T extends Extension> Set<Class<T>> supported() {
		//noinspection unchecked
		return (Set) extensions.keySet();
	}

	public <T extends Extension> boolean supports(Class<T> type) {
		return extensions.containsKey(type);
	}

	public <T extends Extension> T get(Class<T> type) {
		return typecast(extensions.get(type), type);
	}
}
