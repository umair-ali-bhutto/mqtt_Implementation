package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ComplAssignmentSetup;
import com.ag.generic.service.ComplAssignmentsSetupService;
import com.ag.generic.util.AgLogger;

@Service
public class ComplAssignmentsSetupServiceImpl implements ComplAssignmentsSetupService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long insert(ComplAssignmentSetup tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public ComplAssignmentSetup fetchByID(long tcn) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNamedQuery("ComplAssignmentSetup.fetchByID").setParameter("id", tcn);
			return (ComplAssignmentSetup) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ComplAssignmentSetup fetchByOtherParams(String category, String type, String subType, int level) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
			Query cb = entityManager.createNamedQuery("ComplAssignmentSetup.fetchByOtherParams")
					.setParameter("category", category).setParameter("type", type).setParameter("subType", subType)
					.setParameter("level", level);
			return (ComplAssignmentSetup) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplAssignmentSetup> fetchAll() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
			Query cb = entityManager.createNamedQuery("ComplAssignmentSetup.fetchAll");
			return (List<ComplAssignmentSetup>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplAssignmentSetup> fetchAllByID(long id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllByID");
			Query cb = entityManager.createNamedQuery("ComplAssignmentSetup.fetchAllByID").setParameter("id", id);
			return (List<ComplAssignmentSetup>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void update(ComplAssignmentSetup id) {
		try {
			entityManager.merge(id);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public ComplAssignmentSetup fetchRecordByOtherParams(String category, String type, String subType, int level) {

		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNamedQuery("ComplAssignmentSetup.fetchRecordByOtherParams")
					.setParameter("category", category).setParameter("type", type).setParameter("subType", subType)
					.setParameter("level", level);
			return (ComplAssignmentSetup) cb.getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}
	}

}
