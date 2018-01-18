package org.m2m.api.transform;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.m2m.api.ModelA;
import org.m2m.api.ModelB;
import sun.misc.Unsafe;
public class TransformerTest {

	private static Unsafe UNSAFE = getUnsafeInstance();
	
	public static void main(String[] args) throws Exception {
		final TransformerTest test = new TransformerTest();
		
		test.execute();
	}
	
	public void execute() throws Exception{
		
//		String password = new String("l00k@myHor$e");
//		String fake = new String(password.replaceAll(".", "?"));
//		System.out.println(password); // l00k@myHor$e
//		System.out.println(fake); // ????????????
//		 
//		UNSAFE.copyMemory(fake, 0L, null, toAddress(password), sizeOf(password));
//		 
//		System.out.println(password); // ????????????
//		System.out.println(fake); // ????????????
		
		
		final ModelA modelA = initModelA();
		final ModelA clone = new ModelA();
		final ModelB instance = (ModelB) UNSAFE.allocateInstance(ModelB.class);
//		ModeB.class.getFields()
		
		final Map<Field, Long> modelAOffsets = getObjectFieldOffsets(ModelA.class);
		final Map<Field, Long> modelBOffsets = getObjectFieldOffsets(ModelB.class);
		long offsetFA = modelAOffsets.entrySet().iterator().next().getValue();
		long offsetFB = modelBOffsets.entrySet().iterator().next().getValue();
		Object value = UNSAFE.getObject(modelA, offsetFA);
		long sizeOf = sizeOf(value);
		
		System.out.println(modelA);
		System.out.println(instance);
//		UNSAFE.copyMemory(toAddress(modelA)+offsetFA, toAddress(instance)+offsetFB, sizeOf);
		
		UNSAFE.putObject(instance, offsetFB, value);
//		UNSAFE.setMemory(arg0, arg1, arg2, arg3);
		
//		UNSAFE.copyMemory(modelA, offsetFA, 
//				instance, offsetFB, sizeOf);
		
//		UNSAFE.copyMemory(arg0, arg1, arg2);
		
//		System.out.println(instance.getField3());
		System.out.println(instance);
		//;Memory(arg0, arg1, arg2);
		
		//TODO
		// 1/ UNSAFE.allocMemory(size) => size of field (dependends of type)
		// 2/ knows all offset for each fields of target class
		// 3/ start = location of data to copied
		// 4/ address = location where to copy data
		// 5/ size of field.
		// UNSAFE.copyMemory(start, address, size)
		
	}
	
	private Map<Field, Long> getObjectFieldOffsets(final Class<?> cl) {
		final Map<Field, Long> offsets = new HashMap<Field, Long>();
		for (final Field f : cl.getDeclaredFields()) {
			offsets.put(f, UNSAFE.objectFieldOffset(f));
		}
		return offsets;
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

	
	private long toAddress(final Object object) {
		Object[] array = {object};
		final int offset = UNSAFE.arrayBaseOffset(Object[].class);
		return normalize(UNSAFE.getInt(array, offset));
	}
	
	private long simplifiedSizeOf(Object object) {
		return UNSAFE.getAddress(normalize(UNSAFE.getInt(object, 4L))+12L);
	}
	
	private long normalize(int value) {
		if (value>0) return value;
		return (~0L>>>32)&value;
	}
	
	private static Unsafe getUnsafeInstance() {
		try {
			Field unsafe = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
			unsafe.setAccessible(true);
			return (sun.misc.Unsafe) unsafe.get(null);
		} catch(final Exception exception) {
			//TODOs
			return null;
		}
	}
	
	private ModelA initModelA() {
		final ModelA a = new ModelA();
		a.setField1("field1");
		a.setField2(1);
		return a;
	}
	private ModelB initModelB() {
		final ModelB b = new ModelB();
		b.setField3("field3");
		b.setField4(2);
		return b;
	}
	
}
