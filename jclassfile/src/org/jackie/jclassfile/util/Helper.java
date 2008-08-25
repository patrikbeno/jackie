package org.jackie.jclassfile.util;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.attribute.GenericAttribute;
import org.jackie.jclassfile.attribute.std.Code;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.impl.Utf8;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class Helper {

	static public void writeConstantReference(Constant constant, DataOutput out) throws IOException {
		if (constant == null) {
			out.writeShort(0);
		} else {
			constant.writeReference(out);
		}
	}

}
