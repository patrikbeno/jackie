package org.jackie.compiler.javacintegration;

import javac.javax.tools.Diagnostic;
import javac.javax.tools.DiagnosticListener;
import javac.javax.tools.JavaFileObject;

import org.jackie.utils.Assert;
import org.jackie.utils.Log;

/**
 * @author Patrik Beno
 */
public class JavacDiagnosticListener implements DiagnosticListener<JavaFileObject> {

	public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
		switch (diagnostic.getKind()) {
			case ERROR:
				Log.error("%s", diagnostic);
				break;
			case MANDATORY_WARNING:
				Log.warn("%s", diagnostic);
				break;
			case WARNING:
			case NOTE:
			case OTHER:
				Log.debug("%s", diagnostic);
				break;
			default:
				throw Assert.invariantFailed(diagnostic.getKind());
		}
	}

}
