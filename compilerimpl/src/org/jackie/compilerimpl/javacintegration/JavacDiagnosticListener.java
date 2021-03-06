package org.jackie.compilerimpl.javacintegration;

import org.jackie.utils.Assert;
import org.jackie.utils.Log;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

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
