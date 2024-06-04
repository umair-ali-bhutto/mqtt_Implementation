package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.AccountMaster;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.service.AccountMasterService;

@Service
public class AccountMasterServiceImpl implements AccountMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(AccountMaster acm) {
		try {
			entityManager.persist(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void update(AccountMaster acm) {
		try {
			entityManager.merge(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	public AccountMaster fetchById(String corpId, String accId) {
		try {
			Query query = entityManager.createNamedQuery("AccountMaster.fetchById", AccountMaster.class);
			query.setParameter("corpid", corpId);
			query.setParameter("accId", accId);
			AccountMaster lst = (AccountMaster) query.getSingleResult();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

	@Override
	public List<AccountMaster> fetchAllByUserCode(String userCode) {
		try {
			String q = "select acc_id from account_master where cid = '" + userCode + "' order by acc_id";
			AgLogger.logInfo(getClass(), q);
			Query query = entityManager.createNativeQuery(q);
			List<AccountMaster> lst = (List<AccountMaster>) query.getResultList();
			
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountsMaster LOV: ", ex);
		}
		return null;
	}

	@Override
	public List<AccountMaster> fetchAllByCorpId(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("AccountMaster.fetchAllByCorpId", AccountMaster.class);
			query.setParameter("corpid", corpId);
			ArrayList<AccountMaster> lst = (ArrayList<AccountMaster>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}
}