package org.jackie.test.jclassfile;

import org.jackie.test.JackieTest;
import org.testng.annotations.Test;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.utils.ByteArrayDataInput;
import org.jackie.utils.IOHelper;
import org.jackie.utils.Assert;
import org.jackie.asmtools.CodeBlock;
import org.jackie.asmtools.XAttribute;
import org.jackie.tools.DumpClass;
import org.jackie.test.testdata.ClassStructure;
import org.objectweb.asm.Type;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.File;

/**
 * @author Patrik Beno
 */
@Test
public class BugTest extends JackieTest {

	public void loadJavaHelperViaClassLoader() throws Exception {

		Class cls = ClassStructure.Methods.class;
		ClassFile cf = new ClassFile();
		cf.load(new ByteArrayDataInput(Util.getByteCode(cls)));
		byte[] bytes = cf.toByteArray();

		final File dir = createTempDir(DirOwner.METHOD);

		FileOutputStream out1 = new FileOutputStream(new File(dir, "before.class"));
		out1.write(Util.getByteCode(cls));
		IOHelper.close(out1);

		FileOutputStream out2 = new FileOutputStream(new File(dir, "after.class"));
		out2.write(bytes);
		IOHelper.close(out2);

		Util.validate(cls.getName(), bytes);

	}



}
