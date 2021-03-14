package com.github.honoluluhenk.idclass.integration.jackson;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.github.honoluluhenk.idclass.ID;
import com.github.honoluluhenk.idclass.integration.EntityClassNameParser;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("unused")
class EntityClassNameParserTest {

	private static class SomeEntity {
		// nop
	}

	private final EntityClassNameParser parser = new EntityClassNameParser(ID.class);

	@Nested
	class parsing_something_other_than_ID {
		@Test
		void throws_exception_on_plain_class() {
			IllegalArgumentException ex = assertThrows(
					IllegalArgumentException.class,
					() -> parser.entityClassFrom(Object.class)
			);

			assertThat(ex)
					.hasMessage("Can only parse class " + ID.class.getName() + "<?> but got: java.lang.Object");
		}

		@Test
		void throws_on_nonID_ParameterizedType_with_good_error_message() throws NoSuchMethodException {
			class OtherGenericType<T> {
				// nop
			}

			Object bean = new Object() {
				void methodWithOtherGenericParam(OtherGenericType<SomeEntity> ignored) {
					// nop
				}
			};

			Method m = bean.getClass().getDeclaredMethod("methodWithOtherGenericParam", OtherGenericType.class);
			Type type = m.getGenericParameterTypes()[0];

			IllegalArgumentException ex = assertThrows(
					IllegalArgumentException.class,
					() -> parser.entityClassFrom(type)
			);

			assertThat(ex)
					.hasMessageContaining("Can only parse class " + ID.class.getName() + "<?>")
					.hasMessageContaining(OtherGenericType.class.getName());
		}

	}

	@Nested
	@SuppressWarnings("rawtypes")
	class parsing_a_raw_ID {
		@Test
		void throws_exception_with_good_error_message() throws NoSuchMethodException {
			Object bean = new Object() {
				void methodWithRawIDParam(ID ignored) {
					// nop
				}
			};

			Method m = bean.getClass().getDeclaredMethod("methodWithRawIDParam", ID.class);
			Type type = m.getGenericParameterTypes()[0];

			IllegalArgumentException ex = assertThrows(
					IllegalArgumentException.class,
					() -> parser.entityClassFrom(type));

			assertThat(ex)
					.hasMessage(ID.class.getName() + " required with generic type arguments but only got raw type");
		}
	}

	@Nested
	class parsing {
		@Test
		void extracts_the_generic_class_on_simple_entities() throws NoSuchMethodException {
			Object bean = new Object() {
				void methodWithGenericIDParam(ID<SomeEntity> id) {
					// nop
				}
			};
			Method m = bean.getClass().getDeclaredMethod("methodWithGenericIDParam", ID.class);
			Type type = m.getGenericParameterTypes()[0];

			Class<?> actual = parser.entityClassFrom(type);

			assertThat(actual)
					.isSameAs(SomeEntity.class);
		}

		@Test
		void extracts_the_generic_class_on_entities_with_type_arguments() throws NoSuchMethodException {
			class GenericEntity<T> {
				// nop
			}

			Object bean = new Object() {
				void methodWithGenericIDParam(ID<GenericEntity<String>> id) {
					// nop
				}
			};

			Method m = bean.getClass().getDeclaredMethod("methodWithGenericIDParam", ID.class);
			Type type = m.getGenericParameterTypes()[0];

			Class<?> actual = parser.entityClassFrom(type);

			assertThat(actual)
					.isSameAs(GenericEntity.class);
		}

		@Test
		void throws_for_unsupported_generic_param_types_like_GenericArrayType() throws NoSuchMethodException {
			class GenericEntity<T> {
				// nop
			}

			Object bean = new Object() {
				void methodWithGenericIDParam(ID<GenericEntity<String>[]> id) {
					// nop
				}
			};

			Method m = bean.getClass().getDeclaredMethod("methodWithGenericIDParam", ID.class);
			Type type = m.getGenericParameterTypes()[0];

			IllegalArgumentException ex = assertThrows(
					IllegalArgumentException.class,
					() -> parser.entityClassFrom(type));

			assertThat(ex)
					.hasMessageContaining("Not yet implemented")
					.hasMessageContaining("GenericArrayType")
					.hasMessageContaining("GenericEntity<java.lang.String>[]");
		}

	}

}
