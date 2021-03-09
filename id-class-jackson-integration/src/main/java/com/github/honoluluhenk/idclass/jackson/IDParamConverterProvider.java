package com.github.honoluluhenk.idclass.jackson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.UUID;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.github.honoluluhenk.idclass.ID;
import lombok.var;

import static java.util.Objects.requireNonNull;

@Provider
public class IDParamConverterProvider implements ParamConverterProvider {

	@Override
	@SuppressWarnings("unchecked")
	public <T> ParamConverter<T> getConverter(
			Class<T> rawType,
			Type genericType,
			Annotation[] annotations
	) {
		if (!rawType.equals(ID.class)) {
			return null;
		}

		var entityClass = new EntityClassNameParser(ID.class)
				.entityClassOf(genericType);

		ParamConverter<T> result = (ParamConverter<T>) new IDParamConverter(entityClass);

		return result;
	}

	private static final class IDParamConverter implements ParamConverter<ID<?>> {
		private final Class<?> entityClass;

		private IDParamConverter(Class<?> entityClass) {
			this.entityClass = requireNonNull(entityClass);
		}

		@Override
		public ID<?> fromString(String value) {
			if (value == null) {
				return null;
			}

			ID<?> id = ID.of(UUID.fromString(value), entityClass);
			return id;
		}

		@Override
		public String toString(ID<?> value) {
			if (value == null) {
				return null;
			}

			return value.getId().toString();
		}
	}
}
