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

	protected ConstantPool pool;

	Integer index;
	int references;
	Task resolver; // load()

	protected Constant(ConstantPool pool) {
		this.pool = pool;
	}

	public abstract CPEntryType type();

	protected Factory factory() {
		return pool.factory;
	}

	public Integer getIndex() {
		return index;
	}

	void setIndex(Integer index) {
		this.index = index;
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

	protected void setResolver(Task task) {
		resolver = task;
	}

	boolean isResolved() {
		return resolver == null;
	}

	void resolve() {
		try {
			resolver.execute();
			resolver = null;
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	public void writeReference(DataOutput out) throws IOException {
		out.writeShort(NOTNULL(getIndex()));
		references++;
	}

	public void writeByteReference(DataOutput out) throws IOException {
		out.writeByte(NOTNULL(getIndex()));
		references++;
	}

	public String toString() {
		return String.format("#%d %s: %s", getIndex(), type().name(), valueToString());
	}

	protected String valueToString() {
		return null;
	}
}
