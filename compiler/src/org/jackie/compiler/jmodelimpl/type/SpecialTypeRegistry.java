package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.SpecialType;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Patrik Beno
 */
public class SpecialTypeRegistry {

	protected Map<Class<? extends SpecialType>, Class> implementationByInterface;

	protected Map<Class, Class<? extends SpecialType>> interfaceByImplementation;

	public void registerImplementation(Class<? extends SpecialType> iface, Class implclass) {
		interfaceByImplementation.put(implclass, iface);
		implementationByInterface.put(iface, implclass);
	}

	public Class<? extends SpecialType> getSpecialTypeInterface(Class implclass) {
		return interfaceByImplementation.get(implclass);
	}

	public Class getImplementationClass(Class<? extends SpecialType> iface) {
		return implementationByInterface.get(iface);
	}


	public void unregisterSpecialType(Class<? extends SpecialType> iface) {
		Class cls = implementationByInterface.remove(iface);
		if (cls != null) {
			interfaceByImplementation.remove(cls);
		}
	}

	public void reset() {
		implementationByInterface = new HashMap<Class<? extends SpecialType>, Class>();
		interfaceByImplementation = new HashMap<Class, Class<? extends SpecialType>>();
	}


}
