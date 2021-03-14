package com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.honoluluhenk.idclass.ID;
import com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest.fixtures.fixtures.BasicEntity;
import com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest.fixtures.fixtures.ChildEntity;
import com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest.fixtures.fixtures.ParentEntity;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static java.util.UUID.fromString;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@SuppressWarnings({ "SqlDialectInspection", "SqlNoDataSourceInspection", "SqlResolve" })
public class ComplexPersistenceTest {
	@Inject
	EntityManager em;

	private final ID<ParentEntity> PARENT_ID =
			ID.of(fromString("55738a2b-1082-415f-b5a0-80bf7961e732"), ParentEntity.class);
	private final ID<BasicEntity> BASICENTITY_ID =
			ID.of(fromString("94c4cc6d-dcbe-4588-88cb-4a08da3ba3ba"), BasicEntity.class);
	private final ID<ChildEntity> CHILD1_ID =
			ID.of(fromString("5b9aab14-4b2c-4a30-bc04-6cffd8b8e193"), ChildEntity.class);
	private final ID<ChildEntity> CHILD2_ID =
			ID.of(fromString("aafa8bd9-5db5-405c-8711-886845150650"), ChildEntity.class);

	@Test
	@TestTransaction
	void persists_and_loads() {
		ParentEntity fixture = new ParentEntity()
				.setId(PARENT_ID)
				.setName("Parent")
				.setBasicEntity(new BasicEntity().setId(BASICENTITY_ID))
				.addChild(new ChildEntity()
						.setId(CHILD1_ID)
						.setName("Child 1"))
				.addChild(new ChildEntity()
						.setId(CHILD2_ID)
						.setName("Child2"));

		em.persist(fixture);

		em.flush();
		em.clear();

		// mainly for documentation: the entitymanager is empty
		// => entities actually get loaded from DB lateron instead of first-level-cache
		assertThat(em.contains(fixture))
				.isFalse();

		ParentEntity actual = em.find(ParentEntity.class, PARENT_ID);

		assertThat(actual)
				.isNotSameAs(fixture);
		assertThat(actual)
				.isEqualTo(fixture);
		assertThat(actual.getChildren())
				.containsExactlyElementsOf(fixture.getChildren())
		;

	}

}
