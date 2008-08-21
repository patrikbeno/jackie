package org.jackie.jclassfile.util;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.utils.Log;

import java.util.List;
import java.util.ArrayList;
import java.io.DataInput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class Helper {

	static public List<AttributeInfo> loadAttributes(ClassFileProvider owner, DataInput in) throws IOException {
		int count = in.readUnsignedShort();
		List<AttributeInfo> attributes = new ArrayList<AttributeInfo>(count);
		while (count-- > 0) {
			AttributeInfo a = new AttributeInfo(owner, in);
			attributes.add(a);
			Log.debug("%s", a);
		}
		return attributes;
	}

}
