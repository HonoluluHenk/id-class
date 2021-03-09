package com.github.honoluluhenk.idclass.jackson.testing.fixtures;

import com.github.honoluluhenk.idclass.ID;
import lombok.Data;

@Data
public class SomeEntity {
	private final ID<SomeEntity> id;
}
