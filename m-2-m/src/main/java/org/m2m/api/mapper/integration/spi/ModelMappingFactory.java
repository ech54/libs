package org.m2m.api.mapper.integration.spi;

import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.mapping.ModelMapping;
import org.m2m.api.model.support.ModelDefinitionSupport;

/**
 * @author Emilien Charton
 */
public interface ModelMappingFactory {
	
	/**
	 * Provides a new {@link ModelMapping} based on {@link ModelKnowledger}
	 *  and {@link ModelDefinitionSupport} classes.
	 * @param modelA The A model.
	 * @param modelB the B model.
	 * @return The new instance.
	 */
	default ModelMapping map(final Class<?> modelA, final Class<?> modelB) {
		return ModelMapping.newInstance(knowledger(), definitionSupport()).map(modelA, modelB);
	}
	
	ModelKnowledger knowledger();
	ModelDefinitionSupport definitionSupport();
}
