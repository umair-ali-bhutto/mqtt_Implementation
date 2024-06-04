package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ComplState;
import com.ag.generic.service.ComplStateService;
import com.ag.generic.util.AgLogger;

@Service
public class ComplStateServiceImpl implements ComplStateService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long insert(ComplState tcn) {
		try {
		entityManager.persist(tcn);
		return tcn.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public ComplState fetchByID(ComplState tcn) {
		return null;
	}

	@Override
	public ComplState fetchByOtherParams(ComplState tcn) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplState> fetchAll() {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
		Query cb = entityManager.createNamedQuery("ComplState.fetchAll");
		return (List<ComplState>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplState> fetchAllByID(long id) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
		Query cb = entityManager.createNamedQuery("ComplState.fetchByID").setParameter("id", id);
		return (List<ComplState>) cb.getResultList();
	} catch (NoResultException nre) {
		return null;
	}
	}

	@Override
	public void update(ComplState id) {
		try {
		entityManager.merge(id);
		} catch (NoResultException nre) {
			
		}
		}

}
