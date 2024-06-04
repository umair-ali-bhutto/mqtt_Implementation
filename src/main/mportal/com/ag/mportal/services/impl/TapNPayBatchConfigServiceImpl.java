package com.ag.mportal.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.TapNPayBatchConfig;
import com.ag.mportal.services.TapNPayBatchConfigService;

@Service
public class TapNPayBatchConfigServiceImpl implements TapNPayBatchConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TapNPayBatchConfig fetchBatchConfig(String mid, String tid) {
		try {
			Query cb = entityManager.createNamedQuery("TapNPayBatchConfig.retrieveAll");
			cb.setParameter("mid", mid);
			cb.setParameter("tid", tid);
			return (TapNPayBatchConfig) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void updateBatch(TapNPayBatchConfig tcf) {
		entityManager.merge(tcf);
	}

}
