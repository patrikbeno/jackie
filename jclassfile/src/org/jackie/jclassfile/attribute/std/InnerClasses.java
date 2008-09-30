package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.util.Helper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class InnerClasses extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "InnerClasses";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new InnerClasses(owner);
		}
	}

	/*
InnerClasses_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 number_of_classes;
    	{  u2 inner_class_info_index;
    	   u2 outer_class_info_index;
    	   u2 inner_name_index;
    	   u2 inner_class_access_flags;
    	} classes[number_of_classes];
    }   
    */

	class Item {
		ClassRef inner;
		ClassRef outer;
		Utf8 innerName;
		Flags access;
	}

	void test() {
		// test
		new Serializable() {};
	}

	List<Item> classes;


	public InnerClasses(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in, ConstantPool pool) throws IOException {
		readLength(in);
		int count = in.readUnsignedShort();
		classes = new ArrayList<Item>(count);
		while (count-- > 0) {
			Item item = new Item();
			item.inner = pool().getConstant(in.readUnsignedShort(), ClassRef.class);
			item.outer = pool().getConstant(in.readUnsignedShort(), ClassRef.class);
			item.innerName = pool().getConstant(in.readUnsignedShort(), Utf8.class);
			item.access = Flags.create(in, pool());
			classes.add(item);
		}
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, 2+classes.size()*8);
		out.writeShort(classes.size());
		for (Item item : classes) {
			Helper.writeConstantReference(item.inner, out);
			Helper.writeConstantReference(item.outer, out);
			Helper.writeConstantReference(item.innerName, out);
			item.access.save(out);
		}
	}
}
