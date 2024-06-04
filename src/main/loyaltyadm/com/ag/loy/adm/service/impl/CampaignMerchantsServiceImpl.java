package com.ag.loy.adm.service.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CampaignMerchants;
import com.ag.loy.adm.service.CampaignMerchantsService;

@Service
public class CampaignMerchantsServiceImpl implements CampaignMerchantsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveCampaignCurrency(CampaignMerchants campaignMerchants) {
		try {
			entityManager.persist(campaignMerchants);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void updateCampaignCurrency(CampaignMerchants campaignMerchants) {
		try {
			entityManager.merge(campaignMerchants);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void delete(String campid) {
		try {
			String sqlss = "DELETE FROM CAMPAIGN_MERCHANTS WHERE CAMPID ='" + campid + "'";
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From CAMPAIGN_MERCHANTS || QUERY : " + sqlss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<CampaignMerchants> fetchAllRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignMerchants.fetchAllRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<CampaignMerchants>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ArrayList<CampaignMerchants> fetchAllRecordByCampId(String campId, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignMerchants.fetchAllRecordByCampId");
			cb.setParameter("corpId", corpId);
			cb.setParameter("campId", campId);
			return (ArrayList<CampaignMerchants>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}