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
import java.io.InputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class DumpClass {

	static public void main(String[] args) throws FileNotFoundException {
		File f = new File(args[0]);
		DumpClass.dump(new FileInputStream(f), System.out);
	}

	static public void dump(InputStream byteCodeInput, PrintStream out) {
		ClassFile cf = new ClassFile();
		cf.load(new DataInputWrapper(byteCodeInput));

		print(out, "class %s extends %s implements %s",
				cf.classname(), cf.superclass(), toNames(cf.interfaces()));

		print(out, "Fields:");
		for (FieldInfo f : cf.fields()) {
			print(out, "\t%s : %s", f.name(), f.typeDescriptor().getTypeName());
		}

		print(out, "Methods:");
		for (MethodInfo m : cf.methods()) {
			print(out, "\t%s(%s) : %s", m.name(), toNames2(m.methodDescriptor().getParameterTypes()), m.methodDescriptor().getReturnType().getTypeName());
			dumpCode(out, m);
		}

		print(out, "Attributes");
		for (AttributeInfo a : cf.attributes()) {
			print(out, "\t%s @ %s: %s", a.name(), a.owner(), a);
		}
	}

	static void dumpCode(PrintStream out, MethodInfo m) {
		Code code = null;
		for (AttributeInfo a : m.attributes()) {
			if (a instanceof Code) {
				code = (Code) a;
				break;
			}
		}

		if (code == null) { return; }

		for (Instruction insn : code.instructions()) {
			print(out, "\t\t%s", insn);
		}
	}

	static void print(PrintStream out, String msg, Object... args) {
		out.printf(msg, args);
		out.println();
	}

	static List<String> toNames(List<ClassRef> refs) {
		List<String> names = new ArrayList<String>();
		for (ClassRef cref : refs) {
			names.add(cref.value());
		}
		return names;
	}

	static List<String> toNames2(List<TypeDescriptor> list) {
		List<String> names = new ArrayList<String>();
		for (TypeDescriptor d : list) {
			names.add(d.getTypeName());
		}
		return names;
	}
}
