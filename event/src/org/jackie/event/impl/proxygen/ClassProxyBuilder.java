package org.jackie.event.impl.proxygen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import static org.jackie.utils.JavaHelper.FALSE;
import org.jackie.utils.Assert;
import org.jackie.asmtools.MethodBuilder;
import org.jackie.asmtools.ClassBuilder;
import org.jackie.asmtools.ConstructorBuilder;
import org.jackie.event.Event;

import java.lang.reflect.Method;

/**
 * @author Patrik Beno
 */
public class ClassProxyBuilder extends ClassBuilder {

	Class type;

	public ClassProxyBuilder(Class type) {
		this.type = type;
	}

	public String classname() {
		return type.getName() + "$$EventProxy$$";
	}

	protected Class superclass() {
		return type;
	}

	protected void constructors() {
		execute(new ConstructorBuilder(this) {
			protected void body() {
				// todo provide API for constructors
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(superclass()), "<init>", "()V");
				mv.visitInsn(RETURN);
				mv.visitMaxs(1, 1);
			}
		});
	}

	protected void methods() {
		for (final Method m : superclass().getMethods()) {
			execute(new MethodBuilder(this, m.getName(), m.getReturnType(), m.getParameterTypes()) {
				protected void body() {
					execute(new InvokeEvent(this, m));
				}
			});
		}
	}

}
