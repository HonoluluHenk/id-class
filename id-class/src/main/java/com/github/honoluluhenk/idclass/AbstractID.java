package com.github.honoluluhenk.idclass;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PROTECTED;
import static lombok.EqualsAndHashCode.CacheStrategy.LAZY;

@RequiredArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(cacheStrategy = LAZY)
@Getter
public abstract class AbstractID<Entity, Id extends Serializable>
		implements IDSupplier<Id>, Serializable {
	private static final long serialVersionUID = -731756912866512037L;

	private final @NonNull Id id;
	private final @NonNull Class<Entity> entityClass;

	@Override
	public @NonNull String toString() {
		String result = String.format("%s[%s,%s]", getClass().getSimpleName(), id, entityClass.getSimpleName());

		return result;
	}
}
