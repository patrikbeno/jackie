package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class FieldRef extends BaseRef {

	/*
CONSTANT_Fieldref_info {
    	u1 tag;
    	u2 class_index;
    	u2 name_and_type_index;
    }
	 */

	FieldRef(ConstantPool pool) {
		super(pool);
	}

	FieldRef(ConstantPool pool, String clsname, String name, String type) {
		super(pool, clsname, name, type);
	}

	public CPEntryType type() {
		return CPEntryType.FIELDREF;
	}
}
