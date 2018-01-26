package org.m2m.api.mapper.api;

/**
 * Mapping definition contract to implement with custom model mapping.
 * @author Emilien Charton
 */
public interface MappingDefinition {
	
	/**
	 * You must overrides method to define yourself model mapping.
	 * @param factory The factory to define an new mapping.
	 */
	void mapping(final ModelMappingFactory factory);
	
}
