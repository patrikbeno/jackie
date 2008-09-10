package org.jackie.compiler_impl.bytecode;

import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.jackie.jclassfile.model.ClassFile;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Patrik Beno
 */
public class ClassFileParser implements JClassParser {

	public void execute(InputStream from, LoadLevel loadLevel) throws IOException {
		ClassFile classfile = new ClassFile();
		classfile.load(new DataInputStream(from));
		JClassBuilder builder = new JClassBuilder(classfile);
		builder.build();
	}

}
