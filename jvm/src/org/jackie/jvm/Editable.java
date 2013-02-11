package org.jackie.jvm;

/**
 * @author Patrik Beno
 */
public interface Editable<T extends Editor> {

	boolean isEditable();

	T edit();

}
