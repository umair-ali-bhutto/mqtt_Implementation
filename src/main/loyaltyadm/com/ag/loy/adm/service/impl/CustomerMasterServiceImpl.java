package com.ag.loy.adm.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.loy.adm.entity.CustomerMaster;
import com.ag.loy.adm.service.CustomerMasterService;

@Service
public class CustomerMasterServiceImpl implements CustomerMasterService{
	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public CustomerMaster findAllByCidCorpId(String cid, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CustomerMaster.findAll");
			cb.setParameter("corpid", corpId);
			cb.setParameter("cid", cid);
			return (CustomerMaster) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}
