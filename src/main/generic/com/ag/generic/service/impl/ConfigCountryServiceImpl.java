package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ConfigCountry;
import com.ag.generic.service.ConfigCountryService;
import com.ag.generic.util.AgLogger;

@Service
public class ConfigCountryServiceImpl implements ConfigCountryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long save(ConfigCountry configCountry) throws Exception {
		try {
		entityManager.persist(configCountry);
		return configCountry.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigCountry> findAll(String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findAll");
		//ADD ORDER BY ID ASC CLAUSE
		Query cb = entityManager.createNamedQuery("ConfigCountry.findAll").setParameter("corpId", corpId);
		return (List<ConfigCountry>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ConfigCountry findByCode(String code,String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findByCode");
		//ADD ORDER BY ID ASC CLAUSE
		Query cb = entityManager.createNamedQuery("ConfigCountry.findByCode").setParameter("code", code).setParameter("corpId", corpId);
		return (ConfigCountry) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void update(ConfigCountry tcn) {
		try {
		entityManager.merge(tcn);
		} catch (NoResultException nre) {
		
		}

	}

}
