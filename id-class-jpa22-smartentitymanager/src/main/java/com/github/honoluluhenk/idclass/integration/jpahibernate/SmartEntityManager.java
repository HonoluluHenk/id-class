package com.github.honoluluhenk.idclass.integration.jpahibernate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import com.github.honoluluhenk.idclass.AbstractID;

public class SmartEntityManager implements EntityManager {

	private final EntityManager em;

	public SmartEntityManager(EntityManager delegate) {
		this.em = delegate;
	}

	/**
	 * Returns the delegate {@link EntityManager} on which this instance is based.
	 *
	 * You should never need this except for maybe debugging purposes.
	 */
	public EntityManager getDelegateEntityManager() {
		return em;
	}

	@Override
	public String toString() {
		return "SmartEntityManager[" + em + ']';
	}

	@Override
	public void persist(Object o) {
		em.persist(o);
	}

	@Override
	public <T> T merge(T t) {
		return em.merge(t);
	}

	@Override
	public void remove(Object o) {
		em.remove(o);
	}

	@Override
	public <T> T find(Class<T> aClass, Object o) {
		return em.find(aClass, o);
	}

	public <T> Optional<T> findOne(Class<T> aClass, Object o) {
		return wrapOne(() -> em.find(aClass, o));
	}

	/**
	 * See {@link #find(Class, Object)}.
	 */
	public <Entity, ID extends AbstractID<Entity, ?>> Entity find(ID entityID) {
		Entity result = find(entityID.getEntityClass(), entityID.getId());
		return result;
	}
	/**
	 * See {@link #find(Class, Object)}.
	 */
	public <Entity, ID extends AbstractID<Entity, ?>> Optional<Entity> findOne(ID entityID) {
		return wrapOne(() -> find(entityID.getEntityClass(), entityID.getId()));
	}
	
	@Override
	public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
		return em.find(aClass, o, map);
	}

	public <T> Optional<T> findOne(Class<T> aClass, Object o, Map<String, Object> map) {
		return wrapOne(() -> em.find(aClass, o, map));
	}

	/**
	 * See {link {@link #find(Class, Object, Map)}}.
	 */
	public <Entity, ID extends AbstractID<Entity, ?>> Entity find(ID entityID, Map<String, Object> map) {
		return find(entityID.getEntityClass(), entityID.getId(), map);
	}

	/**
	 * See {link {@link #find(Class, Object, Map)}}.
	 */
	public <Entity, ID extends AbstractID<Entity, ?>> Optional<Entity> findOne(ID entityID, Map<String, Object> map) {
		return wrapOne(() -> find(entityID.getEntityClass(), entityID.getId(), map));
	}

	@Override
	public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
		return em.find(aClass, o, lockModeType);
	}

	public <T> Optional<T> findOne(Class<T> aClass, Object o, LockModeType lockModeType) {
		return wrapOne(() -> em.find(aClass, o, lockModeType));
	}

	public <Entity, ID extends AbstractID<Entity, ?>> Entity find(ID entityID, LockModeType lockModeType) {
		return find(entityID.getEntityClass(), entityID.getId(), lockModeType);
	}

	public <Entity, ID extends AbstractID<Entity, ?>> Optional<Entity> findOne(ID entityID, LockModeType lockModeType) {
		return wrapOne(() -> find(entityID.getEntityClass(), entityID.getId(), lockModeType));
	}

	@Override
	public <T> T find(
			Class<T> aClass,
			Object o,
			LockModeType lockModeType,
			Map<String, Object> map
	) {
		return em.find(aClass, o, lockModeType, map);
	}

	public <T> Optional<T> findOne(
			Class<T> aClass,
			Object o,
			LockModeType lockModeType,
			Map<String, Object> map
	) {
		return wrapOne(() -> em.find(aClass, o, lockModeType, map));
	}
	public <Entity, ID extends AbstractID<Entity, ?>> Entity find(
			ID entityID,
			LockModeType lockModeType,
			Map<String, Object> map
	) {
		return find(entityID.getEntityClass(), entityID.getId(), lockModeType, map);
	}

	public <Entity, ID extends AbstractID<Entity, ?>> Optional<Entity> findOne(
			ID entityID,
			LockModeType lockModeType,
			Map<String, Object> map
	) {
		return wrapOne(() -> find(entityID.getEntityClass(), entityID.getId(), lockModeType, map));
	}

	private <Entity> Optional<Entity> wrapOne(Supplier<Entity> entity) {
		try {
			Optional<Entity> result = Optional.of(entity.get());
			return result;
		} catch (NoResultException nre) {
			return Optional.empty();
		}
	}

	@Override
	public <T> T getReference(Class<T> aClass, Object o) {
		return em.getReference(aClass, o);
	}

	public <Entity, ID extends AbstractID<Entity, ?>> Entity getReference(ID entityID) {
		Entity result = getReference(entityID.getEntityClass(), entityID.getId());
		return result;
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public void setFlushMode(FlushModeType flushModeType) {
		em.setFlushMode(flushModeType);
	}

	@Override
	public FlushModeType getFlushMode() {
		return em.getFlushMode();
	}

	@Override
	public void lock(Object o, LockModeType lockModeType) {
		em.lock(o, lockModeType);
	}

	@Override
	public void lock(
			Object o,
			LockModeType lockModeType,
			Map<String, Object> map) {
		em.lock(o, lockModeType, map);
	}

	@Override
	public void refresh(Object o) {
		em.refresh(o);
	}

	@Override
	public void refresh(Object o, Map<String, Object> map) {
		em.refresh(o, map);
	}

	@Override
	public void refresh(Object o, LockModeType lockModeType) {
		em.refresh(o, lockModeType);
	}

	@Override
	public void refresh(
			Object o,
			LockModeType lockModeType,
			Map<String, Object> map) {
		em.refresh(o, lockModeType, map);
	}

	@Override
	public void clear() {
		em.clear();
	}

	@Override
	public void detach(Object o) {
		em.detach(o);
	}

	@Override
	public boolean contains(Object o) {
		return em.contains(o);
	}

	@Override
	public LockModeType getLockMode(Object o) {
		return em.getLockMode(o);
	}

	@Override
	public void setProperty(String s, Object o) {
		em.setProperty(s, o);
	}

	@Override
	public Map<String, Object> getProperties() {
		return em.getProperties();
	}

	@Override
	public SmartQuery createQuery(String s) {
		return new SmartQuery(em.createQuery(s));
	}

	@Override
	public <T> SmartTypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return new SmartTypedQuery<>(em.createQuery(criteriaQuery));
	}

	@Override
	public SmartQuery createQuery(CriteriaUpdate criteriaUpdate) {
		return new SmartQuery(em.createQuery(criteriaUpdate));
	}

	@Override
	public SmartQuery createQuery(CriteriaDelete criteriaDelete) {
		return new SmartQuery(em.createQuery(criteriaDelete));
	}

	@Override
	public <T> SmartTypedQuery<T> createQuery(String s, Class<T> aClass) {
		return new SmartTypedQuery<>(em.createQuery(s, aClass));
	}

	@Override
	public SmartQuery createNamedQuery(String s) {
		return new SmartQuery(em.createNamedQuery(s));
	}

	@Override
	public <T> SmartTypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
		return new SmartTypedQuery<>(em.createNamedQuery(s, aClass));
	}

	@Override
	public SmartQuery createNativeQuery(String s) {
		return new SmartQuery(em.createNativeQuery(s));
	}

	@Override
	public SmartQuery createNativeQuery(String s, Class aClass) {
		return new SmartQuery(em.createNativeQuery(s, aClass));
	}

	@Override
	public SmartQuery createNativeQuery(String s, String s1) {
		return new SmartQuery(em.createNativeQuery(s, s1));
	}

	@Override
	public SmartStoredProcedureQuery createNamedStoredProcedureQuery(String s) {
		return new SmartStoredProcedureQuery(em.createNamedStoredProcedureQuery(s));
	}

	@Override
	public SmartStoredProcedureQuery createStoredProcedureQuery(String s) {
		return new SmartStoredProcedureQuery(em.createStoredProcedureQuery(s));
	}

	@SuppressWarnings("OverloadedVarargsMethod")
	@Override
	public SmartStoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
		return new SmartStoredProcedureQuery(em.createStoredProcedureQuery(s, classes));
	}

	@SuppressWarnings("OverloadedVarargsMethod")
	@Override
	public SmartStoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
		return new SmartStoredProcedureQuery(em.createStoredProcedureQuery(s, strings));
	}

	@Override
	public void joinTransaction() {
		em.joinTransaction();
	}

	@Override
	public boolean isJoinedToTransaction() {
		return em.isJoinedToTransaction();
	}

	@Override
	public <T> T unwrap(Class<T> aClass) {
		return em.unwrap(aClass);
	}

	@Override
	public Object getDelegate() {
		return em.getDelegate();
	}

	@Override
	public void close() {
		em.close();
	}

	@Override
	public boolean isOpen() {
		return em.isOpen();
	}

	@Override
	public EntityTransaction getTransaction() {
		return em.getTransaction();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return em.getEntityManagerFactory();
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return em.getCriteriaBuilder();
	}

	@Override
	public Metamodel getMetamodel() {
		return em.getMetamodel();
	}

	@Override
	public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
		return em.createEntityGraph(aClass);
	}

	@Override
	public EntityGraph<?> createEntityGraph(String s) {
		return em.createEntityGraph(s);
	}

	@Override
	public EntityGraph<?> getEntityGraph(String s) {
		return em.getEntityGraph(s);
	}

	@Override
	public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
		return em.getEntityGraphs(aClass);
	}

}
