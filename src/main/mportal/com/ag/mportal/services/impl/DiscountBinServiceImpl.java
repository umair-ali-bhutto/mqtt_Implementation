package com.ag.mportal.services.impl;

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
import com.ag.mportal.services.DiscountBinService;

@Service
public class DiscountBinServiceImpl implements DiscountBinService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(DiscountBin tcn) {
		try {
			entityManager.persist(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountBin: ", ex);
		}
	}

	@Override
	@Transactional
	public void update(DiscountBin tcn) {
		try {
			entityManager.merge(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountBin: ", ex);
		}
	}

	@Override
	@Transactional
	public List<DiscountBin> getBinByDiscountId(List<Long> lstDiscountId) {
		List<DiscountBin> lstSlab = null;
		try {
			Query query = entityManager.createNamedQuery("DiscountBin.getBinByDisctId", DiscountBin.class);
			query.setParameter("discId", lstDiscountId);

			lstSlab = (List<DiscountBin>) query.getResultList();

			if (!Objects.isNull(lstSlab) && !lstSlab.isEmpty()) {
				return lstSlab;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FETCHING BIN BY DISCOUNT ID: ", ex);
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountBin> fetchAllByDiscId(long discId) {
		List<DiscountBin> lst = new ArrayList<DiscountBin>();
		try {
			Query q = entityManager.createNamedQuery("DiscountBin.fetchByDiscId", DiscountBin.class)
					.setParameter("discId", discId);
			lst = (List<DiscountBin>) q.getResultList();
		} catch (NoResultException nre) {
			AgLogger.logInfo("No Discount Bin Records Found.");
		}
		return lst;
	}

	@Override
	public DiscountBin fetchAllByBinAndProductId(String bin, String productId, long discId) {
		DiscountBin mdl = new DiscountBin();
		try {
			String query = "SELECT * FROM DISC_BIN WHERE ('" + bin
					+ "' BETWEEN BIN_FROM AND BIN_TO) AND BIN_PRODUCT_ID = '" + productId + "' AND DISC_ID = " + discId;
			AgLogger.logInfo("Discount Bin Query|" + query);
			Query q = entityManager.createNativeQuery(query, DiscountBin.class);
			mdl = (DiscountBin) q.setMaxResults(1).getSingleResult();
		} catch (NoResultException nre) {
			AgLogger.logInfo("No DiscountBin Record Found.");
			mdl = null;
		}
		return mdl;
	}

	@Override
	@Transactional
	public void deleteByDiscId(long discId) {
		try {
			String sqlss = "DELETE FROM DISC_BIN WHERE DISC_ID = " + discId;
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From DISC_BIN || QUERY : " + sqlss);
		} catch (NoResultException nre) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
