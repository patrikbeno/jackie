package org.jackie.jclassfile.attribute.dbg;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class LocalVariableTable extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "LocalVariableTable";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new LocalVariableTable(owner);
		}
	}

	/*
   LocalVariableTable_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 local_variable_table_length;
    	{  u2 start_pc;
    	    u2 length;
    	    u2 name_index;
    	    u2 descriptor_index;
    	    u2 index;
    	} local_variable_table[local_variable_table_length];
    }
    */

	class Item {
		static final int SIZE = 10;
		int startpc;
		int length;
		Utf8 name;
		Utf8 descriptor;
		int index;
	}

	List<Item> items;


	public LocalVariableTable(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		int len = readLength(in);
		int count = in.readUnsignedShort();
		items = new ArrayList<Item>(count);
		while (count-- > 0) {
			Item item = new Item();
			item.startpc = in.readUnsignedShort();
			item.length = in.readUnsignedShort();
			item.name = pool.getConstant(in.readUnsignedShort(), Utf8.class);
			item.descriptor = pool.getConstant(in.readUnsignedShort(), Utf8.class);
			item.index = in.readUnsignedShort();
			items.add(item);
		}
		assertLength(2+items.size()*Item.SIZE, len);
		return null;
	}

	protected void writeData(XDataOutput out) {
		writeLength(out, 2+items.size()*Item.SIZE);
		out.writeShort(items.size());
		for (Item item : items) {
			out.writeShort(item.startpc);
			out.writeShort(item.length);
			item.name.writeReference(out);
			item.descriptor.writeReference(out);
			out.writeShort(item.index);
		}
	}
}
