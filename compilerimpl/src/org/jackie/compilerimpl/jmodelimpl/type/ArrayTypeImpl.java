package org.jackie.compilerimpl.jmodelimpl.type;

import org.jackie.compiler.typeregistry.TypeRegistry;
import static org.jackie.context.ContextManager.context;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.builtin.ArrayType;
import org.jackie.jvm.extension.Extension;
import org.jackie.utils.ClassName;

/**
 * @author Patrik Beno
 */
public class ArrayTypeImpl extends AbstractExtension<JClass> implements ArrayType {

	JClass component;
	int dimensions;

	public ArrayTypeImpl(JClass node) {
		super(node);
	}

	public Class<? extends Extension> type() {
		return ArrayType.class;
	}

	public JClass getComponentType() {
		if (component != null) {
			return component;
		}

		ClassName clsname = new ClassName(node().getFQName());
		component = context(TypeRegistry.class).getJClass(clsname.getComponentType());
		dimensions = clsname.getDimensions();

		return component;
	}

	public int getDimensions() {
		getComponentType();
		return dimensions;
	}
}
