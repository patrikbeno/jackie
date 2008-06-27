package org.jackie.asmtools;

import static org.jackie.utils.JavaHelper.FALSE;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class Variables {

	MethodVisitor mv;

	List<Variable> variables;

	boolean parametersClosed;

	{
		variables = new ArrayList<Variable>();
	}

	public void setMethodVisitor(MethodVisitor mv) {
		this.mv = mv;
	}

	public Variable declare(String name, Class type, Label start, Label end, boolean parameter, boolean synthetic) {
		assert mv!=null : "Need MethodVisitor! Use setMethodVisitor()";

		if (parameter && parametersClosed) {
			throw new IllegalArgumentException(); // todo message
		}

		Variable v = new Variable(name, type, variables.size(), start, end, parameter, synthetic);
		variables.add(v);
		
		mv.visitLocalVariable(v.name, Type.getDescriptor(v.type), null, v.start, v.end, v.index);

		return v;
	}

	public int size() {
		return variables.size();
	}

	public Variable get(int index) {
		return variables.get(index);
	}

	public List<Variable> methodParameters(boolean excludeSynthetic) {
		List<Variable> selected = new ArrayList<Variable>();
		for (Variable v : variables) {
			if (excludeSynthetic && v.synthetic) { continue; }
			if (FALSE(v.parameter)) { break; }
			selected.add(v);
		}
		return selected;
	}

}
