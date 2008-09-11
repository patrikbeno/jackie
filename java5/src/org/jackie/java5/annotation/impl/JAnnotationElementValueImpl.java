package org.jackie.java5.annotation.impl;

import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.JAnnotationElement;
import org.jackie.java5.annotation.JAnnotationElementValue;
import org.jackie.java5.annotation.JAnnotationHelper;
import org.jackie.java5.enumtype.JEnumHelper;
import org.jackie.jclassfile.attribute.anno.ElementValue;
import org.jackie.jclassfile.attribute.anno.ElementValue.AnnoElementValue;
import org.jackie.jclassfile.attribute.anno.ElementValue.ArrayElementValue;
import org.jackie.jclassfile.attribute.anno.ElementValue.EnumElementValue;
import org.jackie.jclassfile.attribute.anno.ElementValue.ConstElementValue;
import org.jackie.jclassfile.attribute.anno.ElementValue.ClassElementValue;
import org.jackie.jclassfile.util.ClassNameHelper;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.builtin.JPrimitive;
import org.jackie.jvm.extension.builtin.ArrayTypeHelper;
import org.jackie.jvm.spi.AbstractJNode;
import org.jackie.utils.Assert;
import org.jackie.utils.ClassName;
import static org.jackie.utils.Assert.typecast;
import static org.jackie.utils.Assert.NOTNULL;
import static org.jackie.context.ContextManager.context;
import org.jackie.compiler.typeregistry.TypeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * @author Patrik Beno
 */
public class JAnnotationElementValueImpl extends AbstractJNode implements JAnnotationElementValue {

	JAnnotationElement element;

	/**
	 * Generic annotation element value (does not depend on annotation or attribute type).
	 * Can be either
	 * (a) raw bytecode ElementValue or
	 * (b) any resolved primitive value, or
	 * (c) String representation for plain String values, class names or enum values or 
	 * (d) List of thereof for arrays
	 */
	Object value;


	public JAnnotationElementValueImpl(JAnnotation annotation, JAnnotationElement element) {
		super(annotation);
		this.element = element;
	}

	public JAnnotationElementValueImpl(JAnnotation annotation, JAnnotationElement element, Object value) {
		super(annotation);
		this.element = element;
		this.value = check(value, Boolean.class, Character.class, String.class, Number.class, Enum.class, Class.class, JAnnotation.class, ElementValue.class);
	}

	public JAnnotation getJAnnotation() {
		return (JAnnotation) owner();
	}

	public JClass getType() {
		return element.getType();
	}

	public JAnnotationElement getJAnnotationElement() {
		return element;
	}

	public Object getValue() {
		if (value instanceof ElementValue) {
			value = convertElementValue(getJAnnotationElement().getType(), (ElementValue) value);
		}
		return value;
	}

	public boolean isDefault() {
		return false;
	}

	private Object convertElementValue(JClass jclass, ElementValue evalue) {
		if (JPrimitive.isPrimitive(jclass) || jclass.isInstance(String.class)) {
			assert evalue instanceof ConstElementValue;
			return validate(jclass, ((ConstElementValue) evalue).value());

		} else if (jclass.isInstance(Class.class)) {
			TypeDescriptor desc = ((ClassElementValue) evalue).type();
			String clsname = ClassNameHelper.toJavaClassName(desc);
			JClass converted = context(TypeRegistry.class).getJClass(new ClassName(clsname));
			return NOTNULL(converted);

		} else if (JEnumHelper.isEnum(jclass)) {
			assert evalue instanceof EnumElementValue;
			EnumElementValue enumvalue = (EnumElementValue) evalue;
			assert ClassNameHelper.toJavaClassName(enumvalue.type()).equals(jclass.getFQName());
			return enumvalue.value();

		} else if (JAnnotationHelper.isAnnotation(jclass)) {
			assert evalue instanceof AnnoElementValue;
			AnnoElementValue annovalue = (AnnoElementValue) evalue;
			return new JAnnotationImpl(this, annovalue.annotation());

		} else if (ArrayTypeHelper.isArray(jclass)) {
			assert evalue instanceof ArrayElementValue;
			return convertArray((ArrayElementValue) evalue);

		} else {
			throw Assert.invariantFailed("Unhandled type %s", jclass);
		}
	}

	private List<?> convertArray(ArrayElementValue array) {
		JClass jclass = getJAnnotationElement().getType();
		JClass componentType = ArrayTypeHelper.getComponentType(jclass);
		List<Object> list = new ArrayList<Object>();
		for (ElementValue evalue : array.values()) {

			list.add(convertElementValue(componentType, evalue));
		}
		return list;
	}

	private Object validate(JClass jclass, Object value) {
		if (JPrimitive.isPrimitive(jclass)) {
			JPrimitive jprimitive = JPrimitive.forClassName(jclass.getFQName());
			return typecast(value, jprimitive.getObjectWrapperClass());

		} else if (jclass.isInstance(String.class)) {
			return typecast(value, String.class);

		} else if (jclass.isInstance(Class.class)) {
			return typecast(value, Class.class);

		} else if (JEnumHelper.isEnum(jclass)) {
			return typecast(value, String.class);

		} else {
			throw Assert.invariantFailed("Unhandled type %s", jclass);
		}
	}

	<T> T check(T object, Class ... acceptedTypes) {
		for (Class type : acceptedTypes) {
			if (type.isAssignableFrom(object.getClass())) {
				return object;
			}
		}
		throw new IllegalArgumentException(String.format(
				"Illegal argument type: %s. Expected one of %s",
				object.getClass(), Arrays.asList(acceptedTypes)
		));
	}

}
