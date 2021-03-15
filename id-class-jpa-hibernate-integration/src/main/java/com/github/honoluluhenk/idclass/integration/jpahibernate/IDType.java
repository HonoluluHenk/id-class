package com.github.honoluluhenk.idclass.integration.jpahibernate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Properties;

import com.github.honoluluhenk.idclass.ID;
import com.github.honoluluhenk.idclass.integration.EntityClassNameParser;
import org.hibernate.MappingException;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.hibernate.usertype.DynamicParameterizedType;

@SuppressWarnings({ "rawtypes", "unused" })
public class IDType
		extends AbstractSingleColumnStandardBasicType<ID>
		implements DynamicParameterizedType {

	private static final long serialVersionUID = -2971703286942879664L;
	private Class<?> entityClass = null;

	public IDType() {
		super(new VarcharTypeDescriptor(), new IDJavaTypeDescriptor());
		((IDJavaTypeDescriptor) getJavaTypeDescriptor())
				.setTypeArgumentSupplier(() -> entityClass);
	}

	@Override
	public void setParameterValues(Properties properties) {
		//noinspection UseOfPropertiesAsHashtable
		JavaXMember member = (JavaXMember) properties.get(XPROPERTY);

		entityClass = new EntityClassNameParser(ID.class)
				.entityClassFrom(member.getJavaType());
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

}
