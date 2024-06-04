package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DevAdvMaster;
import com.ag.mportal.services.DevAdvMasterService;

@Service
public class DevAdvMasterServiceImpl implements DevAdvMasterService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DevAdvMaster> fetchAll() {
		List<DevAdvMaster> lst = new ArrayList<DevAdvMaster>();
		try {
			Query cb = entityManager.createNamedQuery("DevAdvMaster.fetchAll");
			lst = (List<DevAdvMaster>) cb.getResultList();
			AgLogger.logDebug("", "Query: " + cb);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lst;
	}
	

	@Override
	@Transactional
	public boolean insert(DevAdvMaster DevAdvMaster) {
		try {
			entityManager.persist(DevAdvMaster);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public void update(DevAdvMaster DevAdvMaster) {
		try {
			entityManager.merge(DevAdvMaster);
		} catch (Exception e) {
		}
	}


	@Override
	public long fetchAdvId() {
		try {
			String query = AppProp.getProperty("adv.fetch.max.advid");
			AgLogger.logInfo(query);
			Query cb = entityManager.createNativeQuery(query);
			long res =Long.parseLong(cb.getSingleResult().toString());
			return res;
		} catch (NoResultException nre) {
			return 10001L;
		}
	}

}
