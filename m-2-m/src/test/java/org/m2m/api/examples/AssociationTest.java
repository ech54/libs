package org.m2m.api.examples;

import org.junit.Test;
import org.m2m.api.ModelA;
import org.m2m.api.ModelB;
import org.m2m.api.ModelC;
import org.m2m.api.ModelD;
import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.mapper.ModelKnowledgerCacheMemory;
import org.m2m.api.mapper.ModelMapperDirectMemoryAccess;
import org.m2m.api.mapper.ModelMapperObject;
import org.m2m.api.mapping.ModelMapping;
import org.m2m.api.model.support.ModelDefinitionSupport;
import org.m2m.api.model.support.ModelDefinitionSupportJavaType;

import static org.junit.Assert.*;

public class AssociationTest {


    private ModelKnowledger knowledger = null;
    private ModelDefinitionSupport modelDefinition = new ModelDefinitionSupportJavaType();
    private ModelMapperObject mapperObject = null;


    /**

     */
    @Test
    public void example_association() {
        newMapping()
                .map(ModelC.class, ModelD.class)
                .register();

        final ModelA a = new ModelA();
        a.setField1("field value 0");
        a.setField5("field value 1");
        a.setField6(1);

        final ModelC c = new ModelC();
        c.setField9("field value 2");
        c.setField10(2);
        c.setRefModelA(a);

        final ModelD dResult = this.mapperObject.map(c, ModelD.class);
        assertNotNull(dResult);
        assertEquals(2, dResult.getField10());
        assertEquals("field value 2", dResult.getField9());
        assertNotNull(dResult.getRefModelA());
        assertEquals("field value 0", dResult.getRefModelA().getField1());
        assertEquals("field value 1", dResult.getRefModelA().getField5());
        assertEquals(1, dResult.getRefModelA().getField6());
        c.getRefModelA().setField6(5);
        assertEquals(1, dResult.getRefModelA().getField6());
    }


    // Setup the model mapping.
    private ModelMapping newMapping() {
        this.knowledger = new ModelKnowledgerCacheMemory();
        this.mapperObject = new ModelMapperDirectMemoryAccess(knowledger);
        final ModelMapping mapping = ModelMapping.newInstance(knowledger, modelDefinition);
        return mapping;
    }
}
