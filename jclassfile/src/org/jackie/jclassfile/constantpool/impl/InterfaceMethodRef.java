package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.ConstantPool;

/**
 * @author Patrik Beno
 */
public class InterfaceMethodRef extends MethodRef {

	/*
CONSTANT_InterfaceMethodref_info {
    	u1 tag;
    	u2 class_index;
    	u2 name_and_type_index;
    }
	 */


	public InterfaceMethodRef(ConstantPool pool) {
		super(pool);
	}

	public CPEntryType type() {
		return CPEntryType.INTERFACE_METHODREF;
	}

}