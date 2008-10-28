package org.jackie.compilerimpl.jmodelimpl;

import org.jackie.jclassfile.flags.Access;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jvm.props.AccessMode;
import org.jackie.utils.Assert;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class AccessModeHelper {

	static public AccessMode toAccessMode(Flags flags) {
		if (flags.isSet(Access.PUBLIC)) {
			return AccessMode.PUBLIC;
		} else if (flags.isSet(Access.PROTECTED)) {
			return AccessMode.PROTECTED;
		} else if (flags.isSet(Access.PRIVATE)) {
			return AccessMode.PRIVATE;
		} else {
			return AccessMode.PACKAGE;
		}
	}

	static public Access toAccess(AccessMode accessMode) {
		switch (accessMode) {
			case PACKAGE:
				return null;
			case PRIVATE:
				return Access.PRIVATE;
			case PROTECTED:
				return Access.PROTECTED;
			case PUBLIC:
				return Access.PUBLIC;
			default:
				throw Assert.invariantFailed(accessMode);
		}
	}
}
