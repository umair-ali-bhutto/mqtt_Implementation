package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.ReconLog;
import com.ag.mportal.services.ReconLogService;

@Service
public class ReconLogServiceImpl implements ReconLogService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(ReconLog mdl) {
		try {
			entityManager.persist(mdl);
		} catch (Exception e) {
		}
	}

	@Override
	@Transactional
	public void update(ReconLog mdl) {
		try {
			entityManager.merge(mdl);
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReconLog> fetchAll() {
		List<ReconLog> lst = new ArrayList<ReconLog>();
		try {
			Query cb = entityManager.createNamedQuery("ReconLog.fetchAll", ReconLog.class);
			lst = (List<ReconLog>) cb.getResultList();
		} catch (Exception e) {
		}
		return lst;
	}

}
