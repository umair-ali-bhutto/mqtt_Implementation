package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ConfigRegion;
import com.ag.generic.service.ConfigRegionService;
import com.ag.generic.util.AgLogger;

@Service
public class ConfigRegionServiceImpl implements ConfigRegionService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long save(ConfigRegion configRegion) throws Exception {
		try {
		entityManager.persist(configRegion);
		return configRegion.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigRegion> findAll(String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findAll");
		// order by id asc
		Query cb = entityManager.createNamedQuery("ConfigRegion.findAll").setParameter("corpId", corpId);
		return (List<ConfigRegion>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ConfigRegion findByCode(String code,String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findAll");
		// order by id asc
		Query cb = entityManager.createNamedQuery("ConfigRegion.findByCode").setParameter("code", code).setParameter("corpId", corpId);
		return (ConfigRegion) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigRegion> findByCountryCode(String countryCode,String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findByCountryCode");
		// order by id asc
		Query cb = entityManager.createNamedQuery("ConfigRegion.findByCountryCode").setParameter("countryCode",
				countryCode).setParameter("corpId", corpId);
		return (List<ConfigRegion>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void update(ConfigRegion configRegion) {
		try {
		entityManager.merge(configRegion);
		} catch (NoResultException nre) {
			
		}

	}

}
