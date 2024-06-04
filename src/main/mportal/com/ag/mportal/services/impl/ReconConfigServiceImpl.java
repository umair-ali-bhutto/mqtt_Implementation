package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.ReconConfig;
import com.ag.mportal.services.ReconConfigService;

@Service
public class ReconConfigServiceImpl implements ReconConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(ReconConfig mdl) {
		try {
			entityManager.persist(mdl);
		} catch (Exception e) {
		}
	}

	@Override
	@Transactional
	public void update(ReconConfig mdl) {
		try {
			entityManager.merge(mdl);
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReconConfig> fetchAll() {
		List<ReconConfig> lst = new ArrayList<ReconConfig>();
		try {
			Query cb = entityManager.createNamedQuery("ReconConfig.fetchAll", ReconConfig.class);
			lst = (List<ReconConfig>) cb.getResultList();
		} catch (Exception e) {
		}
		return lst;
	}

}
