package com.ag.mportal.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DiscountCity;
import com.ag.mportal.services.DiscountCityService;

@Service
public class DiscountCityServiceImpl implements DiscountCityService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(DiscountCity tcn) {
		try {
			entityManager.persist(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountCity: ", ex);
		}
	}
	
	@Override
	@Transactional
	public void update(DiscountCity tcn) {
		try {
			entityManager.merge(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountCity: ", ex);
		}
	}

}
