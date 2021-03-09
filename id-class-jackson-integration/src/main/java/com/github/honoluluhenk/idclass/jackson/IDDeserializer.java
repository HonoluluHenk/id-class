package com.github.honoluluhenk.idclass.jackson;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.honoluluhenk.idclass.ID;
import lombok.var;

import static java.util.Objects.requireNonNull;

public class IDDeserializer extends StdDeserializer<ID<?>> implements ContextualDeserializer {
	private static final long serialVersionUID = -2453994657477625250L;

	private Class<?> entityClass = null;

	public IDDeserializer() {
		super(ID.class);
	}

	private IDDeserializer(Class<?> entityClass) {
		super(ID.class);

		this.entityClass = entityClass;
	}

	@Override
	public ID<?> deserialize(
			JsonParser jsonParser,
			DeserializationContext deserializationContext
	) throws IOException {
		requireNonNull(entityClass);

		String id = jsonParser.getValueAsString();

		if (id == null) {
			return null;
		}

		var result = ID.of(UUID.fromString(id), entityClass);

		return result;
	}

	@Override
	public JsonDeserializer<?> createContextual(
			DeserializationContext ctxt,
			BeanProperty property
	) {
		var idContentClass = property.getType().getBindings().getTypeParameters().get(0).getRawClass();

		return new IDDeserializer(idContentClass);
	}
}
