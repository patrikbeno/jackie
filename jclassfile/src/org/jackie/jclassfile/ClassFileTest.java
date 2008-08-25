package org.jackie.jclassfile;

import org.jackie.jclassfile.model.ClassFile;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

class Sample<T> {
	List<T> items;
	<T> void method(T t) {}
}
