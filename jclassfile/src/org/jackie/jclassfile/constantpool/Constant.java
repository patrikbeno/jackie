package org.jackie.jclassfile.constantpool;

import static org.jackie.jclassfile.constantpool.ConstantPool.constantPool;
import org.jackie.jclassfile.model.Base;
import org.jackie.utils.Assert;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;
import static org.jackie.utils.Assert.NOTNULL;
import static org.jackie.utils.Assert.doAssert;

import java.util.List;

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

	static public abstract class Loader {

		protected abstract Constant create();

		public Constant load(XDataInput in, ConstantPool pool, List<Task> secondPass) {
			Constant c = create();
			Task task = c.readConstantDataOrGetResolver(in, pool);
			if (task != null) { secondPass.add(task); }
			return c;
		}
	}

	ConstantPool pool;

	protected Constant() {
	}

	public abstract CPEntryType type();

	public ConstantPool pool() {
		return pool;
	}

	public void detach() {
		pool = null;
	}

	public boolean isRegistered() {
		return pool != null && pool.isRegistered(this);
	}

	public int index() {
		doAssert(isRegistered(), "Unknown constant index. Constant not registered: %s", this);
		return pool().indexOf(this);
	}

	public boolean isLongData() { // occupies 2 entries in the pool
		return false;
	}

	public final void load(XDataInput in, ConstantPool pool) {
		throw Assert.unsupported("see ConstantPool.load()");
	}

	public final void save(XDataOutput out) {
		// u1 tag;
		out.writeByte(type().code());
		// u1 info[];
		writeConstantData(out);
	}

	protected abstract Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool);

	protected abstract void writeConstantData(XDataOutput out);

	public void writeReference(XDataOutput out) {
		out.writeShort(NOTNULL(index()));
	}

	public void writeByteReference(XDataOutput out) {
		out.writeByte(NOTNULL(index()));
	}

	public String toString() {
		if (isRegistered()) {
			return String.format("#%s %s: %s", index(), type().name(), valueToString());
		} else {
			return String.format("%s: %s", type().name(), valueToString());
		}
	}

	protected String valueToString() {
		return null;
	}
}