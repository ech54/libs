package org.m2m.api.mapper;

import org.m2m.api.mapper.converter.ValueRules;
import org.m2m.api.mapping.Direction;
import org.m2m.api.model.FieldAllocation;
import org.m2m.api.model.ModelDefinition;
import org.m2m.api.model.ModelMappingDefinition;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * Model knowledge provides all information to drive the process
 *  of value copy.
 * 
 * @author ECH54
 * @date Dec 19, 2017
 */
public interface ModelKnowledger {

	/**
	 * Get all class fields and memory offset inside the class footprint.
	 * @param object The object.
	 * @return The list of model fields.
	 */
	List<FieldAllocation> getFields(final Object object);

	/**
	 * Get all class fields and memory offset inside the class footprint.
	 * @param javaType The class type.
	 * @return The list of model fields.
	 */
	List<FieldAllocation> getFields(final Class<?> javaType);

	/** 
	 * Register a model definition.
	 * @param definition The model definition.
	 */
	void register(final ModelDefinition definition);

	/**
	 * Register a model mapping definition.
	 * @param mapping The model mapping definition.
	 */
	void register(final ModelMappingDefinition mapping);

	/**
	 * High level method which finds an optional <code>ModelMappingRule</code> 
	 *  based on the field to copy from one class to an other class.
	 * @param field The field to search.
	 * @param direction The class direction from->to.
	 * @return The model mapping rule if existing.
	 */
	Optional<ValueRules> findTuple(final Field field,
			final Direction direction);
}
