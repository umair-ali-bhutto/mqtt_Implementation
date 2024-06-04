package com.ag.loy.adm.service.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CampaignMaster;
import com.ag.loy.adm.service.CampaignMasterService;

@Service
public class CampaignMasterServiceImpl implements CampaignMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ArrayList<CampaignMaster> fetchAllRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignMaster.fetchAllRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<CampaignMaster>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public CampaignMaster fetchAllRecordById(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CampaignMaster.fetchAllRecordById");
			cb.setParameter("corpId", corpId);
			cb.setParameter("campId", id);
			return (CampaignMaster) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long save(CampaignMaster campaignMaster) {
		try {
			entityManager.persist(campaignMaster);
			return Long.parseLong(campaignMaster.getCampId());
		} catch (Exception nre) {
			nre.printStackTrace();
			return 0l;
		}
	}

	@Override
	@Transactional
	public void update(CampaignMaster campaignMaster) {
		try {
			entityManager.merge(campaignMaster);
		} catch (Exception nre) {
		}

	}
	
	@Override
	public String fetchMaxCampId() {
		int maxId = 0;
		try {
			String q = AppProp.getProperty("fetch.max.campaign.id");
			Query query = entityManager.createNativeQuery(q);
			String res = (String) query.getSingleResult();
			maxId = Integer.parseInt(res);
		} catch (NoResultException nre) {
			maxId = 0;
		}
		maxId = maxId + 1;
		String max = String.valueOf(maxId);
		String Id = StringUtils.leftPad(max, 5, "0");

		return Id;
	}

}