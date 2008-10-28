package org.jackie.jclassfile.model;

import org.jackie.jclassfile.util.MethodDescriptor;

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

	public MethodDescriptor methodDescriptor() {
		return new MethodDescriptor(descriptor());
	}

	public void methodDescriptor(MethodDescriptor descriptor) {
		this.descriptor(descriptor.getDescriptor());
	}

	public String toString() {
		return String.format("%s %s", name.value(), descriptor.value());
	}
}
