package org.jackie.compilerimpl.util;

import org.jackie.utils.Assert;
import org.jackie.utils.PackageName;
import org.junit.Test;

/**
 * @author Patrik Beno
 */
public class PackageNameTest {

    @Test
	public void parseStandardPackage() {
		PackageName pckgname = new PackageName("java.lang.reflection");
		Assert.expected("java.lang.reflection", pckgname.getFQName(), "getFQName()");
		Assert.expected("reflection", pckgname.getName(), "getName()");
		Assert.expected("java.lang", pckgname.getParent().getFQName(), "parent.getFQName()");
		Assert.expected("java", pckgname.getParent().getParent().getFQName(), "parent.parent.getFQName()");
		Assert.expected(PackageName.DEFAULT, pckgname.getParent().getParent().getParent(), "parent up to default package!");
		Assert.expected(PackageName.DEFAULT, PackageName.DEFAULT.getParent(), "default's parent");
	}


}
