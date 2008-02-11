package org.jackie.utils.naming;


/**
 * @author Patrik Beno
 */
public class QDHNameFactoryFactory implements NameFactoryFactory<QDHName> {
	public NameFactory<QDHName> createNameFactory() {
		return new QDHNameFactory("./");
	}

	public NameFactory<QDHName> createNameFactory(String separatorChars) {
		return new QDHNameFactory(separatorChars);
	}
}

