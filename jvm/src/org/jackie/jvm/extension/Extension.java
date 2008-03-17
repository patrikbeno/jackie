package org.jackie.jvm.extension;

import org.jackie.jvm.JNode;

/**
 * Extended types are advanced views on regular classes (e.g. interface, annotation or enum).
 * This interface links them to the underlying physical class in model. 
 *
 * @author Patrik Beno
 */
public interface Extension<T extends JNode> {

	T node();


}
