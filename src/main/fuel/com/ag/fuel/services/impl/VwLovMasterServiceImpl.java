package com.ag.fuel.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.fuel.entity.VwLovMaster;
import com.ag.fuel.services.VwLovMasterService;

@Service
public class VwLovMasterServiceImpl implements VwLovMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<VwLovMaster> fetchAll() {
		try {
			Query query = entityManager.createNamedQuery("VwLovMaster.findAll", VwLovMaster.class);
			@SuppressWarnings("unchecked")
			List<VwLovMaster> list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<VwLovMaster> fetchAllByScreenID(String screenId, String corpid) {
		try {
			Query query = entityManager.createNamedQuery("VwLovMaster.findAllByScreenId", VwLovMaster.class);
			query.setParameter("screenId", screenId);
			query.setParameter("corpid", corpid);
			@SuppressWarnings("unchecked")
			List<VwLovMaster> list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
