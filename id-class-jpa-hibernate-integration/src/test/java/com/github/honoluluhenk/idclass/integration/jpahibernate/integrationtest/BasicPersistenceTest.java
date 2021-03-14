package com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest;

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.honoluluhenk.idclass.ID;
import com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest.fixtures.fixtures.BasicEntity;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static java.util.UUID.fromString;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@SuppressWarnings({ "SqlDialectInspection", "SqlNoDataSourceInspection", "SqlResolve" })
public class BasicPersistenceTest {
	@Inject
	EntityManager em;

	private final String ENTITY_ID_STRING = "55738a2b-1082-415f-b5a0-80bf7961e732";
	private final UUID ENTITY_UUID = fromString(ENTITY_ID_STRING);
	private final ID<BasicEntity> ENTITY_ID = ID.of(ENTITY_UUID, BasicEntity.class);

	@Test
	@TestTransaction
	void persists_and_serializes_id() {
		BasicEntity entity = BasicEntity.of(ENTITY_UUID);
		em.persist(entity);
		em.flush();

		String actualId = (String) em
				.createNativeQuery("SELECT e.id FROM BasicEntity e WHERE e.id = :id")
				.setParameter("id", ENTITY_ID_STRING)
				.getSingleResult();

		assertThat(actualId)
				.isEqualTo(ENTITY_ID_STRING);
	}

	@Test
	@TestTransaction
	void loads_and_deserializes_id() {
		em
				.createNativeQuery("INSERT INTO BasicEntity (id) VALUES (:id)")
				.setParameter("id", ENTITY_ID_STRING)
				.executeUpdate();

		BasicEntity actual = em.find(BasicEntity.class, ID.of(ENTITY_UUID, BasicEntity.class));

		assertThat(actual.getId())
				.isEqualTo(ENTITY_ID);
	}

}
