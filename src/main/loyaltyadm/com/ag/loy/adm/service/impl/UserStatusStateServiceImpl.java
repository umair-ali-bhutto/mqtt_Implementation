package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.UserStatusState;
import com.ag.loy.adm.service.UserStatusStateService;

@Service
public class UserStatusStateServiceImpl implements UserStatusStateService {

	

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(UserStatusState acm) {
		try {
			entityManager.persist(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void update(UserStatusState acm) {
		try {
			entityManager.merge(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	public UserStatusState fetchById(String corpId, String accId) {
		try {
			Query query = entityManager.createNamedQuery("UserStatusState.fetchById", UserStatusState.class);
			query.setParameter("corpid", corpId);
			query.setParameter("accId", accId);
			UserStatusState lst = (UserStatusState) query.getSingleResult();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

	@Override
	public List<UserStatusState> fetchAllByCorpId(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("UserStatusState.fetchAllByCorpId", UserStatusState.class);
			query.setParameter("corpid", corpId);
			ArrayList<UserStatusState> lst = (ArrayList<UserStatusState>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}

}