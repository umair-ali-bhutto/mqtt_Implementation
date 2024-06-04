package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.UserStatusMaster;
import com.ag.loy.adm.service.UserStatusService;

@Service
public class UserStatusServiceImpl implements UserStatusService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(UserStatusMaster acm) {
		try {
			entityManager.persist(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void update(UserStatusMaster acm) {
		try {
			entityManager.merge(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	public UserStatusMaster fetchById(String corpId, String accId) {
		try {
			Query query = entityManager.createNamedQuery("UserStatusMaster.fetchById", UserStatusMaster.class);
			query.setParameter("corpid", corpId);
			query.setParameter("accId", accId);
			UserStatusMaster lst = (UserStatusMaster) query.getSingleResult();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

	@Override
	public List<UserStatusMaster> fetchAllByCorpId(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("UserStatusMaster.fetchAllByCorpId", UserStatusMaster.class);
			query.setParameter("corpid", corpId);
			ArrayList<UserStatusMaster> lst = (ArrayList<UserStatusMaster>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

	@Override
	public List<UserStatusMaster> fetchByCorpId(String corpId) {
		try {
			String q = "select * from user_status_master where corpid='" + corpId + "'";
			AgLogger.logInfo(getClass(), q);
			Query query = entityManager.createNativeQuery(q, UserStatusMaster.class);
			List<UserStatusMaster> lst = (List<UserStatusMaster>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

	@Override
	public List<UserStatusMaster> fetchAllByCorpIdUtype(String corpId, String usertype) {
		try {
			Query query = entityManager.createNamedQuery("UserStatusMaster.findAll", UserStatusMaster.class);
			query.setParameter("corpid", corpId);
			query.setParameter("usertype", usertype);
			ArrayList<UserStatusMaster> lst = (ArrayList<UserStatusMaster>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING UserStatusMaster: ", ex);
		}
		return null;
	}

	@Override
	public UserStatusMaster fetchByUsertype(String corpId, String usertype) {
		try {
			Query query = entityManager.createNamedQuery("UserStatusMaster.findByUsertype", UserStatusMaster.class);
			query.setParameter("corpid", corpId);
			query.setParameter("usertype", usertype);
			UserStatusMaster lst = (UserStatusMaster) query.getSingleResult();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING UserStatusMaster: ", ex);
		}
		return null;
	}

}