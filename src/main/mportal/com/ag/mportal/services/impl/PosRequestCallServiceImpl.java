package com.ag.mportal.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.PosRequestCalls;
import com.ag.mportal.services.PosRequestCallsService;

@Service
public class PosRequestCallServiceImpl implements PosRequestCallsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PosRequestCalls fetchByMessageType(String messageType) {
		PosRequestCalls lstFedRates = null;
		Session sess = null;
		try {
			Query query = entityManager.createNamedQuery("PosRequestCalls.findByMessageType");
			query.setParameter("messageType", messageType);
			lstFedRates = (PosRequestCalls) query.setMaxResults(1).getSingleResult();
			return lstFedRates;
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
			return null;
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

	}

}
