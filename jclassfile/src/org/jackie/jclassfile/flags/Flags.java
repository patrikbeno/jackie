package org.jackie.jclassfile.flags;

import org.jackie.jclassfile.model.Base;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class Flags extends Base {

	static public Flags create(XDataInput in, ConstantPool pool) {
		Flags flags = new Flags();
		flags.load(in, pool);
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

	public void load(XDataInput in, ConstantPool pool) {
		flags = in.readUnsignedShort();
	}

	public void save(XDataOutput out) {
		out.writeShort(flags);
	}
}
