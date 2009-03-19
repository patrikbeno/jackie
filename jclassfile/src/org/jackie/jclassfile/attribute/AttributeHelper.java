package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.constantpool.impl.Utf8;
import static org.jackie.jclassfile.constantpool.ConstantPool.constantPool;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.utils.Log;
import org.jackie.utils.XDataInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * @author Patrik Beno
 */
public class AttributeHelper {

	static public List<AttributeInfo> loadAttributes(AttributeSupport owner, XDataInput in) {
		ConstantPool pool = constantPool();

		int count = in.readUnsignedShort();
		List<AttributeInfo> attributes = new ArrayList<AttributeInfo>(count);
		while (count-- > 0) {
			Utf8 name = pool.getConstant(in.readUnsignedShort(), Utf8.class);
			AttributeProvider provider = AttributeProviderRegistry.instance().getProvider(name.value());
			AttributeInfo a = (provider != null)
					? provider.createAttribute(owner)
					: new GenericAttribute(owner, name);
			Log.debug("Loading attribute %s using provider %s", name.value(), provider != null ? provider : "<generic>");			
			a.load(in, pool);
			attributes.add(a);
		}
		
		return attributes;
	}

	static public void removeAttribute(String name, List<AttributeInfo> attributes) {
		for (Iterator<AttributeInfo> it = attributes.iterator(); it.hasNext();) {
			AttributeInfo a = it.next();
			if (a.name().equals(name)) { it.remove(); }
		}
	}

	static public void qdhRemoveUnsupportedAttributes(List<AttributeInfo> attributes) {
		// JAC-30 workaround; fixme: QDH to workaround missing StackMapTable implementation
		AttributeHelper.removeAttribute("StackMapTable", attributes);
		// JAC-32: also temporarily ignore other debugging attributes
		AttributeHelper.removeAttribute("SourceFile", attributes);
		AttributeHelper.removeAttribute("SourceDebugExtension", attributes);
		AttributeHelper.removeAttribute("LineNumberTable", attributes);
		AttributeHelper.removeAttribute("LocalVariableTable", attributes);
		AttributeHelper.removeAttribute("LocalVariableTypeTable", attributes);
		// hacking annotations
		AttributeHelper.removeAttribute("RuntimeVisibleAnnotations", attributes);
		AttributeHelper.removeAttribute("RuntimeVisibleParameterAnnotations", attributes);
		AttributeHelper.removeAttribute("RuntimeInvisibleAnnotations", attributes);
		AttributeHelper.removeAttribute("RuntimeInvisibleParameterAnnotations", attributes);
	}
}
