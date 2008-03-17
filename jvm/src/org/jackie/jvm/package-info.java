/**
 * Provides core JVM model definition for the Jackie compiler (model only, to be implemented
 * by implementer/provider).
 * <p>
 * Brief model overview:
 * <p>
 * Model graph is composed of {@link JNode JNodes}.
 * JNodes usually provide read-only access to their contents. JNodes can also be
 * made {@link Editable editable} and provide their specialized {@link Editor editor}.
 * <p>
 * Core model basically provides standard JVM features like:
 * <ul>
 * <li> {@link JPackage packages} and {@link JClass classes} with
 *      {@link org.jackie.jvm.structure.JField fields} and
 *      {@link org.jackie.jvm.structure.JMethod methods}
 * <li> generic JVM {@link org.jackie.jvm.attribute.JAttribute attributes}
 * <li> support for special core types (
 *      {@link org.jackie.jvm.extension.builtin.PrimitiveType primitives},
 *      {@link org.jackie.jvm.extension.builtin.ArrayType arrays})
 * </ul>
 * <p>
 * Model can be extended by pluggable
 * {@link org.jackie.jvm.extension.ExtensionProvider ExtensionProviders}
 * which provide
 * {@link org.jackie.jvm.extension.Extension Extension}s. Extensions can be used to provide special
 * views and/or editors on standard (core) model entities.
 *
 * @author Patrik Beno
 */
package org.jackie.jvm;

