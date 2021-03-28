package com.github.honoluluhenk.idclass.integration.jackson;

import java.util.UUID;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.github.honoluluhenk.idclass.ID;
import org.jetbrains.annotations.Nullable;

import static com.github.honoluluhenk.idclass.integration.jackson.IDDeserializer.parseIDGenericTypeArgument;
import static java.util.Objects.requireNonNull;

public class IDKeyDeserializer extends StdKeyDeserializer implements ContextualKeyDeserializer {
	private static final long serialVersionUID = -2444743523474470944L;
	private final Class<?> entityClass;

	public IDKeyDeserializer() {
		super(TYPE_UUID, ID.class);
		entityClass = null;
	}

	private IDKeyDeserializer(Class<?> entityClass) {
		super(TYPE_UUID, ID.class);
		this.entityClass = entityClass;
	}

	@Override
	public @Nullable Object deserializeKey(String key, DeserializationContext ctxt) {
		requireNonNull(entityClass);

		if (key == null) {
			return null;
		}

		ID<?> result = ID.of(UUID.fromString(key), entityClass);

		return result;
	}

	@Override
	public KeyDeserializer createContextual(
			DeserializationContext ctxt,
			BeanProperty property
	) {
		JavaType keyType = parseMapKeyType(property.getType());
		Class<?> idEntityParamClass = parseIDGenericTypeArgument(keyType);

		return new IDKeyDeserializer(idEntityParamClass);
	}

	private JavaType parseMapKeyType(JavaType type) {
		MapLikeType mapType = (MapLikeType) type;

		JavaType keyType = mapType.getKeyType();

		return keyType;
	}

}
