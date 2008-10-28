package org.jackie.compilerimpl.bytecode;

import org.jackie.compilerimpl.jmodelimpl.LoadLevel;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.utils.DataInputWrapper;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Patrik Beno
 */
public class ClassFileParser implements JClassParser {

	public void execute(InputStream from, LoadLevel loadLevel) throws IOException {
		ClassFile classfile = new ClassFile();
		classfile.load(new DataInputWrapper(from));
		JClassBuilder builder = new JClassBuilder(classfile);
		builder.build();
	}

}
