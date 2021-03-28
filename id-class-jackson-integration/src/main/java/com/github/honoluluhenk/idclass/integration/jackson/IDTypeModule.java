package com.github.honoluluhenk.idclass.integration.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.honoluluhenk.idclass.ID;

public class IDTypeModule extends SimpleModule {

	private static final long serialVersionUID = 8597299888384245378L;

	public IDTypeModule() {
		addSerializer(ID.class, new IDSerializer());
		addDeserializer(ID.class, new IDDeserializer());
		addKeySerializer(ID.class, new IDKeySerializer());
		addKeyDeserializer(ID.class, new IDKeyDeserializer());
	}
}
