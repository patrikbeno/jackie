package org.jackie.utils.naming;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface Name {

	String getShortName();

	String getName();

	List<String> getNameComponents();

	Name getParent();

	List<Name> getChildren();

}
