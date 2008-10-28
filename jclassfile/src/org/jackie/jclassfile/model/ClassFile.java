package org.jackie.jclassfile.model;

import org.jackie.jclassfile.attribute.AttributeHelper;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.ConstantPool;
import static org.jackie.jclassfile.constantpool.ConstantPool.constantPool;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.flags.Flags;
import static org.jackie.jclassfile.util.Helper.writeConstantReference;
import static org.jackie.utils.CollectionsHelper.*;
import org.jackie.utils.Log;
import org.jackie.utils.IOHelper;
import org.jackie.utils.XDataInput;
import org.jackie.utils.ByteArrayDataOutput;
import org.jackie.utils.XDataOutput;
import static org.jackie.utils.Assert.NOTNULL;
import static org.jackie.context.ContextManager.context;
import static org.jackie.context.ContextManager.closeContext;
import static org.jackie.context.ContextManager.newContext;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class ClassFile implements AttributeSupport {

	/*
ClassFile {
    	u4 magic;
    	u2 minor_version;
    	u2 major_version;
    	u2 constant_pool_count;
    	cp_info constant_pool[constant_pool_count-1];
    	u2 access_flags;
    	u2 this_class;
    	u2 super_class;
    	u2 interfaces_count;
    	u2 interfaces[interfaces_count];
    	u2 fields_count;
    	field_info fields[fields_count];
    	u2 methods_count;
    	method_info methods[methods_count];
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
    }
    */

	static public final boolean DEBUG = true;

	int magic = 0xCAFEBABE;
	int minor;
	int major = 50;

	Flags flags;
	ClassRef classname;
	ClassRef superclass;
	List<ClassRef> interfaces;

	List<FieldInfo> fields;
	List<MethodInfo> methods;
	List<AttributeInfo> attributes;

	{
		flags = new Flags();
	}

	public ClassFile classname(String clsname) {
		classname = ClassRef.create(Utf8.create(clsname));
		return this;
	}

	public String classname() {
		return classname.value();
	}

	public String superclass() {
		return superclass != null ? superclass.value() : null;
	}

	public ClassFile superclass(String clsname) {
		superclass = ClassRef.create(Utf8.create(clsname));
		return this;
	}

	public Flags flags() {
		return flags;
	}

	public List<ClassRef> interfaces() {
		return interfaces;
	}

	public void addInterface(String className) {
		if (interfaces == null) {
			interfaces = new ArrayList<ClassRef>();
		}
		interfaces.add(ClassRef.create(Utf8.create(className)));
	}

	public List<FieldInfo> fields() {
		return fields;
	}

	public void addField(FieldInfo field) {
		if (fields == null) {
			fields = new ArrayList<FieldInfo>();
		}
		fields.add(field);
	}

	public List<MethodInfo> methods() {
		return methods != null ? methods : Collections.<MethodInfo>emptyList();
	}

	public void addMethod(MethodInfo method) {
		if (methods == null) {
			methods = new ArrayList<MethodInfo>();
		}
		methods.add(method);
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

	public AttributeInfo getAtrribute(String name) {
		NOTNULL(name);
		for (AttributeInfo a : attributes()) {
			if (a.name().equals(name)) {
				return a;
			}
		}
		return null;
	}

	public boolean hasAttribute(String name) {
		return getAtrribute(name) != null;
	}

	///

	public void load(XDataInput in) {
		newContext();
		try {
			magic = in.readInt();
			minor = in.readUnsignedShort();
			major = in.readUnsignedShort();

			ConstantPool pool = ConstantPool.create(true);

			pool.load(in, pool);

			flags = Flags.create(in, pool);
			classname = pool.getConstant(in.readUnsignedShort(), ClassRef.class);
			Log.debug("Loading class %s", classname.value());

			superclass = pool.getConstant(in.readUnsignedShort(), ClassRef.class);

			// interfaces
			{
				int count = in.readUnsignedShort();
				interfaces = new ArrayList<ClassRef>(count);
				while (count-- > 0) {
					ClassRef iface = pool.getConstant(in.readUnsignedShort(), ClassRef.class);
					interfaces.add(iface);
				}
			}

			// fields
			{
				int count = in.readUnsignedShort();
				fields = new ArrayList<FieldInfo>(count);
				while (count-- > 0) {
					FieldInfo f = new FieldInfo(this);
					f.load(in, pool);
					fields.add(f);
				}
			}

			// methods
			{
				int count = in.readUnsignedShort();
				methods = new ArrayList<MethodInfo>(count);
				while (count-- > 0) {
					MethodInfo m = new MethodInfo(this);
					m.load(in, pool);
					methods.add(m);
				}
			}

			attributes = AttributeHelper.loadAttributes(this, in);

		} finally {
			closeContext();
		}
	}

	public void save(XDataOutput out) {
		newContext();
		try {
			out.writeInt(magic);
			out.writeShort(minor);
			out.writeShort(major);

			ConstantPool pool = context().set(ConstantPool.class, new ConstantPool());

			ByteArrayDataOutput tmpout = new ByteArrayDataOutput();

			flags.save(tmpout);
			writeConstantReference(classname, tmpout);
			writeConstantReference(superclass, tmpout);

			// interfaces
			tmpout.writeShort(sizeof(interfaces));
			for (ClassRef i : iterable(interfaces)) {
				i.writeReference(tmpout);
			}

			save(tmpout, fields);
			save(tmpout, methods);
			save(tmpout, attributes);

			pool.save(out);

			out.write(tmpout.toByteArray());

		} finally {
			closeContext();
		}
	}

	private void save(XDataOutput out, List<? extends Base> items) {
		out.writeShort(sizeof(items));
		for (Base item : iterable(items)) {
			item.save(out);
		}
	}

	public byte[] toByteArray() {
		ByteArrayDataOutput out = new ByteArrayDataOutput();
		save(out);
		return out.toByteArray();

	}

	public String toString() {
		return String.format("ClassFile(%s)", classname != null ? classname.value() : "?");
	}

}
