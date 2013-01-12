package org.jackie.compilerimpl.typeregistry;

import org.jackie.compiler.typeregistry.TypeRegistry;
import static org.jackie.context.ContextManager.*;
import org.jackie.jvm.JClass;
import org.jackie.utils.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Patrik Beno
 */
public class TypeRegistryTest {


    @Test
	public void primitives() {
		newContext();
		try {
			PrimitiveTypeRegistry r = new PrimitiveTypeRegistry();
			context().set(TypeRegistry.class, r);

			Assert.notNull(r.getJClass(void.class));
			Assert.notNull(r.getJClass(boolean.class));
			Assert.notNull(r.getJClass(byte.class));
			Assert.notNull(r.getJClass(short.class));
			Assert.notNull(r.getJClass(int.class));
			Assert.notNull(r.getJClass(long.class));
			Assert.notNull(r.getJClass(float.class));
			Assert.notNull(r.getJClass(double.class));
			Assert.notNull(r.getJClass(char.class));

			JClass cls = r.getJClass(void.class);
			Assert.expected("void", cls.getFQName(), "invalid class fqname");
		} finally {
			closeContext();
		}
	}

    @Test
	public void primitiveArrays() {
		newContext();
		try {
			PrimitiveTypeRegistry r = new PrimitiveTypeRegistry();
			context().set(TypeRegistry.class, r);

			Assert.notNull(r.getJClass(int[].class));
			Assert.notNull(r.getJClass(int[][].class));
			Assert.notNull(r.getJClass(int[][][].class));
		} finally {
			closeContext();
		}
	}

    @Test
	public void classes() {
		newContext();
		try {
			JClassRegistry r = new JClassRegistry(new HashSet<String>(Arrays.asList(
					Object.class.getName(),
					Object[].class.getName(),
					Object[][].class.getName()
			)));
			context().set(TypeRegistry.class, r);

			Assert.notNull(r.getJClass(Object.class));
			Assert.notNull(r.getJClass(Object[].class));
			Assert.notNull(r.getJClass(Object[][].class));
		} finally {
			closeContext();
		}
	}

    @Test
	public void multiregistry() {
		newContext();
		try {
			MultiRegistry r = new MultiRegistry(Arrays.<TypeRegistry>asList(
					new PrimitiveTypeRegistry(),
					new JClassRegistry(new HashSet<String>(Arrays.asList(
							Object.class.getName(),
							String.class.getName()
					)))
			));
			context().set(TypeRegistry.class, r);

			Assert.notNull(r.getJClass(int.class));
			Assert.notNull(r.getJClass(int[].class));
			Assert.notNull(r.getJClass(String.class));
			Assert.notNull(r.getJClass(String[].class));
		} finally {
			closeContext();
		}

	}

}
