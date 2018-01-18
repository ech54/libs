package org.m2m.api.mapper;

import org.m2m.api.mapper.converter.SimpleCopy;
import org.m2m.api.mapper.converter.TranslationAndConversion;
import org.m2m.api.mapper.converter.ValueRules;
import org.m2m.api.mapping.Direction;
import org.m2m.api.mapping.DirectionDefinition;
import org.m2m.api.model.FieldAllocation;
import org.m2m.api.model.FieldMappingDefinition;
import org.m2m.api.model.ModelDefinition;
import org.m2m.api.model.ModelMappingDefinition;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Memory implementation of the @{@link ModelKnowledger} interface.
 */
public class ModelKnowledgerCacheMemory implements ModelKnowledger {

    /**
     * Reference on the model definitions ordered by javaType.
     */
    private Map<Class<?>, ModelDefinition> registerModelDefinitions = new HashMap<>();

    /**
     * Reference on the mapping definitions ordered by @{@link Direction}.
     */
    private Map<Direction, List<FieldMappingDefinition>> registerMappingDefinitions = new HashMap<>();

    /**
     * Register a new model @{@link ModelMappingDefinition}.
     * @param mapping The model mapping definition.
     */
    @Override
    public void register(final ModelMappingDefinition mapping) {
        mergeMappingField(directionFromTo(mapping), mapping);
    }

    /**
     * Merge <code>FieldMappingDefinition</code> list from initial mapping with the current
     *  field model mapping already present in applicative cache.
     * @param direction The model direction.
     * @param mapping The current mapping model.
     */
    private void mergeMappingField(final Direction direction,
                                   final ModelMappingDefinition mapping) {
        final List<FieldMappingDefinition> mappingFields = getMappingForDirection(direction);
        List<FieldMappingDefinition> expectedMappingFields = mapping.fieldMappings()
                                                                        .stream()
                                                                        .filter(m->m.getDirection().equals(direction))
                                                                        .collect(Collectors.toList());
        mappingFields.addAll(expectedMappingFields);
        if (Objects.nonNull(mappingFields)&&!mappingFields.isEmpty()) {
            this.registerMappingDefinitions.put(direction, mappingFields);
        }
    }

    /**
     * Fetch from cache the existing <code>{@link FieldMappingDefinition}</code> else return
     *  a new instance.
     * @param direction The direction.
     * @return The list of field model definition.
     */
    private List<FieldMappingDefinition> getMappingForDirection(final Direction direction) {
        List<FieldMappingDefinition> mappingFields = this.registerMappingDefinitions.get(direction);
        if (Objects.isNull(mappingFields)) {
            mappingFields = new ArrayList<>();
        }
        return mappingFields;
    }

    // Build a direction instance initialized from A to B models.
    private Direction directionFromTo(final ModelMappingDefinition mapping) {
        return new DirectionDefinition(mapping.modelA().getModelType(), mapping.modelB().getModelType());
    }

    /**
     * Register in cache the current model definition.
     * @param definition The model definition.
     */
    @Override
    public void register(ModelDefinition definition) {
        if (this.registerModelDefinitions.containsKey(definition.getModelType())) {
            return;
        }
        this.registerModelDefinitions.put(definition.getModelType(), definition);
    }

    /**
     * Retrieve all field descriptions link to an instance given in parameter.
     * @param object The object.
     * @return The list of field descriptions.
     */
    @Override
    public List<FieldAllocation> getFields(final Object object) {

        if (Objects.isNull(object)) {
            return Collections.emptyList();
        }
        final ModelDefinition definition = this.registerModelDefinitions.get(object.getClass());
        return Objects.isNull(definition)?Collections.emptyList():definition.getFieldDefinitions();
    }

    public List<FieldAllocation> getFields(final Class<?> javaType) {
        if (Objects.isNull(javaType)) {
            return Collections.emptyList();
        }
        final List<FieldAllocation> fieldResults = new ArrayList<>();
        final ModelDefinition definition = this.registerModelDefinitions.get(javaType);
        if (Objects.nonNull(definition)) {
            fieldResults.addAll(definition.getFieldDefinitions());
        }
        final Optional<ModelDefinition> superclassDefinition = getSuperclassFields(javaType);
        if (superclassDefinition.isPresent()) {
            fieldResults.addAll(superclassDefinition.get().getFieldDefinitions());
        }
        return fieldResults;
    }

    protected Optional<ModelDefinition> getSuperclassFields(final Class<?> javaType) {
        Class<?> superClass = javaType.getSuperclass();
        final ModelDefinition superClassDefinition = this.registerModelDefinitions.get(superClass);
        return (superClassDefinition==null?Optional.empty():Optional.of(superClassDefinition));
    }

    /**
     * Find a tuple {@link Field, @link Direction} from the memory cache. The field comes
     *  from the java class implementation model, and the direction indicates the source
     *  and target javaType.
     * @param field The field to search.
     * @param direction The class direction from->to.
     * @return The optional {@link ValueRules} which could be applied during the process
     *  to copy field from model A to field from model B.
     */
    @Override
    public Optional<ValueRules> findTuple(final Field field, final Direction direction) {

        if (Objects.isNull(direction)||Objects.isNull(field)) {
            return Optional.empty();
        }
        final Direction adaptDirection = lookingForSuperclassNodeDirection(field, direction);
        final Optional<FieldMappingDefinition> result = findMappingDefinition(adaptDirection, field.getName());

        if (!result.isPresent()) {
            return Optional.empty();
        }
        final FieldMappingDefinition mapping = result.get();
        final Optional<FieldAllocation> targetAllocation = findFieldDescription(adaptDirection.getTo(), mapping.getTarget());

        return !targetAllocation.isPresent() ? Optional.empty():
                (!mapping.getConverter().isPresent() ?
                        Optional.of(new SimpleCopy(targetAllocation.get())):
                        Optional.of(new TranslationAndConversion(targetAllocation.get(), mapping.getConverter().get())));
    }

    /**
     * Walk to both node of direction (from, to) to check
     * if a superclass mapping could be used to resolve the field mapping.
     * @param field The field to map.
     * @param direction The initial direction.
     * @return Could be the initial direction or a new one
     *  based on direction's node mapping.
     */
    private Direction lookingForSuperclassNodeDirection(final Field field, final Direction direction) {

        final Class<?> declaringClass = field.getDeclaringClass();
        if (declaringClass!=direction.getTo()) {
            if (direction.getFrom().getSuperclass()==declaringClass) {
                // field is defined into the superclass
                final Direction fromSuperAndToChildNodes = new DirectionDefinition(direction.getFrom().getSuperclass(), direction.getTo());
                if (this.registerMappingDefinitions.containsKey(fromSuperAndToChildNodes)) {
                    return fromSuperAndToChildNodes;
                }
                final Direction fromAndToSuperNodes = new DirectionDefinition(direction.getFrom().getSuperclass(), direction.getTo().getSuperclass());
                if (this.registerMappingDefinitions.containsKey(fromAndToSuperNodes)) {
                    return fromAndToSuperNodes;
                }
            }
        }
        return direction;
    }


    /**
     * Fetch based on the model and field name the {@link FieldAllocation}.
     * @param model The java model.
     * @param fieldName The field name.
     * @return The optional {@link FieldAllocation}.
     */
    private Optional<FieldAllocation> findFieldDescription(final Class<?> model, final String fieldName) {
        if (Objects.isNull(model)||Objects.isNull(fieldName)) {
            return Optional.empty();
        }
        return this.registerModelDefinitions.get(model)
                .getFieldDefinitions().stream()
                .filter(description->description.getFieldName()
                .equalsIgnoreCase(fieldName))
                .findFirst();
    }

    /**
     * Fetch based on the {@link Direction} and field name the {@link FieldAllocation}.
     * @param direction The direction.
     * @param fieldName The field name.
     * @return The optional {@link FieldMappingDefinition}.
     */
    private Optional<FieldMappingDefinition> findMappingDefinition(final Direction direction, final String fieldName) {
        if (Objects.isNull(direction)||Objects.isNull(fieldName)) {
            return Optional.empty();
        }

        final List<FieldMappingDefinition> mappings = this.registerMappingDefinitions.get(direction);
        if (Objects.isNull(mappings)) {
            return Optional.empty();
        }
        return mappings.stream()
                .filter(m->m.getSource()
                        .equalsIgnoreCase(fieldName))
                .findFirst();

    }
}