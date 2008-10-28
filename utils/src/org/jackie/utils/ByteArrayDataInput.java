package org.jackie.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * @author Patrik Beno
 */
public class ByteArrayDataInput extends DataInputWrapper {

	public ByteArrayDataInput(byte[] bytes) {
		super(new DataInputStream(new ByteArrayInputStream(bytes)));
	}
}
