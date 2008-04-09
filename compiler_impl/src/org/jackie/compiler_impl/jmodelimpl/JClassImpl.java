package org.jackie.compiler_impl.jmodelimpl;

import org.jackie.compiler.spi.Compilable;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler_impl.bytecode.ByteCodeBuilder;
import org.jackie.compiler_impl.jmodelimpl.attribute.AttributesImpl;
import org.jackie.compiler_impl.typeregistry.JClassLoader;
import static org.jackie.compiler_impl.util.Helper.assertEditable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JPackage;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.Extensions;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.Flags;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;
import static org.jackie.utils.Assert.typecast;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class JClassImpl implements JClass, Compilable {

	// infrastructure stuff

	protected TypeRegistry typeRegistry;
	public LoadLevel loadLevel;

	// core model

	protected String name;
	protected JPackage jpackage;
	protected JClass superclass;
	protected List<JClass> interfaces;

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

		((JClassLoader)typeRegistry).load(this, min);
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
		return interfaces != null ? Collections.unmodifiableList(interfaces) : Collections.<JClass>emptyList();
	}

	public Flags flags() {
		checkLoaded(LoadLevel.CLASS);
		if (flags == null) {
			flags = new FlagsImpl();
		}
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

	/// binary/bytecode stuff ///


	public void compile() {
		new ByteCodeBuilder() {

			JClass cthis;

			protected void run() {
				cthis = JClassImpl.this;
				//
				bcinit();
				bcextensions();
				bcfields();
				bcmethods();
				bcdone();
			}

			void bcinit() {
				int version = Opcodes.V1_5; // todo hardcoded class version number
				int access = toAccessFlag(flags()) | toAccessFlag(getAccessMode());
				String bcSuperName = (superclass != null) ? bcName(superclass) : null;
				cv().visit(version, access,
							  bcName(cthis),
							  null, // signature
							  bcSuperName,
							  bcNames(cthis.getInterfaces())
				);
				// todo cw.visitSource();
			}

			void bcdone() {
				cv().visitEnd();
			}

			void bcextensions() {
				for (Extension ext : extensions()) {
					if (ext instanceof Compilable) {
						((Compilable) ext).compile();
					}
				}
			}


			void bcfields() {
				for (JField f : getFields()) {
					typecast(f, Compilable.class).compile();
				}
			}

			void bcmethods() {
				for (JMethod m : cthis.getMethods()) {
					typecast(m, Compilable.class).compile();
				}
			}

		};
	}

}
