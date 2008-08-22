package org.jackie.jclassfile;

import org.jackie.jclassfile.model.ClassFile;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.DataOutput;
import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.net.URL;

/**
 * @author Patrik Beno
 */
public class Parser {

	static class Sample {
		String string;
		void method() {}
	}

	static public void main(String[] args) throws IOException {
		DataInput in = new DataInputStream(Sample.class.getResourceAsStream("Parser$Sample.class"));
//		DataInput in = new DataInputStream(MessageFormat.class.getResourceAsStream("MessageFormat.class"));
		ClassFile classfile = new ClassFile();
		classfile.load(in);

		DataOutputStream out = new DataOutputStream(new FileOutputStream("h:\\var\\build\\mvn\\org.jackie\\org.jackie.jclassfile-0.1-SNAPSHOT\\classes\\org\\jackie\\jclassfile\\out.class"));
		classfile.save(out);

		out.close();
	}
}
