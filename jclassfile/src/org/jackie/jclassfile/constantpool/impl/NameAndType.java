package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

/**
 * @author Patrik Beno
 */
public class NameAndType extends Constant {

	/*
CONSTANT_NameAndType_info {
    	u1 tag;
    	u2 name_index;
    	u2 descriptor_index;
    }
	 */

	static public final Loader LOADER = new Loader() {
		protected Constant create() {
			return new NameAndType(); 
		}
	};

	static public NameAndType create(Utf8 name, Utf8 type) {
		return new NameAndType(name, type);
	}

	Utf8 name;
	Utf8 type;

	protected NameAndType() {
	}

	protected NameAndType(Utf8 name, Utf8 type) {
		this.name = name;
		this.type = type;
	}

	public CPEntryType type() {
		return CPEntryType.NAME_AND_TYPE; 
	}

	public void register() {
		name.register();
		type.register();
		super.register();
	}

	public String name() {
		return name.value();
	}

	public String stype() {
		return type.value();
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, final ConstantPool pool) {
		final int nameidx = in.readUnsignedShort();
		final int descidx = in.readUnsignedShort();
		return new Task() {
			public void execute() {
				name = pool.getConstant(nameidx, Utf8.class);
				type = pool.getConstant(descidx, Utf8.class);
			}
		};
	}

	protected void writeConstantData(XDataOutput out) {
		name.writeReference(out);
		type.writeReference(out);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NameAndType that = (NameAndType) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (type != null ? !type.equals(that.type) : that.type != null) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (name != null ? name.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}

	protected String valueToString() {
		return String.format(
				"name={%s}, type={%s}",
				name != null ? name.value() : null,
				type != null ? type.value() : null
		);
	}
}
