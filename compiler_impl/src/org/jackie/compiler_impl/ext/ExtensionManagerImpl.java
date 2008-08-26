package org.jackie.compiler_impl.ext;

import org.jackie.compiler.extension.ExtensionManager;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.context.Loader;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extension;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;

import java.util.HashMap;
import java.util.Map;

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
		loadResources();

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
		if (provider == null) {
			Class cls = classByInterface.get(extensionType);
			assert cls != null : extensionType;
			provider = newInstance(cls, ExtensionProvider.class);
			providers.put(extensionType, provider);
		}

		T ext = typecast(provider.getExtension(jnode), extensionType);
		return ext;

	}

	protected <T> T newInstance(Class cls, Class<T> type) {
		try {
			return typecast(cls.newInstance(), type);
		} catch (InstantiationException e) {
			throw Assert.notYetHandled(e);
		} catch (IllegalAccessException e) {
			throw Assert.notYetHandled(e);
		}
	}

}
