package org.jackie.jvm.extension;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface Extensions {

	<T extends Extension> Set<Class<T>> supported();

	<T extends Extension> boolean supports(Class<T> type);

	<T extends Extension> T get(Class<T> type);

}
