package com.ag.loy.adm.service.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CampaignProducts;
import com.ag.loy.adm.service.CampaignProductsService;

@Service
public class CampaignProductsServiceImpl implements CampaignProductsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveCampaignProducts(CampaignProducts campaignProducts) {
		try {
			entityManager.flush();
			entityManager.clear();
			entityManager.persist(campaignProducts);
		} catch (Exception nre) {
			nre.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void delete(String campaignProducts) {
		try {
			String sqlss = "DELETE FROM CAMPAIGN_PRODUCTS WHERE CAMPID ='" + campaignProducts + "'";
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From CAMPAIGN_PRODUCTS || QUERY : " + sqlss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void updateAwardMasters(CampaignProducts campaignProducts) {
		try {
			entityManager.merge(campaignProducts);
		} catch (Exception nre) {

		}

	}

	@Override
	public ArrayList<CampaignProducts> fetchAllRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignProducts.fetchAllRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<CampaignProducts>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ArrayList<CampaignProducts> fetchAllRecordByCampId(String campId, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignProducts.fetchAllRecordByCampId");
			cb.setParameter("corpId", corpId);
			cb.setParameter("campId", campId);
			return (ArrayList<CampaignProducts>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}