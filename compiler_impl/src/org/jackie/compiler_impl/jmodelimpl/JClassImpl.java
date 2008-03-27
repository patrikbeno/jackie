package org.jackie.compiler_impl.jmodelimpl;

import static org.jackie.compiler_impl.util.Helper.assertEditable;
import org.jackie.compiler_impl.jmodelimpl.attribute.AttributesImpl;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.LoadLevel;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JPackage;
import org.jackie.java5.annotation.Annotations;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.extension.Extensions;
import org.jackie.jvm.props.Flags;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import static java.util.Collections.emptyList;

/**
 * @author Patrik Beno
 */
public class JClassImpl implements JClass {

	// infrastructure stuff

	protected TypeRegistry typeRegistry;
	public LoadLevel loadLevel;

	// core model

	protected String name;
	protected JPackage jpackage;
	protected JClass superclass;
	protected List<JClass> interfaces;

	protected Annotations annotations;
	protected Attributes attributes;

	protected List<JField> fields;
	protected List<JMethod> methods;

	protected AccessMode access;
	protected FlagsImpl flags;

	protected Extensions extensions;


	{
		loadLevel = LoadLevel.NONE;
		access = AccessMode.PACKAGE;
	}


	public JClassImpl(String name, JPackage jpackage, TypeRegistry typeRegistry) {
		this.name = name;
		this.jpackage = jpackage;
		this.typeRegistry = typeRegistry;
	}

	public TypeRegistry getTypeRegistry() {
		return typeRegistry;
	}

	protected void checkLoaded(LoadLevel min) {
		if (this.loadLevel.atLeast(min)) {
			return;
		}

		typeRegistry.loadJClass(this, min);
	}

	/// JClass ///

	public String getName() {
		return name;
	}

	public String getFQName() {
		if (jpackage != null) {
			return jpackage.getFQName() + "." + name;
		} else {
			return name;
		}
	}

	public JPackage getJPackage() {
		return jpackage;
	}

	public JClass getSuperClass() {
		checkLoaded(LoadLevel.CLASS);
		return superclass;
	}

	public List<JClass> getInterfaces() {
		checkLoaded(LoadLevel.CLASS);
		return Collections.unmodifiableList(interfaces);
	}

	public Flags flags() {
		checkLoaded(LoadLevel.CLASS);
		return flags;
	}

	public Attributes attributes() {
		checkLoaded(LoadLevel.ATTRIBUTES);
		if (attributes == null) {
			attributes = new AttributesImpl();
		}
		return attributes;
	}

	public List<JField> getFields() {
		checkLoaded(LoadLevel.API);
		if (fields == null) { 
			return emptyList();
		}
		return Collections.unmodifiableList(fields);
	}

	public List<? extends JMethod> getMethods() {
		checkLoaded(LoadLevel.API);
		if (methods == null) {
			return emptyList();
		}
		return Collections.unmodifiableList(methods);
	}


	/// Accessible ///

	public AccessMode getAccessMode() {
		checkLoaded(LoadLevel.CLASS);
		return access;
	}


	/// Extensions ///

	public Extensions extensions() {
		if (extensions == null) {
			extensions = new ExtensionsImpl(this); 
		}
		return extensions;
	}
	

	/// Editable ///

	public boolean isEditable() {
		return typeRegistry.isEditable();
	}

	public Editor edit() {
		assertEditable(this);
		checkLoaded(LoadLevel.ATTRIBUTES);
		return new Editor() {
			final JClassImpl cthis = JClassImpl.this;
			public Editor setName(String name) {
				JClassImpl.this.name = name;
				return this;
			}

			public Editor setPackage(JPackage jpackage) {
				JClassImpl.this.jpackage = jpackage;
				return this;
			}

			public Editor setSuperClass(JClass jclass) {
				JClassImpl.this.superclass = jclass;
				return this;
			}

			public Editor setInterfaces(JClass... ifaces) {
				for (JClass iface : ifaces) {
					addInterface(iface);
				}
				return this;
			}

			public Editor addInterface(JClass iface) {
				if (interfaces == null) {
					interfaces = new ArrayList<JClass>();
				}
				interfaces.add(iface);
				return this;
			}

			public Editor setAccessMode(AccessMode accessMode) {
				JClassImpl.this.access = accessMode;
				return this;
			}

			public Editor setFlags(Flag ... flags) {
				if (cthis.flags == null) {
					cthis.flags = new FlagsImpl();
				}
				cthis.flags.reset().setAll(flags);
				return this;
			}

			public Editor addField(JField jfield) {
				if (fields == null) {
					fields = new ArrayList<JField>();
				}
				fields.add(jfield);
				return this;
			}

			public Editor addMethod(JMethod jmethod) {
				if (methods == null) {
					methods = new ArrayList<JMethod>();
				}
				methods.add(jmethod);
				return this;
			}

			public JClass editable() {
				return JClassImpl.this;
			}
		};
	}

	public String toString() {
		return getFQName();
	}

	//	/// binary/bytecode stuff ///
//
//
//	protected String bcName() {
//		return getFQName().replace('.', '/');
//	}
//
//	protected String bcSignature() {
//		return null; // todo implement this
//	}
//
//	public String bcDesc() {
//		return bcName(); // fixme need to handle primitives/arrays/classes/...
//	}
//
//	public byte[] compile() {
//		ClassWriter cw = new ClassWriter(0);
//		ClassCompiler c = new ClassCompiler();
//		c.compile(cw);
//		return cw.toByteArray();
//	}
//
//	class ClassCompiler extends AsmSupport implements Compilable<ClassVisitor>  {
//
//		ClassVisitor cv;
//
//		public void compile(ClassVisitor cv) {
//			this.cv = cv;
//			init();
//			nesting();
//			bcannotations();
//			bcfields();
//			bcmethods();
//			done();
//		}
//
//		void init() {
//			int version = Opcodes.V1_5; // todo hardcoded class version number
//			int access = toAccessFlags(asJClass()) | toAccessFlag(flags) | toAccessFlag(accessMode);
//			String bcSuperName = (superclass != null) ? superclass.bcName() : null;
//			cv.visit(version, access, bcName(), bcSignature(), bcSuperName, bcInterfaces());
//			// todo cw.visitSource();
//		}
//
//		void done() {
//			cv.visitEnd();
//		}
//
//		void nesting() {}
//
//		void bcannotations() {
////				for (AnnotationImpl a : annotations) {
////					AnnotationVisitor av = cw.visitAnnotation(a.type.jclass.bcDesc(), true); // everything is visible, what the hell?
////					a.compile(av);
////				}
//		}
//
//		void bcfields() {
////				for (JFieldImpl f : fields) {
////					int access = toAccessFlags(f.type) | toAccessFlag(f.flags) | toAccessFlag(f.accessMode);
////					FieldVisitor fv = cw.visitField(access, f.name, f.type.bcDesc(), f.bcSignature(), null);
////					f.compile(fv);
////				}
//		}
//
//		void bcmethods() {
////				for (JMethodImpl m : methods) {
////					int access = toAccessFlags(m.type) | toAccessFlag(m.flags) | toAccessFlag(m.accessMode);
////					MethodVisitor mv = cw.visitMethod(access, m.name, desc, signature, bcExceptions(m));
////					m.compile(mv);
////				}
//		}
//
//		String[] bcInterfaces() {
//			List<String> bcnames = new LightList<String>();
//			for (JClassImpl iface : interfaces) {
//				bcnames.add(iface.bcName());
//			}
//			return bcnames.toArray(new String[bcnames.size()]);
//		}
//
//		String[] bcExceptions(JMethodImpl m) {
//			List<String> bcnames = new LightList<String>();
//			for (JClassImpl ex : m.exceptions) {
//				bcnames.add(ex.bcName());
//			}
//			return bcnames.toArray(new String[bcnames.size()]);
//		}
//
//	}
//

}