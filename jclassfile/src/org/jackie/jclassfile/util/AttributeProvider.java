package org.jackie.jclassfile.util;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.constantpool.impl.Utf8;

import java.io.DataInput;

/**
 * @author Patrik Beno
 */
public interface AttributeProvider {

	String name();

	AttributeInfo createAttribute(ClassFileProvider owner, Utf8 name, DataInput in);

}
