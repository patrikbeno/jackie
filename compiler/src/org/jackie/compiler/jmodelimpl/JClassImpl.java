package org.jackie.compiler.jmodelimpl;

import org.jackie.compiler.jmodelimpl.annotations.AnnotatedImpl;
import org.jackie.compiler.jmodelimpl.structure.JFieldImpl;
import org.jackie.compiler.jmodelimpl.structure.JMethodImpl;
import org.jackie.compiler.jmodelimpl.type.SpecialTypeImpl;
import static org.jackie.compiler.util.Helper.map;
import org.jackie.compiler.util.LightList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class JClassImpl {

	public Map<Class<? extends SpecialTypeImpl>, SpecialTypeImpl> capabilities;

	public String name;
	public JPackageImpl jpackage;
	public JClassImpl superclass;
	public List<JClassImpl> interfaces;

	public AnnotatedImpl annotations;

	public List<JFieldImpl> fields;
	public List<JMethodImpl> methods;

	{
		interfaces = new LightList<JClassImpl>();
		fields = new LightList<JFieldImpl>();
		methods = new LightList<JMethodImpl>();
		annotations = new AnnotatedImpl();
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
}
