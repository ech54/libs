package org.m2m.api.utils;

import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.mapping.Direction;
import org.m2m.api.mapping.DirectionDefinition;
import org.m2m.api.model.FieldAllocation;
import org.m2m.api.model.FieldMappingDefinition;
import org.m2m.api.model.ModelDefinition;
import org.m2m.api.model.support.ModelDefinitionSupport;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class ModelUtils {

    public static BiPredicate<FieldMappingDefinition, FieldMappingDefinition> MATCHING_FIELD_DEFINITION =
            (f1, f2) -> f1.equals(f2);

    @Deprecated
    //@Deprecated : use directly DirectionDefinition constructor.
    public static Direction direction(final Class<?> from, final Class<?> to) {
        return new DirectionDefinition(from, to);
    }

    public static boolean isFieldExisting(final FieldAllocation field, final List<FieldAllocation> fields) {

        if (Objects.isNull(fields)||fields.isEmpty()
                ||Objects.isNull(field)
                ||Objects.isNull(field.getFieldName())) {
            return false;
        }

        return fields.stream()
                .filter(f->f.getFieldName().equalsIgnoreCase(field.getFieldName()))
                .count()==1;
    }

    public static boolean equivalent(final Field source, final Field target) {
        if (Objects.isNull(source)||Objects.isNull(target)) {
            return false;
        }
        return source.getName().equalsIgnoreCase(target.getName())
                && source.getType().equals(target.getType());
    }

    public static boolean isManageableField(final FieldAllocation field) {
        return Objects.nonNull(field.getField())&&
                isManageableJavaType(field.getField().getType());
    }

    public static boolean isManageableJavaType(final Class<?> javaType) {
        return Objects.nonNull(javaType)&&!javaType.isPrimitive()
                &&!(javaType.equals(String.class));
    }

    public static FieldMappingDefinition toFieldMapping(final FieldAllocation allocation) {
        return new FieldMappingDefinition(
                new DirectionDefinition(allocation.getField().getType(),allocation.getField().getType()),
                allocation.getFieldName());
    }
/*
    public static void registerChildrenModels(final FieldAllocation f,
                                             final ModelKnowledger knowledger,
                                             final ModelDefinitionSupport definitionSupport) {
        final Class<?> javaType = f.getField().getType();
        final ModelDefinition modelDefinition = definitionSupport.build(javaType);
        final List<FieldMappingDefinition> definitions = modelDefinition.getFieldDefinitions().stream()
                .map(ModelUtils::toFieldMapping)
                .collect(Collectors.toList());
        knowledger.register(definitionSupport.build(
                modelDefinition, modelDefinition, false, definitions));
    }
   */
}
