package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.utils.Assert;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;

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
