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
import com.ag.mportal.entity.DiscountMerchant;
import com.ag.mportal.services.DiscountMerchantService;

@Service
public class DiscountMerchantServiceImpl implements DiscountMerchantService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(DiscountMerchant tcn) {
		try {
			entityManager.persist(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountMerchant: ", ex);
		}
	}

	@Override
	@Transactional
	public void update(DiscountMerchant tcn) {
		try {
			entityManager.merge(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountMerchant: ", ex);
		}
	}

	@Override
	@Transactional
	public List<DiscountMerchant> getMerchantByDiscountId(List<Long> lstDiscountId) {
		List<DiscountMerchant> lstSlab = null;
		try {
			Query query = entityManager.createNamedQuery("DiscountMerchant.getMerchantByDisctId",
					DiscountMerchant.class);
			query.setParameter("discId", lstDiscountId);

			lstSlab = (List<DiscountMerchant>) query.getResultList();

			if (!Objects.isNull(lstSlab) && !lstSlab.isEmpty()) {
				return lstSlab;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FETCHING MERCHANT BY DISCOUNT ID: ", ex);
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountMerchant> fetchAllByDiscId(long discId) {
		List<DiscountMerchant> lst = new ArrayList<DiscountMerchant>();
		try {
			Query q = entityManager.createNamedQuery("DiscountMerchant.fetchAllByDiscId", DiscountMerchant.class)
					.setParameter("discId", discId);
			lst = (List<DiscountMerchant>) q.getResultList();
		} catch (NoResultException nre) {
			AgLogger.logInfo("No Discount Merchant Records Found");
		}
		return lst;
	}

	@Override
	public DiscountMerchant fetchByDiscIdAndMid(long discId, String mid) {
		DiscountMerchant mdl = new DiscountMerchant();
		try {
			Query q = entityManager.createNamedQuery("DiscountMerchant.fetchByDiscIdAndMid", DiscountMerchant.class)
					.setParameter("discId", discId).setParameter("mid", mid);
			mdl = (DiscountMerchant) q.setMaxResults(1).getSingleResult();
		} catch (NoResultException nre) {
			AgLogger.logInfo("No DiscountMerchant Records Found");
			mdl = null;
		}
		return mdl;
	}

	@Override
	@Transactional
	public void deleteByDiscId(long discId) {
		try {
			String sqlss = "DELETE FROM DISC_MERCHANT WHERE DISC_ID = " + discId;
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From DISC_MERCHANT || QUERY : " + sqlss);
		} catch (NoResultException nre) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
