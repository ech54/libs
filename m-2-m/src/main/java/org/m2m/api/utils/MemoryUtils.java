package org.m2m.api.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import org.m2m.api.mapper.UnsafeFactory;
import sun.misc.Unsafe;

/**
 * @author ECH54
 * @date Dec 19, 2017
 */
@SuppressWarnings("all")
public class MemoryUtils {

	final static private Unsafe UNSAFE = UnsafeFactory.getInstance();
	
	public static long toAddress(final Object object) {
		Object[] array = {object};
		final int offset = UNSAFE.arrayBaseOffset(Object[].class);
		return normalize(UNSAFE.getInt(array, offset));
	}
	
	public static long normalize(int value) {
		if (value>0) return value;
		return (~0L>>>32)&value;
	}
	
	public static long sizeOf(Object o) {
	    Unsafe u = UNSAFE;
	    if (o==null) {
	    	return 0L;
	    }
	    HashSet<Field> fields = new HashSet<Field>();
	    Class c = o.getClass();
	    while (c != Object.class) {
	        for (Field f : c.getDeclaredFields()) {
	            if ((f.getModifiers() & Modifier.STATIC) == 0) {
	                fields.add(f);
	            }
	        }
	        c = c.getSuperclass();
	    }

	    // get offset
	    long maxSize = 0;
	    for (Field f : fields) {
	        long offset = u.objectFieldOffset(f);
	        if (offset > maxSize) {
	            maxSize = offset;
	        }
	    }

	    return ((maxSize/8) + 1) * 8;   // padding
	}
}
