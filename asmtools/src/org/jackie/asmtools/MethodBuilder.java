package org.jackie.asmtools;

import static org.jackie.utils.JavaHelper.FALSE;
import org.objectweb.asm.Type;

/**
 * @author Patrik Beno
 */
public abstract class MethodBuilder extends CodeBlock {

	protected ClassBuilder classBuilder;

	protected MethodBuilder(ClassBuilder classBuilder, String name, Class type, Class ... args) {
		this.classBuilder = classBuilder;

		this.methodInfo = new MethodInfo(this, name, type, args, exceptions(), ACC_PUBLIC);
		this.mv = classBuilder.cv.visitMethod(
				ACC_PUBLIC, methodInfo.name,
				Type.getMethodDescriptor(Type.getType(methodInfo.type), argtypes()),
				null, typesToStrings(exceptions()));
	}

	//	public MethodBuilder(ClassVisitor cv, Constructor constructor) {
//		super(createMethodInfo(constructor));
//		declareVariable("this", constructor.getDeclaringClass());
//	}
//
//	public MethodBuilder(ClassVisitor cv, Method method) {
//		super(cv, createMethodInfo(method));
//
//		if (Modifier.isStatic(method.getModifiers())) {
//			declareVariable("this", method.getDeclaringClass());
//		}
//		for (Class<?> ptype : method.getParameterTypes()) {
//			declareParameter(String.format("p%s", this.methodInfo.variables.size()), ptype);
//		}
//
//	}

	protected Class[] exceptions() {
		return null;
	}

	public void execute() {
		methodInfo.variables.setMethodVisitor(mv);
		if (FALSE(methodInfo.isStatic())) {
			declareVariable("this", Object.class).synthetic(); // fixme this:type
		}
		for (Class<?> ptype : methodInfo.parameters) {
			declareParameter(String.format("p%s", this.methodInfo.variables.size()), ptype);
		}

		super.execute();

		mv.visitMaxs(Integer.MAX_VALUE, Integer.MAX_VALUE); // fixme stack size
		mv.visitEnd();
	}

	protected void execute(CodeBlock codeBlock) {
		codeBlock.execute();
	}

	private Type[] argtypes() {
		Type[] types = new Type[methodInfo.parameters.length];
		for (int i = 0; i < methodInfo.parameters.length; i++) {
			types[i] = Type.getType(methodInfo.parameters[i]);
		}
		return types;
	}

	static private String[] typesToStrings(Class<?>[] types) {
		if (types == null || types.length == 0) {
			return new String[0];
		}

		String[] strings = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			strings[i] = Type.getInternalName(types[i]);
		}
		return strings;
	}

}
