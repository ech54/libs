package org.m2m.api;

import org.m2m.api.mapper.converter.FieldConverter;
import org.m2m.api.mapping.Direction;
import org.m2m.api.model.FieldMappingDefinition;

import java.util.Optional;


public class FieldMappingDefinitionSupport {

    private Optional<String> field = Optional.empty();
    private String source;
    private String target;
    private Optional<FieldConverter> converter = Optional.empty();
    private Direction direction;

    public static final FieldMappingDefinitionSupport source(final String name) {
        final FieldMappingDefinitionSupport support = new FieldMappingDefinitionSupport();
        support.source=name;
        return support;
    }
    public FieldMappingDefinitionSupport target(final String target) {
        this.target = target;
        return this;
    }

    public FieldMappingDefinitionSupport direction(final Direction direction) {
        this.direction = direction;
        return this;
    }

    public FieldMappingDefinitionSupport convert(final FieldConverter converter) {
        this.converter = Optional.of(converter);
        return this;
    }

    public FieldMappingDefinition mapping() {
        if (this.converter.isPresent()) {
            return new FieldMappingDefinition(this.direction, this.source, this.target, this.converter.get());
        }
        if (this.field.isPresent()) {
            return new FieldMappingDefinition(this.direction, this.field.get());
        }
        return new FieldMappingDefinition(this.direction, this.source, this.target);
    }
}
