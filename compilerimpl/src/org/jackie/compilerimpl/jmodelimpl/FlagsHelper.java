package org.jackie.compilerimpl.jmodelimpl;

import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.flags.Access;
import org.jackie.jvm.props.JFlags;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.JFlags.Editor;
import org.jackie.utils.Mapping;
import static org.jackie.utils.Assert.NOTNULL;

/**
 * @author Patrik Beno
 */
public class FlagsHelper {

	static public void toJFlags(Flags flags, JFlags jflags) {
		Editor editor = jflags.edit();
		for (Access a : Access.values()) {
			if (flags.isSet(a)) {
				Flag f = MAPPING.findLeft(a);
				if (f != null) {
					editor.set(f);
				}
			}
		}
	}

	static public void toFlags(JFlags jflags, Flags flags) {
		for (Flag f : Flag.values()) {
			if (jflags.isSet(f)) {
				Access a = NOTNULL(MAPPING.findRight(f), "No mapping for %s", f);
				flags.set(a);
			}
		}
	}

	static final Mapping<Flag,Access> MAPPING = new Mapping<Flag, Access>() {{
		add(Flag.ABSTRACT, Access.ABSTRACT);
		add(Flag.STATIC, Access.STATIC);
		add(Flag.FINAL, Access.FINAL);
		add(Flag.TRANSIENT, Access.TRANSIENT);
		add(Flag.VOLATILE, Access.VOLATILE);
		add(Flag.NATIVE, Access.NATIVE);
		add(Flag.SYNCHRONIZED, Access.SYNCHRONIZED);
		add(Flag.STRICTFP, Access.STRICT);
		add(Flag.BRIDGE, Access.BRIDGE);
		add(Flag.SYNTHETIC, Access.SYNTHETIC);

		add(null, Access.PUBLIC);
		add(null, Access.PROTECTED);
		add(null, Access.PRIVATE);
	}};
}