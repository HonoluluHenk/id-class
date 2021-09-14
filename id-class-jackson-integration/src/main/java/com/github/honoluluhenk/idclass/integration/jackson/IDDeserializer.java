package com.github.honoluluhenk.idclass.integration.jackson;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.honoluluhenk.idclass.ID;
import org.checkerframework.checker.nullness.qual.Nullable;

import static java.util.Objects.requireNonNull;

public class IDDeserializer extends StdDeserializer<ID<?>> implements ContextualDeserializer {
	private static final long serialVersionUID = -2453994657477625250L;

	private Class<?> entityClass = null;

	/**
	 * Used for registering by user/Jackson-Module
	 */
	public IDDeserializer() {
		super(ID.class);
	}

	/**
	 * Used internally by contextual creation for the actual ID.
	 */
	private IDDeserializer(Class<?> entityClass) {
		super(ID.class);

		this.entityClass = entityClass;
	}

	@Override
	public @Nullable ID<?> deserialize(
			JsonParser jsonParser,
			DeserializationContext deserializationContext
	) throws IOException {
		requireNonNull(entityClass);

		String id = jsonParser.getValueAsString();

		if (id == null) {
			return null;
		}

		ID<?> result = ID.of(UUID.fromString(id), entityClass);

		return result;
	}

	@Override
	public JsonDeserializer<?> createContextual(
			DeserializationContext ctxt,
			BeanProperty property
	) {
		JavaType idType = property.getType();
		Class<?> idEntityParamClass = parseIDGenericTypeArgument(idType);

		return new IDDeserializer(idEntityParamClass);
	}

	private Class<?> parseIDGenericTypeArgument(JavaType idType) {
		Class<?> result = TypeUtil.readGenericTypeArg(idType, 0)
				.getRawClass();

		return result;
	}
}
