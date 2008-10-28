package org.jackie.compilerimpl.jmodelimpl;

import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.JFlags;
import org.jackie.utils.Assert;
import org.jackie.utils.FlagSupport;

import java.util.Set;

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
}
