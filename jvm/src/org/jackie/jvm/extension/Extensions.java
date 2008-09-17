package org.jackie.jvm.extension;

import org.jackie.jvm.Editable;
import org.jackie.jvm.extension.Extensions.Editor;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface Extensions extends Iterable<Extension>, Editable<Editor> {

	Set<Class<? extends Extension>> supported();

	<T extends Extension> boolean supports(Class<T> type);

	<T extends Extension> T get(Class<T> type);

	public interface Editor extends org.jackie.jvm.Editor<Extensions> {

		void add(Extension extension);

	}

}
