/**
 * Provides infrastructure for type registries.
 *
 * Type registry is aware of the active types in the compiler and creates them on demand.
 * Type registries are merely a memory/storage for types; they cannot do anything reasonable about
 * them (like loading or parsing class files...)
 *
 * @see org.jackie.compiler.typeregistry.TypeRegistry
 * @see PrimitiveTypeRegistry
 * @see AbstractTypeRegistry
 * @see ArrayRegistry
 *
 * @author Patrik Beno
 */
package org.jackie.compiler_impl.typeregistry;

