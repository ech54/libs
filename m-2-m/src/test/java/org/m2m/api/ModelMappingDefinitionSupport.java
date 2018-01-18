package org.m2m.api;

import org.m2m.api.model.FieldMappingDefinition;
import org.m2m.api.model.ModelDefinition;
import org.m2m.api.model.ModelMappingDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelMappingDefinitionSupport {

    private ModelDefinition from;
    private ModelDefinition to;
    private List<FieldMappingDefinition> fields;

    public static final ModelMappingDefinitionSupport from(final ModelDefinition definition) {
        final ModelMappingDefinitionSupport support = new ModelMappingDefinitionSupport();
        support.from = definition;
        support.fields=new ArrayList<>();
        return support;
    }

    public ModelMappingDefinitionSupport to(final ModelDefinition definition) {
        this.to =definition;
        return this;
    }

    public ModelMappingDefinitionSupport fields(final FieldMappingDefinition ...fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public ModelMappingDefinition mapping() {

        final ModelDefinition modelA = this.from;
        final ModelDefinition modelB = this.to;
        final List<FieldMappingDefinition> mappings = this.fields;
        return new ModelMappingDefinition() {
            @Override
            public ModelDefinition modelA() {
                return modelA;
            }

            @Override
            public ModelDefinition modelB() {
                return modelB;
            }

            @Override
            public List<FieldMappingDefinition> fieldMappings() {
                return mappings;
            }
        };
    }
}
