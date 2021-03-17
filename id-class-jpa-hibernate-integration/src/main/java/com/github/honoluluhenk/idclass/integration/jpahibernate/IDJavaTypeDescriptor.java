package com.github.honoluluhenk.idclass.integration.jpahibernate;

import java.util.UUID;
import java.util.function.Supplier;

import com.github.honoluluhenk.idclass.ID;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class IDJavaTypeDescriptor implements JavaTypeDescriptor<ID> {
	private static final long serialVersionUID = 7258648752761667869L;

	public static IDJavaTypeDescriptor INSTANCE = new IDJavaTypeDescriptor();

	@SuppressWarnings("FieldHasSetterButNoGetter")
	private Supplier<Class<?>> typeArgumentSupplier = null;

	@Deprecated
	@Override
	public Class<ID> getJavaTypeClass() {
		return ID.class;
	}

	@Override
	public Class<ID> getJavaType() {
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

	void setTypeArgumentSupplier(Supplier<Class<?>> typeArgumentSupplier) {
		this.typeArgumentSupplier = typeArgumentSupplier;
	}
}
