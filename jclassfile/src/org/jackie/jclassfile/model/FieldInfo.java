package org.jackie.jclassfile.model;

import org.jackie.jclassfile.util.TypeDescriptor;
import static org.jackie.utils.CollectionsHelper.iterable;

/**
 * @author Patrik Beno
 */
public class FieldInfo extends MemberInfo {
   /*
field_info {
    	u2 access_flags;
    	u2 name_index;
    	u2 descriptor_index;
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
    }   
    */

	public FieldInfo(ClassFile classfile) {
		super(classfile);
	}

	public TypeDescriptor typeDescriptor() {
		return new TypeDescriptor(descriptor());
	}

	public void typeDescriptor(TypeDescriptor descriptor) {
		this.descriptor(descriptor.getDescriptor());
	}

}
