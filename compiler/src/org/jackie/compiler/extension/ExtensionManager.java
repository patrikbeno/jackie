package org.jackie.compiler.extension;

import org.jackie.jvm.extension.Extension;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.jvm.JNode;
import static org.jackie.utils.Assert.typecast;
import org.jackie.context.ContextObject;

import java.util.Map;
import java.util.HashMap;


/**
 * @author Patrik Beno
 */
public interface ExtensionManager extends ContextObject {

	<T extends Extension> T apply(JNode jnode, Class<T> extensionType );
}
