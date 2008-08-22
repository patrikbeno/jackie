package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.code.Insn;
import org.jackie.jclassfile.util.Helper;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class Code extends AttributeInfo {
   /*
Code_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 max_stack;
    	u2 max_locals;
    	u4 code_length;
    	u1 code[code_length];
    	u2 exception_table_length;
    	{    	u2 start_pc;
    	      	u2 end_pc;
    	      	u2  handler_pc;
    	      	u2  catch_type;
    	}	exception_table[exception_table_length];
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
    }   
    */

	class ExceptionTableItem {
		int startpc;
		int endpc;
		int handlerpc;
		ClassRef exception;
	}

	int maxStack;
	int maxLocals;

	List<Insn> instructions;
	List<ExceptionTableItem> exceptions;
	List<AttributeInfo> attributes;

	public Code(ClassFileProvider owner, DataInput in) throws IOException {
		super(owner, in);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		int len = readLength(in);

		maxStack = in.readUnsignedShort();
		maxLocals = in.readUnsignedShort();

		int codelen = in.readInt();
		for (int i=0; i<codelen;) {
			i += readInstruction(in);
		}

		int exlen = in.readUnsignedShort();
		exceptions = new ArrayList<ExceptionTableItem>(exlen);
		while (exlen-- > 0) {
			ExceptionTableItem item = new ExceptionTableItem();
			item.startpc = in.readUnsignedShort();
			item.endpc = in.readUnsignedShort();
			item.handlerpc = in.readUnsignedShort();
			item.exception = pool().getConstant(in.readUnsignedShort(), ClassRef.class);
			exceptions.add(item);
		}

		attributes = Helper.loadAttributes(owner, in);

		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		int codesize = calculateCodeSize();

		writeLength(out, 2+2+4+codesize+2+exceptions.size()*8);

		out.writeShort(maxStack);
		out.writeShort(maxLocals);

		out.writeInt(codesize);
		for (Insn insn : instructions) {
			insn.write(out);
		}

		out.writeShort(exceptions.size());
		for (ExceptionTableItem item : exceptions) {
			out.writeShort(item.startpc);
			out.writeShort(item.endpc);
			out.writeShort(item.handlerpc);
			item.exception.writeReference(out);
		}

		out.writeShort(attributes.size());
		for (AttributeInfo a : attributes) {
			a.save(out);
		}
	}

	int readInstruction(DataInput in) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	int calculateCodeSize() {
		int size = 0;
		for (Insn insn : instructions) {
			size += insn.size();
		}
		return size;
	}
}
