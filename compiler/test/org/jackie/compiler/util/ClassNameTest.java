package org.jackie.compiler.util;

import org.jackie.utils.Assert;

import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ClassNameTest {

	public void parseSimpleClassName() {
		ClassName clsname = new ClassName("java.lang.Object");
		Assert.expected("java.lang", clsname.getPackageName().getFQName(), "package name!");
		Assert.expected("Object", clsname.getName(), "class name!");
		Assert.expected("Object", clsname.getSimpleName(), "simple class name!");
	}

	public void parseNestedClassName() {
		ClassName clsname = new ClassName("java.lang.Object$Nested");
		Assert.expected("java.lang", clsname.getPackageName().getFQName(), "package name!");
		Assert.expected("Object$Nested", clsname.getName(), "class name!");
		Assert.expected("Nested", clsname.getSimpleName(), "simple class name!");
	}

	public void parseLocalClassName() {
		ClassName clsname = new ClassName("java.lang.Object$Nested$1Local");
		Assert.expected("java.lang", clsname.getPackageName().getFQName(), "package name!");
		Assert.expected("Object$Nested$1Local", clsname.getName(), "class name!");
		Assert.expected("Local", clsname.getSimpleName(), "simple class name!");
	}

}
