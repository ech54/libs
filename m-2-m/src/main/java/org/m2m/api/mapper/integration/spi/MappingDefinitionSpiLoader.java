package org.m2m.api.mapper.integration.spi;

import java.util.ServiceLoader;
import java.util.function.BiConsumer;

import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.mapper.exception.ModelMapperException;
import org.m2m.api.model.support.ModelDefinitionSupport;

public class MappingDefinitionSpiLoader {
	
	ModelKnowledger knowledger;
	ModelDefinitionSupport support;
	
	public MappingDefinitionSpiLoader(final ModelKnowledger knowledger, final ModelDefinitionSupport support) {
		this.knowledger = knowledger;
		this.support = support;
	}
	
	public void load() {
		final ServiceLoader<MappingDefinition> service = ServiceLoader.load(MappingDefinition.class);
		service.forEach(mapping->loadMapping.accept(buildFactory(knowledger, support), mapping));
	}
	
	private ModelMappingFactory buildFactory(final ModelKnowledger knowledger, final ModelDefinitionSupport support) {
		return new ModelMappingFactory() {
			@Override
			public ModelKnowledger knowledger() {
				return knowledger;
			}
			@Override
			public ModelDefinitionSupport definitionSupport() {
				return support;
			}
		};
	}
	
	final BiConsumer<ModelMappingFactory, MappingDefinition> loadMapping = 
			(factory, mapping) -> {
					try {
						mapping.mapping(factory);
					} catch(final Exception exception) {
						throw new ModelMapperException("Can't execute mapping definition.", exception);
					}
				};
}
