package org.m2m.api.mapper.converter;

import org.m2m.api.model.FieldAllocation;

/**
 * Model configuration defines all properties for a simple model.
 * 
 * @author ECH54
 * @date Dec 19, 2017
 */
public interface ValueRules extends FieldAllocation {
	
	/**
	 * Indicates if the value must be included on target 
	 *  structure.
	 * @return <code>true</code> if the value must be copied,
	 *  else <code>false</code>.
	 */
	boolean mustBeCopied();
	
	/**
	 * Indicates if the value mist be converted before to copy
	 *  the value on the structure.
	 * @return <code>true</code> if the value must be converted.
	 */
	boolean mustBeConverted();
	
	/**
	 * Convert a field value depending of the strategy
	 *  defined by user.
	 * @param value The value.
	 * @return The object.
	 */
	Object convert(final Object value);
}
