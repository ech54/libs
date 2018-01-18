package org.m2m.api.model;

import java.lang.reflect.Field;

/**
 * Field definition describes the relation between a class field and 
 *  some items as memory offset.
 * 
 * @author ECH54
 * @date Dec 19, 2017
 */
public interface FieldAllocation {

	String getFieldName();

	/**
	 * Reference on the classe's field.
	 * @return The field.
	 */
	Field getField();
	
	/**
	 * Offset of field in allocation memory.
	 * @return The offset.
	 */
	Long getOffset();
}
