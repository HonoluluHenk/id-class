package com.github.honoluluhenk.idclass.integration.jackson;

import com.fasterxml.jackson.databind.JavaType;
import lombok.experimental.UtilityClass;

@UtilityClass
final class TypeUtil {

	/**
	 * Extract the generic type parameter of a type-with-generic-params.
	 * <p>
	 * Example:
	 * If JavaType represents {@code Map<Foo, Bar>},
	 * then
	 * {@code readGenericTypeArg(javaType, 0)} will return Foo.
	 * {@code readGenericTypeArg(javaType, 1)} will return Bar.
	 */
	static JavaType readGenericTypeArg(
			JavaType idType,
			@SuppressWarnings("SameParameterValue") int index
	) {
		JavaType result = idType
				.getBindings()
				.getTypeParameters()
				.get(index);

		return result;
	}
}
