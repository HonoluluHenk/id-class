package com.github.honoluluhenk.idclass;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class AbstractID<Entity, Id extends Serializable> {
	private final @NonNull Class<Entity> entityClass;
	private final @NonNull Id id;

	@Override
	public @NonNull String toString() {
		String result = String.format("ID[%s, %s]", id, entityClass.getSimpleName());

		return result;
	}
}
