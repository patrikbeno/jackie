package org.jackie.compiler.jmodelimpl;

import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public enum LoadLevel {

	/**
	 * nothing, dummy instance
	 */
	NONE,

	/**
	 * placeholder, stub only (effectivlely nothing)
	 */
	NAME,

	/**
	 * class header, superclass, interfaces, etc
	 */
	CLASS,

	/**
	 * class structure (interface): fields and methods signatures
	 */
	API,

	/**
	 * class/field/method attributes (e.g. annotations, etc)
	 */
	ATTRIBUTES,

	/**
	 * method bodies
	 */
	CODE,

	;

	public boolean atLeast(LoadLevel level) {
		return this.ordinal() >= level.ordinal();
	}

	public boolean lessThan(LoadLevel level) {
		return this.ordinal() < level.ordinal();
	}
}
