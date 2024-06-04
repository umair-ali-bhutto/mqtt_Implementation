package com.ag.loy.adm.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.Parameter;
import com.ag.loy.adm.service.ParameterService;

@Service
public class ParameterServiceImpl implements ParameterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(Parameter acm) {
		try {
			entityManager.persist(acm);
		} catch (Exception nre) {
		}

	}

	@Override
	@Transactional
	public void update(Parameter acm) {
		try {
			entityManager.merge(acm);
		} catch (Exception nre) {
		}

	}

	

	@Override
	public List<Parameter> fetchAllByCorpId(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("Parameter.findAll");
			query.setParameter("corpid", corpId);
			return (List<Parameter>) query.getResultList();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
			return null;
		}
	}


}