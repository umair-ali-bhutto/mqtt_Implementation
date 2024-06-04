package com.ag.metro.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.metro.entity.MetroBatchDetails;
import com.ag.metro.services.MetroBatchDetailService;

@Service
public class MetroBatchDetailServiceImpl implements MetroBatchDetailService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insertMetroBatchSummary(MetroBatchDetails adt) {
		try {
			
			entityManager.merge(adt);
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}

	}

}
