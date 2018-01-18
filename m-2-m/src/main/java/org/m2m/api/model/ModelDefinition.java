package org.m2m.api.model;

import java.util.List;

/**
 * Model configuration defines all properties for a simple model.
 * 
 * @author ECH54
 * @date Dec 19, 2017
 */
public interface ModelDefinition {
	
	/**
	 * Reference on the model type.
	 * @return The model type.
	 */
	Class<?> getModelType();
	
	/**
	 * Reference on the field definitions.
	 * @return The definitions.
	 */
	List<FieldAllocation> getFieldDefinitions();	
}
