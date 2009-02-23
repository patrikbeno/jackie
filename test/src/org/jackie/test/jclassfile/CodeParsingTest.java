package org.jackie.test.jclassfile;

import org.jackie.jclassfile.model.ClassFile;
import org.jackie.test.jclassfile.CodeSamples.InvokeMethod;
import org.jackie.test.jclassfile.CodeSamples.NoCode;
import org.jackie.test.jclassfile.CodeSamples.Exceptions;
import org.jackie.test.jclassfile.CodeSamples.Switches;
import org.jackie.utils.ByteArrayDataInput;
import org.testng.annotations.Test;
import org.objectweb.asm.Type;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.text.MessageFormat;

/**
 * @author Patrik Beno
 */
public class CodeParsingTest {

	@Test
	public void nocode() {
		load(NoCode.class);
	}

	@Test(dependsOnMethods={"nocode"})
	public void invokemethod() {
		load(InvokeMethod.class);
	}

	@Test(dependsOnMethods={"nocode"})
	public void exceptions() {
		load(Exceptions.class);
	}

	@Test(dependsOnMethods={"invokemethod"})
	public void switches() {
		load(Switches.class);
	}

	@Test(dependsOnMethods={"nocode","invokemethod","exceptions"})
	public void kill_dash_nine() {
		load(MessageFormat.class);
		load(Type.class);
	}

	@Test
	public void failedHelloWorld() {
		
	}

	void load(Class cls) {
		byte[] bytecode = Util.getByteCode(cls);
		ClassFile cf = new ClassFile();
		cf.load(new ByteArrayDataInput(bytecode));
	}
}
