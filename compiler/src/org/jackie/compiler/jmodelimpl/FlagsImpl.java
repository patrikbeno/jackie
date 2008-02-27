package org.jackie.compiler.jmodelimpl;

import org.jackie.jmodel.Flag;
import org.jackie.jmodel.Flags;
import static org.jackie.compiler.util.Helper.iterable;

import java.lang.annotation.Annotation;
import java.util.BitSet;

/**
 * @author Patrik Beno
 */
@SuppressWarnings({"ClassExplicitlyAnnotation"})
public class FlagsImpl implements Flags {

	BitSet bits = new BitSet(Flag.values().length);


	public void set(Flag flag) {
		bits.set(flag.ordinal());
	}

	public void set(Flag... flags) {
		for (Flag f : iterable(flags)) {
			set(f);
		}
	}

	public void reset() {
		bits.clear();
	}

	public void reset(Flag flag) {
		bits.clear(flag.ordinal());
	}

	public void reset(Flag... flags) {
		for (Flag f : iterable(flags)) {
			reset(f);
		}
	}

	public boolean isSet(Flag flag) {
		return bits.get(flag.ordinal());
	}

	/// Flags ///

	public boolean abstracT() {
		return bits.get(Flag.ABSTRACT.ordinal());
	}

	public boolean statiC() {
		return bits.get(Flag.STATIC.ordinal());
	}

	public boolean finaL() {
		return bits.get(Flag.FINAL.ordinal());
	}

	public boolean transienT() {
		return bits.get(Flag.TRANSIENT.ordinal());
	}

	public boolean volatilE() {
		return bits.get(Flag.VOLATILE.ordinal());
	}

	public boolean nativE() {
		return bits.get(Flag.NATIVE.ordinal());
	}

	public boolean synchronizeD() {
		return bits.get(Flag.SYNCHRONIZED.ordinal());
	}

	public boolean strictFP() {
		return bits.get(Flag.STRICTFP.ordinal());
	}

	public boolean deprecated() {
		return bits.get(Flag.DEPRECATED.ordinal());
	}

	public boolean bridge() {
		return bits.get(Flag.BRIDGE.ordinal());
	}

	public boolean synthetic() {
		return bits.get(Flag.SYNTHETIC.ordinal());
	}

	public Class<? extends Annotation> annotationType() {
		return Flags.class;
	}

}
