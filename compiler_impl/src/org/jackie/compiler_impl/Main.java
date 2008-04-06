package org.jackie.compiler_impl;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Juraj Burian
 * 
 */
public class Main {

	private List<String> javacArgs = new ArrayList<String>();

	private List<String> asmArgs = new ArrayList<String>();

	private static final String dMessage[] = { 
			"java compiler options:"
			,"-g                         Generate all debugging info (ignored, default)"
			, "-g:none                    Generate no debugging info (not implemented)"
			, "-g:{lines,vars,source}     Generate only some debugging info"
			, "-nowarn                    Generate no warnings"
			, "-verbose                   Output messages about what the compiler is doing"
			, "-deprecation               Output source locations where deprecated APIs are used"
			, "-classpath <path>          Specify where to find user class files"
			, "-cp <path>                 Specify where to find user class files"
			, "-sourcepath <path>         Specify where to find input source files"
			, "-sp <path>                 Specify where to find input source files"
			, "-bootclasspath <path>      Override location of bootstrap class files"
			, "-extdirs <dirs>            Override location of installed extensions"
			, "-endorseddirs <dirs>       Override location of endorsed standards path"
			, "-d <directory>             Specify where to place generated class files"
			, "-encoding <encoding>       Specify character encoding used by source files"
			, "-source <release>          Provide source compatibility with specified release"
			, "-target <release>          Generate class files for specific VM version"
			, "-version                   Version information"
			, "-help                      Print a synopsis of standard options"
			, "-X                         Print a synopsis of nonstandard options"
			};

	private static final String[] xMessage = {
			"-Xlint                     Enable recommended warnings"
			, "-Xlint:{all,deprecation,unchecked,fallthrough,path,serial,finally,-deprecation,-unchecked,-fallthrough,-path,-serial,-finally}Enable or disable specific warnings"
			, "-Xbootclasspath/p:<path>   Prepend to the bootstrap class path"
			, "-Xbootclasspath/a:<path>   Append to the bootstrap class path"
			, "-Xbootclasspath:<path>     Override location of bootstrap class files"
			, "-Djava.ext.dirs=<dirs>     Override location of installed extensions"
			, "-Djava.endorsed.dirs=<dirs>Override location of endorsed standards path"
			, "-Xmaxerrs <number>         Set the maximum number of errors to print"
			, "-Xmaxwarns <number>        Set the maximum number of warnings to print"
			, "-Xstdout <filename>        Redirect standard output"
			};
	
	private static final String invalidFlagMessage = "jackie compiler invalid flag: %s";

	public static void main(String[] args) {
		Main main = new Main();
		int res = main.configure(args);
		if(res != 0) {
			System.exit(res);
		}
	}

	/**
	 * Configure compiler from command line
	 * 
	 * @param args - cmd line arguments
	 * @return true if ok, else false 
	 */
	private int configure(String[] args) {
		// default options for javac
		javacArgs.add("-g");
		int i = 0;
		while(args.length > i) {
			String arg = args[i];
			if(arg.startsWith("-g:")) {
				// TODO - print warning ?
				// TODO - in future versions we need cleaning of debug info supported
				
			} else  if(arg.startsWith("-g")) {
				// TODO - print warning ?
				// TODO - in future versions we need cleaning of debug info supported
			}
			else if (arg.equals("-nowarn")) {
				javacArgs.add("-nowarn");
			}
			else if (arg.equals("-verbose")) {
				javacArgs.add("-verbose");
			}
			else if (arg.equals("-deprecation")) {
				javacArgs.add("-deprecation");
			}
			else if (arg.equals("-classpath") || arg.equals("-cp")) {
				// TODO - implementation
			}
			else if (arg.equals("-sourcepath") || arg.equals("-sp")) {
				// TODO - implementation
			}
			else if(args.equals("-bootclasspath")|| args.equals("-bp")) {
				// TODO - implementation
			}
			else if(args.equals("-extdirs")) {
				// TODO - implementation
			}
			else if(args.equals("-endorseddirs")) {
				// TODO - implementation
			}
			else if(args.equals("-d")) {
				// TODO - implementation
			}
			else if(args.equals("-encoding")) {
				String encoding = args[++i];
				if(!validEncoding(encoding)) {
					return 1;
				}
				javacArgs.add("-encoding");
				javacArgs.add(encoding);
			}
			else if(args.equals("-source")) {
				String source = args[++i];
				if(!validSource(source)) {
					return 1;
				}
				javacArgs.add("-source");
				javacArgs.add(source);
			}
			else if(args.equals("-target")) {
				String target = args[++i];
				if(!validTarget(target)) {
					return 1;
				}
				javacArgs.add("-target");
				javacArgs.add(target);
			}
			else if(args.equals("-version")) {
				javacArgs.add("-version");
			}
			else if(arg.startsWith("-h") || arg.startsWith("-help")) {
				printDefaultMessage();
				return -1;
			}
			else if(arg.startsWith("-X")) {
				printXMessage();
				return -1;
			}
			else {
				System.out.println(String.format(invalidFlagMessage, arg));
				return 1;
			}
			i++;
			// TODO - implementation of X switches 
		}
		return 0;
	}

	private boolean validTarget(String target) {
		if("5".equals(target)) {
			return true;
		}
		if("1.5".equals(target)) {
			return true;
		}
		return false;
	}

	private boolean validSource(String source) {
		if("5".equals(source)) {
			return true;
		}
		if("1.5".equals(source)) {
			return true;
		}
		return false;
	}

	private boolean validEncoding(String encoding) {
		// TODO implementation
		return true;
	}

	private void printXMessage() {
		for(String s: xMessage) {
			System.out.println(s);
		}
	}

	private void printDefaultMessage() {
		for(String s: dMessage) {
			System.out.println(s);
		}
	}

}
