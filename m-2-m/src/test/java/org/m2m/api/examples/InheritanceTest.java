package org.m2m.api.examples;

import static org.junit.Assert.*;
import org.junit.Test;
import org.m2m.api.ModelA;
import org.m2m.api.ModelB;
import org.m2m.api.ModelE;
import org.m2m.api.ModelF;
import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.mapper.ModelKnowledgerCacheMemory;
import org.m2m.api.mapper.ModelMapperDirectMemoryAccess;
import org.m2m.api.mapper.ModelMapperObject;
import org.m2m.api.mapping.ModelMapping;
import org.m2m.api.model.support.ModelDefinitionSupport;
import org.m2m.api.model.support.ModelDefinitionSupportJavaType;

public class InheritanceTest {

    private ModelKnowledger knowledger = null;
    private ModelDefinitionSupport modelDefinition = new ModelDefinitionSupportJavaType();
    private ModelMapperObject mapperObject = null;

    @Test
    public void example_inheritance() {
        final ModelMapping mapping = newMapping();
        mapping.map(ModelA.class, ModelB.class)
                .field("field1","field3")
                .register();
        mapping.map(ModelE.class, ModelF.class)
                .register();

        final ModelE modelE = new ModelE();
        modelE.setFieldTest("field test");
        modelE.setField1("field 1");

        final ModelF modelF = this.mapperObject.map(modelE, ModelF.class);


        assertEquals("field test", modelF.getFieldTest());
        assertEquals("field 1", modelF.getField3());
    }


    // Setup the model mapping.
    private ModelMapping newMapping() {
        this.knowledger = new ModelKnowledgerCacheMemory();
        this.mapperObject = new ModelMapperDirectMemoryAccess(knowledger);
        final ModelMapping mapping = ModelMapping.newInstance(knowledger, modelDefinition);
        return mapping;
    }

}
