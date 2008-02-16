package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.utils.Assert;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Patrik Beno
 */
@Test
public class TypeRegistryTest {


	public void primitives() {
		PrimitiveTypeRegistry r = new PrimitiveTypeRegistry();
		Assert.notNull(r.getJClass(void.class));
		Assert.notNull(r.getJClass(boolean.class));
		Assert.notNull(r.getJClass(byte.class));
		Assert.notNull(r.getJClass(short.class));
		Assert.notNull(r.getJClass(int.class));
		Assert.notNull(r.getJClass(long.class));
		Assert.notNull(r.getJClass(float.class));
		Assert.notNull(r.getJClass(double.class));
		Assert.notNull(r.getJClass(char.class));

		JClassImpl cls = r.getJClass(void.class);
		Assert.expected("void", cls.getFQName(), "invalid class fqname");
	}

	public void primitiveArrays() {
		PrimitiveTypeRegistry r = new PrimitiveTypeRegistry();
		Assert.notNull(r.getJClass(int[].class));
		Assert.notNull(r.getJClass(int[][].class));
		Assert.notNull(r.getJClass(int[][][].class));
	}

	public void classes() {

		JClassRegistry r = new JClassRegistry(new HashSet<String>(Arrays.asList(
				Object.class.getName(),
				Object[].class.getName(),
				Object[][].class.getName()
		)));

		Assert.notNull(r.getJClass(Object.class));
		Assert.notNull(r.getJClass(Object[].class));
		Assert.notNull(r.getJClass(Object[][].class));
	}

	public void multiregistry() {
		MultiRegistry r = new MultiRegistry(Arrays.<TypeRegistry>asList(
				new PrimitiveTypeRegistry(),
				new JClassRegistry(new HashSet<String>(Arrays.asList(
						Object.class.getName(),
						String.class.getName()
				)))
		));

		JClassImpl c;

		Assert.notNull(r.getJClass(int.class));
		Assert.notNull(r.getJClass(int[].class));
		Assert.notNull(r.getJClass(String.class));
		Assert.notNull(r.getJClass(String[].class));

	}

}
