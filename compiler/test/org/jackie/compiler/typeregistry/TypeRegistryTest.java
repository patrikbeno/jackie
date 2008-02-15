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
		Assert.notNull(r.getJClass(boolean.class));
		Assert.notNull(r.getJClass(byte.class));
		Assert.notNull(r.getJClass(short.class));
		Assert.notNull(r.getJClass(int.class));
		Assert.notNull(r.getJClass(long.class));
		Assert.notNull(r.getJClass(float.class));
		Assert.notNull(r.getJClass(double.class));
		Assert.notNull(r.getJClass(char.class));
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

		JClassImpl c;

		c = r.getJClass(Object.class);
		c = r.getJClass(Object[].class);
		c = r.getJClass(Object[][].class);
	}

}
