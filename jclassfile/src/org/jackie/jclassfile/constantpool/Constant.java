package org.jackie.jclassfile.constantpool;

import org.jackie.jclassfile.constantpool.impl.Factory;
import org.jackie.jclassfile.model.Base;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.NOTNULL;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public abstract class Constant extends Base {

	/*
cp_info {
    	u1 tag;
    	u1 info[];
    }
	 */

	ConstantPool pool;

	protected Constant(ConstantPool pool) {
		this.pool = pool;
	}

	public abstract CPEntryType type();

	public int index() {
		return pool().indexOf(this, true);
	}

	protected ConstantPool pool() {
		return pool;
	}

	protected Factory factory() {
		return pool.factory();
	}

	public boolean isLongData() { // occupies 2 entries in the pool
		return false;
	}

	public final void load(DataInput in) throws IOException {
		throw Assert.unsupported(); // see ConstantPool.load()
	}

	public final void save(DataOutput out) throws IOException {
		// u1 tag;
		out.writeByte(type().code());
		// u1 info[];
		writeConstantData(out);
	}

	protected abstract Task readConstantDataOrGetResolver(DataInput in) throws IOException;

	protected abstract void writeConstantData(DataOutput out) throws IOException;

	public <T extends Constant> T intern() {
		return (T) pool().intern(this);
	}

	public void writeReference(DataOutput out) throws IOException {
		out.writeShort(index());
	}

	public void writeByteReference(DataOutput out) throws IOException {
		out.writeByte(index());
	}

	public String toString() {
		Integer idx = pool().indexOf(this);
		return String.format("#%s %s: %s", (idx != null) ? idx : "?", type().name(), valueToString());
	}

	protected String valueToString() {
		return null;
	}
}