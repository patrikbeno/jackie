package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.type.AnnotationTypeImpl;
import org.jackie.compiler.jmodelimpl.type.ArrayTypeImpl;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.typeRegistry;
import org.jackie.compiler.util.EnumProxy;
import org.jackie.compiler.util.Helper;
import org.jackie.jmodel.JPrimitive;
import org.jackie.utils.Assert;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class AnnotatedImpl {

	List<AnnotationNode> asmnodes;

	List<AnnotationImpl> annotations;

	public void addAsmNode(AnnotationNode anode) {
		if (asmnodes == null) {
			asmnodes = new ArrayList<AnnotationNode>();
		}
		asmnodes.add(anode);
	}

	void buildfromAsmNodes() {
		for (AnnotationNode an : asmnodes) {
			AnnotationImpl a = toAnnotation(an);
			annotations.add(a);
		}
	}

	AnnotationImpl toAnnotation(AnnotationNode an) {
		JClassImpl jclass = typeRegistry().getJClass(new ClassName(an.desc));
		AnnotationImpl anno = new AnnotationImpl(this, jclass.getCapability(AnnotationTypeImpl.class));

		List asmvalues = (an.values != null) ? an.values : Collections.emptyList();
		Iterator it = asmvalues.iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			assert it.hasNext();
			Object object = it.next();

			AnnotationAttributeValueImpl value = createAttributeValue(anno, name, object);
		}

		return anno;
	}

	AnnotationAttributeValueImpl createAttributeValue(AnnotationImpl anno, String name, Object object) {
		AnnotationAttributeImpl attrdef = anno.type.getAttributeImpl(name);
		assert attrdef != null;

		Object converted = convert(attrdef.type, object);
		AnnotationAttributeValueImpl value = new AnnotationAttributeValueImpl(anno, attrdef, converted);

		return value;
	}

	Object convert(JClassImpl jclass, Object object) {

		if (Helper.isPrimitive(jclass)) {
			assert JPrimitive.isObjectWrapper(object.getClass());
			return object;

		} else if (jclass.equals(typeRegistry().getJClass(String.class))) {
			assert object instanceof String;
			return object;

		} else if (jclass.equals(typeRegistry().getJClass(Class.class))) {
			assert object instanceof Type;
			return typeRegistry().getJClass(new ClassName((Type) object));

		} else if (Helper.isAnnotation(jclass)) {
			assert object instanceof AnnotationNode;
			return toAnnotation((AnnotationNode) object);

		} else if (Helper.isEnum(jclass)) {
			assert object instanceof String[] && Array.getLength(object)==2;
			String[] names = (String[]) object;
			ClassName clsname = new ClassName(Type.getObjectType(names[0]));
			return new EnumProxy(clsname.getFQName(), names[1]);

		} else if (Helper.isArray(jclass)) {
			assert object instanceof List;
			ArrayTypeImpl array = Helper.asArray(jclass);
			return convertArray(array.componentType, (List) object);

		} else {
			throw Assert.invariantFailed("Annotation attribute type not handled: %s", jclass);
		}
	}

	List convertArray(JClassImpl jclass, List list) {
		List converted = new ArrayList(list.size());
		for (Object object : list) {
			//noinspection unchecked
			converted.add(convert(jclass, object));
		}
		return converted;
	}
}


