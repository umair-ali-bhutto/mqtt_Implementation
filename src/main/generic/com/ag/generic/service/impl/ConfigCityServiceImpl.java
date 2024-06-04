package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ConfigCity;
import com.ag.generic.service.ConfigCityService;
import com.ag.generic.util.AgLogger;

@Service
public class ConfigCityServiceImpl implements ConfigCityService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public long save(ConfigCity configCity) throws Exception {
		try {
		entityManager.persist(configCity);
		return configCity.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigCity> findAll(String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findAll");
		//ADD ORDER BY CLAUSE ID ASC
		Query cb = entityManager.createNamedQuery("ConfigCity.findAll").setParameter("corpId", corpId);
		return (List<ConfigCity>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ConfigCity findByCode(String code,String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findByCode");
		//ADD ORDER BY CLAUSE ID ASC
		Query cb = entityManager.createNamedQuery("ConfigCity.findByCode").setParameter("code", code).setParameter("corpId", corpId);
		return (ConfigCity) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigCity> findByCountryCode(String countryCode,String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findByCountryCode");
		//ADD ORDER BY CLAUSE ID ASC
		Query cb = entityManager.createNamedQuery("ConfigCity.findByCountryCode").setParameter("countryCode", countryCode).setParameter("corpId", corpId);
		return (List<ConfigCity>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigCity> findByRegionCode(String regionCode,String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findByRegionCode");
		//ADD ORDER BY CLAUSE ID ASC
		Query cb = entityManager.createNamedQuery("ConfigCity.findByRegionCode").setParameter("regionCode", regionCode).setParameter("corpId", corpId);
		return (List<ConfigCity>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigCity> findByCountryCodeAndRegionCode(String countryCode, String regionCode,String corpId) {
		try {
		AgLogger.logDebug(getClass(), "Fetching Rec From findByCountryCodeAndRegionCode");
		//ADD ORDER BY CLAUSE ID ASC
		Query cb = entityManager.createNamedQuery("ConfigCity.findByCountryCodeAndRegionCode")
				.setParameter("regionCode", regionCode).setParameter("countryCode", countryCode).setParameter("corpId", corpId);
		return (List<ConfigCity>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void update(ConfigCity configCity) {
		try {
		entityManager.merge(configCity);
		} catch (NoResultException nre) {
			
		}
	}

}
