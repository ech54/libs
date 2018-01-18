package org.m2m.api.mapping;

import org.junit.Test;
import org.m2m.api.ModelA;
import org.m2m.api.ModelB;
import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.model.support.ModelDefinitionSupport;

import static org.mockito.Mockito.*;
public class ModelMappingTest {

    @Test
    public void newInstance() {
        final ModelKnowledger knowledger = mock(ModelKnowledger.class);
        final ModelDefinitionSupport definitionSupport = mock(ModelDefinitionSupport.class);
        final ModelMapping mapping = ModelMapping.newInstance(knowledger, definitionSupport);
        mapping.map(ModelA.class, ModelB.class).register();
        verify(definitionSupport).build(ModelA.class);
        verify(definitionSupport).build(ModelB.class);
    }

    /*
    // stubbing appears before the actual execution
    when(mockedList.get(0)).thenReturn("first");


     //using mock object
     mockedList.add("one");
     mockedList.clear();

     //verification
     verify(mockedList).add("one");
     verify(mockedList).clear();

     */
}
