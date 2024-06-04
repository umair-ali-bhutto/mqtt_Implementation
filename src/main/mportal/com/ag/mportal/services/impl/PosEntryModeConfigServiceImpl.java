package com.ag.mportal.services.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.services.PosEntryModeConfigService;
@Service
public class PosEntryModeConfigServiceImpl implements PosEntryModeConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PosEntryModeConfig fetchByMode(String posEntryMode) {
		PosEntryModeConfig posEntryModeConfig = null;
	
		try {

			try {
				AgLogger.logDebug("0","Fetching Rec From POS_ENTRY_MODE_CONFIG");
				Query cb = entityManager.createNamedQuery("PEMConfig.fetchPemConfigByMode").setParameter("posEntryMode",
						posEntryMode);
				return (PosEntryModeConfig) cb.getSingleResult();
			} catch (NoResultException nre) {
				AgLogger.logerror(getClass(), "Exception in POS_ENTRY_MODE_CONFIG fetch", nre);
				return null;
			}
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception in POS_ENTRY_MODE_CONFIG fetch", e);
		}
		return posEntryModeConfig;
	}

	@Override
	public ArrayList<PosEntryModeConfig> fetchAll() {
		// TODO Auto-generated method stub
		try {

			try {
				AgLogger.logDebug("0","Fetching Rec From POS_ENTRY_MODE_CONFIG FetchAll");
				Query cb = entityManager.createNamedQuery("PEMConfig.fetchAll");
				return (ArrayList<PosEntryModeConfig>) cb.getResultList();
			} catch (NoResultException nre) {
				AgLogger.logerror(getClass(), "Exception in POS_ENTRY_MODE_CONFIG FetchAll", nre);
				return null;
			}
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception in POS_ENTRY_MODE_CONFIG FetchAll", e);
		}
		return null;
	}

}
