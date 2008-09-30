package org.jackie.jclassfile.flags;

import org.jackie.jclassfile.model.Base;
import org.jackie.jclassfile.constantpool.ConstantPool;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class Flags extends Base {

	static public Flags create(DataInput in, ConstantPool pool) throws IOException {
		Flags flags = new Flags();
		flags.load(in);
		return flags;
	}

	int flags;

	public void set(Access flag) {
      flags |= flag.value();
   }

   public void clear(Access flag) {
      flags &= ~flag.value();
   }

   public boolean isSet(Access flag) {
      return (flags & flag.value()) != 0;
   }

   public String toString() {
      List<Access> set = new ArrayList<Access>();
      for (Access flag : Access.values()) {
         if (isSet(flag)) {
            set.add(flag);
         }
      }
      return set.toString();
   }

	public void load(DataInput in) throws IOException {
		flags = in.readUnsignedShort();
	}

	public void save(DataOutput out) throws IOException {
		out.writeShort(flags);
	}
}
