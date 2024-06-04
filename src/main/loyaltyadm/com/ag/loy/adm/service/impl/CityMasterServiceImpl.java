package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CityMaster;
import com.ag.loy.adm.service.CityMasterService;

@Service
public class CityMasterServiceImpl implements CityMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(CityMaster acm) {
		try {
			entityManager.persist(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void update(CityMaster acm) {
		try {
			entityManager.merge(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	public CityMaster fetchById(String corpId, String accId) {
		try {
			Query query = entityManager.createNamedQuery("CityMaster.fetchById", CityMaster.class);
			query.setParameter("corpid", corpId);
			query.setParameter("accId", accId);
			CityMaster lst = (CityMaster) query.getSingleResult();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

	@Override
	public List<CityMaster> fetchAllByCorpId(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("UserStatusState.fetchAllByCorpId", CityMaster.class);
			query.setParameter("corpid", corpId);
			ArrayList<CityMaster> lst = (ArrayList<CityMaster>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}


}