package org.jackie.jclassfile.flags;

import org.jackie.utils.Assert;
import org.jackie.jclassfile.model.Base;

import java.util.List;
import java.util.ArrayList;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;

/**
 * @author Patrik Beno
 */
public class AccessFlags extends Base {

   int flags;

	public AccessFlags() {
	}

	public AccessFlags(DataInput in) throws IOException {
		load(in);
	}

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
