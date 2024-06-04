package com.ag.loy.adm.service.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CampaignCalender;
import com.ag.loy.adm.service.CampaignCalenderService;

@Service
public class CampaignCalenderServiceImpl implements CampaignCalenderService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ArrayList<CampaignCalender> fetchAllRecordByCampId(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignCalender.fetchAllRecordByCampId");
			cb.setParameter("corpId", corpId);
			cb.setParameter("campID", id);
			return (ArrayList<CampaignCalender>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void save(CampaignCalender comp) {
		try {
			entityManager.flush();
			entityManager.clear();
			entityManager.persist(comp);
		} catch (Exception nre) {
			nre.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void delete(String campaignProducts) {
		try {
			String sqlss = "DELETE FROM CAMPAIGN_CALENDAR WHERE CAMPID ='" + campaignProducts + "'";
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From CAMPAIGN_CALENDAR || QUERY : " + sqlss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}