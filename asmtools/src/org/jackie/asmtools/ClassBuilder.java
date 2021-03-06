package org.jackie.asmtools;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;

/**
 * @author Patrik Beno
 */
public abstract class ClassBuilder implements Opcodes {

	static private final boolean DUMP_BYTE_CODE = false;

	protected ClassVisitor cv;

	public byte[] build() {
		try {
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			this.cv = cw;
			execute();
			byte[] bytecode = cw.toByteArray();

			if (DUMP_BYTE_CODE) {
				ClassVisitor dump = new TraceClassVisitor(new PrintWriter(System.out));
				ClassReader cr = new ClassReader(bytecode);
				cr.accept(dump, 0);				
			}

			return bytecode;

		} finally {
			this.cv = null;
		}
	}

	protected void execute() {
		header();
		fields();
		initializers();
		constructors();
		methods();
		cv.visitEnd();		
	}

	public abstract String classname();

	protected Class superclass() {
		return Object.class;
	}

	protected Class[] interfaces() {
		return null;
	}

	

	protected void header() {
		cv.visit(V1_6, ACC_PUBLIC,
					classname().replace('.','/'),
					null,
					Type.getInternalName(superclass()),
					toBinaryClassNames(interfaces()));
	}

	protected void fields() {}

	protected void constructors() {}

	protected void initializers() {}

	protected void methods() {}

	protected void execute(CodeBlock block) {
		block.execute();
	}

	private String[] toBinaryClassNames(Class[] classes) {
		if (classes == null || classes.length == 0) {
			return new String[0];
		}

		String[] strings = new String[classes.length];
		for (int i = 0; i < classes.length; i++) {
			strings[i] = Type.getInternalName(classes[i]);
		}
		return strings;
	}

}
