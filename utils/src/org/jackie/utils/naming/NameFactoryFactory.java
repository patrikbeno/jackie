package org.jackie.utils.naming;

/**
 * @author Patrik Beno
 */
public interface NameFactoryFactory<T extends Name> {

	NameFactory<T> createNameFactory();

	NameFactory<T> createNameFactory(String separatorChars);

}
