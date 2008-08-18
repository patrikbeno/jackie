package org.jackie.compiler_impl.jmodelimpl.code;

import org.jackie.jvm.code.CodeBlock;
import org.jackie.jvm.spi.AbstractJNode;
import org.jackie.jvm.JNode;
import org.jackie.compiler.spi.Compilable;
import org.jackie.utils.Assert;
import org.jackie.compiler_impl.bytecode.BCClassContext;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Patrik Beno
 */
public class ASMCodeBlockImpl extends AbstractJNode implements CodeBlock, Compilable {

    InsnList instructions;

	 public ASMCodeBlockImpl(JNode jnode, InsnList instructions) {
		 super(jnode);
		 this.instructions = instructions;
    }

    public void compile() {
        instructions.accept(BCClassContext.instance().methodVisitor);
    }
}
