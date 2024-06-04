package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ComplStatus;
import com.ag.generic.service.ComplStatusService;
import com.ag.generic.util.AgLogger;

@Service
public class ComplStatusServiceImpl implements ComplStatusService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long insert(ComplStatus tcn) {
		try {
		entityManager.persist(tcn);
		return tcn.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public ComplStatus fetchByID(ComplStatus tcn) {
		return null;
	}

	@Override
	public ComplStatus fetchByOtherParams(ComplStatus tcn) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplStatus> fetchAll() {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
		Query cb = entityManager.createNamedQuery("ComplStatus.fetchAll");
		return (List<ComplStatus>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplStatus> fetchAllByID(long id) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
		Query cb = entityManager.createNamedQuery("ComplStatus.fetchByID").setParameter("id", id);
		return (List<ComplStatus>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void update(ComplStatus id) {
		try {
		entityManager.merge(id);
		} catch (NoResultException nre) {
			
		}
		}

}
