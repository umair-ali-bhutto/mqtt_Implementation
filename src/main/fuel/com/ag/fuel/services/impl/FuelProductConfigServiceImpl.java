package com.ag.fuel.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.fuel.entity.FuelProductConfig;
import com.ag.fuel.services.FuelProductConfigService;

@Service
public class FuelProductConfigServiceImpl implements FuelProductConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<FuelProductConfig> fetchAll() {
		try {
			Query query = entityManager.createNamedQuery("FuelProductConfig.fetchAll", FuelProductConfig.class);
			List<FuelProductConfig> list = query.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<FuelProductConfig> fetchAllByCorpId(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("FuelProductConfig.fetchAllByCorpId", FuelProductConfig.class)
					.setParameter("corpId", corpId);
			List<FuelProductConfig> list = query.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

}
