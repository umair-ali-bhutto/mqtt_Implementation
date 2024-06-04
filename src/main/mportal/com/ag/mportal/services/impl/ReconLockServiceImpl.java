package com.ag.mportal.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.entity.ReconLock;
import com.ag.mportal.services.ReconLockService;

@Service
public class ReconLockServiceImpl implements ReconLockService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(ReconLock mdl) {
		try {
			entityManager.persist(mdl);
		} catch (Exception e) {
		}
	}

	@Override
	@Transactional
	public void update(ReconLock mdl) {
		try {
			entityManager.merge(mdl);
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReconLock> fetchAll() {
		List<ReconLock> lst = new ArrayList<ReconLock>();
		try {
			Query cb = entityManager.createNamedQuery("ReconLock.fetchAll", ReconLock.class);
			lst = (List<ReconLock>) cb.getResultList();
		} catch (Exception e) {
		}
		return lst;
	}

	@Override
	public List<Long> fetchConfigIds() {
		List<Long> configIds = new ArrayList<Long>();
		try {
			String query = "SELECT RECON_CONFIG_ID FROM RECON_LOCK WHERE STATUS = 'ACTIVE'";
			Query q = entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object> temp = (List<Object>) q.getResultList();
			for (Object obj : temp) {
				configIds.add(Long.parseLong(obj.toString()));
			}
		} catch (NoResultException nre) {
		} catch (Exception e) {
			e.printStackTrace();
		}

		return configIds;
	}

	@Override
	@Transactional
	public void createLock(long configId) {
		try {
			ReconLock mdl = new ReconLock();
			mdl.setReconConfigId(configId);
			mdl.setStatus("ACTIVE");
			mdl.setRemarks("STARTED");
			mdl.setEntryDate(new Timestamp(new Date().getTime()));
			entityManager.persist(mdl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void releaseLock(long configId) {
		try {
			ReconLock mdl = fetchByConfigId(configId);
			mdl.setStatus("INACTIVE");
			mdl.setRemarks("ENDED");
			mdl.setUpdDate(new Timestamp(new Date().getTime()));
			entityManager.merge(mdl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ReconLock fetchByConfigId(long configId) {
		ReconLock mdl = new ReconLock();
		try {
			Query q = entityManager.createNamedQuery("ReconLock.fetchById", ReconLock.class)
					.setParameter("reconConfigId", configId).setParameter("status", "ACTIVE");
			mdl = (ReconLock) q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdl;
	}

}
