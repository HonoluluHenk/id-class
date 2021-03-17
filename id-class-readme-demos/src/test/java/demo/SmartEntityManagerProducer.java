package demo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.github.honoluluhenk.idclass.integration.jpahibernate.SmartEntityManager;

@ApplicationScoped
public class SmartEntityManagerProducer {

	@PersistenceContext
	EntityManager defaultEntitymanager;

	@Produces
	@RequestScoped
	public SmartEntityManager produceSmartEntityManager() {
		return new SmartEntityManager(defaultEntitymanager);
	}
}
