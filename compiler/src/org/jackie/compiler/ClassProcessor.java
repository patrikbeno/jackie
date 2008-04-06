package org.jackie.compiler;

import org.jackie.jvm.JClass;

/**
 * 
 * @author Juraj Burian
 *
 */
public interface ClassProcessor {
	
	public enum Category {
		EARLY_OBSERVER,
		EDITOR,
		LATE_OBSERVER
	}

	void execute(JClass jclass);
	
	Category getCategory();
}
