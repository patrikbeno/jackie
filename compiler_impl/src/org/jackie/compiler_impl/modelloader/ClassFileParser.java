package org.jackie.compiler_impl.modelloader;

import org.jackie.compiler_impl.JClassParser;
import org.jackie.compiler_impl.modelloader.JClassBuilder;
import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.jackie.utils.Assert;
import org.jackie.jclassfile.model.ClassFile;

import java.io.InputStream;
import java.io.IOException;
import java.io.DataInputStream;

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
