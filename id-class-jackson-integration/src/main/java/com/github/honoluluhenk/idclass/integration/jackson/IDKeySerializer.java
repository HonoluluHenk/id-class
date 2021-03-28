package com.github.honoluluhenk.idclass.integration.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.github.honoluluhenk.idclass.ID;

@SuppressWarnings("rawtypes")
public class IDKeySerializer extends StdSerializer<ID> {
	private static final long serialVersionUID = -7076370540705726828L;

	public IDKeySerializer() {
		super(SimpleType.constructUnsafe(ID.class));
	}

	@Override
	public void serialize(
			ID value,
			JsonGenerator gen,
			SerializerProvider provider
	) throws IOException {
		if (value == null) {
			return;
		}

		String serialized = value.getId().toString();
		gen.writeFieldName(serialized);
	}
}
