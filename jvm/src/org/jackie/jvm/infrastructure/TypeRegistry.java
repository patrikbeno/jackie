package org.jackie.jvm.infrastructure;

import org.jackie.jvm.JClass;
import org.jackie.jvm.JPackage;

/**
 * @author Patrik Beno
 */
public interface TypeRegistry {

	JPackage getJPackage(String fqname);

	JClass getJClass(String fqname);

}
