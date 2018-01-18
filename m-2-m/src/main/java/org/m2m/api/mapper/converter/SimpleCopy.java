package org.m2m.api.mapper.converter;

import org.m2m.api.model.FieldAllocation;

import java.lang.reflect.Field;

/**
 * @author ECH54
 * @date Dec 20, 2017
 */
public class SimpleCopy implements ValueRules {

	/**
	 * Reference on the field memory allocation.
	 */
	private FieldAllocation field;

	/**
	 * Default constructor.
	 * @param field The field.
	 */
	public SimpleCopy(final FieldAllocation field) {
		this.field = field;
	}

	/**
	 * @see FieldAllocation#getFieldName()
	 */
	@Override
	public String getFieldName() {
		return getField().getName();
	}

	/**
	 * @see org.m2m.api.model.FieldAllocation#getField()
	 */
	@Override
	public Field getField() {
		return this.field.getField();
	}

	/**
	 * @see org.m2m.api.model.FieldAllocation#getOffset()
	 */
	@Override
	public Long getOffset() {
		return this.field.getOffset();
	}

	/**
	 * Indicates if the field must be copied during the model copy process.
	 * @return <code>true</code> if the field must be copied,
	 * else <code>false</code>.
	 */
	@Override
	public boolean mustBeCopied() {
		return true;
	}

	/**
	 * Indicates if the field value must be converted during the model copy
	 * 	process.
	 * @return <code>true</code> if the field must be converted,
	 * 	else <code>false</code>.
	 */
	@Override
	public boolean mustBeConverted() {
		return false;
	}
	
	/**
	 * @see org.m2m.api.mapper.converter.ValueRules#convert(java.lang.Object)
	 */
	@Override
	public Object convert(final Object value) {
		throw new UnsupportedOperationException(""); //TODO
	}

}
