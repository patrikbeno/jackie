package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class RuntimeVisibleAnnotations extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "RuntimeVisibleAnnotations";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new RuntimeVisibleAnnotations(owner);
		}
	}


	/**
RuntimeVisibleAnnotations_attribute {
     u2 attribute_name_index;
     u4 attribute_length;
     u2 num_annotations;
     annotation annotations[num_annotations];
   }

annotation {
     u2 type_index;
     u2 num_element_value_pairs;
     {
       u2 element_name_index;
       element_value value;
     } element_value_pairs[num_element_value_pairs];
   }

 element_value {
     u1 tag;
     union {
       u2   const_value_index;
       {
         u2   type_name_index;
         u2   const_name_index;
       } enum_const_value;
       u2   class_info_index;
       annotation annotation_value;
       {
         u2    num_values;
         element_value values[num_values];
       } array_value;
     } value;
   }
	 */

	class Anno {
		Utf8 type;
		List<Element> elements;
	}
	class Element {
		String tag; // todo make it enum?
	}

	class ConstElement extends Element {
		Constant value;
	}
	class ClassElement extends Element {
		Utf8 classinfo;
	}
	class EnumElement extends Element {
		Utf8 type;
		Utf8 value;
	}
	class AnnoElement extends Element {
		Anno annotation;
	}
	class ArrayElement extends Element {
		List<Element> values;
	}

	List<Anno> annos;

	public RuntimeVisibleAnnotations(ClassFileProvider owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		readLength(in);

		int count = in.readUnsignedShort();
		while (count-- > 0) {
			Anno anno = new Anno();
			annos.add(anno);
		}

		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
