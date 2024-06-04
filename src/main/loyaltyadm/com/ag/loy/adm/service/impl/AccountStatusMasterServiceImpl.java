package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.AccountStatusMaster;
import com.ag.loy.adm.service.AccountStatusMasterService;

@Service
public class AccountStatusMasterServiceImpl implements AccountStatusMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<AccountStatusMaster> fetchAllByCorpIdAcctype(String corpId,String acctype) {
		try {
			Query query = entityManager.createNamedQuery("AccountStatusMaster.fetchAll", AccountStatusMaster.class);
			query.setParameter("corpid", corpId);
			query.setParameter("acctype", acctype);
			ArrayList<AccountStatusMaster> lst = (ArrayList<AccountStatusMaster>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountStatusMaster: ", ex);
		}
		return null;
	}


}