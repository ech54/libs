package org.m2m.api.mapper;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * @author ECH54
 * @date Dec 19, 2017
 */
@SuppressWarnings("all")
public class UnsafeFactory {

	private static Unsafe UNSAFE = getUnsafeInstance();
	
	public static Unsafe getInstance() {
		return UNSAFE;
	}
	
	private static Unsafe getUnsafeInstance() {
		try {
			final Field unsafe = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
			unsafe.setAccessible(true);
			return (sun.misc.Unsafe) unsafe.get(null);
		} catch(final Exception exception) {
			return null;
		}
	}
}
