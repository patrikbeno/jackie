package org.jackie.compiler_impl.jmodelimpl.code;

import org.jackie.jvm.structure.JCode;
import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.extension.Extensions;
import org.jackie.jvm.Editor;
import org.jackie.jvm.code.CodeBlock;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;
import org.jackie.compiler_impl.jmodelimpl.attribute.AttributesImpl;
import org.jackie.compiler_impl.jmodelimpl.ExtensionsImpl;
import static org.jackie.context.ContextManager.context;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.spi.Compilable;

/**
 * @author Patrik Beno
 */
public class JCodeImpl implements JCode, Compilable {

    JMethod jmethod;
    AttributesImpl attributes;
    ExtensionsImpl extensions;
    CodeBlock codeblock;

    public JCodeImpl(JMethod jmethod) {
        this.jmethod = jmethod;
    }

    public JMethod getJMethod() {
        return jmethod;
    }

    public CodeBlock getCodeBlock() {
        return codeblock;
    }

    public Attributes attributes() {
        if (attributes == null) {
            attributes = new AttributesImpl();
        }
        return attributes;
    }

    public Extensions extensions() {
        if (extensions == null) {
            extensions = new ExtensionsImpl(this);
        }
        return extensions;
    }

    public boolean isEditable() {
        return getJMethod().isEditable(); 
    }

    public Editor edit() {
        return new Editor() {

            JCodeImpl jcode = JCodeImpl.this;

            public JCode editable() {
                return jcode;
            }

            public Editor setCodeBlock(CodeBlock codeblock) {
                jcode.codeblock = codeblock;
                return this;
            }
        };
    }

    public void compile() {
        typecast(codeblock, Compilable.class).compile(); 
    }
}
