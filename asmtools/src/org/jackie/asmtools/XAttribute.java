package org.jackie.asmtools;

import org.jackie.utils.Assert;
import org.objectweb.asm.Attribute;

import java.lang.reflect.Field;

/**
 * @author Patrik Beno
 */
public class XAttribute extends Attribute {

	public XAttribute(String type) {
		super(type);
	}

	public XAttribute(String type, byte[] value) {
		super(type);
		try {
			Field f = Attribute.class.getDeclaredField("value");
			f.setAccessible(true);
			f.set(this, value);
		} catch (NoSuchFieldException e) {
			throw Assert.notYetHandled(e);
		} catch (IllegalAccessException e) {
			throw Assert.notYetHandled(e);
		}
	}

}
