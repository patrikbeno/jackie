package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.builtin.ArrayType;
import org.jackie.utils.Assert;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;

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

		ClassName clsname = new ClassName(node());
		component = context().typeRegistry().getJClass(clsname.getComponentType());
		dimensions = clsname.getDimensions();

		return component;
	}

	public int getDimensions() {
		getComponentType();
		return dimensions;
	}
}
