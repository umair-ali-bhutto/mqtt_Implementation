package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.BroadcastMsg;
import com.ag.generic.service.BroadCastMessageService;
import com.ag.generic.util.AgLogger;

@Service
public class BroadCastMessageServiceImpl implements BroadCastMessageService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(BroadcastMsg sdt) {
		try {
		entityManager.persist(sdt);
		} catch (NoResultException nre) {
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BroadcastMsg> searchBroadCastMessage() {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From searchBroadCastMessage");
		Query cb = entityManager.createNamedQuery("BroadcastMsg.searchBroadCastMessage").setParameter("isSent", "N");
		return (List<BroadcastMsg>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void updateBroadCastMessagById(BroadcastMsg id) {
		try {
		entityManager.merge(id);
		} catch (NoResultException nre) {
			
		}
	}

}
