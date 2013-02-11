package org.jackie.tools;

import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.NOTNULL;
import static org.jackie.utils.Assert.doAssert;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.lang.reflect.Array;

/**
 * @author Patrik Beno
 */
public class Option<T> {

	String name;
	Class<T> type;
	String description;

	boolean mandatory;
	T dflt;

	String value;
	T converted;

	Option(String name, Class<T> type) {
		this.name = NOTNULL(name);
		this.type = NOTNULL(type);
	}

	public Option<T> description(String desc) {
		this.description = desc;
		return this;
	}

	public Option<T> mandatory(boolean mandatory) {
		this.mandatory = mandatory;
		return this;
	}

	public Option<T> dflt(T dflt) {
		this.dflt = dflt;
		return this;
	}


	/// read ///

	public String name() {
		return name;
	}

	public Class type() {
		return type;
	}

	public String description() {
		return description;
	}

	public boolean mandatory() {
		return mandatory;
	}

	public T dflt() {
		return dflt;
	}

	public String value() {
		return value;
	}


	public T get() {
		if (converted != null) {
			return converted;
		}

		converted = (value != null) ? type.cast(convert()) : dflt;

		if (mandatory) {
			Assert.doAssert(converted != null, "Missing option %s", name());
		}

		return converted;
	}

	private Object convert() {
		return convert(value, type);
	}

	private Object convert(String value, Class type) {
		if (type.equals(String.class)) {
			return value;

		} else if (type.equals(Boolean.class)) {
			doAssert(BOOLEAN.contains(value), "Illegal value. Valid values: %s", BOOLEAN);
			return TRUE.contains(value);

		} else if (type.equals(File.class)) {
			return value != null ? new File(value) : null;

		} else if (type.isArray()) {
			String[] strings = value.split(File.pathSeparator);
			Object[] items = (Object[]) Array.newInstance(type.getComponentType(), strings.length);
			for (int i = 0; i < strings.length; i++) {
				items[i] = convert(strings[i], type.getComponentType());
			}
			return items;

		} else {
			throw Assert.invariantFailed("Unhandled type: %s", this.type.getName());
		}
	}

	static final List<String> TRUE = asList("true", "yes", "", "1");
	static final List<String> FALSE = asList("false", "no", "0");
	static final List<String> BOOLEAN = new ArrayList<String>() {{ addAll(TRUE); addAll(FALSE); }};

}
