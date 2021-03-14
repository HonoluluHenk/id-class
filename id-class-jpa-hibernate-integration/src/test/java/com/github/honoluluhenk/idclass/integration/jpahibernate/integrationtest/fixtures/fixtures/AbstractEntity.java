package com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest.fixtures.fixtures;

import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.github.honoluluhenk.idclass.ID;
import lombok.ToString;
import org.hibernate.annotations.Type;

@MappedSuperclass
@ToString
public abstract class AbstractEntity<Entity extends AbstractEntity<Entity>> {
	@Id
	@Type(type = "com.github.honoluluhenk.idclass.integration.jpahibernate.IDConverter")
	private ID<Entity> id;

	public Entity setId(ID<Entity> id) {
		this.id = id;

		@SuppressWarnings("unchecked")
		Entity entity = (Entity) this;
		return entity;
	}

	public ID<Entity> getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AbstractEntity)) {
			return false;
		}
		AbstractEntity<?> that = (AbstractEntity<?>) o;
		return getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
