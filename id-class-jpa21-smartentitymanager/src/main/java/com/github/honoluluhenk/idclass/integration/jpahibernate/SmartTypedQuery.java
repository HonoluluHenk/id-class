package com.github.honoluluhenk.idclass.integration.jpahibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Parameter;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TemporalType;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

@SuppressWarnings("TypeParameterHidesVisibleType")
public class SmartTypedQuery<T> implements TypedQuery<T> {
	private final TypedQuery<T> typedQuery;

	public SmartTypedQuery(TypedQuery<T> typedQuery) {
		this.typedQuery = typedQuery;
	}

	/**
	 * Returns the delegate {@link TypedQuery} on which this instance is based.
	 *
	 * You should never need this except for maybe debugging purposes.
	 */
	public TypedQuery<T> getDelegateTypedQuery() {
		return typedQuery;
	}

	@Override
	public String toString() {
		return "SmartTypedQuery[" + typedQuery + ']';
	}

	@Override
	public List<T> getResultList() {
		return typedQuery.getResultList();
	}

	@Override
	public T getSingleResult() {
		return typedQuery.getSingleResult();
	}

	/**
	 * Wrap {@link #getSingleResult()} in a {@link Optional} in case of {@link NoResultException}.
	 *
	 * Throws all exceptions from {@link #getSingleResult()} for the same reasons
	 * (except NoResultException for obvious reaons).
	 *
	 * @throws NonUniqueResultException     – if more than one result
	 * @throws IllegalStateException        – if called for a Java Persistence query language UPDATE or DELETE
	 *                                      statement
	 * @throws QueryTimeoutException        – if the query execution exceeds the query timeout value set and only the
	 *                                      statement is rolled back
	 * @throws TransactionRequiredException – if a lock mode other than NONE has been set and there is no transaction
	 *                                      or the persistence context has not been joined to the transaction
	 * @throws PessimisticLockException     – if pessimistic locking fails and the transaction is rolled back
	 * @throws LockTimeoutException         – if pessimistic locking fails and only the statement is rolled back
	 * @throws PersistenceException         – if the query execution exceeds the query timeout value set and the
	 *                                      transaction is rolled back
	 */
	public Optional<T> findOne() {
		try {
			return Optional.of(getSingleResult());
		} catch (NoResultException nre) {
			return Optional.empty();
		}
	}

	@Override
	public SmartTypedQuery<T> setMaxResults(int maxResult) {
		return new SmartTypedQuery<>(typedQuery.setMaxResults(maxResult));
	}

	@Override
	public SmartTypedQuery<T> setFirstResult(int startPosition) {
		return new SmartTypedQuery<>(typedQuery.setFirstResult(startPosition));
	}

	@Override
	public SmartTypedQuery<T> setHint(String hintName, Object value) {
		return new SmartTypedQuery<>(typedQuery.setHint(hintName, value));
	}

	@Override
	public <T1> SmartTypedQuery<T> setParameter(Parameter<T1> param, T1 value) {
		return new SmartTypedQuery<>(typedQuery.setParameter(param, value));
	}

	@Override
	public SmartTypedQuery<T> setParameter(
			Parameter<Calendar> param,
			Calendar value,
			TemporalType temporalType) {
		return new SmartTypedQuery<>(typedQuery.setParameter(param, value, temporalType));
	}

	@Override
	public SmartTypedQuery<T> setParameter(
			Parameter<Date> param,
			Date value,
			TemporalType temporalType) {
		return new SmartTypedQuery<>(typedQuery.setParameter(param, value, temporalType));
	}

	@Override
	public SmartTypedQuery<T> setParameter(String name, Object value) {
		return new SmartTypedQuery<>(typedQuery.setParameter(name, value));
	}

	@Override
	public SmartTypedQuery<T> setParameter(String name, Calendar value, TemporalType temporalType) {
		return new SmartTypedQuery<>(typedQuery.setParameter(name, value, temporalType));
	}

	@Override
	public SmartTypedQuery<T> setParameter(String name, Date value, TemporalType temporalType) {
		return new SmartTypedQuery<>(typedQuery.setParameter(name, value, temporalType));
	}

	@Override
	public SmartTypedQuery<T> setParameter(int position, Object value) {
		return new SmartTypedQuery<>(typedQuery.setParameter(position, value));
	}

	@Override
	public SmartTypedQuery<T> setParameter(int position, Calendar value, TemporalType temporalType) {
		return new SmartTypedQuery<>(typedQuery.setParameter(position, value, temporalType));
	}

	@Override
	public SmartTypedQuery<T> setParameter(int position, Date value, TemporalType temporalType) {
		return new SmartTypedQuery<>(typedQuery.setParameter(position, value, temporalType));
	}

	@Override
	public SmartTypedQuery<T> setFlushMode(FlushModeType flushMode) {
		return new SmartTypedQuery<>(typedQuery.setFlushMode(flushMode));
	}

	@Override
	public SmartTypedQuery<T> setLockMode(LockModeType lockMode) {
		return new SmartTypedQuery<>(typedQuery.setLockMode(lockMode));
	}

	@Override
	public int executeUpdate() {
		return typedQuery.executeUpdate();
	}

	@Override
	public int getMaxResults() {
		return typedQuery.getMaxResults();
	}

	@Override
	public int getFirstResult() {
		return typedQuery.getFirstResult();
	}

	@Override
	public Map<String, Object> getHints() {
		return typedQuery.getHints();
	}

	@Override
	public Set<Parameter<?>> getParameters() {
		return typedQuery.getParameters();
	}

	@Override
	public Parameter<?> getParameter(String name) {
		return typedQuery.getParameter(name);
	}

	@Override
	public <T> Parameter<T> getParameter(String name, Class<T> type) {
		return typedQuery.getParameter(name, type);
	}

	@Override
	public Parameter<?> getParameter(int position) {
		return typedQuery.getParameter(position);
	}

	@Override
	public <T> Parameter<T> getParameter(int position, Class<T> type) {
		return typedQuery.getParameter(position, type);
	}

	@Override
	public boolean isBound(Parameter<?> param) {
		return typedQuery.isBound(param);
	}

	@Override
	public <T> T getParameterValue(Parameter<T> param) {
		return typedQuery.getParameterValue(param);
	}

	@Override
	public Object getParameterValue(String name) {
		return typedQuery.getParameterValue(name);
	}

	@Override
	public Object getParameterValue(int position) {
		return typedQuery.getParameterValue(position);
	}

	@Override
	public FlushModeType getFlushMode() {
		return typedQuery.getFlushMode();
	}

	@Override
	public LockModeType getLockMode() {
		return typedQuery.getLockMode();
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		return typedQuery.unwrap(cls);
	}
}
