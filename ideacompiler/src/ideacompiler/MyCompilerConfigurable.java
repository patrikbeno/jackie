package ideacompiler;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MyCompilerConfigurable implements Configurable {

    MyCompiler compiler;

    public MyCompilerConfigurable(MyCompiler compiler) {
        this.compiler = compiler;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return new JPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
    }

    @Override
    public void reset() {
    }

    @Override
    public void disposeUIResources() {
    }

    @Nls
    @Override
    public String getDisplayName() {
        return getClass().getSimpleName();
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "";
    }
}
