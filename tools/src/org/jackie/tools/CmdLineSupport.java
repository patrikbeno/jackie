package org.jackie.tools;

import static org.jackie.utils.Assert.doAssert;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class CmdLineSupport {

	Class main;
	List<Option> options;
	Map<String,Option> optionsByName;

	{
		options = new ArrayList<Option>();
		optionsByName = new HashMap<String, Option>();
	}

	public CmdLineSupport(Class main) {
		this.main = main;
	}

	public <T> Option<T> createOption(String name, Class<T> type) {
		Option<T> o = new Option<T>(name, type);
		options.add(o);
		optionsByName.put(name, o);
		return o;
	}

	public void parse(String... args) {
		for (String s : args) {
			parseOption(s);
		}
		for (Option o : options) {
			o.get(); // validate
		}
	}

	protected void parseOption(String encoded) {
		if (encoded.length() == 0) { return; }

		String[] values = encoded.split("=", 2);
		doAssert(values.length == 2, "Invalid option: '%s'", encoded);

		String name = values[0];
		String value = values[1];

		Option option = optionsByName.get(name);
		doAssert(option != null, "Unknown option '%s'", name);

		option.value = value;
	}

	public void printcfg() {
		System.out.printf("Usage: %s [name=value] ... %n", main.getName());
		System.out.println("Options:");

		String pattern = "    %-17s %-10s %-10s %s (%s)%n";

		System.out.printf(pattern, "NAME", "TYPE", "MANDATORY", "DESCRIPTION", "DEFAULT VALUE");
		for (Option o : options) {
			System.out.printf(pattern,
									o.name(), o.type().getSimpleName(),
									o.mandatory() ? "Y" : "",
									o.description(),
									o.dflt()
			);
		}
	}
}
