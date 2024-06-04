package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DevAdvMerchant;
import com.ag.mportal.services.DevAdvMerchantService;

@Service
public class DevAdvMerchantServiceImpl implements DevAdvMerchantService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DevAdvMerchant> fetchAll() {
		List<DevAdvMerchant> lst = new ArrayList<DevAdvMerchant>();
		try {
			Query cb = entityManager.createNamedQuery("DevAdvMerchant.fetchAll");
			lst = (List<DevAdvMerchant>) cb.getResultList();
			AgLogger.logDebug("", "Query: " + cb);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}

	@Override
	@Transactional
	public boolean insert(DevAdvMerchant DevAdvMerchant) {
		try {
			entityManager.persist(DevAdvMerchant);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public void update(DevAdvMerchant DevAdvMerchant) {
		try {
			entityManager.merge(DevAdvMerchant);
		} catch (Exception e) {
		}
	}

}
