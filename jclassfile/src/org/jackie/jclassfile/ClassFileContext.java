package org.jackie.jclassfile;

import org.jackie.context.ContextObject;
import static org.jackie.context.ContextManager.context;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.impl.Factory;
import org.jackie.jclassfile.attribute.AttributeProviderRegistry;

/**
 * @author Patrik Beno
 */
public class ClassFileContext implements ContextObject {

	static public ClassFileContext classFileContext() {
		return context(ClassFileContext.class);
	}

	ClassFile classFile;
	AttributeProviderRegistry attributeProviderRegistry;

	public ClassFileContext(ClassFile classFile, AttributeProviderRegistry attributeProviderRegistry) {
		this.classFile = classFile;
		this.attributeProviderRegistry = attributeProviderRegistry;
	}

	public ClassFile classFile() {
		return classFile;
	}

	public ConstantPool constantPool() {
		return classFile().pool();
	}

	public Factory constantFactory() {
		return constantPool().factory();
	}

	public AttributeProviderRegistry attributeProviderRegistry() {
		return attributeProviderRegistry;
	}
}
