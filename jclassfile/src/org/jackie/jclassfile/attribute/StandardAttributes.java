package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.attribute.dbg.LineNumberTable;
import org.jackie.jclassfile.attribute.dbg.LocalVariableTable;
import org.jackie.jclassfile.attribute.dbg.LocalVariableTypeTable;
import org.jackie.jclassfile.attribute.dbg.SourceDebugExtension;
import org.jackie.jclassfile.attribute.dbg.SourceFile;
import org.jackie.jclassfile.attribute.std.Code;
import org.jackie.jclassfile.attribute.std.ConstantValue;
import org.jackie.jclassfile.attribute.std.EnclosingMethod;
import org.jackie.jclassfile.attribute.std.Exceptions;
import org.jackie.jclassfile.attribute.std.InnerClasses;
import org.jackie.jclassfile.attribute.std.Signature;
import org.jackie.jclassfile.attribute.std.Synthetic;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;

/**
 * @author Patrik Beno
 */
public class StandardAttributes {

	void init() {
		AttributeProviderRegistry registry = AttributeProviderRegistry.instance();

		registry.addProvider(new AttributeProvider("Code") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new Code(owner);
			}
		});
		registry.addProvider(new AttributeProvider("ConstantValue") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new ConstantValue(owner);
			}
		});
		registry.addProvider(new AttributeProvider("Deprecated") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new org.jackie.jclassfile.attribute.std.Deprecated(owner);
			}
		});
		registry.addProvider(new AttributeProvider("EnclosingMethod") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new EnclosingMethod(owner);
			}
		});
		registry.addProvider(new AttributeProvider("Exceptions") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new Exceptions(owner);
			}
		});
		registry.addProvider(new AttributeProvider("InnerClasses") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new InnerClasses(owner);
			}
		});
		registry.addProvider(new AttributeProvider("LineNumberTable") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new LineNumberTable(owner);
			}
		});
		registry.addProvider(new AttributeProvider("LocalVariableTable") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new LocalVariableTable(owner);
			}
		});
		registry.addProvider(new AttributeProvider("LocalVariableTypeTable") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new LocalVariableTypeTable(owner);
			}
		});
		registry.addProvider(new AttributeProvider("Signature") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new Signature(owner);
			}
		});
		registry.addProvider(new AttributeProvider("SourceDebugExtension") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new SourceDebugExtension(owner);
			}
		});
		registry.addProvider(new AttributeProvider("SourceFile") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new SourceFile(owner);
			}
		});
		registry.addProvider(new AttributeProvider("Synthetic") {
			public AttributeInfo createAttribute(ClassFileProvider owner) {
				return new Synthetic(owner);
			}
		});
	}

}
