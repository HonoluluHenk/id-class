package com.github.honoluluhenk.idclass;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.github.honoluluhenk.idclass.fixtures.SomeEntity;
import lombok.var;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EntityClassNameParserTest {
	private final EntityClassNameParser parser = new EntityClassNameParser(ID.class);

	@SuppressWarnings("unused")
	public static class OtherGenericType<T> {
		// nop
	}

	@Test
	void throws_if_Type_is_not_an_ID() {
		var ex = assertThrows(
				IllegalArgumentException.class,
				() -> parser.entityClassOf(Object.class)
		);

		assertThat(ex)
				.hasMessage("Can only parse class " + ID.class.getName() + "<?> but got: java.lang.Object");
	}

	@Test
	void throws_if_generic_param_is_missing() throws NoSuchMethodException {
		var bean = new Object() {
			@SuppressWarnings({ "rawtypes", "unused" })
			void methodWithRawIDParam(ID ignored) {
				// nop
			}
		};

		Method m = bean.getClass().getDeclaredMethod("methodWithRawIDParam", ID.class);
		Type[] type = m.getGenericParameterTypes();

		var ex = assertThrows(
				IllegalArgumentException.class,
				() -> parser.entityClassOf(type[0]));

		assertThat(ex)
				.hasMessageContaining("ParameterizedType required but got: java.lang.Class");
	}

	@Test
	void throws_if_other_generic_parameter() throws NoSuchMethodException {
		var bean = new Object() {
			@SuppressWarnings("unused")
			void methodWithOtherGenericParam(OtherGenericType<SomeEntity> ignored) {
				// nop
			}
		};

		Method m = bean.getClass().getDeclaredMethod("methodWithOtherGenericParam", OtherGenericType.class);
		Type[] type = m.getGenericParameterTypes();

		var ex = assertThrows(
				IllegalArgumentException.class,
				() -> parser.entityClassOf(type[0])
		);

		assertThat(ex)
				.hasMessageContaining("Can only parse class " + ID.class.getName() + "<?>")
				.hasMessageContaining(OtherGenericType.class.getName());
	}

	@Test
	void extracts_the_generic_class() throws NoSuchMethodException {
		var bean = new Object() {
			@SuppressWarnings("unused")
			void methodWithGenericIDParam(ID<SomeEntity> id) {
				// nop
			}
		};
		Method m = bean.getClass().getDeclaredMethod("methodWithGenericIDParam", ID.class);
		Type[] type = m.getGenericParameterTypes();
		var actual = parser.entityClassOf(type[0]);

		assertThat(actual)
				.isSameAs(SomeEntity.class);
	}
}
