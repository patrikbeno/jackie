package ideacompiler;

import java.util.HashSet;
import java.util.Set;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class CompileSession {

    static public final Key<CompileSession> KEY = Key.create("CompileSession");

    Set<VirtualFile> files = new HashSet<>();
    Set<Module> modules = new HashSet<>();
    int classes;
    int methods;
    long chars;

    Integer totalFiles;



}
