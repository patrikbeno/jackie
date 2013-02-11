package org.jackie.jclassfile.constantpool;

import org.jackie.jclassfile.constantpool.Constant.Loader;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.constantpool.impl.FieldRef;
import org.jackie.jclassfile.constantpool.impl.MethodRef;
import org.jackie.jclassfile.constantpool.impl.InterfaceMethodRef;
import org.jackie.jclassfile.constantpool.impl.NameAndType;
import org.jackie.jclassfile.constantpool.impl.StringRef;
import org.jackie.jclassfile.constantpool.impl.IntegerRef;
import org.jackie.jclassfile.constantpool.impl.FloatRef;
import org.jackie.jclassfile.constantpool.impl.LongRef;
import org.jackie.jclassfile.constantpool.impl.DoubleRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Patrik Beno
 */
public enum CPEntryType {

	UTF8(1, Utf8.LOADER),

   CLASS(7, ClassRef.LOADER),
   FIELDREF(9, FieldRef.LOADER),
   METHODREF(10, MethodRef.LOADER),
   INTERFACE_METHODREF(11, InterfaceMethodRef.LOADER),
	NAME_AND_TYPE(12, NameAndType.LOADER),

   STRING(8, StringRef.LOADER),
   INTEGER(3, IntegerRef.LOADER),
   FLOAT(4, FloatRef.LOADER),
   LONG(5, LongRef.LOADER),
   DOUBLE(6, DoubleRef.LOADER),

	;

	static private final CPEntryType[] INDEX_BY_VALUE = valuesSortedByCode(true);

	private int code;
	private Loader loader;

	private CPEntryType(int code, Loader loader) {
		this.code = code;
		this.loader = loader;
	}

	public int code() {
		return code;
	}

	public Loader loader() {
		return loader;
	}

	static public CPEntryType forCode(int code) {
		return INDEX_BY_VALUE[code];
	}

	public String toString() {
		return String.format("%s(%s)", name(), code());
	}

	static public CPEntryType[] valuesSortedByCode(boolean indexableByCode) {
		// sort them all by code
		CPEntryType[] types = values();
		Arrays.sort(types, new Comparator<CPEntryType>() {
			public int compare(CPEntryType o1, CPEntryType o2) {
				return Integer.valueOf(o1.code()).compareTo(o2.code());
			}
		});

		if (!indexableByCode) {
			return types; // done, nothing more to do
		}

		// add neccessary NULL values where needed
		List<CPEntryType> result = new ArrayList<CPEntryType>();
		for (CPEntryType t : types) {
			while (result.size() < t.code()) {
				result.add(null);
			}
			result.add(t);
		}

		return result.toArray(new CPEntryType[result.size()]);
	}


}
