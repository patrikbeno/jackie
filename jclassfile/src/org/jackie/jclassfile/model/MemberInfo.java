package org.jackie.jclassfile.model;

import org.jackie.jclassfile.attribute.AttributeHelper;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.code.ConstantPoolSupport;
import static org.jackie.utils.CollectionsHelper.*;
import org.jackie.utils.Log;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Patrik Beno
 */
public abstract class MemberInfo extends Base implements AttributeSupport, ConstantPoolSupport {
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

	Flags flags;
	Utf8 name;
	Utf8 descriptor;

	List<AttributeInfo> attributes;

	protected MemberInfo(ClassFile classfile) {
		this.classfile = classfile;
	}

	public Flags flags() {
		if (flags == null) {
			flags = new Flags();
		}
		return flags;
	}

	public String name() {
		return name.value();
	}

	public void name(String name) {
		this.name = Utf8.create(name);
	}

	public String descriptor() {
		return descriptor.value();
	}

	public void descriptor(String descriptor) {
		this.descriptor = Utf8.create(descriptor);
	}

	public List<AttributeInfo> attributes() {
		return attributes != null ? attributes : Collections.<AttributeInfo>emptyList();
	}

	public void addAttribute(AttributeInfo attribute) {
		if (attributes == null) {
			attributes = new ArrayList<AttributeInfo>();
		}
		attributes.add(attribute);
	}

	public ClassFile classFile() {
		return classfile;
	}

	public void load(XDataInput in, ConstantPool pool) {
		flags = Flags.create(in, pool);
		name = pool.getConstant(in.readUnsignedShort(), Utf8.class);
		descriptor = pool.getConstant(in.readUnsignedShort(), Utf8.class);

		Log.debug("Loading member %s.%s : %s", classfile.classname(), name(), descriptor());

		attributes = AttributeHelper.loadAttributes(this, in);
	}

	public void registerConstants(ConstantPool pool) {
		name = pool.register(name);
		descriptor = pool.register(descriptor);
		for (AttributeInfo a : iterable(attributes)) {
			a.registerConstants(pool);
		}
	}

	public void save(XDataOutput out) {
		Log.debug("Saving %s", this);

		flags().save(out);
		name.writeReference(out);
		descriptor.writeReference(out);

		out.writeShort(sizeof(attributes));
		for (AttributeInfo a : iterable(attributes)) {
			a.save(out);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MemberInfo that = (MemberInfo) o;

		if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) {
			return false;
		}
		if (!descriptor.equals(that.descriptor)) return false;
		if (!flags.equals(that.flags)) return false;
		if (!name.equals(that.name)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = flags.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + descriptor.hashCode();
		result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
		return result;
	}

	public String toString() {
		return String.format("%s: %s", getClass().getSimpleName(), name);
	}
}