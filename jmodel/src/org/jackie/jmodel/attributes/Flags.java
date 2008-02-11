package org.jackie.jmodel.attributes;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Patrik Beno
 */
@Retention(RUNTIME)
public @interface Flags {

	NodeKind kind();

	AccessMode scope() default AccessMode.PACKAGE_PRIVATE;

	boolean abstracT() default false;

	boolean statiC() default false;

	boolean finaL() default false;

	boolean transienT() default false;

	boolean volatilE() default false;

	boolean nativE() default false;

	boolean synchronizeD() default false;

	boolean strictFP() default false;

	boolean deprecated() default false;

	boolean bridge() default false;

	boolean synthetic() default false;

}
