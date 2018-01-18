package org.m2m.api.mapping;

import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.mapper.converter.FieldConverter;
import org.m2m.api.model.FieldMappingDefinition;
import org.m2m.api.model.ModelDefinition;
import org.m2m.api.model.support.ModelDefinitionSupport;
import org.m2m.api.utils.ModelUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelMapping {

    private ModelKnowledger knowledger;
    private ModelDefinitionSupport definitionSupport;
    private boolean mapOnlyDefinedField = false;
    private boolean disableChildrenAutoCopy = false;
    private Optional<Class<?>> modelA= Optional.empty();
    private Optional<Class<?>> modelB= Optional.empty();
    private Direction A_to_B = null;
    private Direction B_to_A = null;

    private List<FieldMappingDefinition> fieldMappings = new ArrayList<>();

    public static final ModelMapping newInstance(final ModelKnowledger knowledger,
                                          final ModelDefinitionSupport definitionSupport) {
        final ModelMapping mapping = new ModelMapping();
        mapping.knowledger=knowledger;
        mapping.definitionSupport=definitionSupport;
        return mapping;
    }

    public ModelMapping map(final Class<?> modelA, final Class<?> modelB) {
        if (Objects.nonNull(modelA)) {
            this.modelA=Optional.of(modelA);
        }
        if (Objects.nonNull(modelB)) {
            this.modelB=Optional.of(modelB);
        }
        if (Objects.nonNull(modelA)&&Objects.nonNull(modelB)) {
            this.A_to_B = new DirectionDefinition(modelA, modelB);
            this.B_to_A = new DirectionDefinition(modelB, modelA);
        }
        return this;
    }

    //TODO
    public ModelMapping parentClass() {

        return this;
    }

    public ModelMapping mapOnlyDefineField() {
        this.mapOnlyDefinedField=!this.mapOnlyDefinedField;
        return this;
    }
    public ModelMapping disableChildrenAutoCopy() {
        this.disableChildrenAutoCopy=!this.disableChildrenAutoCopy;
        return this;
    }

    public ModelMapping field(final String source, final String target) {
        this.fieldMappings.add(new FieldMappingDefinition(this.A_to_B,source,target));
        this.fieldMappings.add(new FieldMappingDefinition(this.B_to_A,target,source));
        return this;
    }

    public ModelMapping field(final String source, final String target,
                              final FieldConverter from, final FieldConverter to) {
        this.fieldMappings.add(new FieldMappingDefinition(A_to_B, source, target, from));
        this.fieldMappings.add(new FieldMappingDefinition(B_to_A, target, source, to));
        return this;
    }

    public ModelMapping field(final String field, final FieldConverter converter) {
        this.fieldMappings.add(new FieldMappingDefinition(A_to_B, field, field, converter));
        this.fieldMappings.add(new FieldMappingDefinition(B_to_A, field, field, converter));
        return this;
    }

    public void register() {
        if (!this.modelA.isPresent()||!this.modelB.isPresent()) {
            return;
        }
        final ModelDefinition definitionModelA = this.definitionSupport.build(this.modelA.get());
        final ModelDefinition definitionModelB = this.definitionSupport.build(this.modelB.get());
        this.knowledger.register(definitionModelA);
        this.knowledger.register(definitionModelB);
        this.knowledger.register(
                this.definitionSupport.build(definitionModelA, definitionModelB,
                        this.mapOnlyDefinedField, this.fieldMappings));
        this.knowledger.register(
                this.definitionSupport.build(definitionModelB, definitionModelA,
                        this.mapOnlyDefinedField, this.fieldMappings));
        if (!this.disableChildrenAutoCopy) {
            definitionModelA
                    .getFieldDefinitions().stream()
                    .filter(ModelUtils::isManageableField)
                    .forEach(f->this.knowledger.register(this.definitionSupport.build(f.getField().getType())));
        }
    }

}
