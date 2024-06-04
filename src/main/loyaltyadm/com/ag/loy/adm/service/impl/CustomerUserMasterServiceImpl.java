package com.ag.loy.adm.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CustomerMaster;
import com.ag.loy.adm.entity.CustomerUserMaster;
import com.ag.loy.adm.service.CustomerUserMasterService;


@Service
public class CustomerUserMasterServiceImpl implements CustomerUserMasterService {
	
	@PersistenceContext
	private EntityManager entityManager;

    @Override
	public List<CustomerUserMaster> findAllByCidCorpId(String cid, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CustomerUserMaster.findAll");
			cb.setParameter("corpid", corpId);
			cb.setParameter("cid", cid);
			return (List<CustomerUserMaster>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}
    
	
	@Override
	public List<CustomerUserMaster> fetchAllByUserCode(String userCode) {
		try {
			String q = "select userid from customer_user_master where cid = '" + userCode + "' order by userid";
			AgLogger.logInfo(getClass(), q);
			Query query = entityManager.createNativeQuery(q);
			List<CustomerUserMaster> lst = (List<CustomerUserMaster>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING CUSTOMERMASTER LOV: ", ex);
		}
		return null;
	}
    
    
    @Override
	public List<CustomerUserMaster> findAllByCardNoCorpId(String cardNumber, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CustomerUserMaster.findAllByCardNumber");
			cb.setParameter("corpid", corpId);
			cb.setParameter("cardNumber", cardNumber);
			return (List<CustomerUserMaster>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}
    @Override
   	public CustomerUserMaster findAllByUidCorpId(String uid, String corpId) {
   		try {
   			Query cb = entityManager.createNamedQuery("CustomerUserMaster.findAllByUid");
   			cb.setParameter("corpid", corpId);
   			cb.setParameter("userid", uid);
   			return (CustomerUserMaster) cb.getSingleResult();
   		} catch (NoResultException nre) {
   			return null;
   		}
   	}
	
}
