package com.ag.loy.adm.service.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CampaignFeatures;
import com.ag.loy.adm.service.CampaignFeaturesService;

@Service
public class CampaignFeaturesServiceImpl implements CampaignFeaturesService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveCampaignFeatures(CampaignFeatures campaignFeatures) {
		try {
			entityManager.flush();
			entityManager.clear();
			entityManager.persist(campaignFeatures);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void updateCampaignFeatures(CampaignFeatures campaignFeatures) {
		try {
			entityManager.merge(campaignFeatures);
		} catch (Exception nre) {
		}

	}

	@Override
	public ArrayList<CampaignFeatures> fetchAllRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignFeatures.fetchAllRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<CampaignFeatures>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ArrayList<CampaignFeatures> fetchAllRecordByCampId(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignFeatures.fetchAllRecordByCampId");
			cb.setParameter("corpId", corpId);
			cb.setParameter("campId", id);
			return (ArrayList<CampaignFeatures>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void delete(String campID) {
		try {
			String sqlss = "DELETE FROM CAMPAIGN_FEATURES WHERE CAMPID ='" + campID + "'";
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From CAMPAIGN_FEATURES || QUERY : " + sqlss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}