package org.jackie.jclassfile.util;

import org.jackie.jclassfile.model.Type;
import org.jackie.utils.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * @author Patrik Beno
 */
public class MethodDescriptor {

	String descriptor;

	List<TypeDescriptor> parameterTypes;
	TypeDescriptor returnType;


	public MethodDescriptor(String descriptor) {
		this.descriptor = descriptor;
		parse();
	}

	public String getDescriptor() {
		return descriptor;
	}

	public List<TypeDescriptor> getParameterTypes() {
		return parameterTypes != null ? parameterTypes : Collections.<TypeDescriptor>emptyList();
	}

	public TypeDescriptor getReturnType() {
		return returnType;
	}

	private void parse() {
		int i = descriptor.lastIndexOf(')');
		parameterTypes = parseParameters(descriptor.substring(1, i));
		returnType = new TypeDescriptor(descriptor.substring(i+1));
	}

	private List<TypeDescriptor> parseParameters(String all) {
		List<TypeDescriptor> descriptors = new ArrayList<TypeDescriptor>();
		int pos = 0;
		int end = all.length();
		while (pos < end) {
			int i = findParameterEnd(all, pos, end);
			descriptors.add(new TypeDescriptor(all.substring(pos, i)));
			pos = i;
		}
		return descriptors;
	}

	private int findParameterEnd(String s, int start, int end) {
		for (int i = start; i < end; i++) {
			char c = s.charAt(i);
			if (c == '[') { continue; }
			if (c == Type.CLASS.code()) {
				i = s.indexOf(';', start);
			}
			return i+1;
		}
		throw Assert.invariantFailed(s);
	}
}
