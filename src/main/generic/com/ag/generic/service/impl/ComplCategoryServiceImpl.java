package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ComplCategory;
import com.ag.generic.service.ComplCategoryService;
import com.ag.generic.util.AgLogger;

@Service
public class ComplCategoryServiceImpl implements ComplCategoryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long insert(ComplCategory tcn) {
		try {
		entityManager.persist(tcn);
		return tcn.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public ComplCategory fetchByID(ComplCategory tcn) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchByID");
		Query cb = entityManager.createNamedQuery("ComplCategory.fetchByID").setParameter("id", tcn.getId());
		return (ComplCategory) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ComplCategory fetchByOtherParams(ComplCategory tcn) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchByOtherParams");
		Query cb = entityManager.createNamedQuery("ComplCategory.fetchByOtherParams")
				.setParameter("code", tcn.getCode()).setParameter("typeCode", tcn.getTypeCode());
		return (ComplCategory) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplCategory> fetchAll() {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
		Query cb = entityManager.createNamedQuery("ComplCategory.fetchAll");
		return (List<ComplCategory>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplCategory> fetchAllByID(int id) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAll");
		Query cb = entityManager.createNamedQuery("ComplCategory.fetchAllByID").setParameter("id", id);
		return (List<ComplCategory>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void update(ComplCategory id) {
		try {
		entityManager.merge(id);
		} catch (NoResultException nre) {
			
		}
	}

}
