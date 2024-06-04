package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.ReconDataDetail;
import com.ag.mportal.services.ReconDataDetailService;

@Service
public class ReconDataDetailServiceImpl implements ReconDataDetailService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(ReconDataDetail mdl) {
		try {
			entityManager.flush();
			entityManager.clear();
			entityManager.persist(mdl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void update(ReconDataDetail mdl) {
		try {
			entityManager.merge(mdl);
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReconDataDetail> fetchAll() {
		List<ReconDataDetail> lst = new ArrayList<ReconDataDetail>();
		try {
			Query cb = entityManager.createNamedQuery("ReconDataDetail.fetchAll", ReconDataDetail.class);
			lst = (List<ReconDataDetail>) cb.getResultList();
		} catch (Exception e) {
		}
		return lst;
	}

}
