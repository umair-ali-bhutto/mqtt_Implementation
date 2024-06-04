package com.ag.mportal.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.ConfigEcrRouting;
import com.ag.mportal.services.ConfigEcrRoutingService;

@Service
public class ConfigEcrRoutingServiceImpl implements ConfigEcrRoutingService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insertRouting(ConfigEcrRouting tcn) {
		try {
			entityManager.persist(tcn);
		} catch (NoResultException nre) {

		}

	}

	@Override
	public ConfigEcrRouting fetchByMidTid(String mid, String tid,String callType) {

		try {
			Query cb = entityManager.createNamedQuery("ConfigEcrRouting.findByMidTid");
			cb.setParameter("mid", mid);
			cb.setParameter("tid", tid);
			cb.setParameter("callType", callType);
			return (ConfigEcrRouting) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	@Override
	public ConfigEcrRouting fetchByID(long id) {

		try {
			Query cb = entityManager.createNamedQuery("ConfigEcrRouting.findByID");
			cb.setParameter("id", id);
			return (ConfigEcrRouting) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
