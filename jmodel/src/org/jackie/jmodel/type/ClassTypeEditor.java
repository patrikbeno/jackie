package org.jackie.jmodel.type;

import org.jackie.jmodel.SpecialTypeEditor;
import org.jackie.jmodel.structure.JConstructor;

/**
 * @author Patrik Beno
 */
public interface ClassTypeEditor extends SpecialTypeEditor<ClassType> {

	void addConstructor(JConstructor ctor);

}
