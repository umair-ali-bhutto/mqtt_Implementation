package com.ag.mportal.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DiscountCategory;
import com.ag.mportal.services.DiscountCategoryService;

@Service
public class DiscountCategoryServiceImpl implements DiscountCategoryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(DiscountCategory tcn) {
		try {
			entityManager.persist(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountCategory: ", ex);
		}
	}
	
	@Override
	@Transactional
	public void update(DiscountCategory tcn) {
		try {
			entityManager.merge(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountCategory: ", ex);
		}
	}

}
