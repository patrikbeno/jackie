package ideacompiler;

import static com.intellij.openapi.application.ApplicationManager.getApplication;
import static com.intellij.openapi.roots.OrderEnumerator.orderEntries;
import static ideacompiler.MyCompilerConfiguration.myCompilerConfiguration;
import static java.util.Arrays.asList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.intellij.compiler.impl.CompilerUtil;
import com.intellij.compiler.impl.javaCompiler.JavaCompiler;
import com.intellij.compiler.impl.javaCompiler.ModuleChunk;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.compiler.CompilerMessageCategory;
import com.intellij.openapi.compiler.TranslatingCompiler;
import com.intellij.openapi.compiler.ex.CompileContextEx;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.util.Chunk;
import com.intellij.util.containers.OrderedSet;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MyCompiler implements TranslatingCompiler, Disposable {

	Project project;
    JavaCompiler javac;


	public MyCompiler(Project project) {
		this.project = project;
		this.javac = new JavaCompiler(project);

		Disposer.register(project, this);
	}

	@Override
	public boolean isCompilableFile(VirtualFile virtualFile, CompileContext compileContext) {
		return virtualFile.getFileType().equals(StdFileTypes.JAVA);//myCompilerConfiguration(project).isEnabled() || javac.isCompilableFile(virtualFile, compileContext);
	}

	@Override
	public void compile(final CompileContext compileContext, final Chunk<Module> chunk,
							  final VirtualFile[] virtualFiles, final OutputSink outputSink) {

//        if (true) return;

        MyCompilerConfiguration cfg = myCompilerConfiguration(project);

        if (cfg.isRebuild() && !compileContext.isRebuild()) {
            compileContext.requestRebuildNextTime("Only full rebuild is supported!");
            return;
        }

        CompilerManager compilerManager = CompilerManager.getInstance(project);
        TranslatingCompiler[] compilers = compilerManager.getCompilers(TranslatingCompiler.class);
        for (TranslatingCompiler compiler : compilers) {
            if (false == compiler instanceof MyCompiler) {
                compilerManager.removeCompiler(compiler);
            }
        }


        if (!cfg.isEnabled()) {
			compileContext.addMessage(CompilerMessageCategory.WARNING, "Not enabled. Falling back to JavaCompiler", null, 0, 0);
			javac.compile(compileContext, chunk, virtualFiles, outputSink);
			return;
		}

//        if (true) return;

        final PsiManager psimanager = PsiManager.getInstance(project);

        ModuleChunk moduleChunk = new ModuleChunk(
				(CompileContextEx) compileContext, chunk, buildModuleToFilesMap(chunk, compileContext, asList(virtualFiles)));

        final CompileRequest request = new CompileRequest(compileContext, chunk, moduleChunk, virtualFiles, outputSink);

//        if (true) return;

        CompileScope scope = compileContext.getCompileScope();
        Module[] modules = scope.getAffectedModules();
        OrderedSet<String> moduleNames = new OrderedSet<>();
        List<VirtualFile> classpath = new ArrayList<>();
        for (Module module : chunk.getNodes()) {
            Collections.addAll(classpath,
                    orderEntries(module).exportedOnly().librariesOnly().withoutSdk().getClassesRoots());
        }

        CompileSession session = compileContext.getUserData(CompileSession.KEY);

        if (session == null) {
            session = new CompileSession();
            compileContext.putUserData(CompileSession.KEY, session);
        }

        session.files.addAll(asList(virtualFiles));
        session.modules.addAll(chunk.getNodes());

        int threads = cfg.isMultithread() ? cfg.getThreads() : 1;

        if (executor == null || executor.getMaximumPoolSize() != threads) {
            if (executor != null) { executor.shutdownNow(); }
            executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        }

        PrintStream out = System.out;//new PrintWriter(new ByteArrayOutputStream());
        if (true)
        out.printf(
                "Compiling %5d/%5d files in %3d/%3d affected modules. Chunk: %6d files. Threads: %d (active/queued: %d/%d). Classes/methods/chars: %d/%d/%d. Modules: %s. Classpath: %s%n",
                session.files.size(),
                session.totalFiles != null ? session.totalFiles : (session.totalFiles = scope.getFiles(StdFileTypes.JAVA, true).length),
                session.modules.size(), modules.length,
                virtualFiles.length,
                executor.getPoolSize(), executor.getActiveCount(), executor.getQueue().size(),
                session.classes, session.methods, session.chars,
                true ? chunk.getNodes() : "?",
                true ? classpath : "?");

//        if (true) return;

        final CompileSession mysession = session;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (final VirtualFile vf : virtualFiles) {
                    enqueue(new Runnable() {
                        @Override
                        public void run() {
                            PsiFile psifile = psimanager.findFile(vf);
                            synchronized (mysession) {
                                mysession.chars += psifile.getTextLength();
                            }
                            psifile.accept(new JavaRecursiveElementVisitor() {
                                @Override
                                public void visitClass(PsiClass aClass) {
//                            if (true) return;
                                    super.visitClass(aClass);
                                    synchronized (mysession) {
                                        mysession.classes++;
                                    }
                                }

                                @Override
                                public void visitMethod(final PsiMethod method) {
//                            if (true) return;
                                    enqueue(new Runnable() {
                                        @Override
                                        public void run() {
                                            method.accept(new JavaRecursiveElementVisitor() {
                                                @Override
                                                public void visitMethod(PsiMethod method) {
                                                    super.visitMethod(method);
//                                                    method.textToCharArray();
//                                                    method.textToCharArray();
                                                }
                                            });
                                        }
                                    });
                                    synchronized (mysession) { mysession.methods++; }
                                }
                            });
                        }
                    });
                }
            }
        };

//        enqueue(r);
        r.run();


        if (session.files.size() == scope.getFiles(StdFileTypes.JAVA, true).length) {
            Set<Module> ignoredModules = new HashSet<>();
            ignoredModules.addAll(asList(scope.getAffectedModules()));
            ignoredModules.removeAll(session.modules);
            System.out.println(ignoredModules);

            Set<VirtualFile> ignoredFiles = new HashSet<>();
            ignoredFiles.addAll(asList(scope.getFiles(StdFileTypes.JAVA, true)));
            ignoredFiles.removeAll(session.files);
            System.out.println(ignoredFiles);

            while (true) {
                try {
                    System.out.printf("Waiting for executor completion. Queue: %d%n", executor.getQueue().size());
                    executor.awaitTermination(1, TimeUnit.SECONDS);
                    if (executor.getQueue().isEmpty()) { break; }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.printf("Done in %d msec. Files: %d, classes: %d, methods: %d, chars: %d%n",
                    System.currentTimeMillis() - ((CompileContextEx) compileContext).getStartCompilationStamp(),
                    session.files.size(),  session.classes, session.methods, session.chars);

        }

    }

	@NotNull
	@Override
	public String getDescription() {
		return "my description";
	}

	@Override
	public boolean validateConfiguration(CompileScope compileScope) {
		return true;
	}

	///

	@Override
	public void dispose() {
    }

	///

	Map<Module, List<VirtualFile>> buildModuleToFilesMap(Chunk<Module> myChunk, CompileContext myCompileContext,
																		  List<VirtualFile> filesToCompile) {
		if (myChunk.getNodes().size() == 1) {
			return Collections.singletonMap(
					myChunk.getNodes().iterator().next(), Collections.unmodifiableList(filesToCompile));
		}
		return CompilerUtil.buildModuleToFilesMap(myCompileContext, filesToCompile);
	}

    ///

    ThreadPoolExecutor executor;

    void enqueue(final Runnable runnable) {
        MyCompilerConfiguration cfg = myCompilerConfiguration(project);
        if (cfg.isMultithread()) {
            // async
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    getApplication().runReadAction(runnable);
                }
            });
        } else {
            // sync
            getApplication().runReadAction(runnable);
        }
    }

}
