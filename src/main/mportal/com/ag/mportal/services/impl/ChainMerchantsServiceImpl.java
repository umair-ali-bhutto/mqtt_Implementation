package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.services.ChainMerchantsService;

@Service
public class ChainMerchantsServiceImpl implements ChainMerchantsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insert(ChainMerchant tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public ChainMerchant fetchByID(ChainMerchant tcn) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
			Query cb = entityManager.createNamedQuery("ChainMerchants.fetchByID").setParameter("chainMerchantMid",
					tcn.getMid());
			return (ChainMerchant) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ChainMerchant fetchByOtherParams(ChainMerchant tcn) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
			Query cb = entityManager.createNamedQuery("ChainMerchants.fetchByOtherParams").setParameter("id",
					tcn.getId());
			return (ChainMerchant) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChainMerchant> fetchAll() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
			Query cb = entityManager.createNamedQuery("ChainMerchants.fetchAll");
			return (List<ChainMerchant>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChainMerchant> fetchAllByID(String id) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllByID");
			Query cb = entityManager.createNamedQuery("ChainMerchants.fetchAllByID").setParameter("chainMerchantMid",
					id);
			return (List<ChainMerchant>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void update(ChainMerchant id) {
		try {
			entityManager.merge(id);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public ChainMerchant searchByTypeAndMID(String Type, String mid) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From searchByTypeAndMID");
			Query cb = entityManager.createNamedQuery("ChainMerchants.searchByTypeAndMID")
					.setParameter("chainMerchantMid", mid).setParameter("mid", mid).setParameter("type", Type);
			return (ChainMerchant) cb.setMaxResults(1).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<String> fetchAllNonRegistered() {
		List<String> lst = new ArrayList<String>();
		try {
			String query = AppProp.getProperty("fetch.non.registered.merchants");
			Query cb = entityManager.createNativeQuery(query);
			lst = (List<String>) cb.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

}
