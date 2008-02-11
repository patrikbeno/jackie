package org.jackie.jmodel.structure;

import org.jackie.jmodel.Annotated;
import org.jackie.jmodel.Flagged;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.Named;
import org.jackie.jmodel.Typed;
import org.jackie.jmodel.code.CodeBlock;
import org.jackie.jmodel.type.JClass;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface JMethod extends JNode, Named, Typed, Annotated, Flagged {

	JClass getEnclosingClass();

	List<JParameter> getParameters();

	List<JClass> getExceptions();

	List<JLocalVariable> getLocalVariables();

	CodeBlock getCodeBlock();


}
