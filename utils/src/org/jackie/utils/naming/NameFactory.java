package org.jackie.utils.naming;

/**
 * @author Patrik Beno
 */
public interface NameFactory<T extends Name> {

	T getName(String fqname);

	T getName(T parent, String name);

}
