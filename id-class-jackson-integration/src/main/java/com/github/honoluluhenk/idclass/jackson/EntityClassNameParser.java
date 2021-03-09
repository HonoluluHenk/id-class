package com.github.honoluluhenk.idclass.jackson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.github.honoluluhenk.idclass.AbstractID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Getter
class EntityClassNameParser implements Serializable {
	private static final long serialVersionUID = 1964409785023570224L;

	@SuppressWarnings("rawtypes")
	private final Class<? extends AbstractID> entityClass;

	public Class<?> entityClassOf(Type type) {
		ParameterizedType paramType = validParameterizedType(type);

		// parameterized types do have at least one typeArgument
		// ... or else they wouldn't be ParameterizedTypes but just simple Classes.
		@SuppressWarnings("unchecked")
		Class<AbstractID<?, ?>> entityClass = (Class<AbstractID<?, ?>>) paramType.getActualTypeArguments()[0];
		requireNonNull(entityClass, String.format(
				"%s does not have a generic class parameter????",
				entityClass.getSimpleName()));

		return entityClass;
	}

	private ParameterizedType validParameterizedType(Type type) {
		if (!ParameterizedType.class.isAssignableFrom(type.getClass())) {
			if (!entityClass.isAssignableFrom((Class<?>) type)) {
				// just for a better error message
				throw illegalType(type);
			}

			throw new IllegalArgumentException(String.format(
					"%s required but got: %s",
					ParameterizedType.class.getName(), type.getClass().getName()));
		}

		ParameterizedType parameterizedType = (ParameterizedType) type;

		if (!entityClass.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
			// just for a better error message
			throw illegalType(type);
		}

		return parameterizedType;
	}

	private IllegalArgumentException illegalType(Type type) {
		return new IllegalArgumentException(String.format("Can only parse class %s<?> but got: %s",
				entityClass.getName(), type.getTypeName()));
	}

}
