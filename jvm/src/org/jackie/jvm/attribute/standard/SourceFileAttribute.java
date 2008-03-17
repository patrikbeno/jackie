package org.jackie.jvm.attribute.standard;

import org.jackie.jvm.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public class SourceFileAttribute implements JAttribute {

	protected String sourceFileName;

	public SourceFileAttribute(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getName() {
		return "SourceFile";
	}

	public String getSourceFileName() {
		return sourceFileName;
	}
}