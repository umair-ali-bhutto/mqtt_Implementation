package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.TapNPayRoutingConfig;
import com.ag.mportal.services.TapNPayRoutingConfigService;

@Service
public class TapNPayRoutingConfigServiceImpl implements TapNPayRoutingConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TapNPayRoutingConfig> fetchAll() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchTxnDetailsVoid");
			Query cb = entityManager.createNamedQuery("TapNPayRoutingConfig.retrieveAll");
			return (List<TapNPayRoutingConfig>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
