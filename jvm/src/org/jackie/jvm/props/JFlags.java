package org.jackie.jvm.props;

import org.jackie.jvm.Editable;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface JFlags extends Editable<JFlags.Editor> {

	boolean isSet(Flag flag);

	boolean isAllSet(Flag ... flags);

	Set<Flag> getAllSet();


	public interface Editor extends org.jackie.jvm.Editor<JFlags> {

		Editor reset();

		Editor set(Flag ... flags);

		Editor add(Flag flag);

		Editor clear(Flag flag);

	}

}
