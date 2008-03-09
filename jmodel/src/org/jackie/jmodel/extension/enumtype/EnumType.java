package org.jackie.jmodel.extension.enumtype;

import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.enumtype.JEnumContant;
import org.jackie.jmodel.Editable;
import org.jackie.jmodel.Editor;
import org.jackie.jmodel.JClass;

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

	public interface Editor extends org.jackie.jmodel.Editor<EnumType> {
		
	}

}
