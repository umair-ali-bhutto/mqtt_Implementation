package com.ag.loy.adm.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.TransactionTypeMaster;
import com.ag.loy.adm.service.TransactionTypeMasterService;

@Service
public class TransactionTypeMasterServiceImpl implements TransactionTypeMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveTxnTypeMaster(TransactionTypeMaster sdt) {
		try {
			entityManager.merge(sdt);
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}

	@Override
	public TransactionTypeMaster fetchAllRecordById(String id,String corpId) {
		try {
			Query query = entityManager.createNamedQuery("TransactionTypeMaster.retrieveAllById");
			query.setParameter("corpId", corpId);
			query.setParameter("txnType", id);
			return (TransactionTypeMaster) query.getSingleResult();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING TXN TYPE MASTER: ", ex);
		}
		return null;
	}

	@Override
	public List<TransactionTypeMaster> fetchAllRecord(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("TransactionTypeMaster.retrieveAll");
			query.setParameter("corpId", corpId);
			return (List<TransactionTypeMaster>) query.getResultList();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING TXN TYPE MASTER: ", ex);
		}
		return null;
	}
	
	
	@Override
	public HashMap<Object, Object> fetchAllRecordMap(String corpId) {
		HashMap<Object, Object> mp = new HashMap<Object, Object>();
		try {
			Query query = entityManager.createNamedQuery("TransactionTypeMaster.retrieveAll");
			query.setParameter("corpId", corpId);
			List<TransactionTypeMaster> lst =  (List<TransactionTypeMaster>) query.getResultList();
			
			for (TransactionTypeMaster m : lst) {
				mp.put(m.getId().getTxnType(), m.getMappedId());
			}

			return mp;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING TXN TYPE MASTER: ", ex);
		}
		return null;
	}


}