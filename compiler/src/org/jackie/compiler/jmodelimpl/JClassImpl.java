package org.jackie.compiler.jmodelimpl;

import org.jackie.jmodel.JPackage;
import org.jackie.jmodel.SpecialType;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.structure.JAnnotation;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.ReferenceType;
import org.jackie.jmodel.structure.JTypeVariable;
import org.jackie.jmodel.structure.JField;
import org.jackie.utils.Assert;

import javax.lang.model.element.NestingKind;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class JClassImpl implements JClass {

	public Map<Class<? extends SpecialType>, SpecialType> capabilities;

	public String name;
	public JPackageImpl jpackage;
	public JClassImpl superclass;

	public AnnotatedImpl annotations;

	/// JClass ///

	public Set<Class<? extends SpecialType>> getSpecialTypeCapabilities() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public <T extends SpecialType> boolean isSpecialType(Class<T> type) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public <T extends SpecialType> T getSpecialTypeView(Class<T> type) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public NestingKind getNestingKind() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JClass getEnclosingClass() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JMethod getEnclosingMethod() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Set<JClass> getNestedClasses() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public String getName() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public String getFQName() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JPackage getJPackage() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JClass getSuperClass() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public ReferenceType getGenericSuperClass() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public List<JClass> getInterfaces() {
		throw Assert.notYetImplemented(); // todo implement this
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
}
