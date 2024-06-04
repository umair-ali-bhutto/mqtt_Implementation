package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.util.AgLogger;

@Service
public class ComplAssignmentsServiceImpl implements ComplAssignmentsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insert(ComplAssignment id) {
		try {
			AgLogger.logInfo("Complaints Assignmnet Inserted.");
			entityManager.persist(id);
			return id.getId();
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public ComplAssignment fetchByID(int tcn) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNamedQuery("ComplAssignment.fetchByID").setParameter("id",
					Long.valueOf(tcn));
			return (ComplAssignment) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ComplAssignment fetchByOtherParams(long id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
			Query cb = entityManager.createNamedQuery("ComplAssignment.fetchByOtherParams").setParameter("id", id);
			return (ComplAssignment) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplAssignment> fetchAll() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
			// ADD ORDER BY CLAUSE ID ASC
			Query cb = entityManager.createNamedQuery("ComplAssignment.fetchAll");
			return (List<ComplAssignment>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplAssignment> fetchAllByID(int id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllByID");
			Query cb = entityManager.createNamedQuery("ComplAssignment.fetchAllByID").setParameter("userId", id);
			return (List<ComplAssignment>) cb.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public void update(ComplAssignment id) {
		try {
			entityManager.merge(id);
		} catch (NoResultException nre) {

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplAssignment> fetchAllByIDByCategory(int id, String category, String type, String subType) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllByIDByCategory");
			// ADD ORDER BY CLAUSE ID ASC
			// ADD procBy is null and procDate is null
			Query cb = entityManager.createNamedQuery("ComplAssignment.fetchAllByIDByCategory")
					.setParameter("userId", id).setParameter("category", category).setParameter("type", type)
					.setParameter("subType", subType);
			return (List<ComplAssignment>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ComplAssignment fetchByComplID(int compId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByComplID");
			// ADD ORDER BY CLAUSE ID ASC
			Query cb = entityManager.createNamedQuery("ComplAssignment.fetchByComplID").setParameter("compId", compId);
			return (ComplAssignment) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ComplAssignment fetchRecordHavingProcByAndProcDateNull(int tcn) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchRecordHavingProcByAndProcDateNull");
			// ADD ORDER BY CLAUSE ID ASC
			// ADD procBy is null and procDate is null
			Query cb = entityManager.createNamedQuery("ComplAssignment.fetchRecordHavingProcByAndProcDateNull")
					.setParameter("compId", tcn);
			return (ComplAssignment) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
