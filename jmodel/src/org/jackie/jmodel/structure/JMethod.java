package org.jackie.jmodel.structure;

import org.jackie.jmodel.props.Annotated;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.props.Named;
import org.jackie.jmodel.props.Typed;
import org.jackie.jmodel.code.CodeBlock;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface JMethod extends JNode, Named, Typed, Annotated {

	JClass getEnclosingClass();

	List<JParameter> getParameters();

	List<JClass> getExceptions();

	List<JLocalVariable> getLocalVariables();

	CodeBlock getCodeBlock();

}
