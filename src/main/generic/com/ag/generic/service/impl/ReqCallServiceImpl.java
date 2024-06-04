package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ReqCall;
import com.ag.generic.service.ReqCallService;

@Service
public class ReqCallServiceImpl implements ReqCallService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	
	@Override
	public List<ReqCall> fetchByAllRequestCalls() {
		try {
		//ORDER BY ID ASC
		Query cb = entityManager.createNamedQuery("ReqCall.fetchByAllRequestCalls");
		return (List<ReqCall>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
