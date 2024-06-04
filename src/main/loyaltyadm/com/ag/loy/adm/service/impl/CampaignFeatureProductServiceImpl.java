package com.ag.loy.adm.service.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CampaignFeatureProduct;
import com.ag.loy.adm.service.CampaignFeatureProductService;

@Service
public class CampaignFeatureProductServiceImpl implements CampaignFeatureProductService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveCampaignProducts(CampaignFeatureProduct campaignProducts) {
		try {

			entityManager.persist(campaignProducts);
			entityManager.flush();
			entityManager.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void delete(String campaignProducts) {
		try {
			String sqlss = "DELETE FROM CAMPAIGN_FEATURE_PRODUCTS WHERE CAMPID ='" + campaignProducts + "'";
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From CAMPAIGN_FEATURE_PRODUCTS || QUERY : " + sqlss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void updateAwardMasters(CampaignFeatureProduct campaignProducts) {
		try {
			entityManager.merge(campaignProducts);
		} catch (Exception nre) {
		}

	}

	@Override
	public ArrayList<CampaignFeatureProduct> fetchAllRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignFeatureProduct.fetchAllRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<CampaignFeatureProduct>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ArrayList<CampaignFeatureProduct> fetchAllRecordByCampId(String campId, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignFeatureProduct.fetchAllRecordByCampId");
			cb.setParameter("corpId", corpId);
			cb.setParameter("campID", campId);
			return (ArrayList<CampaignFeatureProduct>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}