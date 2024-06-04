package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.TxnLogsDetail;
import com.ag.mportal.services.TxnLogsDetailService;

@Service
public class TxnLogsDetailServiceImpl implements TxnLogsDetailService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public long insertLog(List<TxnLogsDetail> que) throws Exception {
		try {
		entityManager.persist(que);
		return 0;
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

}
