package org.jackie.compiler_impl.jmodelimpl.type;

import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.builtin.ArrayType;
import org.jackie.utils.ClassName;
import static org.jackie.compiler.Context.context;

/**
 * @author Patrik Beno
 */
public class ArrayTypeImpl extends AbstractExtension<JClass> implements ArrayType {

	JClass component;
	int dimensions;

	public ArrayTypeImpl(JClass node) {
		super(node);
	}

	public JClass getComponentType() {
		if (component != null) {
			return component;
		}

		ClassName clsname = new ClassName(node().getFQName());
		component = context().typeRegistry().getJClass(clsname.getComponentType());
		dimensions = clsname.getDimensions();

		return component;
	}

	public int getDimensions() {
		getComponentType();
		return dimensions;
	}
}
