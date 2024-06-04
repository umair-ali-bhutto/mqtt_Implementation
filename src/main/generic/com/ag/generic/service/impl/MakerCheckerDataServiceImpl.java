package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.util.AgLogger;

@Service
public class MakerCheckerDataServiceImpl implements MakerCheckerDataService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(MakerCheckerData tcn) {
		try {
			entityManager.persist(tcn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	@Transactional
	public void update(MakerCheckerData tcn) {
		try {
			entityManager.merge(tcn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public MakerCheckerData fetchByID(long id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNamedQuery("MakerCheckerData.fetchByID").setParameter("id", id);
			return (MakerCheckerData) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public MakerCheckerData fetchByRecID(long id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNamedQuery("MakerCheckerData.fetchByRecID").setParameter("id", id);
			return (MakerCheckerData) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public MakerCheckerData fetchByUniqueIdentifier(Long id, String uq, String uq2, String uq3) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
			Query cb = entityManager.createNamedQuery("MakerCheckerData.fetchByUniqueIdentifier")
					.setParameter("screenId", id).setParameter("uniqueidentifier", uq)
					.setParameter("uniqueidentifier2", uq2).setParameter("uniqueidentifier3", uq3);
			return (MakerCheckerData) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public MakerCheckerData fetchByUniqueIdentifierVerifier(Long id, String uq, String uq2, String uq3) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByUniqueIdentifierVerifier");
			Query cb = entityManager.createNamedQuery("MakerCheckerData.fetchByUniqueIdentifierVerifier")
					.setParameter("screenId", id).setParameter("uniqueidentifier", uq)
					.setParameter("uniqueidentifier2", uq2).setParameter("uniqueidentifier3", uq3);
			return (MakerCheckerData) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<MakerCheckerData> fetchAllByViewerID(int viewerId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllByViewerID");
			Query cb = entityManager.createNamedQuery("MakerCheckerData.fetchByScreenID").setParameter("viewerId",
					viewerId);
			return (List<MakerCheckerData>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<MakerCheckerData> fetchbyGroupCode(String groupId) {
		try {
			String sql = "SELECT m.ID ,m.CONFIG_ID,m.SCREEN_ID,m.REQUESTED_DATA,m.REQUESTED_BY, m.REQUESTED_ON, "
					+ "m.REQUESTED_BY_REMARKS,m.STATUS, m.APPROVED_BY,m.APPROVED_ON,m.APPROVER_REMARKS,m.VIEWER_ID, "
					+ "m.REJECTED_BY,m.REJECTED_ON,m.REJECTION_REMARKS,m.ACTION_TYPE,m.PAGE_NAME, us.SCREEN_DESC as UNIQUE_IDENTIFIER, "
					+ "u.USER_NAME as UNIQUE_IDENTIFIER_2 ,u.USER_CODE AS UNIQUE_IDENTIFIER_3, m.VERIFIED_BY , m.VERIFIED_ON , "
					+ "m.VERIFIER_REMARKS from MAKER_CHECKER_DATA m,users_login u,USER_SCREENS us,MAKER_CHECKER_CONFIG mc  "
					+ "where m.CONFIG_ID = mc.ID and m.requested_by = u.user_Id and m.screen_Id = us.screen_Id and "
					+ "m.STATUS = 'PROCESS' AND mc.GROUP_ASSIGNED_APPROVER = " + groupId + "  ORDER BY m.ID desc";
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams" + sql);
			Query cb = entityManager.createNativeQuery(sql, MakerCheckerData.class);
			return (List<MakerCheckerData>) cb.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<MakerCheckerData> fetchVerifierbyGroupCode(String groupId) {
		try {
			String sql = "SELECT m.ID ,m.CONFIG_ID,m.SCREEN_ID,m.REQUESTED_DATA,m.REQUESTED_BY, m.REQUESTED_ON,\n"
					+ "m.REQUESTED_BY_REMARKS,m.STATUS, m.APPROVED_BY,m.APPROVED_ON,m.APPROVER_REMARKS,m.VIEWER_ID,\n"
					+ "m.REJECTED_BY,m.REJECTED_ON,us.ROUTER_LINK as REJECTION_REMARKS,m.ACTION_TYPE,m.PAGE_NAME,\n"
					+ "us.SCREEN_DESC as UNIQUE_IDENTIFIER,u.USER_NAME as UNIQUE_IDENTIFIER_2 ,u.USER_CODE AS UNIQUE_IDENTIFIER_3,\n"
					+ "m.VERIFIED_BY , m.VERIFIED_ON , m.VERIFIER_REMARKS from MAKER_CHECKER_DATA m,users_login u,USER_SCREENS us,\n"
					+ "MAKER_CHECKER_CONFIG mc  where m.CONFIG_ID = mc.ID and m.requested_by = u.user_Id and m.screen_Id = us.screen_Id \n"
					+ "and m.STATUS = 'PROCESS_FOR_VERIFIER' AND mc.GROUP_ASSIGNED_VERIFIER = " + groupId
					+ " ORDER BY m.ID desc";
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams" + sql);
			Query cb = entityManager.createNativeQuery(sql, MakerCheckerData.class);
			return (List<MakerCheckerData>) cb.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
