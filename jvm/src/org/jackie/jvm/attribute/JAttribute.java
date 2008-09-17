package org.jackie.jvm.attribute;

import org.jackie.jvm.Editable;
import org.jackie.jvm.Editor;
import org.jackie.jvm.JNode;
import org.jackie.jvm.props.Named;

/**
 * @author Patrik Beno
 */
public interface JAttribute extends JNode, Named, Editable<Editor<? extends JAttribute>> {

	String getName();

	Object getValue();

	Editor<? extends JAttribute> edit();

}
