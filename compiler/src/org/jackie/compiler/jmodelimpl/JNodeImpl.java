package org.jackie.compiler.jmodelimpl;

import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;
import org.jackie.jmodel.JClass;

/**
 * @author Patrik Beno
 */
public class JNodeImpl {

	protected JClassImpl getClassImpl(JClass jclass) {
		return context().typeRegistry().getJClass(new ClassName(jclass));
	}

}
