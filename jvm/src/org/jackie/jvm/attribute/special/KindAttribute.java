package org.jackie.jvm.attribute.special;

import org.jackie.jvm.JNode;
import org.jackie.jvm.spi.AbstractJAttribute;


/**
 * @author Patrik Beno
 */
public class KindAttribute extends AbstractJAttribute<Kind> {

	public KindAttribute(JNode jnode, Kind value) {
		super(jnode, "Kind", value);
	}

}
