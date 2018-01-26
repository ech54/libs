package org.m2m.api.mapper.integration.spi;

import org.junit.Test;
import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.mapper.ModelKnowledgerCacheMemory;
import org.m2m.api.model.support.ModelDefinitionSupport;
import org.m2m.api.model.support.ModelDefinitionSupportJavaType;

public class SpiLoaderTest {
	
    private ModelKnowledger knowledger = new ModelKnowledgerCacheMemory();
    private ModelDefinitionSupport support = new ModelDefinitionSupportJavaType();
//    private ModelDefinitionSupport modelDefinition = new ModelDefinitionSupportJavaType();

    @Test
    public void loadSpis() {
    	
    	final MappingDefinitionSpiLoader loader = new MappingDefinitionSpiLoader(knowledger, support);
    	loader.load();
    	
    }
    
    
}
