package com.ag.mportal.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.BatchSettlement;
import com.ag.mportal.services.BatchSettlementService;

@Service
public class BatchSettlementServiceImpl implements BatchSettlementService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insertBatch(BatchSettlement tcn) {
		try {
			entityManager.persist(tcn);
		} catch (NoResultException nre) {

		}
	}

}
