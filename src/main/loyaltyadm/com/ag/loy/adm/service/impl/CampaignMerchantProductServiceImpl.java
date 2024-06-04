package com.ag.loy.adm.service.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CampaignMerchantProduct;
import com.ag.loy.adm.service.CampaignMerchantProductService;

@Service
public class CampaignMerchantProductServiceImpl implements CampaignMerchantProductService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveCampaignProducts(CampaignMerchantProduct campaignProducts) {
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
			String sqlss = "DELETE FROM CAMPAIGN_MERCHANTS_PRODUCTS WHERE CAMPID ='" + campaignProducts + "'";
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From CAMPAIGN_MERCHANTS_PRODUCTS || QUERY : " + sqlss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void updateAwardMasters(CampaignMerchantProduct campaignProducts) {
		try {
			entityManager.merge(campaignProducts);
		} catch (Exception nre) {
		}

	}

	@Override
	public ArrayList<CampaignMerchantProduct> fetchAllRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignMerchantProduct.fetchAllRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<CampaignMerchantProduct>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ArrayList<CampaignMerchantProduct> fetchAllRecordByCampId(String campId, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignMerchantProduct.fetchAllRecordByCampId");
			cb.setParameter("corpId", corpId);
			cb.setParameter("campID", campId);
			return (ArrayList<CampaignMerchantProduct>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}