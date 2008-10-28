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
public class FieldRef extends BaseRef {

	/*
CONSTANT_Fieldref_info {
    	u1 tag;
    	u2 class_index;
    	u2 name_and_type_index;
    }
	 */

	static public final Loader LOADER = new Loader() {
		protected Constant create() {
			return new FieldRef(); 
		}
	};

	protected FieldRef() {
	}

	protected FieldRef(ClassRef classref, NameAndType nametype) {
		super(classref, nametype);
	}

	public CPEntryType type() {
		return CPEntryType.FIELDREF;
	}

	protected String valueToString() {
		return String.format("%s.%s : %s", classref.value(), nametype.name(), nametype.stype());
	}
}
