package com.github.honoluluhenk.idclass.integration.jpahibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Parameter;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TemporalType;
import javax.persistence.TransactionRequiredException;

@SuppressWarnings("rawtypes")
public class SmartQuery implements Query {
	private final Query query;

	public SmartQuery(@SuppressWarnings("CdiInjectionPointsInspection") Query query) {
		this.query = query;
	}

	/**
	 * Returns the delegate {@link Query} on which this instance is based.
	 *
	 * You should never need this except for maybe debugging purposes.
	 */
	public Query getDelegateQuery() {
		return query;
	}

	@Override
	public String toString() {
		return "SmartQuery[" + query + ']';
	}

	@Override
	public List getResultList() {
		return query.getResultList();
	}

	@Override
	public Stream getResultStream() {
		return query.getResultStream();
	}

	@Override
	public Object getSingleResult() {
		return query.getSingleResult();
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
	public Optional<Object> findOne() {
		try {
			return Optional.of(getSingleResult());
		} catch (NoResultException nre) {
			return Optional.empty();
		}
	}

	@Override
	public int executeUpdate() {
		return query.executeUpdate();
	}

	@Override
	public SmartQuery setMaxResults(int maxResult) {
		return new SmartQuery(query.setMaxResults(maxResult));
	}

	@Override
	public int getMaxResults() {
		return query.getMaxResults();
	}

	@Override
	public SmartQuery setFirstResult(int startPosition) {
		return new SmartQuery(query.setFirstResult(startPosition));
	}

	@Override
	public int getFirstResult() {
		return query.getFirstResult();
	}

	@Override
	public SmartQuery setHint(String hintName, Object value) {
		return new SmartQuery(query.setHint(hintName, value));
	}

	@Override
	public Map<String, Object> getHints() {
		return query.getHints();
	}

	@Override
	public <T> SmartQuery setParameter(Parameter<T> param, T value) {
		return new SmartQuery(query.setParameter(param, value));
	}

	@Override
	public SmartQuery setParameter(
			Parameter<Calendar> param,
			Calendar value,
			TemporalType temporalType) {
		return new SmartQuery(query.setParameter(param, value, temporalType));
	}

	@Override
	public SmartQuery setParameter(
			Parameter<Date> param,
			Date value,
			TemporalType temporalType) {
		return new SmartQuery(query.setParameter(param, value, temporalType));
	}

	@Override
	public SmartQuery setParameter(String name, Object value) {
		return new SmartQuery(query.setParameter(name, value));
	}

	@Override
	public SmartQuery setParameter(String name, Calendar value, TemporalType temporalType) {
		return new SmartQuery(query.setParameter(name, value, temporalType));
	}

	@Override
	public SmartQuery setParameter(String name, Date value, TemporalType temporalType) {
		return new SmartQuery(query.setParameter(name, value, temporalType));
	}

	@Override
	public SmartQuery setParameter(int position, Object value) {
		return new SmartQuery(query.setParameter(position, value));
	}

	@Override
	public SmartQuery setParameter(int position, Calendar value, TemporalType temporalType) {
		return new SmartQuery(query.setParameter(position, value, temporalType));
	}

	@Override
	public SmartQuery setParameter(int position, Date value, TemporalType temporalType) {
		return new SmartQuery(query.setParameter(position, value, temporalType));
	}

	@Override
	public Set<Parameter<?>> getParameters() {
		return query.getParameters();
	}

	@Override
	public Parameter<?> getParameter(String name) {
		return query.getParameter(name);
	}

	@Override
	public <T> Parameter<T> getParameter(String name, Class<T> type) {
		return query.getParameter(name, type);
	}

	@Override
	public Parameter<?> getParameter(int position) {
		return query.getParameter(position);
	}

	@Override
	public <T> Parameter<T> getParameter(int position, Class<T> type) {
		return query.getParameter(position, type);
	}

	@Override
	public boolean isBound(Parameter<?> param) {
		return query.isBound(param);
	}

	@Override
	public <T> T getParameterValue(Parameter<T> param) {
		return query.getParameterValue(param);
	}

	@Override
	public Object getParameterValue(String name) {
		return query.getParameterValue(name);
	}

	@Override
	public Object getParameterValue(int position) {
		return query.getParameterValue(position);
	}

	@Override
	public SmartQuery setFlushMode(FlushModeType flushMode) {
		return new SmartQuery(query.setFlushMode(flushMode));
	}

	@Override
	public FlushModeType getFlushMode() {
		return query.getFlushMode();
	}

	@Override
	public SmartQuery setLockMode(LockModeType lockMode) {
		return new SmartQuery(query.setLockMode(lockMode));
	}

	@Override
	public LockModeType getLockMode() {
		return query.getLockMode();
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		return query.unwrap(cls);
	}

}
