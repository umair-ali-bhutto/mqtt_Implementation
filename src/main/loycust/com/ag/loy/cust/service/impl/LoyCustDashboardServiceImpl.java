package com.ag.loy.cust.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.cust.service.LoyCustDashboardService;


@Service
public class LoyCustDashboardServiceImpl implements LoyCustDashboardService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String getCidFromAccountUser(String corpId, String cardNumber) {
		try {
			String sql = "select userid  from account_users d where corpid ='"+corpId+"' and cid = '"+cardNumber+"' and cr_on = (select max(j.cr_on) from account_users j where j.cid=d.cid)";
			AgLogger.logInfo(sql);
			Query cb = entityManager.createNativeQuery(sql);
			return (String) cb.getSingleResult();
			} catch (NoResultException nre) {
				return "N/A";
			}
	}

	@Override
	public String getCustIdFromAccountUser(String corpId, String uid) {
		try {
			String sql = "select cid  from account_users d where d.corpid ='"+corpId+"' and d.userid = '"+uid+"' ";
			AgLogger.logInfo(sql);
			Query cb = entityManager.createNativeQuery(sql);
			return (String) cb.getSingleResult();
			} catch (NoResultException nre) {
				return "N/A";
			}
	}
	

}
