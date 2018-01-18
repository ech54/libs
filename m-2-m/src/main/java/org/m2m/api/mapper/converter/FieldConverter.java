package org.m2m.api.mapper.converter;

/**
 * Contract defines the conversion between both fields 
 *  from different models.
 * That means field source <S> from <code>model A</code> will be converted 
 *  to field target <T> for <code>model B</code>.
 *  
 * @author ech
 * @param <S> The source field.
 * @param <R> The target field.
 */
@FunctionalInterface
public interface FieldConverter<S, T> {

	/**
	 * Convert a field source to a field target.
	 * @param source The source.
	 * @return The target.
	 */
	T convert(final S source);
	
}
