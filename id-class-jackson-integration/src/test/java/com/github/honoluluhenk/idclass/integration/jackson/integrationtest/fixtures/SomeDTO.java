package com.github.honoluluhenk.idclass.integration.jackson.integrationtest.fixtures;

import com.github.honoluluhenk.idclass.ID;
import lombok.Data;

@Data
public class SomeDTO {
	private ID<SomeEntity> id;
}
