package com.ag.loy.adm.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.UserStatusLog;
import com.ag.loy.adm.service.UserStatusLogService;

@Service
public class UserStatusLogServiceImpl implements UserStatusLogService{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<UserStatusLog> fetchAll() {
		try {
			Query cb = entityManager.createNamedQuery("UserStatusLog.fetchAll",UserStatusLog.class);
			return (List<UserStatusLog>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserStatusLog fetchByCardNo(String corpId, String cardNumber) {
		try {
   			Query cb = entityManager.createNamedQuery("UserStatusLog.fetchByCardNo",UserStatusLog.class);
   			cb.setParameter("userid",cardNumber );
   			cb.setParameter("corpid",corpId );
   			return (UserStatusLog) cb.getSingleResult();
   		} catch (NoResultException nre) {
   			return null;
   		}
	}

}
