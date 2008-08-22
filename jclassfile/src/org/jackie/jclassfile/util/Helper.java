package org.jackie.jclassfile.util;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.attribute.GenericAttribute;
import org.jackie.jclassfile.constantpool.Constant;
import static org.jackie.utils.Assert.doAssert;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class Helper {

	static public List<AttributeInfo> loadAttributes(ClassFileProvider owner, DataInput in) throws IOException {
		int count = in.readUnsignedShort();
		List<AttributeInfo> attributes = new ArrayList<AttributeInfo>(count);
		while (count-- > 0) {
			AttributeInfo a = new GenericAttribute(owner, in);
			attributes.add(a);
		}
		return attributes;
	}

	static public void writeConstantReference(Constant constant, DataOutput out) throws IOException {
		if (constant == null) {
			out.writeShort(0);
		} else {
			constant.writeReference(out);
		}
	}

}
