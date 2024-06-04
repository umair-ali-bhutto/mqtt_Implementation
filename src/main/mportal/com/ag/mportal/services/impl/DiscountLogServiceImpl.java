package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DiscountLog;
import com.ag.mportal.entity.DiscountMaster;
import com.ag.mportal.services.DiscountLogService;

@Service
public class DiscountLogServiceImpl implements DiscountLogService {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public long insert(DiscountLog discountLog) {
		long id = 0;
		try {
			entityManager.persist(discountLog);
			id = discountLog.getId();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountLog: ", ex);
		}
		return id;
	}

	@Transactional
	@Override
	public void update(DiscountLog discountLog) {
		try {
			entityManager.merge(discountLog);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountLog: ", ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountLog> fetchAll() {
		List<DiscountLog> lst = new ArrayList<DiscountLog>();
		try {
			Query q = entityManager.createNamedQuery("DiscountLog.fetchAll", DiscountLog.class);
			lst = q.getResultList();
		} catch (Exception e) {

		}
		return lst;
	}

	@Override
	public List<DiscountLog> fetchAllByCid(String cid) {
		List<DiscountLog> lst = new ArrayList<DiscountLog>();
		try {
			Query q = entityManager.createNamedQuery("DiscountLog.fetchAllByCid", DiscountLog.class).setParameter("cid",
					cid);
			lst = (List<DiscountLog>) q.getResultList();
		} catch (Exception e) {

		}
		return lst;
	}

	public DiscountLog getById(long recId) {
		DiscountLog mdl = new DiscountLog();
		try {
			Query q = entityManager.createNamedQuery("DiscountLog.fetchById", DiscountLog.class).setParameter("recId",
					recId);
			mdl = (DiscountLog) q.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdl;
	}

	@Override
	public int fetchTxnCountPerDay(String cid, long discId) {
		int count = 0;
		try {
			String query = "SELECT COUNT(*) FROM DISC_LOG WHERE CID = '" + cid
					+ "' AND DISCOUNT_AVAILED = 1 AND DISC_ID = " + discId
					+ " AND (CONVERT(DATE,TXN_DATE)) = (CONVERT(DATE,GETDATE()))";
			AgLogger.logInfo("fetchTxnCountPerDay Query|" + query);
			Query q = entityManager.createNativeQuery(query);
			count = (int) q.getSingleResult();
		} catch (NoResultException nre) {
			count = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int fetchTxnCountPerWeek(String cid, long discId, String startDate, String endDate) {
		int count = 0;
		try {
			String query = "SELECT COUNT(*) FROM DISC_LOG where CID = '" + cid
					+ "' and DISCOUNT_AVAILED = 1 and DISC_ID = " + discId + " and (CONVERT(DATE,TXN_DATE) BETWEEN '"
					+ startDate + "' and '" + endDate + "')";
			AgLogger.logInfo("fetchTxnCountPerWeek Query|" + query);
			Query q = entityManager.createNativeQuery(query);
			count = (int) q.getSingleResult();
		} catch (NoResultException nre) {
			count = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int fetchTxnCountPerMonth(String cid, long discId) {
		int count = 0;
		try {
			String query = "SELECT COUNT(*) FROM DISC_LOG where CID = '" + cid
					+ "' and DISCOUNT_AVAILED = 1 and DISC_ID = " + discId
					+ " AND MONTH(TXN_DATE) = MONTH(GETDATE()) AND YEAR(TXN_DATE) = YEAR(GETDATE())";
			AgLogger.logInfo("fetchTxnCountPerMonth Query|" + query);
			Query q = entityManager.createNativeQuery(query);
			count = (int) q.getSingleResult();
		} catch (NoResultException nre) {
			count = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int fetchTxnCountPerYear(String cid, long discId) {
		int count = 0;
		try {
			String query = "SELECT COUNT(*) FROM DISC_LOG where CID = '" + cid
					+ "' and DISCOUNT_AVAILED = 1 and DISC_ID = " + discId + " AND YEAR(TXN_DATE) = YEAR(GETDATE())";
			AgLogger.logInfo("fetchTxnCountPerYear Query|" + query);
			Query q = entityManager.createNativeQuery(query);
			count = (int) q.getSingleResult();
		} catch (NoResultException nre) {
			count = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
