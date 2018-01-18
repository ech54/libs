package org.m2m.api.mapper.exception;

/**
 * @author ECH54
 * @date Dec 20, 2017
 */
public class ModelMapperException extends RuntimeException {

	public ModelMapperException() {
		super();
	}
	
	public ModelMapperException(final String message) {
		super(message);
	}
	
	public ModelMapperException(final Throwable throwable) {
		super(throwable);
	}
	
	public ModelMapperException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
