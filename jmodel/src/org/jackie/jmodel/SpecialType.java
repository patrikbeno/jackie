package org.jackie.jmodel;

import org.jackie.jmodel.props.FQNamed;
import org.jackie.jmodel.props.Named;

/**
 * Extended types are advanced views on regular classes (e.g. interface, annotation or enum).
 * This interface links them to the underlying physical class in model. 
 *
 * @author Patrik Beno
 */
public interface SpecialType extends Named, FQNamed {

	JClass getJClass();

}
