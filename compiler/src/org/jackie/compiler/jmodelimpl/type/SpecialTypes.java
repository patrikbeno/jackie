package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.bytecode.AsmSupport;
import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.Extension;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author Patrik Beno
 */
public class SpecialTypes extends AsmSupport {

	Set<ExtensionProvider> providers;

	{
		providers = new HashSet<ExtensionProvider>();

		// fixme hardcoded ExtensionProvider list
		providers.add(new PrimitiveTypeProvider());

		providers.add(new ClassTypeProvider());
		providers.add(new InterfaceTypeProvider());
		providers.add(new EnumTypeProvider());
		providers.add(new AnnotationTypeProvider());

		providers.add(new PackageTypeProvider());
	}


	public Map<Class<? extends Extension>, Extension> apply(JClass jclass) {
		Map<Class<? extends Extension>, Extension> specials = null;
		for (ExtensionProvider p : providers) {
			Extension special = p.getExtension(jclass);
			if (special != null) {
				if (specials == null) {
					specials = new HashMap<Class<? extends Extension>, Extension>();
				}
				specials.put(p.getType(), special);
			}
		}
		return (specials != null) ? specials : Collections.EMPTY_MAP;
	}
}
