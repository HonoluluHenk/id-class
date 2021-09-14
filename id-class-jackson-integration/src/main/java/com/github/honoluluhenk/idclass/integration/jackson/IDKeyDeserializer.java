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
import org.checkerframework.checker.nullness.qual.Nullable;

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
		JavaType idType;
		if (property == null) {
			// direct method parameter, e.g. public void foo(Map<ID<Foo>, Foo> fooMap)
			idType = ctxt.getContextualType();
		} else {
			idType = property.getType();
		}

		Class<?> idEntityParamClass = parseIDGenericTypeArgument(idType);

		return new IDKeyDeserializer(idEntityParamClass);
	}

	private Class<?> parseIDGenericTypeArgument(JavaType type) {
		if (!(type instanceof MapLikeType)) {
			throw new IllegalArgumentException("ID.class currently only supports Maps-keys but got: " + type);
		}
		MapLikeType mapType = (MapLikeType) type;

		Class<?> result = TypeUtil.readGenericTypeArg(mapType.getKeyType(), 0)
				.getRawClass();

		return result;
	}

}
