package org.jackie.jmodel.type;

import org.jackie.jmodel.FQNamed;
import org.jackie.jmodel.Named;

/**
 * Extended types are advanced views on regular classes (e.g. interface, annotation or enum).
 * This interface links them to the underlying physical class in model. 
 *
 * @author Patrik Beno
 */
public interface ExtendedType extends Named, FQNamed {

	JClass getJClass();

}
