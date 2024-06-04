package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CustomerStatusMaster;
import com.ag.loy.adm.service.CustomerStatusMasterService;

@Service
public class CustomerStatusMasterServiceImpl implements CustomerStatusMasterService{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CustomerStatusMaster> fetchAllByCorpIdCusttype(String corpId,String custtype) {
		try {
			Query query = entityManager.createNamedQuery("CustomerStatusMaster.findAll", CustomerStatusMaster.class);
			query.setParameter("corpid", corpId);
			query.setParameter("custtype", custtype);
			ArrayList<CustomerStatusMaster> lst = (ArrayList<CustomerStatusMaster>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}
}
