package org.jackie.compilerimpl.jmodelimpl;

import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.Flags;
import org.jackie.utils.Assert;
import org.jackie.utils.FlagSupport;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public class FlagsImpl extends FlagSupport<Flag> implements Flags {

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

			final FlagsImpl flags = FlagsImpl.this;

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

			public Flags editable() {
				return flags;
			}
		};
	}
}
