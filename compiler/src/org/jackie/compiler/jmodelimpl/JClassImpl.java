package org.jackie.compiler.jmodelimpl;

import org.jackie.compiler.jmodelimpl.annotations.AnnotatedImpl;
import org.jackie.compiler.jmodelimpl.structure.JFieldImpl;
import org.jackie.compiler.jmodelimpl.structure.JMethodImpl;
import org.jackie.compiler.jmodelimpl.type.SpecialTypeImpl;
import org.jackie.compiler.util.LightList;

import java.util.List;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class JClassImpl {

	public Map<Class<? extends SpecialTypeImpl>, ? extends SpecialTypeImpl> capabilities;

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
	}

	public String getFQName() {
		return jpackage.getFQName() + "." + name;
	}

	public <T extends SpecialTypeImpl> T getCapability(Class<T> type) {
		return type.cast(capabilities.get(type));
	}

	public void addCapability(SpecialTypeImpl capability) {
		// todo implement
	}

	public JClassImpl addField(JFieldImpl field) {
		fields.add(field);
		return this;
	}

	public String toString() {
		return getFQName();
	}
}
