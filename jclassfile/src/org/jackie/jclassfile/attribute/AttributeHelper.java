package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeProviderRegistry;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class AttributeHelper {

	static public List<AttributeInfo> loadAttributes(ClassFileProvider owner, DataInput in) throws IOException {
		int count = in.readUnsignedShort();
		List<AttributeInfo> attributes = new ArrayList<AttributeInfo>(count);
		while (count-- > 0) {
			Utf8 name = owner.classFile().pool().getConstant(in.readUnsignedShort(), Utf8.class);
			AttributeProvider provider = AttributeProviderRegistry.instance().getProvider(name.value());
			AttributeInfo a = (provider != null)
					? provider.createAttribute(owner)
					: GenericAttribute.create(owner, name, in);
			a.load(in);
			attributes.add(a);
		}
		return attributes;
	}

}
