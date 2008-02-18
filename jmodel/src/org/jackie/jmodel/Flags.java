package org.jackie.jmodel;

/**
 * @author Patrik Beno
 */
public @interface Flags {

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
