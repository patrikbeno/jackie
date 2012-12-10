package ideacompiler;

import com.intellij.compiler.OutputParser;
import com.intellij.compiler.impl.javaCompiler.BackendCompiler;
import com.intellij.compiler.impl.javaCompiler.ModuleChunk;
import com.intellij.compiler.impl.javaCompiler.javac.JavacCompiler;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Set;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MyCompiler implements BackendCompiler {

    Project project;

    PsiManager psimanager;
    MyPsiListener listener;
    JavacCompiler javac;
    
    public MyCompiler(Project project) {
        this.project = project;
        this.listener = new MyPsiListener(this);
        this.psimanager = PsiManager.getInstance(project);
        this.javac = new JavacCompiler(project);
        
        psimanager.addPsiTreeChangeListener(listener, listener);

        // interesting classes:
        // PsiEquivalenceUtil
    }

    @NotNull
    @Override
    public String getId() {
        return "IdeaCompiler";
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return getId();
    }

    @NotNull
    @Override
    public Configurable createConfigurable() {
        return javac.createConfigurable();
    }

    @NotNull
    @Override
    public Set<FileType> getCompilableFileTypes() {
        return javac.getCompilableFileTypes();
    }

    @Nullable
    @Override
    public OutputParser createErrorParser(@NotNull String s, Process process) {
        return javac.createErrorParser(s, process);
    }

    @Nullable
    @Override
    public OutputParser createOutputParser(@NotNull String s) {
        return javac.createOutputParser(s);
    }

    @Override
    public boolean checkCompiler(CompileScope compileScope) {
        return javac.checkCompiler(compileScope);
    }

    @NotNull
    @Override
    public Process launchProcess(@NotNull final ModuleChunk moduleChunk, @NotNull String s,
            @NotNull CompileContext compileContext)
            throws IOException {
        System.out.println("MyCompiler.launchProcess()");

        ApplicationManager.getApplication().runReadAction(new Runnable() {
            @Override
            public void run() {
                for (VirtualFile file : moduleChunk.getFilesToCompile()) {
                    PsiFile psifile = psimanager.findFile(file);
                    psifile.accept(new JavaRecursiveElementVisitor() {
                        @Override
                        public void visitClassObjectAccessExpression(PsiClassObjectAccessExpression expression) {
                            System.out.println(expression);
                            super.visitClassObjectAccessExpression(expression);
                        }

                        @Override
                        public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                            PsiMethod m = expression.resolveMethod();
                            System.out.println(expression.getNode().getTextRange().getLength());
                            System.out.printf("visitMethodCallExpression() :: %s.%s%s%n", m.getContainingClass().getQualifiedName(), m.getName(), m.getParameterList().getText());
                            super.visitMethodCallExpression(expression);
                        }
                    });
//            ClassFileViewProviderFactory factory = new ClassFileViewProviderFactory();
//            ClassFileViewProvider provider = (ClassFileViewProvider)
//                    factory.createFileViewProvider(file, psifile.getLanguage(), psimanager, true);
//            System.out.println(provider);
                }
            }
        });



        System.out.println(moduleChunk.getFilesToCompile());

        return javac.launchProcess(moduleChunk, s, compileContext);
    }

    @Override
    public void compileFinished() {
        System.out.println("compileFinished()");
        javac.compileFinished();
    }
}
