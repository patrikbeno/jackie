package org.jackie.jclassfile.model;

import org.jackie.jclassfile.flags.AccessFlags;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.util.Helper;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import java.util.List;

/**
 * @author Patrik Beno
 */
public abstract class MemberInfo extends Base implements ClassFileProvider {
   /*
field|member info {
    	u2 access_flags;
    	u2 name_index;
    	u2 descriptor_index;
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
    }
    */

	ClassFile classfile;

	AccessFlags access;
	Utf8 name;
	Utf8 descriptor;

	List<AttributeInfo> attributes;

	protected MemberInfo(ClassFile classfile) {
		this.classfile = classfile;
	}

	protected MemberInfo(ClassFile classfile, DataInput in) throws IOException {
		this(classfile);
		load(in);
	}

	public ClassFile classFile() {
		return classfile;
	}

	public void load(DataInput in) throws IOException {
		ConstantPool pool = classfile.pool();

		access = new AccessFlags(in);
		name = pool.getConstant(in.readUnsignedShort(), Utf8.class);
		descriptor = classfile.pool().getConstant(in.readUnsignedShort(), Utf8.class);
		attributes = Helper.loadAttributes(this, in);
	}

	public void save(DataOutput out) throws IOException {
		access.save(out);
		name.writeReference(out);
		descriptor.writeReference(out);

		out.writeInt(attributes.size());
		for (AttributeInfo a : attributes) {
			a.save(out);
		}
	}

	public String toString() {
		return String.format("%s: %s", getClass().getSimpleName(), name);
	}
}