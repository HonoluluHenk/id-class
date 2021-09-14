package com.github.honoluluhenk.idclass.integration.jackson.integrationtest.setup;

import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.honoluluhenk.idclass.integration.jackson.IDTypeModule;
import io.quarkus.jackson.ObjectMapperCustomizer;

@Singleton
@SuppressWarnings("unused")
public class TestingObjectMapperCustomizer implements ObjectMapperCustomizer {

	@Override
	public void customize(ObjectMapper objectMapper) {
		objectMapper.registerModule(new IDTypeModule());
	}

}

