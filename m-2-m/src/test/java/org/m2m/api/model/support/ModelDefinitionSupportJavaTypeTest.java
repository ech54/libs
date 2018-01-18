package org.m2m.api.model.support;

import org.junit.Test;
import org.m2m.api.ModelA;
import org.m2m.api.ModelB;
import org.m2m.api.mapper.UnsafeFactory;
import org.m2m.api.mapping.Direction;
import org.m2m.api.model.FieldMappingDefinition;
import org.m2m.api.model.ModelDefinition;
import org.m2m.api.model.ModelMappingDefinition;
import static org.m2m.api.utils.ModelUtils.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ModelDefinitionSupportJavaTypeTest {

    @Test
    public void build_ModelDefinition() throws Exception{
        final ModelDefinition definition = new ModelDefinitionSupportJavaType().build(ModelA.class);
        assertEquals(ModelA.class, definition.getModelType());

        definition.getFieldDefinitions().stream().forEach(System.out::println);
        assertEquals(2, definition.getFieldDefinitions().size());

        assertEquals("field1", definition.getFieldDefinitions().get(0).getField().getName());
        assertEquals(String.class, definition.getFieldDefinitions().get(0).getField().getType());
        assertEquals(offset(ModelA.class.getDeclaredField("field1")), definition.getFieldDefinitions().get(0).getOffset());

        assertEquals("field2", definition.getFieldDefinitions().get(1).getField().getName());
        assertEquals(int.class, definition.getFieldDefinitions().get(1).getField().getType());
        assertEquals(offset(ModelA.class.getDeclaredField("field2")), definition.getFieldDefinitions().get(1).getOffset());
    }

    private Long offset(Field field) {
        return UnsafeFactory.getInstance().objectFieldOffset(field);
    }

    @Test
    public void build_ModelMappingDefinition() throws Exception {
        final Direction a_b = direction(ModelA.class, ModelB.class);
        final Direction b_a = direction(ModelB.class, ModelA.class);

        final List<FieldMappingDefinition> fieldMapping = new ArrayList<>();
        fieldMapping.add(new FieldMappingDefinition(a_b,"field1", "field3"));
        fieldMapping.add(new FieldMappingDefinition(b_a,"field2", "field4"));
        final ModelMappingDefinition definition = new ModelDefinitionSupportJavaType()
                .build(model(ModelA.class), model(ModelB.class), true, fieldMapping);

        assertEquals(ModelA.class, definition.modelA().getModelType());
        assertEquals(ModelB.class, definition.modelB().getModelType());
        assertEquals(2, definition.fieldMappings().size());

    }

    private ModelDefinition model(final Class<?> javaType) {
        return new ModelDefinitionSupportJavaType().build(javaType);
    }
}
