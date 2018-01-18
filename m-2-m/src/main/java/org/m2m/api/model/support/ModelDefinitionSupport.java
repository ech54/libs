package org.m2m.api.model.support;

import org.m2m.api.model.FieldMappingDefinition;
import org.m2m.api.model.ModelDefinition;
import org.m2m.api.model.ModelMappingDefinition;

import java.util.List;

/**
 * Support class which provides API to build <code>ModelDefinition</code>
 *  family classes.
 */
public interface ModelDefinitionSupport {

    /**
     * Builds a <code>ModelDefinition</code> based on the a java type.
     * @param javaType The given java type.
     * @return The <code>ModelDefinition</code>.
     */
    ModelDefinition build(final Class<?> javaType);

    /**
     * Builds a <code>ModelMappingDefinition</code> based on both models
     *  and a list of mapping fields.
     * Indicator drives if fields must be merged with automatic natural
     *  mapping fields for these both models.
     * @param modelA The model A.
     * @param modeB The model B.
     * @param onlyDefinedMapping If "true", it indicates that automatic field mappings
     *                              must be merged, else "false.
     * @param fields The configured mapping fields.
     * @return The <code>ModelMappingDefinition</code> result.
     */
    ModelMappingDefinition build(final ModelDefinition modelA, final ModelDefinition modeB,
                                 final boolean onlyDefinedMapping, final List<FieldMappingDefinition> fields);
}
