package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.ConstantPool;

/**
 * @author Patrik Beno
 */
public class MethodRef extends BaseRef {

	/*
CONSTANT_Methodref_info {
    	u1 tag;
    	u2 class_index;
    	u2 name_and_type_index;
    }
	 */

	MethodRef(ConstantPool pool) {
		super(pool);
	}

	public CPEntryType type() {
		return CPEntryType.METHODREF;
	}

	protected String valueToString() {
		return String.format("%s.%s%s", classref.value(), nametype.name(), nametype.stype());
	}

}