package org.m2m.api.model;

import org.m2m.api.mapper.converter.FieldConverter;
import org.m2m.api.mapping.Direction;

import java.util.Objects;
import java.util.Optional;

/**
 * Models mapping between two fields from different java model Type.
 * The direction drives how to map field source to target.
 */
public class FieldMappingDefinition {

    /**
     * Reference on the mapping model direction.
     */
    private Direction direction;
    /**
     * Reference on the field source to map.
     */
    private String source;
    /**
     * Reference on the mapping destination.
     */
    private String target;
    /**
     * Reference on optional field value converter.
     */
    private Optional<FieldConverter> converter = Optional.empty();

    /**
     * Main constructor to define a simple copy of field between both models.
     * @param direction The model direction.
     * @param fieldName The field's name to copy.
     */
    public FieldMappingDefinition(final Direction direction, final String fieldName){
        this.direction=direction;
        this.source=fieldName;
        this.target=fieldName;
    }

    /**
     * Constructor defines a copy and field translation between both models.
     * @param direction The model direction.
     * @param source The field's name source.
     * @param target The field's name target.
     */
    public FieldMappingDefinition(final Direction direction, final String source, final String target) {
        this.direction=direction;
        this.source = source;
        this.target = target;
    }

    /**
     * Constructor defines a field value conversion and translation of fields.
     * @param direction The model direction.
     * @param source The source.
     * @param target The target.
     * @param converter The value conversion.
     */
    public FieldMappingDefinition(final Direction direction, final String source, final String target, final FieldConverter converter) {
        this.direction=direction;
        this.source=source;
        this.target=target;
        this.converter=Optional.of(converter);
    }


    @Override
    public boolean equals(final Object object) {

        if (Objects.isNull(object)||!FieldMappingDefinition.class.isInstance(object)) {
            return false;
        }
        final FieldMappingDefinition definition = (FieldMappingDefinition) object;
        return definition.getSource().equalsIgnoreCase(this.source)
                && definition.getTarget().equalsIgnoreCase(this.target)
                && definition.getDirection().equals(this.direction);
    }

    @Override
    public int hashCode() {
        return this.source.hashCode()
                +this.target.hashCode()
                +this.direction.hashCode();
    }

    /**
     * Accessor in reading on the model direction.
     * @return The direction.
     */
    public Direction getDirection(){
        return this.direction;
    }

    /**
     * Accessor in reading on the field's source.
     * @return The source.
     */
    public String getSource() {
        return this.source;
    }

    /**
     * Accessor in reading on the field's target.
     * @return The target.
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * Accessor in reading on the field value converter.
     * @return The converter.
     */
    public Optional<FieldConverter> getConverter() {
        return this.converter;
    }
}
