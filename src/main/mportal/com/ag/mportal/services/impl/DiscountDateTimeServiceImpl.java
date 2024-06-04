package com.ag.mportal.services.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DiscountBin;
import com.ag.mportal.entity.DiscountDateTime;
import com.ag.mportal.services.DiscountDateTimeService;

@Service
public class DiscountDateTimeServiceImpl implements DiscountDateTimeService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(DiscountDateTime tcn) {
		try {
			entityManager.persist(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountDateTime: ", ex);
		}
	}

	@Override
	@Transactional
	public void update(DiscountDateTime tcn) {
		try {
			entityManager.merge(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountDateTime: ", ex);
		}
	}


	@Override
	public DiscountDateTime fetchByTimestamp(long discId, Timestamp transactionTime) {
		DiscountDateTime mdl = new DiscountDateTime();
		try {
			String query = "SELECT * FROM DISC_DATE_TIME WHERE DISC_ID = " + discId + " AND ('" + transactionTime
					+ "' BETWEEN DISC_START_TIME AND DISC_END_TIME)";
			AgLogger.logInfo("Discount DateTime Query|" + query);
			Query q = entityManager.createNativeQuery(query, DiscountDateTime.class);
			mdl = (DiscountDateTime) q.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			AgLogger.logInfo("No DiscountDateTime Record Found");
			mdl = null;
		}
		return mdl;
	}

	@Override
	public List<DiscountDateTime> fetchByDisc(long discId) {
		List<DiscountDateTime> lst = new ArrayList<DiscountDateTime>();
		try {
			Query q = entityManager.createNamedQuery("DiscountDateTime.fetchAllByDisc", DiscountDateTime.class)
					.setParameter("discId", discId);
			lst = (List<DiscountDateTime>) q.getResultList();
		} catch (NoResultException nre) {
		}
		return lst;
	}

	@Override
	@Transactional
	public void deleteByDiscId(long discId) {
		try {
			String sqlss = "DELETE FROM DISC_DATE_TIME WHERE DISC_ID = " + discId;
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From DISC_DATE_TIME || QUERY : " + sqlss);
		} catch (NoResultException nre) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
