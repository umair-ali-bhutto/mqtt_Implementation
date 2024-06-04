package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.TapNPaySaf;
import com.ag.mportal.services.TapNPaySafService;

@Service
public class TapNPaySafServiceImpl implements TapNPaySafService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(TapNPaySaf tcn) {
		try {
			entityManager.persist(tcn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	@Transactional
	public void update(TapNPaySaf tcn) {
		try {
			entityManager.merge(tcn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public List<TapNPaySaf> fetchAll() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
			Query cb = entityManager.createNamedQuery("TapNPaySaf.fetchAll");
			return (List<TapNPaySaf>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}