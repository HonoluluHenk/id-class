package com.github.honoluluhenk.idclass;

import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ID<Entity> extends AbstractID<Entity, UUID> {

	ID(@NonNull UUID id, @NonNull Class<Entity> clazz) {
		super(clazz, id);
	}

	public static <Entity> @NonNull ID<Entity> of(@NonNull UUID id, @NonNull Class<Entity> clazz) {
		return new ID<>(id, clazz);
	}

	// FIXME: do I want this?
	public static <Entity> @NonNull ID<Entity> of(@NonNull String uuid, @NonNull Class<Entity> clazz) {
		return new ID<>(UUID.fromString(uuid), clazz);
	}

	public static <Entity extends IDSupplier<UUID>>
	@NonNull ID<Entity> from(
			@NonNull Entity idSupplier
	) {
		@SuppressWarnings("unchecked")
		Class<Entity> aClass = (Class<Entity>) idSupplier.getClass();
		UUID id = idSupplier.getId();

		ID<Entity> result = new ID<>(id, aClass);

		return result;
	}
}
