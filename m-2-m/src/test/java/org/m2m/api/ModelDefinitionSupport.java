package org.m2m.api;

import org.m2m.api.model.FieldAllocation;
import org.m2m.api.model.ModelDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModelDefinitionSupport {

    private Class<?> model;

    private FieldAllocation[] fields;

    public static final ModelDefinitionSupport of(final Class<?> model) {
        final ModelDefinitionSupport support = new ModelDefinitionSupport();
        support.model = model;
        return support;
    }

    public ModelDefinitionSupport fields(final FieldAllocation ... fields) {
        if (Objects.nonNull(fields)) {
            this.fields = fields;
        }
        return this;
    }

    public ModelDefinition model() {

        final Class<?> model = this.model;
        final List<FieldAllocation> fields = Arrays.asList(this.fields);
        return new ModelDefinition() {
            @Override
            public Class<?> getModelType() { return model;  }

            @Override
            public List<FieldAllocation> getFieldDefinitions() { return fields; }
        };
    }
}
