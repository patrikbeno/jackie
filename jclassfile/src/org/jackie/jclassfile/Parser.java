package org.jackie.jclassfile;

import org.jackie.jclassfile.model.ClassFile;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
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
//		DataInput in = new DataInputStream(Sample.class.getResourceAsStream("Parser$Sample.class"));
		DataInput in = new DataInputStream(MessageFormat.class.getResourceAsStream("MessageFormat.class"));
		ClassFile classfile = new ClassFile();
		classfile.load(in);

		DataOutputStream out = new DataOutputStream(new FileOutputStream("h:\\var\\out.class"));
		classfile.save(out);

		out.close();
	}
}
