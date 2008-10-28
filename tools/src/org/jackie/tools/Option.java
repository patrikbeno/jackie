package org.jackie.tools;

import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.NOTNULL;

import java.io.File;

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

	private Option(String name, Class<T> type, T dflt, String description) {
		this.name = NOTNULL(name);
		this.type = NOTNULL(type);
		this.converted = dflt;
		this.description = description;
	}

	public String name() {
		return name;
	}

	public Class type() {
		return type;
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
		if (value == null) {
			return null;
		}
		if (converted != null) {
			return converted;
		}

		converted = type.cast(convert());

		return converted;
	}

	private Object convert() {
		if (type.equals(String.class)) {
			return value;
		} else if (type.equals(File.class)) {
			return value != null ? new File(value) : null;
		} else {
			throw Assert.invariantFailed("Unhandled type: %s", type.getName());
		}
	}

}
