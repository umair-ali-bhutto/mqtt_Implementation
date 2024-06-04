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
import com.ag.loy.adm.entity.AwardMasters;
import com.ag.loy.adm.service.AwardMastersService;

@Service
public class AwardMastersServiceImpl implements AwardMastersService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Long saveAwardMasters(AwardMasters awardMasters) {
		try {
			entityManager.persist(awardMasters);
			return awardMasters.getmAwardId();
		} catch (Exception nre) {
			return 0l;
		}
	}

	@Override
	@Transactional
	public boolean updateAwardMasters(AwardMasters awardMasters) {
		try {
			entityManager.merge(awardMasters);
			return true;
		} catch (Exception nre) {
			nre.printStackTrace();
			return false;

		}
	}

	@Override
	public ArrayList<AwardMasters> fetchAllRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("AwardMasters.fetchAllRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<AwardMasters>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	@Override
	public ArrayList<AwardMasters> fetchAllActiveRecords(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("AwardMasters.fetchAllActiveRecords");
			cb.setParameter("corpId", corpId);
			return (ArrayList<AwardMasters>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ArrayList<AwardMasters> fetchAllRecordById(Long id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("AwardMasters.fetchAllRecordById");
			cb.setParameter("corpId", corpId);
			cb.setParameter("mAwardId", id);
			return (ArrayList<AwardMasters>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public AwardMasters fetchByAwardName(String awardName, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("AwardMasters.fetchByAwardName");
			cb.setParameter("corpId", corpId);
			cb.setParameter("description", awardName);
			return (AwardMasters) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	@Override
	public String fetchMaxAwardId() {
		int maxId = 0;
		try {
			String q = AppProp.getProperty("fetch.max.award.id");
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