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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Parameter;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;

@SuppressWarnings("rawtypes")
public class SmartStoredProcedureQuery implements StoredProcedureQuery {
	private final StoredProcedureQuery storedProcedureQuery;

	public SmartStoredProcedureQuery(StoredProcedureQuery storedProcedureQuery) {
		this.storedProcedureQuery = storedProcedureQuery;
	}

	/**
	 * Returns the delegate {@link StoredProcedureQuery} on which this instance is based.
	 *
	 * You should never need this except for maybe debugging purposes.
	 */
	public StoredProcedureQuery getDelegateStoredProcedureQuery() {
		return storedProcedureQuery;
	}

	@Override
	public String toString() {
		return "SmartStoredProcedureQuery[" + storedProcedureQuery + ']';
	}

	@Override
	public SmartStoredProcedureQuery setHint(String hintName, Object value) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setHint(hintName, value));
	}

	@Override
	public <T> SmartStoredProcedureQuery setParameter(Parameter<T> param, T value) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setParameter(param, value));
	}

	@Override
	public SmartStoredProcedureQuery setParameter(
			Parameter<Calendar> param,
			Calendar value, TemporalType temporalType) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setParameter(param, value, temporalType));
	}

	@Override
	public SmartStoredProcedureQuery setParameter(
			Parameter<Date> param,
			Date value,
			TemporalType temporalType) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setParameter(param, value, temporalType));
	}

	@Override
	public SmartStoredProcedureQuery setParameter(String name, Object value) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setParameter(name, value));
	}

	@Override
	public SmartStoredProcedureQuery setParameter(
			String name,
			Calendar value,
			TemporalType temporalType) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setParameter(name, value, temporalType));
	}

	@Override
	public SmartStoredProcedureQuery setParameter(String name, Date value, TemporalType temporalType) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setParameter(name, value, temporalType));
	}

	@Override
	public SmartStoredProcedureQuery setParameter(int position, Object value) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setParameter(position, value));
	}

	@Override
	public SmartStoredProcedureQuery setParameter(
			int position,
			Calendar value,
			TemporalType temporalType) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setParameter(position, value, temporalType));
	}

	@Override
	public SmartStoredProcedureQuery setParameter(
			int position,
			Date value,
			TemporalType temporalType) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setParameter(position, value, temporalType));
	}

	@Override
	public SmartStoredProcedureQuery setFlushMode(FlushModeType flushMode) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.setFlushMode(flushMode));
	}

	@Override
	public SmartStoredProcedureQuery registerStoredProcedureParameter(
			int position,
			Class type,
			ParameterMode mode) {
		return new SmartStoredProcedureQuery(
				storedProcedureQuery.registerStoredProcedureParameter(
						position,
						type,
						mode));
	}

	@Override
	public SmartStoredProcedureQuery registerStoredProcedureParameter(
			String parameterName,
			Class type,
			ParameterMode mode) {
		return new SmartStoredProcedureQuery(storedProcedureQuery.registerStoredProcedureParameter(
				parameterName,
				type,
				mode));
	}

	@Override
	public Object getOutputParameterValue(int position) {
		return storedProcedureQuery.getOutputParameterValue(position);
	}

	@Override
	public Object getOutputParameterValue(String parameterName) {
		return storedProcedureQuery.getOutputParameterValue(parameterName);
	}

	@Override
	public boolean execute() {
		return storedProcedureQuery.execute();
	}

	@Override
	public int executeUpdate() {
		return storedProcedureQuery.executeUpdate();
	}

	@Override
	public List getResultList() {
		return storedProcedureQuery.getResultList();
	}

	@Override
	public Object getSingleResult() {
		return storedProcedureQuery.getSingleResult();
	}


	/**
	 * Wrap {@link #getSingleResult()} in a {@link Optional} in case of {@link NoResultException}.
	 *
	 * Throws all exceptions from {@link #getSingleResult()} for the same reasons
	 * (except NoResultException for obvious reaons).
	 *
	 * @throws NonUniqueResultException if more than one result
	 * @throws QueryTimeoutException if the query execution exceeds
	 *         the query timeout value set and only the statement is
	 *         rolled back
	 * @throws PersistenceException if the query execution exceeds
	 *         the query timeout value set and the transaction
	 *         is rolled back
	 */
	public Optional<Object> getSoleResult() {
		try {
			return Optional.of(getSingleResult());
		} catch (NoResultException nre) {
			return Optional.empty();
		}
	}

	@Override
	public boolean hasMoreResults() {
		return storedProcedureQuery.hasMoreResults();
	}

	@Override
	public int getUpdateCount() {
		return storedProcedureQuery.getUpdateCount();
	}

	@Override
	public Stream getResultStream() {
		return storedProcedureQuery.getResultStream();
	}

	@Override
	public SmartQuery setMaxResults(int maxResult) {
		return new SmartQuery(storedProcedureQuery.setMaxResults(maxResult));
	}

	@Override
	public int getMaxResults() {
		return storedProcedureQuery.getMaxResults();
	}

	@Override
	public SmartQuery setFirstResult(int startPosition) {
		return new SmartQuery(storedProcedureQuery.setFirstResult(startPosition));
	}

	@Override
	public int getFirstResult() {
		return storedProcedureQuery.getFirstResult();
	}

	@Override
	public Map<String, Object> getHints() {
		return storedProcedureQuery.getHints();
	}

	@Override
	public Set<Parameter<?>> getParameters() {
		return storedProcedureQuery.getParameters();
	}

	@Override
	public Parameter<?> getParameter(String name) {
		return storedProcedureQuery.getParameter(name);
	}

	@Override
	public <T> Parameter<T> getParameter(String name, Class<T> type) {
		return storedProcedureQuery.getParameter(name, type);
	}

	@Override
	public Parameter<?> getParameter(int position) {
		return storedProcedureQuery.getParameter(position);
	}

	@Override
	public <T> Parameter<T> getParameter(int position, Class<T> type) {
		return storedProcedureQuery.getParameter(position, type);
	}

	@Override
	public boolean isBound(Parameter<?> param) {
		return storedProcedureQuery.isBound(param);
	}

	@Override
	public <T> T getParameterValue(Parameter<T> param) {
		return storedProcedureQuery.getParameterValue(param);
	}

	@Override
	public Object getParameterValue(String name) {
		return storedProcedureQuery.getParameterValue(name);
	}

	@Override
	public Object getParameterValue(int position) {
		return storedProcedureQuery.getParameterValue(position);
	}

	@Override
	public FlushModeType getFlushMode() {
		return storedProcedureQuery.getFlushMode();
	}

	@Override
	public SmartQuery setLockMode(LockModeType lockMode) {
		return new SmartQuery(storedProcedureQuery.setLockMode(lockMode));
	}

	@Override
	public LockModeType getLockMode() {
		return storedProcedureQuery.getLockMode();
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		return storedProcedureQuery.unwrap(cls);
	}
}
