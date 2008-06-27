package org.jackie.asmtools;

/**
 * @author Patrik Beno
 */
public abstract class ConstructorBuilder extends MethodBuilder {

	public ConstructorBuilder(ClassBuilder classBuilder) {
		super(classBuilder, "<init>", void.class);
	}

}
