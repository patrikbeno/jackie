package org.jackie.jclassfile.attribute.anno;

import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public enum Tag {

	// primitives

	BYTE('B'),
	CHAR('C'),
	DOUBLE('D'),
	FLOAT('F'),
	INT('I'),
	LONG('J'),
	SHORT('S'),
	BOOLEAN('Z'),

	// other

	STRING('s'),
	ENUM('e'),
	CLASS('c'),
	ANNOTATION('@'),
	ARRAY('['),

	;

	private char id;

	Tag(char id) {
		this.id = id;
	}

	public char id() {
		return id;
	}

	static public Tag forId(char id) {
		Tag tag = forId0(id);
		if (tag.id()==id) { return tag; }

		throw Assert.invariantFailed("ID mismatch: wanted: '%s', returned: '%s'", id, tag.id());
	}

	static private Tag forId0(char id) {
		switch (id) {
			case 'B': return BYTE;
			case 'C': return CHAR;
			case 'D': return DOUBLE;
			case 'F': return FLOAT;
			case 'I': return INT;
			case 'J': return LONG;
			case 'S': return SHORT;
			case 'Z': return BOOLEAN;
			case 's': return STRING;
			case 'e': return ENUM;
			case 'c': return CLASS;
			case '@': return ANNOTATION;
			case '[': return ARRAY;
			default:
				throw Assert.invariantFailed(Character.toString(id));
		}
	}
}
