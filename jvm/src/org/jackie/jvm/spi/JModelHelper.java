package org.jackie.jvm.spi;

import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import static org.jackie.utils.Assert.NOTNULL;
import static org.jackie.utils.JavaHelper.FALSE;

/**
 * @author Patrik Beno
 */
public class JModelHelper {

	static public JClass findOwningJClass(JNode jnode) {
		NOTNULL(jnode, "Missing jnode");
		JNode candidate = jnode;
		while (candidate != null && FALSE(candidate instanceof JClass)) {
			candidate = candidate.owner();
		}
		return (JClass) candidate;
	}

	static public boolean isEditable(JNode jnode) {
		NOTNULL(jnode, "Missing jnode");
		JClass jclass = findOwningJClass(jnode);
		boolean editable = (jclass != null && jclass.isEditable());
		return editable;
	}

}
