package org.jackie.compiler.jmodelimpl;

import org.jackie.compiler.bytecode.AsmSupport;
import org.jackie.compiler.bytecode.Compilable;
import org.jackie.compiler.jmodelimpl.annotations.AnnotatedImpl;
import org.jackie.compiler.jmodelimpl.structure.JFieldImpl;
import org.jackie.compiler.jmodelimpl.structure.JMethodImpl;
import org.jackie.compiler.jmodelimpl.type.SpecialTypeImpl;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.util.ConvertingList;
import org.jackie.compiler.util.Convertor;
import static org.jackie.compiler.util.Helper.*;
import org.jackie.compiler.util.LightList;
import org.jackie.jmodel.AccessMode;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JClassEditor;
import org.jackie.jmodel.JPackage;
import org.jackie.jmodel.SpecialType;
import org.jackie.jmodel.props.Accessible;
import org.jackie.jmodel.structure.JAnnotation;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.JTypeVariable;
import org.jackie.jmodel.structure.ReferenceType;
import org.jackie.utils.Assert;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class JClassImpl extends JNodeImpl implements Accessible {

	public TypeRegistry registry;
	public LoadLevel loadLevel;

	public Map<Class<? extends SpecialTypeImpl>, SpecialTypeImpl> capabilities;

	public String name;
	public JPackageImpl jpackage;
	public JClassImpl superclass;
	public List<JClassImpl> interfaces;

	public AnnotatedImpl annotations;

	public List<JFieldImpl> fields;
	public List<JMethodImpl> methods;

	public AccessMode accessMode;
	public FlagsImpl flags;

	//

	public String source, debug; 


	{
		interfaces = new LightList<JClassImpl>();
		fields = new LightList<JFieldImpl>();
		methods = new LightList<JMethodImpl>();
		annotations = new AnnotatedImpl();
	}

	public AccessMode getAccessMode() {
		return accessMode;
	}

	public String getFQName() {
		if (jpackage != null) {
			return jpackage.getFQName() + "." + name;
		} else {
			return name;
		}
	}

	public <T extends SpecialTypeImpl> T getCapability(Class<T> type) {
		return type.cast(map(capabilities).get(type));
	}

	public void addCapability(SpecialTypeImpl capability) {
		if (capabilities == null) {
			capabilities = new HashMap<Class<? extends SpecialTypeImpl>, SpecialTypeImpl>();
		}
		capabilities.put(capability.getClass(), capability);
	}

	public JClassImpl addField(JFieldImpl field) {
		fields.add(field);
		return this;
	}

	public JClassImpl addMethod(JMethodImpl jmethod) {
		methods.add(jmethod);
		return this;
	}

	public String toString() {
		return getFQName();
	}

	public JClass asJClass() {
		return new JClass() {
			public JClassEditor edit() {
				return asJClassEditor();
			}

			public Set<Class<? extends SpecialType>> getSpecialTypeCapabilities() {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public <T extends SpecialType> boolean isSpecialType(Class<T> type) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public <T extends SpecialType> T getSpecialTypeView(Class<T> type) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public String getName() {
				return name;
			}

			public String getFQName() {
				return JClassImpl.this.getFQName();
			}

			public JPackage getJPackage() {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public JClass getSuperClass() {
				return superclass.asJClass();
			}

			public ReferenceType getGenericSuperClass() {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public List<JClass> getInterfaces() {
				return new ConvertingList<JClass,JClassImpl>(interfaces, new JClassConvertor());
			}

			public List<ReferenceType> getGenericInterfaces() {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public List<JTypeVariable> getTypeVariables() {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public List<JField> getFields() {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public List<? extends JMethod> getMethods() {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public List<JAnnotation> getJAnnotations() {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public List<? extends Annotation> getAnnotations() {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public <T extends Annotation> T getAnnotation(Class<T> type) {
				throw Assert.notYetImplemented(); // todo implement this
			}
		};
	}

	public JClassEditor asJClassEditor() {
		return new JClassEditor() {
			public void setName(String name) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public void setPackage(JPackage jpackage) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public void setSuperClass(JClass jclass) {
				JClassImpl c = getClassImpl(jclass);
			}

			public void setInterfaces(JClass ... ifaces) {
				interfaces.clear();
				for (JClass iface : iterable(ifaces)) {
					addInterface(iface);
				}
			}

			public void addInterface(JClass iface) {
				interfaces.add(getClassImpl(iface));
			}

			public void setEnclosingClass(JClass jclass) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public void setEnclosingMethod(JMethod jmethod) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public void addField(JField jfield) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public void addMethod(JMethod jmethod) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public JClass node() {
				return asJClass();
			}
		};
	}

	static class JClassConvertor extends Convertor<JClass,JClassImpl> {
		public JClass execute(JClassImpl jcls) {
			return jcls.asJClass();
		}
	}


	/// binary/bytecode stuff ///


	protected String bcName() {
		return getFQName().replace('.', '/');
	}

	protected String bcSignature() {
		return null; // todo implement this
	}

	public String bcDesc() {
		return bcName(); // fixme need to handle primitives/arrays/classes/...
	}

	public byte[] compile() {
		ClassWriter cw = new ClassWriter(0);
		ClassCompiler c = new ClassCompiler();
		c.compile(cw);
		return cw.toByteArray();
	}

	class ClassCompiler extends AsmSupport implements Compilable<ClassVisitor>  {

		ClassVisitor cv;

		public void compile(ClassVisitor cv) {
			this.cv = cv;
			init();
			nesting();
			bcannotations();
			bcfields();
			bcmethods();
			done();
		}

		void init() {
			int version = Opcodes.V1_5; // todo hardcoded class version number
			int access = toAccessFlags(JClassImpl.this) | toAccessFlag(flags) | toAccessFlag(accessMode);
			String bcSuperName = (superclass != null) ? superclass.bcName() : null;
			cv.visit(version, access, bcName(), bcSignature(), bcSuperName, bcInterfaces());
			// todo cw.visitSource();
		}

		void done() {
			cv.visitEnd();
		}

		void nesting() {}

		void bcannotations() {
//				for (AnnotationImpl a : annotations) {
//					AnnotationVisitor av = cw.visitAnnotation(a.type.jclass.bcDesc(), true); // everything is visible, what the hell?
//					a.compile(av);
//				}
		}

		void bcfields() {
//				for (JFieldImpl f : fields) {
//					int access = toAccessFlags(f.type) | toAccessFlag(f.flags) | toAccessFlag(f.accessMode);
//					FieldVisitor fv = cw.visitField(access, f.name, f.type.bcDesc(), f.bcSignature(), null);
//					f.compile(fv);
//				}
		}

		void bcmethods() {
//				for (JMethodImpl m : methods) {
//					int access = toAccessFlags(m.type) | toAccessFlag(m.flags) | toAccessFlag(m.accessMode);
//					MethodVisitor mv = cw.visitMethod(access, m.name, desc, signature, bcExceptions(m));
//					m.compile(mv);
//				}
		}

		String[] bcInterfaces() {
			List<String> bcnames = new LightList<String>();
			for (JClassImpl iface : interfaces) {
				bcnames.add(iface.bcName());
			}
			return bcnames.toArray(new String[bcnames.size()]);
		}

		String[] bcExceptions(JMethodImpl m) {
			List<String> bcnames = new LightList<String>();
			for (JClassImpl ex : m.exceptions) {
				bcnames.add(ex.bcName());
			}
			return bcnames.toArray(new String[bcnames.size()]);
		}

	}


}
