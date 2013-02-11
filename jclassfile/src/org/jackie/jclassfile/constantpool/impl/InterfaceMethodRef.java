package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.utils.Assert;
import org.jackie.utils.XDataInput;

import java.util.List;

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

	static public final Loader LOADER = new Loader() {
		protected Constant create() {
			return new InterfaceMethodRef(); 
		}
	};

	static public InterfaceMethodRef create(ClassRef classref, NameAndType nametype) {
		return new InterfaceMethodRef(classref, nametype);
	}

	protected InterfaceMethodRef() {
	}

	protected InterfaceMethodRef(ClassRef classref, NameAndType nametype) {
		super(classref, nametype);
	}

	public CPEntryType type() {
		return CPEntryType.INTERFACE_METHODREF;
	}

}