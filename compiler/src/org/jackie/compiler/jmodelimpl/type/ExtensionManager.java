package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.bytecode.AsmSupport;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationsProvider;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.java5.annotation.AnnotationType;
import org.jackie.java5.annotation.Annotations;
import org.jackie.java5.base.ClassType;
import org.jackie.java5.base.InterfaceType;
import org.jackie.jmodel.extension.builtin.PrimitiveType;
import org.jackie.jmodel.extension.builtin.ArrayType;
import org.jackie.java5.enumtype.EnumType;
import org.jackie.java5.extra.PackageType;
import static org.jackie.utils.Assert.typecast;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class ExtensionManager extends AsmSupport {

	Map<Class<? extends Extension>, ExtensionProvider> providers;

	{
		providers = new HashMap<Class<? extends Extension>, ExtensionProvider>();

		// fixme hardcoded ExtensionProvider list
		providers.put(PrimitiveType.class, new PrimitiveTypeProvider());
		providers.put(ArrayType.class, new ArrayTypeProvider());

		providers.put(ClassType.class, new ClassTypeProvider());
		providers.put(InterfaceType.class, new InterfaceTypeProvider());
		providers.put(EnumType.class, new EnumTypeProvider());
		providers.put(AnnotationType.class, new AnnotationTypeProvider());
		providers.put(Annotations.class, new AnnotationsProvider());

		providers.put(PackageType.class, new PackageTypeProvider());
	}

	public <T extends Extension> T apply(JNode jnode, Class<T> extensionType ) {
		ExtensionProvider provider = providers.get(extensionType);
		T ext = typecast(provider.getExtension(jnode), extensionType);
		return ext;
	}
}
