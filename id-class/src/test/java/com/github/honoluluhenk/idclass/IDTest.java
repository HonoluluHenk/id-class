package com.github.honoluluhenk.idclass;

import java.util.UUID;

import com.github.honoluluhenk.idclass.fixtures.SomeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IDTest {
	private final String uuidString = "16f26c8c-bb42-4e35-8a2a-adfa27658ad6";
	private final UUID uuid = UUID.fromString(uuidString);

	@Nested
	class of_UUID {

		@Test
		void returns_an_ID_with_given_values() {
			ID<SomeEntity> id = ID.of(uuid, SomeEntity.class);

			assertThat(id.getId())
					.isSameAs(uuid);

			assertThat(id.getEntityClass())
					.isSameAs(SomeEntity.class);

		}
	}

	@Nested
	class of_string {
		@Test
		void returns_an_ID_with_given_values() {
			ID<SomeEntity> id = ID.of(uuidString, SomeEntity.class);

			assertThat(id.getId())
					.isEqualTo(uuid);

			assertThat(id.getEntityClass())
					.isSameAs(SomeEntity.class);
		}

		@Test
		void throws_for_illegal_uuid() {

			IllegalArgumentException ex = Assertions.assertThrows(
					IllegalArgumentException.class,
					() -> ID.of("illegal uuid", SomeEntity.class)
			);

			assertThat(ex)
					.hasMessageContaining("illegal uuid");
		}

	}

	@Nested
	class from {

		@Test
		void returns_an_id_with_given_values() {
			class MyEntity implements IDSupplier<UUID> {

				@Override
				public UUID getId() {
					return UUID.fromString("bfab0fb5-3309-4645-bcb6-76ca45250814");
				}
			}

			ID<MyEntity> actual = ID.from(new MyEntity());

			assertThat(actual)
					.isEqualTo(ID.of("bfab0fb5-3309-4645-bcb6-76ca45250814", MyEntity.class));
		}

		@Test
		void show_what_happens_when_passing_in_lambdas() {
			ID<IDSupplier<UUID>> actual =
					ID.from(() -> UUID.fromString("bfab0fb5-3309-4645-bcb6-76ca45250814"));

			assertThat(actual.toString())
					.startsWith("ID[bfab0fb5-3309-4645-bcb6-76ca45250814,IDTest$from$$Lambda$");
		}
	}

	@Nested
	class parse_string {

		@Test
		void returns_an_id_with_given_values() {
			ID<SomeEntity> actual = ID.parse(uuidString, SomeEntity.class);
			assertThat(actual)
					.isEqualTo(ID.of(uuid, SomeEntity.class));
		}

		@Test
		@SuppressWarnings("ConstantConditions")
		void returns_null_for_null_id() {
			ID<SomeEntity> actual = ID.parse((String) null, SomeEntity.class);

			assertThat(actual)
					.isNull();
		}

	}

	@Nested
	class parse_uuid {

		@Test
		void returns_an_id_with_given_values() {
			ID<SomeEntity> actual = ID.parse(uuid, SomeEntity.class);
			assertThat(actual)
					.isEqualTo(ID.of(uuid, SomeEntity.class));
		}

		@Test
		@SuppressWarnings("ConstantConditions")
		void returns_null_for_null_id() {
			ID<SomeEntity> actual = ID.parse((UUID) null, SomeEntity.class);

			assertThat(actual)
					.isNull();
		}

	}

	@Nested
	class randomdUUID {

		@Test
		void generates_different_UUIDs() {
			ID<SomeEntity> fist = ID.randomUUID(SomeEntity.class);
			ID<SomeEntity> second = ID.randomUUID(SomeEntity.class);

			assertThat(fist.getId())
					.isNotEqualTo(second.getId());
		}
	}

}
