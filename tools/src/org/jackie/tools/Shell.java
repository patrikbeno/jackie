package org.jackie.tools;

import org.jackie.utils.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Patrik Beno
 */
public class Shell {

	static public void main(String[] args) {

		Option<File> properties = Option.create(
				"properties",
				File.class, new File("shell.properties"),
				"Path to shell configuration properties");

		CmdLineSupport cmdline = new CmdLineSupport(properties);
		try {
			cmdline.parse(args);
		} catch (Exception e) {
			System.out.println(e);
			cmdline.printcfg();
			System.exit(-1);
		}

		Shell shell = new Shell(properties.get());
		shell.run();
	}

	Properties properties;
	Main main;
	boolean quit;

	List<String> commands;
	Map<String, Method> commandsByName;

	{
		main = new Main();
		gatherCommands();
	}

	public Shell(File fproperties) {
		try {
			System.out.printf("Loading configuration from %s%n", fproperties.getAbsoluteFile());

			properties = new Properties();
			properties.load(new FileInputStream(fproperties));

			main.srcdir = new File(properties.getProperty("sources"));
			main.classpath = new ArrayList<File>();
			for (String s : properties.getProperty("classpath").split(";")) {
				main.classpath.add(new File(s));
			}
			main.jarname = new File(properties.getProperty("jarname"));
			main.overwrite = true;

			showcfg();

		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	void run() {
		System.out.println("(-: Welcome to Jackie Shell! :-)");
		help();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while (!quit) {
				System.out.print("jackie$> ");
				String command = br.readLine();
				execute(command);
			}
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	void execute(String command) {
		if (command.length() == 0) {
			help();
			return;
		}
		try {
			String[] args = command.split(" ");
			String cmd = args[0];

			if (!commands.contains(cmd)) {
				System.out.printf("Unrecognized command: \"%s\"%n", cmd);
				help();
				return;
			}


			Method m = commandsByName.get(cmd);

			if (m.getParameterTypes().length > 0) {
				m.invoke(this, (Object) args);
			} else {
				m.invoke(this);
			}

		} catch (IllegalAccessException e) {
			throw Assert.notYetHandled(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace(System.out);
		}
	}

	void gatherCommands() {
		List<String> commands = new ArrayList<String>();
		Map<String,Method> byname = new HashMap<String, Method>();
		for (Method m : getClass().getDeclaredMethods()) {
			if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())) {
				commands.add(m.getName());
				byname.put(m.getName(), m);
			}
		}
		this.commandsByName = byname;
		this.commands = commands;
	}


	/// commands

	public void help() {
		System.out.printf("Commands: %s%n", commands);
	}

	public void quit() {
		System.out.println("Good bye!");
		quit = true;
	}

	public void compile() {
		main.run();
	}

	public void showcfg() {
		System.out.println("Configuration:");
		System.out.printf("Sources        : %s%n", main.srcdir);
		System.out.printf("Class path     : %s%n", main.classpath);
		System.out.printf("Output jar file: %s%n", main.jarname);
	}

}
