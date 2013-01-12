package org.jackie.test.jclassfile;

import org.jackie.jclassfile.util.MethodDescriptor;
import org.jackie.utils.Assert;
import org.junit.Test;

/**
 * @author Patrik Beno
 */
public class MethodDescriptorTest {

    @Test
	public void returnPrimitives() {
		MethodDescriptor desc;

		desc = new MethodDescriptor("()V");
		Assert.expected("void", desc.getReturnType().getTypeName(), "invalid return type");

		desc = new MethodDescriptor("()B");
		Assert.expected("byte", desc.getReturnType().getTypeName(), "invalid return type");
	}

    @Test
	public void returnObject() {
		MethodDescriptor desc = new MethodDescriptor("()Ljava/lang/Object;");
		Assert.expected("java/lang/Object", desc.getReturnType().getTypeName(), "invalid return type");
	}

    @Test
	public void returnArray() {
		MethodDescriptor desc;

		desc = new MethodDescriptor("()[Ljava/lang/Object;");
		Assert.expected("java/lang/Object", desc.getReturnType().getTypeName(), "invalid return type");
		Assert.expected(1, desc.getReturnType().getDimensions(), "invalid array dimensions");

		desc = new MethodDescriptor("()[[[Ljava/lang/Object;");
		Assert.expected("java/lang/Object", desc.getReturnType().getTypeName(), "invalid return type");
		Assert.expected(3, desc.getReturnType().getDimensions(), "invalid array dimensions");
	}

    @Test
	public void parameters() {
		MethodDescriptor desc;

		desc = new MethodDescriptor("(BC)[Ljava/lang/Object;");
		Assert.expected(2, desc.getParameterTypes().size(), "invalid parameter count");
		Assert.expected("byte", desc.getParameterTypes().get(0).getTypeName(), "invalid parameter type");
		Assert.expected("char", desc.getParameterTypes().get(1).getTypeName(), "invalid parameter type");

	}



}
