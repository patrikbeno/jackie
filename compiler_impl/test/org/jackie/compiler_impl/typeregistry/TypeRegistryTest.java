package org.jackie.compiler_impl.typeregistry;

import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.Context;
import static org.jackie.compiler.Context.context;
import org.jackie.utils.Assert;
import org.jackie.jvm.JClass;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Patrik Beno
 */
@Test
public class TypeRegistryTest {


	public void primitives() {
		Context.createContext();
		PrimitiveTypeRegistry r = new PrimitiveTypeRegistry();
		context().typeRegistry(r);

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
	}

	public void primitiveArrays() {
		Context.createContext();
		PrimitiveTypeRegistry r = new PrimitiveTypeRegistry();
		context().typeRegistry(r);

		Assert.notNull(r.getJClass(int[].class));
		Assert.notNull(r.getJClass(int[][].class));
		Assert.notNull(r.getJClass(int[][][].class));
	}

	public void classes() {

		Context.createContext();
		JClassRegistry r = new JClassRegistry(new HashSet<String>(Arrays.asList(
				Object.class.getName(),
				Object[].class.getName(),
				Object[][].class.getName()
		)));
		context().typeRegistry(r);

		Assert.notNull(r.getJClass(Object.class));
		Assert.notNull(r.getJClass(Object[].class));
		Assert.notNull(r.getJClass(Object[][].class));
	}

	public void multiregistry() {
		Context.createContext();
		MultiRegistry r = new MultiRegistry(Arrays.<TypeRegistry>asList(
				new PrimitiveTypeRegistry(),
				new JClassRegistry(new HashSet<String>(Arrays.asList(
						Object.class.getName(),
						String.class.getName()
				)))
		));
		context().typeRegistry(r);

		Assert.notNull(r.getJClass(int.class));
		Assert.notNull(r.getJClass(int[].class));
		Assert.notNull(r.getJClass(String.class));
		Assert.notNull(r.getJClass(String[].class));

	}

}
