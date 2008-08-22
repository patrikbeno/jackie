package org.jackie.jclassfile.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class MethodInfo extends MemberInfo {
   /*
method_info {
    	u2 access_flags;
    	u2 name_index;
    	u2 descriptor_index;
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
    }
    */


	public MethodInfo(ClassFile classfile) {
		super(classfile);
	}

	public MethodInfo(ClassFile classfile, DataInput in) throws IOException {
		super(classfile, in);
	}

	public void save(DataOutput out) throws IOException {
		access.save(out);
		name.writeReference(out);
		descriptor.writeReference(out);

		out.writeShort(attributes.size());
		for (AttributeInfo a : attributes) {
			a.save(out);
		}
	}
	
}
