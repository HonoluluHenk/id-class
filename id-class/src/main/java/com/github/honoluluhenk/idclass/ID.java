package com.github.honoluluhenk.idclass;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

/**
 * An entity-bound ID with {@link UUID} value.
 */
@Getter
@Setter
public class ID<Entity> extends AbstractID<Entity, UUID> implements Serializable {

	private static final long serialVersionUID = 7424247026749697941L;

	// FIXME: @NonNull nur wo's wirklich braucht
	ID(
			@NonNull UUID id,
			@NonNull Class<Entity> clazz
	) {
		super(id, clazz);
	}

	/**
	 * Simple factory method.
	 */
	public static <Entity> @NonNull ID<Entity> of(
			UUID id,
			Class<Entity> clazz
	) {
		return new ID<>(id, clazz);
	}

	/**
	 * initialize using {@link UUID#randomUUID()}.
	 */
	public static <Entity> @NonNull ID<Entity> randomUUID(
			Class<Entity> clazz
	) {
		return of(UUID.randomUUID(), clazz);
	}

	/**
	 * initializes passing the uuid string to {@link UUID#fromString(String)}.
	 *
	 * @throws IllegalArgumentException – If name does not conform to the string representation
	 *                                  as described in {@link UUID#toString()}.
	 */
	public static <Entity> @NonNull ID<Entity> of(
			@NonNull String uuid,
			@NonNull Class<Entity> clazz
	) {
		return of(UUID.fromString(uuid), clazz);
	}

	/**
	 * Convenience for null-handling.
	 *
	 * See {@link #of(String, Class)} for details of the uuid-string-argument.
	 *
	 * @return null if uuid is null, non-null otherwise.
	 */
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

	/**
	 * Convenience for null-handling.
	 *
	 * @return null if uuid is null, non-null otherwise.
	 */
	@Nullable
	@Contract("null,_->null; !null,_->!null")
	public static <T> ID<T> parse(
			@Nullable UUID id,
			@NonNull Class<T> entityClass
	) {
		if (id == null) {
			return null;
		}

		ID<T> result = of(id, entityClass);

		return result;
	}

	/**
	 * Takes the id + class from the supplier.
	 *
	 * Supplier should be an actual class (not just a lambda)
	 * because the ID-class is taken from from idSupplier.getClass()...
	 * which does not yield usable results for a lambda params.
	 */
	public static <Entity extends IDSupplier<UUID>>
	@NonNull ID<Entity> from(
			@NonNull Entity idSupplier
	) {
		@SuppressWarnings("unchecked")
		Class<Entity> aClass = (Class<Entity>) idSupplier.getClass();
		UUID id = idSupplier.getId();

		ID<Entity> result = of(id, aClass);

		return result;
	}

}
