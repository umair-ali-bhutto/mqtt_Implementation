package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ReqCallsParam;
import com.ag.generic.service.ReqCallsParamService;

@Service
public class ReqCallsParamServiceImpl implements ReqCallsParamService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReqCallsParam> fetchByAllRequestCallsParameters() {
		try {
		//ORDER BY ID ASC
		Query cb = entityManager.createNamedQuery("ReqCallsParam.fetchByAllRequestCallsParameters");
		return (List<ReqCallsParam>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
