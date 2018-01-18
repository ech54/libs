package org.m2m.api.mapper;

import java.util.Optional;

/**
 * Service map model from instance to a class target.
 * 
 * @author ECH54
 * @date Dec 19, 2017
 */
public interface ModelMapperObject {
	
	/**
	 * Map object given in parameter to its target model.
	 * @param object The object instance.
	 * @param target The target class.
	 * @return The target instance.
	 */
	<T> T map(final Object object, final Class<T> target);

}
