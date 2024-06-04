package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.ReconConfig;
import com.ag.mportal.entity.ReconConfigDetail;
import com.ag.mportal.services.ReconConfigDetailService;
import com.ag.mportal.services.ReconConfigService;

@Service
public class ReconConfigDetailServiceImpl implements ReconConfigDetailService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(ReconConfigDetail mdl) {
		try {
			entityManager.persist(mdl);
		} catch (Exception e) {
		}
	}

	@Override
	@Transactional
	public void update(ReconConfigDetail mdl) {
		try {
			entityManager.merge(mdl);
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReconConfigDetail> fetchAll() {
		List<ReconConfigDetail> lst = new ArrayList<ReconConfigDetail>();
		try {
			Query cb = entityManager.createNamedQuery("ReconConfigDetail.fetchAll", ReconConfigDetail.class);
			lst = (List<ReconConfigDetail>) cb.getResultList();
		} catch (Exception e) {
		}
		return lst;
	}

	@Override
	public List<ReconConfigDetail> fetchByConfigId(long reconConfigId) {
		List<ReconConfigDetail> lst = new ArrayList<ReconConfigDetail>();
		try {
			Query cb = entityManager.createNamedQuery("ReconConfigDetail.fetchByConfigId", ReconConfigDetail.class)
					.setParameter("reconConfigId", reconConfigId);
			lst = (List<ReconConfigDetail>) cb.getResultList();
		} catch (Exception e) {
		}
		return lst;
	}

}
