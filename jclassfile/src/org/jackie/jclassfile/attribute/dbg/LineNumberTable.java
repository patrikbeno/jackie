package org.jackie.jclassfile.attribute.dbg;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class LineNumberTable extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "LineNumberTable";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new LineNumberTable(owner);
		}
	}

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


	public LineNumberTable(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
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

	protected void writeData(XDataOutput out) {
		writeLength(out, 2+items.size()*4);
		out.writeShort(items.size());
		for (Item item : items) {
			out.writeShort(item.startpc);
			out.writeShort(item.lineNumber);
		}
	}
}
