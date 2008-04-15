package org.jackie.java5.enumtype;

import org.jackie.jvm.Editable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;

import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface EnumType extends Extension<JClass>, Editable<EnumType.Editor> {

	Set<String> getEnumConstantNames();

	JEnumContant getEnumConstant(String name);

	JEnumContant getEnumConstant(int ordinal);

	List<JEnumContant> getEnumContants();

	public interface Editor extends org.jackie.jvm.Editor<EnumType> {
		
	}

}
