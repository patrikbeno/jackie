package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class StackMapTable extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "StackMapTable";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new StackMapTable(owner);
		}
	}

	/*
StackMapTable_attribute {                           !!! this is Java6, should be in java6 module !!!
	u2 attribute_name_index;
	u4 attribute_length
	u2 number_of_entries;
	stack_map_frame entries[number_of_entries];
}
union stack_map_frame {
	same_frame;
	same_locals_1_stack_item_frame;
	same_locals_1_stack_item_frame_extended;
	chop_frame;
	same_frame_extended;
	append_frame;
	full_frame;
}
same_frame {
	u1 frame_type = SAME; // 0-63 
}
same_locals_1_stack_item_frame {
	u1 frame_type = SAME_LOCALS_1_STACK_ITEM; // 64-127
	verification_type_info stack[1];
}
same_locals_1_stack_item_frame_extended {
	u1 frame_type = SAME_LOCALS_1_STACK_ITEM_EXTENDED; // 247
	u2 offset_delta;
	verification_type_info stack[1];
}
chop_frame {
	u1 frame_type=CHOP; // 248-250
	u2 offset_delta;
}
same_frame_extended {
	u1 frame_type = SAME_FRAME_EXTENDED; // 251
	u2 offset_delta;
}
append_frame {
	u1 frame_type = APPEND; // 252-254
	u2 offset_delta;
	verification_type_info locals[frame_type -251];
}
full_frame {
	u1 frame_type = FULL_FRAME; // 255
	u2 offset_delta;
	u2 number_of_locals;
	verification_type_info locals[number_of_locals];
	u2 number_of_stack_items;
	verification_type_info stack[number_of_stack_items];
}
union verification_type_info {
	Top_variable_info;
	Integer_variable_info;
	Float_variable_info;
	Long_variable_info;
	Double_variable_info;
	Null_variable_info;
	UninitializedThis_variable_info;
	Object_variable_info;
	Uninitialized_variable_info;
}
Top_variable_info {
	u1 tag = ITEM_Top; // 0
}
Integer_variable_info {
	u1 tag = ITEM_Integer; // 1 
}
Float_variable_info {
	u1 tag = ITEM_Float; // 2
}
Long_variable_info {
	u1 tag = ITEM_Long; // 4
}
Double_variable_info {
	u1 tag = ITEM_Double; // 3
}
Null_variable_info {
	u1 tag = ITEM_Null; // 5 
}
UninitializedThis_variable_info {
	u1 tag = ITEM_UninitializedThis; // 6
}
Object_variable_info {
	u1 tag = ITEM_Object; // 7
	u2 cpool_index;
}
Uninitialized_variable_info {
	u1 tag = ITEM_Uninitialized // 8
	u2 offset;
}
	*/

	public StackMapTable(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	protected void writeData(XDataOutput out) {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
