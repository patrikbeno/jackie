package org.jackie.compiler_impl.asmintegration;

import java.io.IOException;
import java.io.InputStream;

import org.jackie.compiler.LoadLevel;
import org.jackie.compiler.bytecode.JClassParser;
import org.jackie.compiler_impl.bytecode.JClassReader;
import org.objectweb.asm.ClassReader;

/**
 * 
 * @author Juraj Burian
 * 
 */
public class ASMJClassParser implements JClassParser {

	public void execute(InputStream from, LoadLevel loadLevel) throws IOException {
		ClassReader cr = new ClassReader(from);
		JClassReader reader = new JClassReader(loadLevel);
		cr.accept(reader, 0); // todo setup flags!
	}
}
