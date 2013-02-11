package ideacompiler;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.components.StorageScheme;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

import static com.intellij.openapi.components.ServiceManager.getService;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
@State(
		name = "MyCompilerConfiguration",
		storages = {
				@Storage(file = StoragePathMacros.WORKSPACE_FILE),
				@Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + "/mycompiler.xml",
							scheme = StorageScheme.DIRECTORY_BASED)
		}
)
public class MyCompilerConfiguration implements PersistentStateComponent<MyCompilerConfiguration>, Disposable {

	static public MyCompilerConfiguration myCompilerConfiguration(Project project) {
		return getService(project, MyCompilerConfiguration.class);
	}


	private boolean enabled;
    private boolean rebuild;
	private boolean multithread;
    private int threads;


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


    public boolean isRebuild() {
        return rebuild;
    }

    public void setRebuild(boolean rebuild) {
        this.rebuild = rebuild;
    }

    public boolean isMultithread() {
		return multithread;
	}

	public void setMultithread(boolean multithread) {
		this.multithread = multithread;
	}

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    ///

	@Override
	public void dispose() {
	}

	@Nullable
	@Override
	public MyCompilerConfiguration getState() {
		return this;
	}

	@Override
	public void loadState(MyCompilerConfiguration state) {
		this.enabled = state.enabled;
		this.rebuild = state.rebuild;
		this.multithread = state.multithread;
        this.threads = state.threads;
    }


}
