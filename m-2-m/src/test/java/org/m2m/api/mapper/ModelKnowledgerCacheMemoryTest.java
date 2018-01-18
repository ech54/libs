package org.m2m.api.mapper;

import org.junit.Test;
import org.m2m.api.ModelA;
import org.m2m.api.ModelB;
import org.m2m.api.mapper.converter.ValueRules;
import org.m2m.api.model.ModelDefinition;
import org.m2m.api.model.ModelMappingDefinition;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.m2m.api.FieldMappingDefinitionSupport.source;
import static org.m2m.api.FieldMemorySupport.field;
import static org.m2m.api.ModelDefinitionSupport.of;
import static org.m2m.api.ModelMappingDefinitionSupport.from;
import static org.m2m.api.utils.ModelUtils.direction;

public class ModelKnowledgerCacheMemoryTest {

    @Test
    public void findTuple() throws Exception {

        final ModelKnowledger knowledger = knowledger();
        final Optional<ValueRules> ruleForField1 = knowledger.findTuple(
                ModelA.class.getDeclaredField("field1"), direction(ModelA.class, ModelB.class));

        assertEquals(true, ruleForField1.isPresent());
        assertEquals(true, ruleForField1.get().mustBeCopied());
        assertEquals(false, ruleForField1.get().mustBeConverted());

        final Optional<ValueRules> ruleForField2 = knowledger.findTuple(
                ModelA.class.getDeclaredField("field2"), direction(ModelA.class, ModelB.class));
        assertEquals(true, ruleForField2.isPresent());
        assertEquals(true, ruleForField2.get().mustBeCopied());
        assertEquals(true, ruleForField2.get().mustBeConverted());
        assertEquals("Converted Field", ruleForField2.get().convert("Initial Value"));
    }

    private ModelKnowledger knowledger() {
        final ModelKnowledger knowledger = new ModelKnowledgerCacheMemory();
        knowledger.register(modelA());
        knowledger.register(modelB());
        knowledger.register(mappingA_B());
        knowledger.register(mappingB_A());
        return knowledger;
    }

    private ModelMappingDefinition mappingA_B() {

        return from(modelA())
                .to(modelB())
                .fields(source("field1").target("field3")
                        .direction(direction(ModelA.class, ModelB.class)).mapping())
                .fields(source("field2").target("field4").convert(f->"Converted Field")
                        .direction(direction(ModelA.class, ModelB.class)).mapping())
                .mapping();
    }

    private ModelMappingDefinition mappingB_A() {
        return from(modelA())
                .to(modelB())
                .fields(source("field3").target("field1")
                        .direction(direction(ModelA.class, ModelB.class)).mapping())
                .fields(source("field4").target("field2").convert(f-> "Inversed field value")
                        .direction(direction(ModelB.class, ModelA.class)).mapping())
                .mapping();
    }

    private ModelDefinition modelA() {
        return of(ModelA.class)
                .fields(
                        field("field1", ModelA.class,3l),
                        field("field2", ModelA.class,5l)
                )
                .model();
    }

    private ModelDefinition modelB() {
        return of(ModelB.class)
                .fields(
                        field("field3", ModelB.class,2l),
                        field("field4", ModelB.class,6l)
                )
                .model();
    }
}
