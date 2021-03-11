package com.github.honoluluhenk.idclass;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

@Getter
@Setter
public class ID<Entity> extends AbstractID<Entity, UUID> implements Serializable {

	private static final long serialVersionUID = 7424247026749697941L;

	ID(
			@NonNull UUID id,
			@NonNull Class<Entity> clazz
	) {
		super(id, clazz);
	}

	public static <Entity> @NonNull ID<Entity> of(
			@NonNull UUID id,
			@NonNull Class<Entity> clazz
	) {
		return new ID<>(id, clazz);
	}

	/**
	 * See {@link UUID#fromString(String)}.
	 *
	 * @throws IllegalArgumentException – If name does not conform to the string representation
	 *                                  as described in {@link UUID#toString()}.
	 */
	public static <Entity> @NonNull ID<Entity> of(
			@NonNull String uuid,
			@NonNull Class<Entity> clazz
	) {
		return new ID<>(UUID.fromString(uuid), clazz);
	}

	@Nullable
	@Contract("null,_->null; !null,_->!null")
	public static <T> ID<T> parse(
			@Nullable String uuid,
			@NonNull Class<T> entityClass
	) {
		if (uuid == null) {
			return null;
		}

		ID<T> result = parse(UUID.fromString(uuid), entityClass);

		return result;
	}

	@Nullable
	@Contract("null,_->null; !null,_->!null")
	public static <T> ID<T> parse(
			@Nullable UUID id,
			@NonNull Class<T> entityClass
	) {
		if (id == null) {
			return null;
		}

		ID<T> result = ID.of(id, entityClass);

		return result;
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
