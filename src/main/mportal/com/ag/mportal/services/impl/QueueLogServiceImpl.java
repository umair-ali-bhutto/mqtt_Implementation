package com.ag.mportal.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.QueueLog;
import com.ag.mportal.services.QueueLogService;
@Service
public class QueueLogServiceImpl implements QueueLogService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insertLog(QueueLog que) throws Exception {
		try {
		entityManager.persist(que);
		return que.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public QueueLog fetchQueueLogDetails(String mId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchQueueLogDetails");
		Query cb = entityManager.createNamedQuery("BannerTicker.fetchQueueLogDetails").setParameter("mid", mId)
				.setParameter("status", "NEW_REC");
		return (QueueLog) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void update(QueueLog que) {
		try {
		entityManager.merge(que);
		} catch (NoResultException nre) {
			
		}
	}

}
