package org.jackie.tools;

import org.jackie.jclassfile.model.ClassFile;
import org.jackie.utils.Assert;
import org.jackie.utils.DataInputWrapper;

import java.io.File;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class DumpClass {

	static public void main(String[] args) {
		File f = new File(args[0]);
		DumpClass dump = new DumpClass(f);
		dump.dump();
	}

	File file;

	public DumpClass(File file) {
		this.file = file;
	}

	void dump() {
		try {
			ClassFile cf = new ClassFile();
			cf.load(new DataInputWrapper(new FileInputStream(file)));

		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}
}
