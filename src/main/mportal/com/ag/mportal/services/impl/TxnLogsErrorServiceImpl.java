package com.ag.mportal.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.TxnLogError;
import com.ag.mportal.services.TxnLogsErrorService;

@Service
public class TxnLogsErrorServiceImpl implements TxnLogsErrorService {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public void insertLog(TxnLogError que) throws Exception {
		try {
		entityManager.persist(que);
		} catch (NoResultException nre) {
			
		}
	}

}
