package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.bytecode.AsmSupport;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationsProvider;
import org.jackie.compiler.typeregistry.EditAction;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.extension.Extensible;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.jmodel.extension.annotation.AnnotationType;
import org.jackie.jmodel.extension.annotation.Annotations;
import org.jackie.jmodel.extension.base.ClassType;
import org.jackie.jmodel.extension.base.InterfaceType;
import org.jackie.jmodel.extension.builtin.PrimitiveType;
import org.jackie.jmodel.extension.builtin.ArrayType;
import org.jackie.jmodel.extension.enumtype.EnumType;
import org.jackie.jmodel.extension.extra.PackageType;
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
