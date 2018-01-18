package org.m2m.api.model;

import java.util.List;

/**
 * Model mapping definition defines all properties
 *  for the mapping process between both models.
 */
public interface ModelMappingDefinition {

    /**
     * Provides the model A.
     * @return The model A.
     */
    ModelDefinition modelA();

    /**
     * Provides the model B.
     * @return The model B.
     */
    ModelDefinition modelB();

    /**
     * Provides the list of configured mapping between
     *  model A and B.
     * @return The list of possible mapping.
     */
    List<FieldMappingDefinition> fieldMappings();
}
