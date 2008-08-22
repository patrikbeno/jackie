package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class LocalVariableTable extends AttributeInfo {
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

	public LocalVariableTable(ClassFileProvider owner, DataInput in) throws IOException {
		super(owner, in);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		int len = readLength(in);
		int count = in.readUnsignedShort();
		items = new ArrayList<Item>(count);
		while (count-- > 0) {
			Item item = new Item();
			item.startpc = in.readUnsignedShort();
			item.length = in.readUnsignedShort();
			item.name = pool().getConstant(in.readUnsignedShort(), Utf8.class);
			item.descriptor = pool().getConstant(in.readUnsignedShort(), Utf8.class);
			item.index = in.readUnsignedShort();
			items.add(item);
		}
		assertLength(2+items.size()*Item.SIZE, len);
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
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
