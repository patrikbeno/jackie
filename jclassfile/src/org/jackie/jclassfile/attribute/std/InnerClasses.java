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
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;

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

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		readLength(in);
		int count = in.readUnsignedShort();
		classes = new ArrayList<Item>(count);
		while (count-- > 0) {
			Item item = new Item();
			item.inner = pool.getConstant(in.readUnsignedShort(), ClassRef.class);
			item.outer = pool.getConstant(in.readUnsignedShort(), ClassRef.class);
			item.innerName = pool.getConstant(in.readUnsignedShort(), Utf8.class);
			item.access = Flags.create(in, pool);
			classes.add(item);
		}
		return null;
	}

	protected void writeData(XDataOutput out) {
		writeLength(out, 2+classes.size()*8);
		out.writeShort(classes.size());
		for (Item item : classes) {
			Helper.writeConstantReference(item.inner, out);
			Helper.writeConstantReference(item.outer, out);
			Helper.writeConstantReference(item.innerName, out);
			item.access.save(out);
		}
	}

	@Override
	public void registerConstants(ConstantPool pool) {
		super.registerConstants(pool);
		for (Item i : classes) {
			if (i.inner != null) { i.inner = pool.register(i.inner); }
			if (i.outer != null) { i.outer = pool.register(i.outer); }
			if (i.innerName != null) { i.innerName = pool.register(i.innerName); }
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		for (Item item : classes) {
			f.format("%s %s %s %s;", item.inner, item.outer, item.innerName, item.access);
		}
		return sb.toString();
	}
}
