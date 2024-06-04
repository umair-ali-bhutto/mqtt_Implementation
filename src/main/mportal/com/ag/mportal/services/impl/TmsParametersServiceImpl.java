package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.TmsParameters;
import com.ag.mportal.entity.TmsParametersDefaultValues;
import com.ag.mportal.services.TmsParametersService;

@Service
public class TmsParametersServiceImpl implements TmsParametersService {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<TmsParameters> fetchByTid(String tid) {
		try {
			Query cb = entityManager.createNamedQuery("TmsParameters.retrieveAllByTID").setParameter("partId", tid);
			return (List<TmsParameters>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
		
	}


	@Override
	public List<TmsParametersDefaultValues> fetchAllDefaultParameters() {
		try {
			Query cb = entityManager.createNamedQuery("TmsParametersDefaultValues.retrieveAll");
			return (List<TmsParametersDefaultValues>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}


	@Override
	@Transactional
	public void insert(List<TmsParameters> tms) {
		for(TmsParameters t:tms) {
			entityManager.persist(t);
		}
		
	}
	
	@Override
	@Transactional
	public void update(List<TmsParameters> tms) {
		for(TmsParameters t:tms) {
			entityManager.merge(t);
		}
		
	}

}
