package ideacompiler;

import com.intellij.compiler.impl.javaCompiler.ModuleChunk;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.TranslatingCompiler.OutputSink;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Chunk;

import static java.lang.Thread.currentThread;

/**
* @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
* @version $Id$
*/
class CompileRequest {

	CompileContext compileContext;
	Chunk<Module> chunk;
	ModuleChunk moduleChunk;
	VirtualFile[] virtualFiles;
	OutputSink outputSink;


	CompileRequest(CompileContext compileContext,
						Chunk<Module> chunk, ModuleChunk moduleChunk,
						VirtualFile[] virtualFiles,
						OutputSink outputSink) {
		this.compileContext = compileContext;
		this.chunk = chunk;
		this.moduleChunk = moduleChunk;
		this.virtualFiles = virtualFiles;
		this.outputSink = outputSink;
	}

	synchronized void waitForCompletion() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
			currentThread().interrupt();
		}
	}

	synchronized void done() {
		notifyAll();
	}
}
