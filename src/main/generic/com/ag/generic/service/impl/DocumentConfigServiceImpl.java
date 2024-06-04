package com.ag.generic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.generic.entity.DocumentConfig;
import com.ag.generic.service.DocumentConfigService;

@Service
public class DocumentConfigServiceImpl implements DocumentConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DocumentConfig> fetchAll() {
		List<DocumentConfig> lst = new ArrayList<DocumentConfig>();
		try {
			Query cb = entityManager.createNamedQuery("DocumentConfig.fetchAll");
			lst = (List<DocumentConfig>) cb.getResultList();
			AgLogger.logDebug("", "Query: " + cb);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

	@Override
	public List<DocumentConfig> fetchAllByGroupCode(String corpId, int userGroup) {
		List<DocumentConfig> lst = new ArrayList<DocumentConfig>();
		try {
			AgLogger.logInfo(corpId);
			AgLogger.logInfo(String.valueOf(userGroup));

			Query cb = entityManager.createNamedQuery("DocumentConfig.fetchAllByGroupCode");
			cb.setParameter("corpId", corpId);
			cb.setParameter("userGroup", userGroup);
			lst = (List<DocumentConfig>) cb.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

}
