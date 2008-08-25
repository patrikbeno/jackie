package org.jackie.jclassfile.attribute.dbg;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class LineNumberTable extends AttributeInfo {
   /*
LineNumberTable_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 line_number_table_length;
    	{  u2 start_pc;
    	   u2 line_number;
    	} line_number_table[line_number_table_length];
    }   
    */

	class Item {
		int startpc;
		int lineNumber;
	}

	List<Item> items;


	public LineNumberTable(ClassFileProvider owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		int len = readLength(in);
		int count = in.readUnsignedShort();
		items = new ArrayList<Item>(count);
		while (count-- > 0) {
			Item item = new Item();
			item.startpc = in.readUnsignedShort();
			item.lineNumber = in.readUnsignedShort();
			items.add(item);
		}
		assertLength(2+items.size()*4, len);
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, 2+items.size()*4);
		out.writeShort(items.size());
		for (Item item : items) {
			out.writeShort(item.startpc);
			out.writeShort(item.lineNumber);
		}
	}
}
