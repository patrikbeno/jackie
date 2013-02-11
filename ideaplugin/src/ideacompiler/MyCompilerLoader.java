package ideacompiler;

import static java.util.Arrays.asList;

import java.util.HashSet;

import com.intellij.compiler.impl.javaCompiler.JavaCompiler;
import com.intellij.openapi.compiler.Compiler;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MyCompilerLoader extends AbstractProjectComponent {

	JavaCompiler javaCompiler;

	public MyCompilerLoader(Project project) {
		super(project);
	}

	@NotNull
	@Override
	public String getComponentName() {
		return getClass().getSimpleName();
	}

	@Override
	public void projectOpened() {
		CompilerManager manager = CompilerManager.getInstance(myProject);

        Compiler[] compilers = manager.getCompilers(Compiler.class);
        for (Compiler compiler : compilers) {
            manager.removeCompiler(compiler);
        }

//        JavaCompiler[] compilers = manager.getCompilers(JavaCompiler.class);
//		javaCompiler = compilers.length > 0 ? compilers[0] : null;
//		manager.removeCompiler(javaCompiler);

		manager.addTranslatingCompiler(
				new MyCompiler(myProject),
				new HashSet<FileType>(asList(StdFileTypes.JAVA)),
				new HashSet<FileType>(asList(StdFileTypes.CLASS))
		);
	}
}
