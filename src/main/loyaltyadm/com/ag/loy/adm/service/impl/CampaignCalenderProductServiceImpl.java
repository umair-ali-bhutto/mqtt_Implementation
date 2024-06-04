package com.ag.loy.adm.service.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CampaignCalenderProduct;
import com.ag.loy.adm.service.CampaignCalenderProductService;

@Service
public class CampaignCalenderProductServiceImpl implements CampaignCalenderProductService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveCampaignProducts(CampaignCalenderProduct campaignProducts) {
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
			String sqlss = "DELETE FROM CAMPAIGN_CALENDAR_PRODUCTS WHERE CAMPID ='" + campaignProducts + "'";
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From CAMPAIGN_CALENDAR_PRODUCTS || QUERY : " + sqlss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void updateAwardMasters(CampaignCalenderProduct campaignProducts) {
		try {
			entityManager.merge(campaignProducts);

		} catch (Exception nre) {

		}

	}

	@Override
	public ArrayList<CampaignCalenderProduct> fetchAllRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignCalenderProduct.fetchAllRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<CampaignCalenderProduct>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ArrayList<CampaignCalenderProduct> fetchAllRecordByCampId(String campId, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignCalenderProduct.fetchAllRecordByCampId");
			cb.setParameter("corpId", corpId);
			cb.setParameter("campID", campId);
			return (ArrayList<CampaignCalenderProduct>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}