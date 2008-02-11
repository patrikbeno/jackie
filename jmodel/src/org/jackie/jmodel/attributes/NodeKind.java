package org.jackie.jmodel.attributes;

/**
 * @author Patrik Beno
 */
public enum NodeKind {

	PRIMITIVE,
	ARRAY,

	CLASS,
	INTERFACE,
	ENUM,
	ANNOTATION,

	FIELD,
	FIELD_ACCESSOR,
	ANNOTATION_ATTRIBUTE,

	METHOD,
	CONSTRUCTOR,
	CLASS_INITIALIZER,

}
