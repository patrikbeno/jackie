package org.jackie.jclassfile.constantpool;

import org.jackie.utils.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Patrik Beno
 */
public enum CPEntryType {

	UTF8(1),

   CLASS(7),
   FIELDREF(9),
   METHODREF(10),
   INTERFACE_METHODREF(11),
	NAME_AND_TYPE(12),

   STRING(8),
   INTEGER(3),
   FLOAT(4),
   LONG(5),
   DOUBLE(6),

	;

	static private final CPEntryType[] INDEX_BY_VALUE = valuesSortedByCode(true);

	private int code;

	private CPEntryType(int code) {
		this.code = code;
	}

	public int code() {
		return code;
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
