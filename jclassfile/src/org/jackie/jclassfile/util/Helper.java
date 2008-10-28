package org.jackie.jclassfile.util;

import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.utils.XDataOutput;

import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class Helper {

	static public void writeConstantReference(Constant constant, XDataOutput out) {
		if (constant == null) {
			out.writeShort(0);
		} else {
			constant.writeReference(out);
		}
	}

}
