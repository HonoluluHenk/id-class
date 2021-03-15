package com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest.fixtures.fixtures;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.github.honoluluhenk.idclass.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicEntity {
	@Id
	@Type(type = "com.github.honoluluhenk.idclass.integration.jpahibernate.IDType")
	private ID<BasicEntity> id;

	public static BasicEntity of(UUID id) {
		return new BasicEntity(ID.of(id, BasicEntity.class));
	}

}
