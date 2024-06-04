package com.ag.loy.adm.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.FeatureMaster;
import com.ag.loy.adm.service.FeatureMasterService;

@Service
public class FeatureMasterServiceImpl implements FeatureMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveFeature(FeatureMaster sdt) {
		try {
			entityManager.persist(sdt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<FeatureMaster> getAllFeatures(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("FeatureMaster.getAllFeatures");
			cb.setParameter("corpId", corpId);
			return (List<FeatureMaster>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public FeatureMaster getAllFeatures(String featureID, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("FeatureMaster.getAllFeaturesByFeatureId");
			cb.setParameter("corpId", corpId);
			cb.setParameter("featureId", featureID);
			return (FeatureMaster) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void updateFeatureMaster(FeatureMaster sdt) {
		try {
			entityManager.merge(sdt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String fetchMaxId() {
		String res = "";
		try {
			String q = "SELECT MAX(FEATUREID) FROM FEATURE_MASTER";
			Query query = entityManager.createNativeQuery(q);
			res = String.valueOf(query.getSingleResult());
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION : ", ex);
		}
		return res;
	}

}