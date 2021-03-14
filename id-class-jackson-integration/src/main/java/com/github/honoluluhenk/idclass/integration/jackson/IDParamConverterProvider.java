package com.github.honoluluhenk.idclass.integration.jackson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.UUID;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.github.honoluluhenk.idclass.ID;
import com.github.honoluluhenk.idclass.integration.EntityClassNameParser;

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
		if (!isApplicable(rawType)) {
			return null;
		}

		Class<?> entityClass = new EntityClassNameParser(ID.class)
				.entityClassFrom(genericType);

		ParamConverter<T> result = (ParamConverter<T>) new IDParamConverter(entityClass);

		return result;
	}

	private <T> boolean isApplicable(Class<T> rawType) {
		return rawType.equals(ID.class);
	}

	private static final class IDParamConverter implements ParamConverter<ID<?>> {
		private final Class<?> entityClass;

		@SuppressWarnings("CdiInjectionPointsInspection")
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
