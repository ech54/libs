package org.m2m.api.mapper;

import java.util.Objects;
import java.util.Optional;

import org.m2m.api.mapper.converter.ValueRules;
import org.m2m.api.mapper.exception.ModelMapperException;
import org.m2m.api.mapper.exception.Null;
import org.m2m.api.model.FieldAllocation;
import static org.m2m.api.utils.ModelUtils.*;

import org.m2m.api.utils.ModelUtils;
import sun.misc.Unsafe;

/**
 * Main implementation for the {@link ModelMapperObject}.
 *
 * The class provides operations to map two different java models.
 * That part is the engine which uses all {@link org.m2m.api.model.ModelMappingDefinition}
 *  to decide based on given instance and a target java Type how to map field values
 *  between both models.
 *
 * @author ECH54
 * @date Dec 19, 2017
 */
@SuppressWarnings("all")
public class ModelMapperDirectMemoryAccess implements ModelMapperObject {

	/**
	 * Reference on the unsafe singleton.
	 */
	private static Unsafe UNSAFE = UnsafeFactory.getInstance();

	/**
	 * Reference on the model knowledger {@link ModelKnowledger} which
	 *  contains all mapping model preferences.
	 */
	private ModelKnowledger knowledger;
	
	/**
	 * Default constructor.
	 */
	public ModelMapperDirectMemoryAccess(final ModelKnowledger knowledger) {
		this.knowledger = knowledger;
	}

	@Override
	public <T> T map(final Object object, final Class<T> target) {
		if (Objects.isNull(object)) {
			return null;
		}
		return mapModel(object, object.getClass(), target);
	}

	// Map both models based on their javaType.
	public <T> T mapModel(final Object object, final Class<?> source, final Class<T> target) {
		Object copy = null;

		try {
			copy = UNSAFE.allocateInstance(target);
			for (final FieldAllocation modelField : this.knowledger.getFields(source)) {
				Object value = UNSAFE.getObject(object, modelField.getOffset());
				if (ModelUtils.isManageableJavaType(modelField.getField().getType())) {
					final Class fieldType = modelField.getField().getType();
					value = mapModel(value, fieldType, fieldType);
				}
				if (Objects.nonNull(source)&source.isAssignableFrom(target)) {
					UNSAFE.putObject(copy, modelField.getOffset(), value);
				}
				else {
					final Optional<ValueRules> ruleTargetResult =
							this.knowledger.findTuple(modelField.getField(), direction(source, target));
					if (ruleTargetResult.isPresent()) {
						final ValueRules targetRule = ruleTargetResult.get();
						if (targetRule.mustBeCopied()) {
							if (targetRule.mustBeConverted()) {
								value = targetRule.convert(value);
							}
							UNSAFE.putObject(copy, targetRule.getOffset(), value);
						}
					}
				}
			}
		} catch(final Exception exception) {
			throw new ModelMapperException(
					String.format("Mapping operation in failure between source:'%s' to %s",
							source.getSimpleName(), target.getSimpleName()), exception);
		}
		return (Objects.nonNull(copy)?target.cast(copy):null);
	}

// TODO: to remove old code.
/*
		Object copy = null;
		try {
			copy = UNSAFE.allocateInstance(target);
			for (final FieldAllocation modelField : this.knowledger.getFields(object)) {
				Object value = UNSAFE.getObject(object, modelField.getOffset());
				if (Objects.nonNull(object)&&object.getClass().isAssignableFrom(target)) {
					UNSAFE.putObject(copy, modelField.getOffset(), value);
				}
				else {
					final Optional<ValueRules> ruleTargetResult =
							this.knowledger.findTuple(modelField.getField(), direction(object.getClass(), target));
					if (ruleTargetResult.isPresent()) {
						final ValueRules targetRule = ruleTargetResult.get();
						if (targetRule.mustBeCopied()) {
							if (targetRule.mustBeConverted()) {
								value = targetRule.convert(value);
							}
							UNSAFE.putObject(copy, targetRule.getOffset(), value);
						}
					}
				}
			}
		} catch(final Exception exception) {
			final Class<?> source = (Objects.nonNull(object)?object.getClass():Null.class);
			throw new ModelMapperException(
					String.format("Mapping operation in failure between source:'%s' to %s",
					source.getSimpleName(), target.getSimpleName()), exception);
		}
		return (Objects.nonNull(copy)?target.cast(copy):null);
*/



}
