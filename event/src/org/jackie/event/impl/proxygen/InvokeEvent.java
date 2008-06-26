package org.jackie.event.impl.proxygen;

import org.jackie.asmtools.CodeBlock;
import org.jackie.asmtools.Variable;
import org.jackie.utils.Assert;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * @author Patrik Beno
 */
public class InvokeEvent extends CodeBlock {

	protected Method eventMethod;

	public InvokeEvent(CodeBlock parent, Method eventMethod) {
		this.eventMethod = eventMethod;
	}

	protected void body() {
		Variable iterator = declareVariable("iterator", Iterator.class);
		Variable next = declareVariable("next", eventMethod.getDeclaringClass());

		// code:: EventManager.eventManager().getListeners(type).iterator()
		{
			invoke(Refs.EventManager$eventManager);
			push(eventMethod.getDeclaringClass());
			invoke(Refs.EventManager$getListeners);
			invoke(Refs.Set$iterator);
			store(iterator);
		}

		// code:: loop
		{
			Label lcontinue = new Label();
			Label lbreak = new Label();

			label(lcontinue);
			load(iterator);
			invoke(Refs.Iterator$hasNext);
			jumpif(false, lbreak);

			load(iterator);
			invoke(Refs.Iterator$next);
			store(next);

			tryinvoke(next);

			jump(lcontinue);

			label(lbreak);
		}
	}

	private void tryinvoke(Variable next) {
		Label ltry = new Label();
		Label ltryend = new Label();
		Label lcatch = new Label();
		Label lcatchend = new Label();

		mv.visitTryCatchBlock(ltry, ltryend, lcatch, Type.getDescriptor(Throwable.class));

		label(ltry);
		dispatch(next); // guarded methodInfo invocation
		label(ltryend);
		jump(lcatchend); // try bock success

		label(lcatch);
		invoke(Refs.ClassProxyHelper$onException);
		label(lcatchend);
	}

	void dispatch(Variable next) {		
		load(next);
		for (Variable v : methodInfo.variables.methodParameters(true)) {
			load(v);
		}
		invoke(eventMethod);
	}

}
