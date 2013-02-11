package org.jackie.jclassfile.util;

import org.jackie.jclassfile.model.Type;
import org.jackie.utils.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class MethodDescriptor {

	String descriptor;

	TypeDescriptor returnType;
	List<TypeDescriptor> parameterTypes;


	public MethodDescriptor(String descriptor) {
		this.descriptor = descriptor;
		parse();
	}

	public MethodDescriptor(TypeDescriptor returnType, List<TypeDescriptor> parameterTypes) {
		this.returnType = returnType;
		this.parameterTypes = parameterTypes;
	}

	public String getDescriptor() {
		if (descriptor == null) {
			descriptor = buildDescriptor();
		}
		return descriptor;
	}

	public TypeDescriptor getReturnType() {
		return returnType;
	}

	public List<TypeDescriptor> getParameterTypes() {
		return parameterTypes != null ? parameterTypes : Collections.<TypeDescriptor>emptyList();
	}

	private void parse() {
		int i = descriptor.lastIndexOf(')');
		parameterTypes = parseParameters(descriptor.substring(1, i));
		returnType = new TypeDescriptor(descriptor.substring(i+1));
	}

	protected String buildDescriptor() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		for (TypeDescriptor d : getParameterTypes()) {
			sb.append(d.getDescriptor());
		}
		sb.append(')');
		sb.append(returnType.getDescriptor());

		return sb.toString();
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
