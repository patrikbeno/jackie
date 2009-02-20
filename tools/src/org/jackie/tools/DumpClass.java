package org.jackie.tools;

import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.model.MethodInfo;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.jackie.jclassfile.code.Instruction;
import org.jackie.jclassfile.attribute.std.Code;
import org.jackie.utils.Assert;
import org.jackie.utils.DataInputWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class DumpClass {

	static public void main(String[] args) {
		File f = new File(args[0]);
		DumpClass dump = new DumpClass(f);
		dump.dump();
	}

	File file;

	public DumpClass(File file) {
		this.file = file;
	}

	void dump() {
		try {
			ClassFile cf = new ClassFile();
			cf.load(new DataInputWrapper(new FileInputStream(file)));

			print("class %s extends %s implements %s",
					cf.classname(), cf.superclass(), toNames(cf.interfaces()));

			print("Fields:");
			for (FieldInfo f : cf.fields()) {
				print("\t%s : %s", f.name(), f.typeDescriptor().getTypeName());
			}

			print("Methods:");
			for (MethodInfo m : cf.methods()) {
				print("\t%s(%s) : %s", m.name(), toNames2(m.methodDescriptor().getParameterTypes()), m.methodDescriptor().getReturnType().getTypeName());
				dumpCode(m);
			}

			print("Attributes");
			for (AttributeInfo a : cf.attributes()) {
				print("\t%s @ %s: %s", a.name(), a.owner(), a);
			}

		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	void dumpCode(MethodInfo m) {
		Code code = null;
		for (AttributeInfo a : m.attributes()) {
			if (a instanceof Code) {
				code = (Code) a;
				break;
			}
		}

		if (code == null) { return; }

		for (Instruction insn : code.instructions()) {
			print("\t\t%s", insn);
		}
	}

	void print(String msg, Object... args) {
		System.out.printf(msg, args);
		System.out.println();
	}

	List<String> toNames(List<ClassRef> refs) {
		List<String> names = new ArrayList<String>();
		for (ClassRef cref : refs) {
			names.add(cref.value());
		}
		return names;
	}

	List<String> toNames2(List<TypeDescriptor> list) {
		List<String> names = new ArrayList<String>();
		for (TypeDescriptor d : list) {
			names.add(d.getTypeName());
		}
		return names;
	}
}
