package org.m2m.api.model.support;

import org.m2m.api.mapper.UnsafeFactory;
import org.m2m.api.model.FieldAllocation;
import org.m2m.api.model.FieldMappingDefinition;
import org.m2m.api.model.ModelDefinition;
import org.m2m.api.model.ModelMappingDefinition;
import static org.m2m.api.utils.ModelUtils.*;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class ModelDefinitionSupportJavaType implements ModelDefinitionSupport {

    final static private Unsafe UNSAFE = UnsafeFactory.getInstance();

    @Override
    public ModelDefinition build(final Class<?> javaType) {

        if (Objects.isNull(javaType)) {
            throw new IllegalArgumentException("Java type argument can't be null.");
        }
        return model(javaType, Arrays.asList(javaType.getDeclaredFields())
                                        .stream()
                                        .map(f->  field(f, UNSAFE.objectFieldOffset(f)))
                                        .collect(Collectors.toList())
        );
    }

    private ModelDefinition model(final Class<?> type, final List<FieldAllocation> fields) {
        return new ModelDefinition() {
            @Override
            public Class<?> getModelType() { return type; }

            @Override
            public List<FieldAllocation> getFieldDefinitions() { return fields; }
        };
    }

    private FieldAllocation field(final Field field, final long allocation) {
        return new FieldAllocation() {
            @Override
            public String getFieldName() { return field.getName(); }

            @Override
            public Field getField() { return field; }

            @Override
            public Long getOffset() { return allocation; }
        };
    }

    @Override
    public ModelMappingDefinition build(final ModelDefinition A,
                                        final ModelDefinition B,
                                        final boolean onlyDefinedMapping,
                                        final List<FieldMappingDefinition> fields) {
        if (onlyDefinedMapping) {
            return mapping(A,B,fields);
        }
        final List<FieldMappingDefinition> sameFields = mergeSameFields(A, B);
        final List<FieldMappingDefinition> mergeFields = fields.subList(0, fields.size());
        for(final FieldMappingDefinition sameField : sameFields) {
            if (fields.stream()
                    .filter(f->MATCHING_FIELD_DEFINITION.test(f, sameField))
                    .count()==0) {
                mergeFields.add(sameField);
            }
        }
        return mapping(A, B, mergeFields);
    }

    private List<FieldMappingDefinition> mergeSameFields(final ModelDefinition A, final ModelDefinition B) {
        final List<FieldMappingDefinition> results = new ArrayList<>();
        A.getFieldDefinitions().stream().forEach(aField->{
            B.getFieldDefinitions().stream().forEach(bField->{
                if (equivalent(aField.getField(), bField.getField())) {
                    results.add(new FieldMappingDefinition(direction(A.getModelType(), B.getModelType()),
                            aField.getFieldName()));
                    results.add(new FieldMappingDefinition(direction(B.getModelType(),A.getModelType()),
                            aField.getFieldName()));
                }
            });
        });
        return results;
    }

    private ModelMappingDefinition mapping(final ModelDefinition A,
                                    final ModelDefinition B,
                                    final List<FieldMappingDefinition> fields) {

        return new ModelMappingDefinition() {
            @Override
            public ModelDefinition modelA() { return A; }

            @Override
            public ModelDefinition modelB() { return B; }

            @Override
            public List<FieldMappingDefinition> fieldMappings() { return fields; }
        };
    }

}
