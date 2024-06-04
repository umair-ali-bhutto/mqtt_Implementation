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
import com.ag.mportal.entity.DiscountMerchantTerminal;
import com.ag.mportal.services.DiscountMerchantTerminalService;

@Service
public class DiscountMerchantTerminalServiceImpl implements DiscountMerchantTerminalService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(DiscountMerchantTerminal tcn) {
		try {
			entityManager.persist(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountMerchantTerminal: ", ex);
		}
	}

	@Override
	@Transactional
	public void update(DiscountMerchantTerminal tcn) {
		try {
			entityManager.merge(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountMerchantTerminal: ", ex);
		}
	}

	@Override
	public DiscountMerchantTerminal fetchByDiscountIdAndTid(long discId, long discMidId, String terminalId) {
		DiscountMerchantTerminal mdl = new DiscountMerchantTerminal();
		try {
			Query q = entityManager
					.createNamedQuery("DiscountMerchantTerminal.fetchByDiscIdAndDiscMidId",
							DiscountMerchantTerminal.class)
					.setParameter("discId", discId).setParameter("discMidId", discMidId)
					.setParameter("terminalId", terminalId);
			mdl = (DiscountMerchantTerminal) q.setMaxResults(1).getSingleResult();
		} catch (NoResultException nre) {
			AgLogger.logInfo("No DiscountMerchantTerminal Record Found");
			mdl = null;
		}
		return mdl;
	}

	@Override
	public List<DiscountMerchantTerminal> fetchByDiscId(long discId) {
		List<DiscountMerchantTerminal> lst = new ArrayList<DiscountMerchantTerminal>();
		try {
			Query q = entityManager
					.createNamedQuery("DiscountMerchantTerminal.fetchByDiscId", DiscountMerchantTerminal.class)
					.setParameter("discId", discId);
			lst = (List<DiscountMerchantTerminal>) q.getResultList();
		} catch (NoResultException nre) {
			AgLogger.logInfo("No Discount Merchant Terminal Record Found");
		}
		return lst;
	}

	@Override
	@Transactional
	public void deleteByDiscId(long discId) {
		try {
			String sqlss = "DELETE FROM DISC_MERCHANT_TERMINAL WHERE DISC_ID = " + discId;
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From DISC_MERCHANT_TERMINAL || QUERY : " + sqlss);
		} catch (NoResultException nre) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
