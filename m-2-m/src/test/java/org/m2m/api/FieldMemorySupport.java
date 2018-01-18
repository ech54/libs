package org.m2m.api;

import org.m2m.api.model.FieldAllocation;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FieldMemorySupport {

    public static final FieldAllocation field(String name, Class<?> javaType, long offset) {

        final FieldAllocation allocation = mock(FieldAllocation.class);
        when(allocation.getFieldName()).thenReturn(name);
        when(allocation.getOffset()).thenReturn(offset);
        Field field;
        try  {
            field = javaType.getDeclaredField(name);
        } catch(final Exception exception) {
            throw new RuntimeException(exception);
        }

        when(allocation.getField()).thenReturn(field);
        return allocation;
    }

}
