package com.github.honoluluhenk.idclass.jackson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.github.honoluluhenk.idclass.AbstractID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EntityClassNameParser implements Serializable {
	private static final long serialVersionUID = 1964409785023570224L;

	@SuppressWarnings("rawtypes")
	private final Class<? extends AbstractID> entityClass;

	public Class<?> entityClassFrom(Type type) {
		ParameterizedType idType = validParameterizedIDType(type);

		Class<?> entityTypeParam = validEntityTypeParam(idType);

		return entityTypeParam;
	}

	private Class<?> validEntityTypeParam(ParameterizedType idType) {
		// parameterized types do have at least one typeArgument
		// ... or else they wouldn't be ParameterizedTypes but just simple Classes :)
		Type entityParamType = idType.getActualTypeArguments()[0];

		if (ParameterizedType.class.isAssignableFrom(entityParamType.getClass())) {
			Class<?> result = (Class<?>) ((ParameterizedType) entityParamType).getRawType();
			return result;
		}

		if (Class.class.isAssignableFrom(entityParamType.getClass())) {
			Class<?> result = (Class<?>) entityParamType;
			return result;
		}

		// e.g.: GenericArrayType
		throw entityParamTypeNotImplemented(entityParamType);
	}

	private ParameterizedType validParameterizedIDType(Type type) {
		if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
			ParameterizedType parameterizedType = (ParameterizedType) type;

			if (entityClass.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
				return parameterizedType;
			}

			throw illegalType(type);
		}

		if (entityClass.isAssignableFrom((Class<?>) type)) {
			throw rawType();
		}

		throw illegalType(type);

	}

	private IllegalArgumentException entityParamTypeNotImplemented(Type entityParamType) {
		String message = String.format("Not yet implemented: %s = %s", entityParamType.getClass(), entityParamType);
		IllegalArgumentException ex = new IllegalArgumentException(message);
		return ex;
	}

	private IllegalArgumentException rawType() {
		String message = String.format(
				"%s required with generic type arguments but only got raw type",
				entityClass.getName());
		IllegalArgumentException ex = new IllegalArgumentException(message);
		return ex;
	}

	private IllegalArgumentException illegalType(Type type) {
		String message = String.format("Can only parse class %s<?> but got: %s",
				entityClass.getName(), type.getTypeName());
		return new IllegalArgumentException(message);
	}

}
