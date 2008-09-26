package org.jackie.compilerimpl.jmodelimpl.code;

import org.jackie.compiler.spi.Compilable;
import org.jackie.compilerimpl.jmodelimpl.ExtensionsImpl;
import org.jackie.compilerimpl.jmodelimpl.attribute.AttributesImpl;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.code.CodeBlock;
import org.jackie.jvm.extension.Extensions;
import org.jackie.jvm.spi.AbstractJNode;
import org.jackie.jvm.structure.JCode;
import org.jackie.jvm.structure.JMethod;
import static org.jackie.utils.Assert.typecast;

/**
 * @author Patrik Beno
 */
public class JCodeImpl extends AbstractJNode implements JCode, Compilable {

    AttributesImpl attributes;
    ExtensionsImpl extensions;
    CodeBlock codeblock;

    public JCodeImpl(JMethod jmethod) {
        super(jmethod);
    }

    public JMethod getJMethod() {
        return (JMethod) owner;
    }

    public CodeBlock getCodeBlock() {
        return codeblock;
    }

    public Attributes attributes() {
        if (attributes == null) {
            attributes = new AttributesImpl(this);
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
