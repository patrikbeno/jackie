package org.jackie.compiler.util;

import org.testng.annotations.Test;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
@Test
public class PackageNameTest {

	public void parseStandardPackage() {
		PackageName pckgname = new PackageName("java.lang.reflection");
		Assert.expected("java.lang.reflection", pckgname.getFQName(), "getFQName()");
		Assert.expected("reflection", pckgname.getName(), "getName()");
		Assert.expected("java.lang", pckgname.getParent().getFQName(), "parent.getFQName()");
		Assert.expected("java", pckgname.getParent().getParent().getFQName(), "parent.parent.getFQName()");
		Assert.expected(null, pckgname.getParent().getParent().getParent(), "parent up to default package!");
	}


}
