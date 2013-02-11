package ideacompiler;

import static ideacompiler.MyCompilerConfiguration.myCompilerConfiguration;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.toString;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.compiler.TranslatingCompiler;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MyConfig implements Configurable {

    private Project project;

    private JPanel panel;

    private JCheckBox enabledCheckBox;

    private JCheckBox alwaysRebuildCheckBox;

    private JCheckBox enableAsynchronousParalelizedBuildCheckBox;

    private JTextField threads;

    public MyConfig(Project project) {
        this.project = project;
    }

    public void load(MyCompilerConfiguration data) {
        enabledCheckBox.setSelected(data.isEnabled());
        alwaysRebuildCheckBox.setSelected(data.isRebuild());
        enableAsynchronousParalelizedBuildCheckBox.setSelected(data.isMultithread());
        threads.setText(asString(data.getThreads()));
    }

    public void save(MyCompilerConfiguration data) {
        data.setEnabled(enabledCheckBox.isSelected());
        data.setRebuild(alwaysRebuildCheckBox.isSelected());
        data.setMultithread(enableAsynchronousParalelizedBuildCheckBox.isSelected());
        data.setThreads(asInteger(threads.getText()));
    }

    public boolean isModified(MyCompilerConfiguration data) {
        if (enabledCheckBox.isSelected() != data.isEnabled()) return true;
        if (alwaysRebuildCheckBox.isSelected() != data.isRebuild()) return true;
        if (enableAsynchronousParalelizedBuildCheckBox.isSelected() != data.isMultithread()) return true;
        if (asInteger(threads.getText()) != data.getThreads()) return true;
        return false;
    }

    ///

    String asString(int i) {
        return Integer.toString(i);
    }

    int asInteger(String s) {
        return s != null && !s.isEmpty() ? Integer.parseInt(s) : 0;
    }

    ///


    @Nls
    @Override
    public String getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return panel;
    }

    @Override
    public boolean isModified() {
        return isModified(myCompilerConfiguration(project));
    }

    @Override
    public void apply() throws ConfigurationException {
        save(myCompilerConfiguration(project));
    }

    @Override
    public void reset() {
        load(myCompilerConfiguration(project));
    }

    @Override
    public void disposeUIResources() {
    }
}
