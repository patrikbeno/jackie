package org.jackie.jmodel.structure;

import org.jackie.jmodel.Editable;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.extension.annotation.Annotated;
import org.jackie.jmodel.extension.Extensible;
import org.jackie.jmodel.attribute.Attributed;
import org.jackie.jmodel.code.CodeBlock;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Flag;
import org.jackie.jmodel.props.Named;
import org.jackie.jmodel.props.Typed;
import org.jackie.jmodel.props.Flagged;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface JMethod extends JNode, Named, Typed, Flagged, Attributed, Extensible, Editable<JMethod.Editor> {

	JClass getJClass();

	List<JParameter> getParameters();

	List<JClass> getExceptions();

	List<JVariable> getLocalVariables();

	CodeBlock getCodeBlock();


	public interface Editor extends org.jackie.jmodel.Editor<JMethod> {

		Editor setName(String name);

		Editor setType(JClass type);

		Editor setParameters(List<JParameter> parameters);

		Editor setExceptions(List<JClass> exceptions);

		Editor setAccessMode(AccessMode accessMode);

		Editor setFlags(Flag ... flags);
	}

}
