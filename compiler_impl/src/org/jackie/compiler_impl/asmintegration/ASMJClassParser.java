package org.jackie.compiler_impl.asmintegration;

import org.jackie.compiler_impl.JClassParser;
import org.jackie.compiler_impl.bytecode.JClassReader;
import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;

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
