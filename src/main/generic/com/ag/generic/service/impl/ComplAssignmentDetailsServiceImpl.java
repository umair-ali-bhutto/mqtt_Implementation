package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ComplAssignmentDetail;
import com.ag.generic.service.ComplAssignmentDetailsService;
import com.ag.generic.util.AgLogger;

@Service
public class ComplAssignmentDetailsServiceImpl implements ComplAssignmentDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long insert(ComplAssignmentDetail tcn) {
		try {
		entityManager.persist(tcn);
		return tcn.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public ComplAssignmentDetail fetchByID(ComplAssignmentDetail tcn) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
		Query cb = entityManager.createNamedQuery("ComplAssignmentDetails.fetchByID").setParameter("id", tcn.getId());
		return (ComplAssignmentDetail) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ComplAssignmentDetail fetchByOtherParams(ComplAssignmentDetail tcn) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
		Query cb = entityManager.createNamedQuery("ComplAssignmentDetails.fetchByOtherParams").setParameter("id",
				tcn.getId());
		return (ComplAssignmentDetail) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplAssignmentDetail> fetchAll() {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
		// ADD ORDER BY ID asc clause
		Query cb = entityManager.createNamedQuery("ComplAssignmentDetails.fetchAll");
		return (List<ComplAssignmentDetail>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplAssignmentDetail> fetchAllByID(long id) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllByID");
		// ADD ORDER BY ID asc clause
		Query cb = entityManager.createNamedQuery("ComplAssignmentDetails.fetchAllByID").setParameter("id", id);
		return (List<ComplAssignmentDetail>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void update(ComplAssignmentDetail id) {
		try {
		entityManager.merge(id);
		} catch (NoResultException nre) {
			
		}
	}

}
