package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.Merchant;
import com.ag.mportal.services.MerchantService;
@Service
public class MerchantServiceImpl implements MerchantService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long insert(Merchant tcn) {
		try {
		entityManager.persist(tcn);
		return tcn.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public Merchant fetchByID(Merchant tcn) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
		Query cb = entityManager.createNamedQuery("Merchant.fetchByID").setParameter("id", tcn.getId());
		return (Merchant) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Merchant fetchByOtherParams(Merchant tcn) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
		Query cb = entityManager.createNamedQuery("Merchant.fetchByOtherParams").setParameter("id", tcn.getId());
		return (Merchant) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchant> fetchAll() {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
		//ADD ORDER BY ID ASC
		Query cb = entityManager.createNamedQuery("Merchant.fetchAll");
		return (List<Merchant>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchant> fetchAllByID(long id) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
		//ADD ORDER BY ID ASC
		Query cb = entityManager.createNamedQuery("Merchant.fetchAllByID").setParameter("id", id);
		return (List<Merchant>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void update(Merchant id) {
		try {
		entityManager.merge(id);
		} catch (NoResultException nre) {
			
		}
	}

}
