package org.jackie.event.impl.proxygen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import static org.jackie.utils.JavaHelper.FALSE;
import org.jackie.utils.Assert;
import org.jackie.utils.Log;
import org.jackie.asmtools.MethodBuilder;
import org.jackie.asmtools.ClassBuilder;
import org.jackie.asmtools.ConstructorBuilder;
import org.jackie.event.Event;

import java.lang.reflect.Method;

/**
 * @author Patrik Beno
 */
public class ClassProxyBuilder extends ClassBuilder {

	static public String SUFFIX = "$$EventProxy$$";

	Class<?> type;

	public ClassProxyBuilder(Class<? extends Event> type) {
		this.type = type;
	}

	public String classname() {
		return type.getName() + SUFFIX;
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
			}
		});
	}

	protected void methods() {
		for (final Method m : superclass().getMethods()) {
			if (FALSE(Event.class.isAssignableFrom(m.getDeclaringClass()))) { continue; }
			
			Log.debug("Generating event proxy bytecode for %s", m);
			execute(new MethodBuilder(this, m.getName(), m.getReturnType(), m.getParameterTypes()) {
				protected void body() {
					execute(new InvokeEvent(this, m));
					doreturn();
				}
			});
		}
	}

}
