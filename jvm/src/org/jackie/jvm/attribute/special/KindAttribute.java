package org.jackie.jvm.attribute.special;

import org.jackie.jvm.spi.AbstractJAttribute;
import org.jackie.jvm.JNode;


/**
 * @author Patrik Beno
 */
public class KindAttribute extends AbstractJAttribute<Kind> {

	public KindAttribute(JNode jnode, Kind value) {
		super(jnode, "Kind", value);
	}

}
