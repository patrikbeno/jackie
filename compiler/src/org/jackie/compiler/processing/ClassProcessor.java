package org.jackie.compiler.processing;

import org.jackie.jvm.JClass;

/**
 * 
 * @author Juraj Burian
 *
 */
public interface ClassProcessor {
	
	void execute(final JClass jclass);
	
	ProcessorCategory getCategory();
}
