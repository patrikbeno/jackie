package org.jackie.jvm.structure;

import org.jackie.jvm.Editable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extensible;
import org.jackie.jvm.attribute.Attributed;
import org.jackie.jvm.code.CodeBlock;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.Named;
import org.jackie.jvm.props.Typed;
import org.jackie.jvm.props.Flagged;

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


	public interface Editor extends org.jackie.jvm.Editor<JMethod> {

		Editor setName(String name);

		Editor setType(JClass type);

		Editor setParameters(List<JParameter> parameters);

		Editor setExceptions(List<JClass> exceptions);

		Editor setAccessMode(AccessMode accessMode);

		Editor setFlags(Flag ... flags);
	}

}
