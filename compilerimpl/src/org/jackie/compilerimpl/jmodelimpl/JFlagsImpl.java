package org.jackie.compilerimpl.jmodelimpl;

import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.JFlags;
import org.jackie.utils.Assert;
import org.jackie.utils.FlagSupport;
import static org.jackie.utils.Assert.NOTNULL;
import org.jackie.jclassfile.flags.Access;

import java.util.Set;
import java.util.Map;
import java.util.Collections;
import java.util.IdentityHashMap;

/**
 * @author Patrik Beno
 */
public class JFlagsImpl extends FlagSupport<Flag> implements JFlags {

	protected Class<Flag> type() {
		return Flag.class;
	}

	public Set<Flag> getAllSet() {
		return all();
	}

	public boolean isEditable() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Editor edit() {
//		assertEditable(this);
		return new Editor() {

			final JFlagsImpl flags = JFlagsImpl.this;

			public Editor reset() {
				flags.reset();
				return this;
			}

			public Editor set(Flag... flags) {
				setAll(flags);
				return this;
			}

			public Editor add(Flag flag) {
				flags.set(flag);
				return this;
			}

			public Editor clear(Flag flag) {
				flags.clear(flag);
				return this;
			}

			public JFlags editable() {
				return flags;
			}
		};
	}

	public void compile(org.jackie.jclassfile.flags.Flags flags) {
		for (Flag f : all()) {
			Access access = NOTNULL(MAPPING.get(f));
			flags.set(access);
		}
	}

	static final Map<Flag, Access> MAPPING
			= Collections.unmodifiableMap(new IdentityHashMap<Flag, Access>(){{

		put(Flag.ABSTRACT, Access.ABSTRACT);
		put(Flag.BRIDGE, Access.BRIDGE);
		put(Flag.FINAL, Access.FINAL);
		put(Flag.NATIVE, Access.NATIVE);
		put(Flag.STATIC, Access.STATIC);
		put(Flag.STRICTFP, Access.STRICT);
		put(Flag.SYNCHRONIZED, Access.SYNCHRONIZED);
		put(Flag.SYNTHETIC, Access.SYNTHETIC);
		put(Flag.TRANSIENT, Access.TRANSIENT);

	}});
}
