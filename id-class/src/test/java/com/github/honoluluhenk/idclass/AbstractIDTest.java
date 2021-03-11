package com.github.honoluluhenk.idclass;

import java.util.UUID;

import com.github.honoluluhenk.idclass.fixtures.SomeEntity;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AbstractIDTest {

	@Nested
	class ToString {
		class MyID extends AbstractID<SomeEntity, UUID> {
			private static final long serialVersionUID = -2401643258965637637L;

			public MyID() {
				super(UUID.fromString("bfab0fb5-3309-4645-bcb6-76ca45250814"), SomeEntity.class);
			}
		}

		@Test
		void includes_subclass_name() {

			String actual = new MyID().toString();

			assertThat(actual)
					.startsWith("MyID");
		}

		@Test
		void includes_id_and_entity() {

			String actual = new MyID().toString();

			assertThat(actual)
					.contains("[bfab0fb5-3309-4645-bcb6-76ca45250814,SomeEntity]");
		}
	}

}
