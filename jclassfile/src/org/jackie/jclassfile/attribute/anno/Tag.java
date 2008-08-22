package org.jackie.jclassfile.attribute.anno;

/**
 * @author Patrik Beno
 */
public enum Tag {

	BYTE('B'),
	CHAR('C'),
	DOUBLE('D'),
	FLOAT('F'),
	INT('I'),
	LONG('J'),
	SHORT('S'),
	BOOLEAN('Z'),

	// special

	CLASS('L'), // Ljava/lang/Object;
	ARRAY('['), // [[[C

	;

	private char id;

	Tag(char id) {
		this.id = id;
	}

	public char id() {
		return id;
	}
}
