package com.ag.generic.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.ConfigCorporate;
import com.ag.generic.service.ConfigCorporateService;
import com.ag.generic.util.AgLogger;

@Service
public class ConfigCorporateServiceImpl implements ConfigCorporateService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ConfigCorporate getCorporateDetails(String packageName) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Record From CorporateDetails");
			Query cb = entityManager.createNamedQuery("ConfigCorporate.fetchCorporateByPkgName", ConfigCorporate.class)
					.setParameter("pkgName", packageName);
			return (ConfigCorporate) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ConfigCorporate fetchByCorpId(String corpId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Record From ConfigCorporate");
			Query cb = entityManager.createNamedQuery("ConfigCorporate.fetchByCorpId", ConfigCorporate.class)
					.setParameter("corpId", corpId);
			return (ConfigCorporate) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
