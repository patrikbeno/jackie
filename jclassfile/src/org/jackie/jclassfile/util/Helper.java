package org.jackie.jclassfile.util;

import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.code.ConstantPoolSupport;
import org.jackie.utils.XDataOutput;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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

	static public <T extends Constant> List<T> register(List<T> items, ConstantPool pool) {
		if (items == null || items.isEmpty()) { return items; }
		
		List<T> newitems = new ArrayList<T>();
		for (T item : new ArrayList<T>(items)) {
			item = pool.register(item);
			newitems.add(item);
		}
		return newitems;
	}

}
