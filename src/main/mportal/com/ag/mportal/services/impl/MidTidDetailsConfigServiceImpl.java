package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.MidTidDetailsConfig;
import com.ag.mportal.services.MidTidDetailsConfigService;

@Service
public class MidTidDetailsConfigServiceImpl implements MidTidDetailsConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<MidTidDetailsConfig> fetchAllConfig() {
		List<MidTidDetailsConfig> lst = new ArrayList<MidTidDetailsConfig>();
		try {
			Query query = entityManager.createNamedQuery("MidTidDetailsConfig.fetchAll");
			lst = (List<MidTidDetailsConfig>) query.getResultList();
		} catch (Exception e) {
		}
		return lst;
	}

}
