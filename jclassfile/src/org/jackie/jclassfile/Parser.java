package org.jackie.jclassfile;

import org.jackie.jclassfile.model.ClassFile;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author Patrik Beno
 */
public class Parser {

	static class Sample {
		String string;
		void method() {}
	}

	static public void main(String[] args) throws IOException {
//		DataInputStream in = new DataInputStream(Sample.class.getResourceAsStream("Parser$Sample.class"));
		DataInputStream in = new DataInputStream(MessageFormat.class.getResourceAsStream("MessageFormat.class"));
		ClassFile classfile = new ClassFile();
		classfile.load(in);
	}
}
