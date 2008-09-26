package org.jackie.compilerimpl.typeregistry;

import org.jackie.compiler.typeregistry.TypeRegistry;

/**
 * @author Patrik Beno
 */
public abstract class EditAction<T> {

	static public <T> T run(TypeRegistry registry, EditAction<T> action) {
		boolean editable = registry.isEditable();
		try {
			registry.setEditable(true);
			action.prepare();
			return action.run();

		} finally {
			registry.setEditable(editable);
			action.done();
		}

	}

	protected void prepare() {}

	protected abstract T run();

	protected void done() {}
}
