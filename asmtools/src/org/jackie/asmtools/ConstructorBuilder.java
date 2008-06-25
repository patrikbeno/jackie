package org.jackie.asmtools;

import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public abstract class ConstructorBuilder extends MethodBuilder {

	public ConstructorBuilder(ClassBuilder classBuilder) {
		super(classBuilder, "<init>", void.class);
	}

}
