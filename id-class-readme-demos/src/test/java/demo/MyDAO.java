package demo;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.github.honoluluhenk.idclass.ID;
import com.github.honoluluhenk.idclass.integration.jpahibernate.SmartEntityManager;

@RequestScoped
@Transactional
class MyDAO {

	@PersistenceContext
	private EntityManager em;

	private SmartEntityManager db() {
		return new SmartEntityManager(em);
	}

	// easier in the long run: implement your own @Producer and directly inject here:
	// @Inject
	// private SmartEntityManager sem;

	public SomeEntity findById(ID<SomeEntity> entityID) {
		return db()
				.find(entityID);
	}

	// also implemented: Java8 Optionals:
	public Optional<SomeEntity> findOneByID(ID<SomeEntity> entityID) {
		return db()
				.findOne(entityID);

		// same for Query/TypedQuery/StoreProcedureQuery: query.getSoleResult()
	}
}
