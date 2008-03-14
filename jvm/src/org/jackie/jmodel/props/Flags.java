package org.jackie.jmodel.props;

import org.jackie.jmodel.Editable;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface Flags extends Editable<Flags.Editor> {

	boolean isSet(Flag flag);

	boolean isAllSet(Flag ... flags);

	Set<Flag> getAllSet();


	public interface Editor extends org.jackie.jmodel.Editor<Flags> {

		Editor reset();

		Editor set(Flag ... flags);

		Editor add(Flag flag);

		Editor clear(Flag flag);

	}

}
