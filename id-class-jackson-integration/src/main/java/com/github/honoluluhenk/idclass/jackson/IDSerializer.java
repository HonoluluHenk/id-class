package com.github.honoluluhenk.idclass.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.github.honoluluhenk.idclass.ID;

@SuppressWarnings("rawtypes")
public class IDSerializer extends StdSerializer<ID> {
	private static final long serialVersionUID = -7076370540705726828L;

	public IDSerializer() {
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

		gen.writeString(value.getId().toString());
	}
}
