package com.github.honoluluhenk.idclass.integration.jackson.integrationtest.setup;

import javax.inject.Singleton;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.honoluluhenk.idclass.integration.jackson.IDTypeModule;
import io.quarkus.jackson.ObjectMapperCustomizer;

@Singleton
@SuppressWarnings("unused")
public class TestingObjectMapperCustomizer implements ObjectMapperCustomizer {

	@Override
	public void customize(ObjectMapper objectMapper) {
		// To suppress serializing properties with null values
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// Take property name from CTOR args' name
		objectMapper.registerModule(new ParameterNamesModule());

		objectMapper.registerModule(new IDTypeModule());
	}

}

