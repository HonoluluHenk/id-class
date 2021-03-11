package com.github.honoluluhenk.idclass.jackson.integrationtest.fixtures;

import com.github.honoluluhenk.idclass.ID;
import lombok.Value;

@Value
public class SomeDTO {
	private final ID<SomeEntity> id;

	// bug in Jackson ParameterNamesModule: it cannot construct beans using CTOR and property-names if there is only
	// one property.
	private final String jacksonBug;
}
