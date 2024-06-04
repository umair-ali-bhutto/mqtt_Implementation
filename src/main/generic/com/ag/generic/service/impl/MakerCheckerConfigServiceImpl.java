package com.ag.generic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.service.MakerCheckerConfigService;
import com.ag.generic.util.AgLogger;

@Service
public class MakerCheckerConfigServiceImpl implements MakerCheckerConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MakerCheckerConfig fetchByID(long id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNamedQuery("MakerCheckerConfig.fetchByID").setParameter("id", id);
			return (MakerCheckerConfig) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public MakerCheckerConfig fetchByScreenID(int screenId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
			Query cb = entityManager.createNamedQuery("MakerCheckerConfig.fetchByScreenID").setParameter("screenId",
					screenId);
			return (MakerCheckerConfig) cb.getSingleResult();
		} catch (Exception nre) {
			nre.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MakerCheckerConfig> fetchAll() {
		List<MakerCheckerConfig> lst = new ArrayList<MakerCheckerConfig>();
		try {
			String query = "SELECT * FROM MAKER_CHECKER_CONFIG WHERE ID IS NOT NULL AND IS_ACTIVE = 1";
			AgLogger.logInfo("fetchAll:" + query);
			Query cb = entityManager.createNativeQuery(query, MakerCheckerConfig.class);
			lst = (List<MakerCheckerConfig>) cb.getResultList();
			AgLogger.logDebug("", "Query: " + query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MakerCheckerConfig> fetchByCorpId(String corpId) {
		List<MakerCheckerConfig> lst = new ArrayList<MakerCheckerConfig>();
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByCorpId");
			Query cb = entityManager.createNamedQuery("MakerCheckerConfig.fetchByCorpId").setParameter("corpId",
					corpId);
			lst = (List<MakerCheckerConfig>) cb.getResultList();
		} catch (NoResultException nre) {
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MakerCheckerConfig> fetchByApproverGroupId(int GroupId) {
		List<MakerCheckerConfig> lst = new ArrayList<MakerCheckerConfig>();
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From FetchByApproverGroupId");
			Query cb = entityManager
					.createNamedQuery("MakerCheckerConfig.fetchByApproverGroupId", MakerCheckerConfig.class)
					.setParameter("groupId", GroupId);
			lst = (List<MakerCheckerConfig>) cb.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MakerCheckerConfig> fetchByVerifierGroupId(int GroupId) {
		List<MakerCheckerConfig> lst = new ArrayList<MakerCheckerConfig>();
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From FetchByVerifierGroupId");
			Query cb = entityManager
					.createNamedQuery("MakerCheckerConfig.fetchByVerifierGroupId", MakerCheckerConfig.class)
					.setParameter("groupId", GroupId);
			lst = (List<MakerCheckerConfig>) cb.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	@Override
	@Transactional
	public void updateGroupAssignedApprover(List<Long> str, int groupID) {
		try {
			AgLogger.logDebug(getClass(), "UpdateGroupAssignedApproverById Called");
			Query cb = entityManager.createNamedQuery("MakerCheckerConfig.updateGroupAssignedApproverById")
					.setParameter("groupId", groupID).setParameter("ids", str);
			cb.executeUpdate();
		} catch (NoResultException nre) {
		}
	}

	@Override
	@Transactional
	public void updateGroupAssignedVerifier(List<Long> str, int groupID) {
		try {
			AgLogger.logDebug(getClass(), "UpdateGroupAssignedVerifierById Called");
			Query cb = entityManager.createNamedQuery("MakerCheckerConfig.updateGroupAssignedVerifierById")
					.setParameter("groupId", groupID).setParameter("ids", str);
			cb.executeUpdate();
		} catch (NoResultException nre) {
		}
	}

}
