package org.jackie.jclassfile.attribute.dbg;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.attribute.AttributeProvider;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class LocalVariableTypeTable extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "LocalVariableTypeTable";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new LocalVariableTypeTable(owner);
		}
	}


	/*
LocalVariableTypeTable_attribute {
     u2 attribute_name_index;
     u4 attribute_length;
     u2 local_variable_type_table_length;
     {
       u2 start_pc;
       u2 length;
       u2 name_index;
       u2 signature_index;
       u2 index;
     } local_variable_type_table[local_variable_type_table_length];
   }

	 */

	class Item {
		int startpc;
		int length;
		Utf8 name;
		Utf8 signature;
		int index;
	}

	List<Item> items;


	public LocalVariableTypeTable(ClassFileProvider owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		readLength(in);

		int count = in.readUnsignedShort();
		items = new ArrayList<Item>(count);
		while (count-- > 0) {
			Item item = new Item();
			item.startpc = in.readUnsignedShort();
			item.length = in.readUnsignedShort();
			item.name = pool().getConstant(in.readUnsignedShort(), Utf8.class);
			item.signature = pool().getConstant(in.readUnsignedShort(), Utf8.class);
			items.add(item);
		}

		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, 2 + items.size() * 10);
		out.writeShort(items.size());
		for (Item item : items) {
			out.writeShort(item.startpc);
			out.writeShort(item.length);
			item.name.writeReference(out);
			item.signature.writeReference(out);
			out.writeShort(item.index);
		}
	}
}
