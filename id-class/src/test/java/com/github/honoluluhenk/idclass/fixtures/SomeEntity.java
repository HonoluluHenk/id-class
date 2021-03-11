package com.github.honoluluhenk.idclass.fixtures;

import com.github.honoluluhenk.idclass.ID;
import lombok.Data;

@Data
public class SomeEntity {
	private final ID<SomeEntity> id;
}
