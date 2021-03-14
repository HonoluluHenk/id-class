package com.github.honoluluhenk.idclass.integration.jpahibernate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Supplier;

import com.github.honoluluhenk.idclass.ID;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.hibernate.usertype.DynamicParameterizedType;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class IDConverter
		extends AbstractSingleColumnStandardBasicType<ID>
		implements DynamicParameterizedType {

	private static final long serialVersionUID = -2971703286942879664L;
	private Class<?> entityClass = null;

	public IDConverter() {
		super(new VarcharTypeDescriptor(), new IDJavaTypeDescriptor());
		((IDJavaTypeDescriptor) getJavaTypeDescriptor())
				.setTypeArgumentSupplier(() -> entityClass);
	}

	@Override
	public void setParameterValues(Properties properties) {
		//noinspection UseOfPropertiesAsHashtable
		JavaXMember member = (JavaXMember) properties.get(XPROPERTY);

		ParameterizedType type = ensureParameterizedType(member.getJavaType());
		Type typeArg = type.getActualTypeArguments()[0];

		if (typeArg instanceof ParameterizedType) {
			ParameterizedType parameterizedTypeArg = (ParameterizedType) typeArg;
			entityClass = (Class<?>) parameterizedTypeArg.getRawType();
		} else {
			entityClass = (Class<?>) typeArg;
		}
	}

	private ParameterizedType ensureParameterizedType(Type javaType) {
		if (!(javaType instanceof ParameterizedType)) {
			throw new MappingException(String.format("Needed: ID<SomeClass>, but got: %s", javaType));
		}
		return (ParameterizedType) javaType;
	}

	@Override
	public String getName() {
		return "IDConverter";
	}

	private static class IDJavaTypeDescriptor implements JavaTypeDescriptor<ID> {
		private static final long serialVersionUID = 7258648752761667869L;

		@SuppressWarnings("FieldHasSetterButNoGetter")
		private Supplier<Class<?>> typeArgumentSupplier = null;

		@Override
		public Class<ID> getJavaTypeClass() {
			return ID.class;
		}

		@Override
		public ID fromString(String string) {
			// no idea under what circumstances this is callead instead of unwrap
			throw new NotYetImplementedException("fromString: " + string);
		}

		@Override
		public <X> X unwrap(ID value, Class<X> type, WrapperOptions options) {
			if (value == null) {
				return null;
			}
			if (type.equals(UUID.class)) {
				return (X) value.getId();
			}
			if (type.equals(String.class)) {
				return (X) value.getId().toString();
			}
			throw unknownUnwrap(type);
		}

		@Override
		public <X> ID wrap(X value, WrapperOptions options) {
			if (value == null) {
				return null;
			}

			Class<?> ec = typeArgumentSupplier.get();

			if (value instanceof UUID) {
				ID result = ID.of((UUID) value, ec);
				return result;
			}
			if ((value instanceof String)) {
				ID result = ID.of(UUID.fromString((String) value), ec);
				return result;
			}

			throw valueClassNotSupported(value);
		}

		private MappingException valueClassNotSupported(Object value) {
			return new MappingException(
					String.format("wrapping of class as ID<?> not supported: %s=%s",
							value.getClass(), value));
		}

		private HibernateException unknownUnwrap(Class conversionType) {
			return new HibernateException(
					"Unknown unwrap conversion requested: " + ID.class.getName() + " to " + conversionType.getName()
			);
		}

		private void setTypeArgumentSupplier(Supplier<Class<?>> typeArgumentSupplier) {
			this.typeArgumentSupplier = typeArgumentSupplier;
		}
	}
}
