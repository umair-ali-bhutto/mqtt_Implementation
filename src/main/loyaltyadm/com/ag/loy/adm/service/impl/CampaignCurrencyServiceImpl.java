package com.ag.loy.adm.service.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CampaignCurrency;
import com.ag.loy.adm.service.CampaignCurrencyService;

@Service
public class CampaignCurrencyServiceImpl implements CampaignCurrencyService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveCampaignCurrency(CampaignCurrency campaignCurrency) {
		try {
			entityManager.flush();
			entityManager.clear();
			entityManager.persist(campaignCurrency);
		} catch (Exception nre) {
			nre.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void updateCampaignCurrency(CampaignCurrency campaignCurrency) {
		try {
			entityManager.merge(campaignCurrency);
		} catch (Exception nre) {
		}

	}

	@Override
	public ArrayList<CampaignCurrency> fetchAllRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignCurrency.fetchAllRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<CampaignCurrency>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ArrayList<CampaignCurrency> fetchAllRecordByCampId(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignCurrency.fetchAllRecordByCampId");
			cb.setParameter("corpId", corpId);
			cb.setParameter("campId", id);
			return (ArrayList<CampaignCurrency>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void delete(String campaignProducts) {
		try {
			String sqlss = "DELETE FROM CAMPAIGN_CURRENCY WHERE CAMPID ='" + campaignProducts + "'";
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From CAMPAIGN_CURRENCY || QUERY : " + sqlss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}