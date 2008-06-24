package org.jackie.jvm.structure;

import org.jackie.jvm.Editable;
import org.jackie.jvm.Editor;
import org.jackie.jvm.JNode;
import org.jackie.jvm.attribute.Attributed;
import org.jackie.jvm.extension.Extensible;

/**
 * @author Patrik Beno
 */
public interface JCode extends JNode, Attributed, Extensible, Editable<Editor> {
	
	public interface Editor extends org.jackie.jvm.Editor<JCode> {

	}

}