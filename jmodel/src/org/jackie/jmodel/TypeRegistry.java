package org.jackie.jmodel;

/**
 * @author Patrik Beno
 */
public interface TypeRegistry {

	JPackage gtJPackage(String fqname);

	JClass getJClass(String fqname);

}
