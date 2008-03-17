package org.jackie.compiler_impl.util;

import static org.jackie.utils.Assert.typecast;
import static org.jackie.utils.Assert.doAssert;
import org.jackie.compiler_impl.jmodelimpl.JClassImpl;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.jvm.JClass;

import java.lang.reflect.Array;

/**
 * @author Patrik Beno
 */
public class Helper {

	public static boolean empty(Object array) {
		return array == null || Array.getLength(array)==0;
	}

	public static int length(Object array) {
		return (array != null) ?  Array.getLength(array) : 0;
	}

	static public void assertEditable(JClass jclass) {
		TypeRegistry registry = typecast(jclass, JClassImpl.class).getTypeRegistry();
		doAssert(registry.isEditable(),
					"Not editable: %s (type registry: %s)", jclass.getFQName(), registry);
	}

	static public JClassImpl impl(JClass jclass) {
		return typecast(jclass, JClassImpl.class);
	}

}
