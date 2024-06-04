package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DevAdvDetail;
import com.ag.mportal.services.DevAdvDetailService;

@Service
public class DevAdvDetailServiceImpl implements DevAdvDetailService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DevAdvDetail> fetchAll() {
		List<DevAdvDetail> lst = new ArrayList<DevAdvDetail>();
		try {
			Query cb = entityManager.createNamedQuery("DevAdvDetail.fetchAll");
			lst = (List<DevAdvDetail>) cb.getResultList();
			AgLogger.logDebug("", "Query: " + cb);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

	@Override
	@Transactional
	public boolean insert(DevAdvDetail DevAdvDetail) {

		try {
			entityManager.persist(DevAdvDetail);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	@Transactional
	public void update(DevAdvDetail DevAdvDetail) {
		try {
			entityManager.merge(DevAdvDetail);
		} catch (Exception e) {
		}
	}

}
