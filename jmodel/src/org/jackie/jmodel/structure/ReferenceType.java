package org.jackie.jmodel.structure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JNode;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface ReferenceType extends JNode {

	JClass getJClass();

	List<JTypeParameter> getTypeParameters();

}
