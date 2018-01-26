package org.m2m.api.mapper.integration.spi;

import org.m2m.api.ModelA;
import org.m2m.api.ModelB;

public class SampleMappingDefinition implements MappingDefinition {
	
	@Override
	public void mapping(ModelMappingFactory factory) {
		factory
			.map(ModelA.class, ModelB.class)
		.register();
	}
}
