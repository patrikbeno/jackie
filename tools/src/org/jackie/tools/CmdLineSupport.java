package org.jackie.tools;

import static org.jackie.utils.Assert.doAssert;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Patrik Beno
 */
public class CmdLineSupport {

	Map<String,Option> options;

	public CmdLineSupport(Option ... options) {
		this.options = new HashMap<String, Option>();
		for (Option o : options) {
			this.options.put(o.name(), o);
		}
	}

	public void parse(String... args) {
		for (String s : args) {
			parseOption(s);
		}
	}

	protected void parseOption(String encoded) {
		String[] values = encoded.split("=", 2);
		doAssert(values.length == 2, "Invalid option: %s", encoded);

		String name = values[0];
		String value = values[1];

		Option option = options.get(name);
		doAssert(option != null, "Unknown option '%s'", name);

		option.value(value);
		option.validate();
	}	
}