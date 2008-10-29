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

	static <T> Option<T> create(String name, Class<T> type, T dflt, String description) {
		return new Option<T>(name, type, dflt, description);
	}

	static <T> Option<T> create(String name, Class<T> type) {
		return create(name, type, null, null);
	}


	String name;
	Class<T> type;
	String value;
	String description;

	T converted;
	T dflt;

	private Option(String name, Class<T> type, T dflt, String description) {
		this.name = NOTNULL(name);
		this.type = NOTNULL(type);
		this.dflt = dflt;
		this.description = description;
	}

	public String name() {
		return name;
	}

	public Class type() {
		return type;
	}

	public String description() {
		return description;
	}

	public String value() {
		return value;
	}

	public void value(String value) {
		this.value = value;
	}

	public void validate() {
		converted = type.cast(convert());
	}

	public T get() {
		if (converted != null) {
			return converted;
		}

		converted = (value != null) ? type.cast(convert()) : dflt;

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
			String[] strings = value.split(",");
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
