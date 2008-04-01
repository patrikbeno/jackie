package org.jackie.compiler_impl;

import org.jackie.context.Loader;
import org.jackie.compiler.extension.ExtensionManager;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.JNode;
import static org.jackie.utils.Assert.typecast;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Patrik Beno
 */
public class ExtensionManagerImpl extends Loader implements ExtensionManager {

	public ExtensionManagerImpl() {
		super("META-INF/org.jackie/extensions.properties");
	}

	Map<Class<? extends Extension>, ExtensionProvider> providers;

	{
		providers = new HashMap<Class<? extends Extension>, ExtensionProvider>();

//		// fixme hardcoded ExtensionProvider list
//		providers.put(PrimitiveType.class, new PrimitiveTypeProvider());
//		providers.put(ArrayType.class, new ArrayTypeProvider());
//
//		providers.put(ClassType.class, new ClassTypeProvider());
//		providers.put(InterfaceType.class, new InterfaceTypeProvider());
//		providers.put(EnumType.class, new EnumTypeProvider());
//		providers.put(AnnotationType.class, new AnnotationTypeProvider());
//		providers.put(Annotations.class, new AnnotationsProvider());
//
//		providers.put(PackageType.class, new PackageTypeProvider());
	}

	public <T extends Extension> T apply(JNode jnode, Class<T> extensionType ) {
		ExtensionProvider provider = providers.get(extensionType);
		T ext = typecast(provider.getExtension(jnode), extensionType);
		return ext;
	}


}
