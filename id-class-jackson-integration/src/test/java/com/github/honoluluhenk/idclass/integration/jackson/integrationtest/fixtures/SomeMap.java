package com.github.honoluluhenk.idclass.integration.jackson.integrationtest.fixtures;

import java.util.Map;

import com.github.honoluluhenk.idclass.ID;
import lombok.Data;

@Data
public class SomeMap {
	Map<ID<SomeEntity>, String> idStringMap;
}
