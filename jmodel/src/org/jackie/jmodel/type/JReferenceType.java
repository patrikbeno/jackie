package org.jackie.jmodel.type;

import org.jackie.jmodel.JNode;
import org.jackie.jmodel.structure.JTypeParameter;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface JReferenceType extends JNode {

	JClass getJClass();

	List<JTypeParameter> getTypeParameters();

}
