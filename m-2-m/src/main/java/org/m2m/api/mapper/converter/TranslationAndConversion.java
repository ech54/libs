package org.m2m.api.mapper.converter;

import org.m2m.api.model.FieldAllocation;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Class allows to apply during the copy process
 *  a translation between two field values.
 * That means value will not be copied on the same field's name
 *  and value could be converted during the operation.
 */
public class TranslationAndConversion implements ValueRules {

    /**
     * Reference on the {@link FieldAllocation}.
     */
    private FieldAllocation field;

    /**
     * Reference on the {@link FieldConverter} which is the conversion function.
     */
    private FieldConverter converter;

    /**
     * Constructor gets in parameters @{@link FieldAllocation} and {@link FieldConverter}.
     * @param field The field.
     * @param converter The conversion function.
     */
    public TranslationAndConversion(final FieldAllocation field, final FieldConverter converter) {
        this.field = field;
        this.converter = converter;
    }

    /**
     * Indicates if the field must be copied during the model copy process.
     * @return <code>true</code> if the field value must be copied, else <code>false</code>.
     */
    @Override
    public boolean mustBeCopied() {
        return true;
    }

    /**
     * Indicates if the field must be copied during the model copy process.
     * @return <code>true</code> if the field value must be converted, else <code>false</code>.
     */
    @Override
    public boolean mustBeConverted() {
        return true;
    }

    /**
     * Method executes the conversion function.
     * @param value The object's value.
     * @return The converted object.
     */
    @Override
    public Object convert(Object value) {
        if (Objects.isNull(value)) {
            return value;
        }
        return this.converter.convert(value);
    }

    /**
     * Accessor in reading on the field name.
     * @return The field name.
     */
    @Override
    public String getFieldName() {
        return this.field.getFieldName();
    }

    /**
     * Accessor in reading on the javaType {@link Field}.
     * @return The field.
     */
    @Override
    public Field getField() {
        return this.field.getField();
    }

    /**
     * Accessor in reading on the field memory offset.
     * @return The offset value.
     */
    @Override
    public Long getOffset() {
        return this.field.getOffset();
    }
}
