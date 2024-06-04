package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CorporateRdmRatio;
import com.ag.loy.adm.service.CorporateRdmRatioService;

@Service
public class CorporateRdmRatioServiceImpl implements CorporateRdmRatioService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(CorporateRdmRatio acm) {
		try {
			entityManager.persist(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void update(CorporateRdmRatio acm) {
		try {
			entityManager.merge(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	public CorporateRdmRatio fetchById(String corpId, String accId) {
		try {
			Query query = entityManager.createNamedQuery("CorporateRdmRatio.fetchById", CorporateRdmRatio.class);
			query.setParameter("corpid", corpId);
			query.setParameter("accId", accId);
			CorporateRdmRatio lst = (CorporateRdmRatio) query.getSingleResult();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

	@Override
	public List<CorporateRdmRatio> fetchAllByCorpId(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("CorporateRdmRatio.fetchAllByCorpId", CorporateRdmRatio.class);
			query.setParameter("corpid", corpId);
			ArrayList<CorporateRdmRatio> lst = (ArrayList<CorporateRdmRatio>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

}