package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.ReconDataMaster;
import com.ag.mportal.services.ReconDataMasterService;

@Service
public class ReconDataMasterServiceImpl implements ReconDataMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insert(ReconDataMaster mdl) {
		long id = 0;
		try {
			entityManager.persist(mdl);
			id = mdl.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	@Transactional
	public void update(ReconDataMaster mdl) {
		try {
			entityManager.merge(mdl);
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReconDataMaster> fetchAll() {
		List<ReconDataMaster> lst = new ArrayList<ReconDataMaster>();
		try {
			Query cb = entityManager.createNamedQuery("ReconDataMaster.fetchAll", ReconDataMaster.class);
			lst = (List<ReconDataMaster>) cb.getResultList();
		} catch (Exception e) {
		}
		return lst;
	}

	@Override
	public ReconDataMaster fetchById(long masterId) {
		ReconDataMaster mdl = new ReconDataMaster();
		try {
			Query cb = entityManager.createNamedQuery("ReconDataMaster.fetchById", ReconDataMaster.class).setParameter("id", masterId);
			mdl = (ReconDataMaster) cb.getSingleResult();
		} catch (Exception e) {
		}
		return mdl;
	}

}
