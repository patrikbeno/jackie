package org.jackie.jmodel.infrastructure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JPackage;

/**
 * @author Patrik Beno
 */
public interface TypeRegistry {

	JPackage getJPackage(String fqname);

	JClass getJClass(String fqname);

}
