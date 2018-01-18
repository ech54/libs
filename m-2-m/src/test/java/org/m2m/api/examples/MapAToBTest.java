package org.m2m.api.examples;

import org.junit.Test;
import org.m2m.api.ModelA;
import org.m2m.api.ModelB;
import org.m2m.api.ModelType;
import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.mapper.ModelKnowledgerCacheMemory;
import org.m2m.api.mapper.ModelMapperDirectMemoryAccess;
import org.m2m.api.mapper.ModelMapperObject;
import org.m2m.api.mapper.converter.FieldConverter;
import org.m2m.api.mapping.ModelMapping;
import org.m2m.api.model.support.ModelDefinitionSupport;
import org.m2m.api.model.support.ModelDefinitionSupportJavaType;

import static org.junit.Assert.*;

/**
 * Usage examples of @see org.m2m.api.mapper.ModelMapperObject
 */
public class MapAToBTest {

    private ModelKnowledger knowledger = null;
    private ModelDefinitionSupport modelDefinition = new ModelDefinitionSupportJavaType();
    private ModelMapperObject mapperObject = null;

    /**
     * Map two models together and copy an existing model A instance
     *  to a new B instance based on the registered model mapping.
     */
    @Test
    public void example_simpleCopy() {
        newMapping()
                .map(ModelA.class, ModelB.class)
                .register();

        final ModelA a = new ModelA();
        a.setField5("field value 1");
        a.setField6(1);
        final ModelB bResult = this.mapperObject.map(a, ModelB.class);
        assertNotNull(bResult);
        assertNull(bResult.getField3());
        assertEquals(0, bResult.getField4());
        assertEquals("field value 1", bResult.getField5());
        assertEquals(1, bResult.getField6());
    }

    /**
     * Map two models together and use inverse copy process
     *  to copy an existing A instance to a new B instance.
     */
    @Test
    public void example_inverseSimpleCopy() {
        newMapping()
                .map(ModelA.class, ModelB.class)
                .register();

        final ModelB b = new ModelB();
        b.setField5("field value 1");
        b.setField6(1);
        final ModelA aResult = this.mapperObject.map(b, ModelA.class);
        assertNotNull(aResult);
        assertNull(aResult.getField1());
        assertEquals(0, aResult.getField2());
        assertEquals("field value 1", aResult.getField5());
        assertEquals(1, aResult.getField6());
    }

    /**
     * This example defines a translation of field values. That means field value
     *  from A model instance is copied to another model field's name
     *  of B instance.
     */
    @Test
    public void example_fieldTranslation() {
        newMapping()
                .map(ModelA.class, ModelB.class)
                .field("field1","field3")
                .register();

        final ModelA a = new ModelA();
        a.setField1("field value 0");
        a.setField5("field value 1");
        a.setField6(1);
        final ModelB bResult = this.mapperObject.map(a, ModelB.class);
        assertNotNull(bResult);
        assertEquals(0, bResult.getField4());
        assertEquals("field value 0", bResult.getField3());
        assertEquals("field value 1", bResult.getField5());
        assertEquals(1, bResult.getField6());
    }

    /**
     * This example shows an inverse translation of field values process.
     */
    @Test
    public void example_inverseFieldTranslation() {
        newMapping()
                .map(ModelA.class, ModelB.class)
                .field("field1","field3")
                .register();

        final ModelB b = new ModelB();
        b.setField3("field value 0");
        b.setField5("field value 1");
        b.setField6(1);
        final ModelA aResult = this.mapperObject.map(b, ModelA.class);
        assertNotNull(aResult);
        assertEquals(0, aResult.getField2());
        assertEquals("field value 0", aResult.getField1());
        assertEquals("field value 1", aResult.getField5());
        assertEquals(1, aResult.getField6());
    }

    /**
     * This example shows how to map both models restricted
     *  to an explicit field mapping definitions.
     */
    @Test
    public void example_onlyDefinedField() {
        newMapping()
                .map(ModelA.class, ModelB.class)
                .field("field1","field3")
                .mapOnlyDefineField()
                .register();

        final ModelA a = new ModelA();
        a.setField1("field value 0");
        a.setField5("field value 1");
        a.setField6(1);
        final ModelB bResult = this.mapperObject.map(a, ModelB.class);
        assertNotNull(bResult);
        assertEquals(0, bResult.getField4());
        assertEquals("field value 0", bResult.getField3());
        assertNull(bResult.getField5());
        assertEquals(0, bResult.getField6());
    }


    private FieldConverter<ModelType, Integer> field2ToFieldType = type -> type.ordinal();
    private FieldConverter<Integer, ModelType> typeToField2 = number -> ModelType.values()[number];

    /**
     * Map together two fields from different models and apply a conversion function.
     */
    @Test
    public void example_conversion() {

        newMapping()
                .map(ModelA.class, ModelB.class)
                .field("field1", "field3")
                .field("field2","type", field2ToFieldType, typeToField2)
                .mapOnlyDefineField()
                .register();

        final ModelA a = new ModelA();
        a.setField1("field value 0");
        a.setField5("field value 1");
        a.setField6(1);
        final ModelB bResult = this.mapperObject.map(a, ModelB.class);
        assertNotNull(bResult);
        assertEquals(0, bResult.getField4());
        assertEquals("field value 0", bResult.getField3());
        assertNull(bResult.getField5());
        assertEquals(0, bResult.getField6());
    }

    // Setup the model mapping.
    private ModelMapping newMapping() {
        this.knowledger = new ModelKnowledgerCacheMemory();
        this.mapperObject = new ModelMapperDirectMemoryAccess(knowledger);
        final ModelMapping mapping = ModelMapping.newInstance(knowledger, modelDefinition);
        return mapping;
    }

}
