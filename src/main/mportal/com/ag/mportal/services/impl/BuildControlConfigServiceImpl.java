package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.BuildControlConfig;
import com.ag.mportal.services.BuildControlConfigService;

@Service
public class BuildControlConfigServiceImpl implements BuildControlConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtilAccess utilAccess;

	@Override
	@Transactional
	public long insert(BuildControlConfig tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return 0l;
		}
	}

	@Override
	@Transactional
	public void update(BuildControlConfig tcn) {
		try {
			entityManager.merge(tcn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public List<BuildControlConfig> fetchAllByModel(String controlModel) {
		List<BuildControlConfig> lstFedRates = null;
		Session sess = null;
		try {
			Query query = entityManager.createNamedQuery("BuildControlConfig.fetchByModel");
			query.setParameter("controlModel", controlModel);
			lstFedRates = (List<BuildControlConfig>) query.getResultList();
			return lstFedRates;
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
			return null;
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

	}
	
	
	@Override
	public List<BuildControlConfig> getBuildControlConfigsByUserIds(List<Long> ids) {
		List<BuildControlConfig> lstFedRates = null;
		Session sess = null;
		try {
			Query query = entityManager.createNamedQuery("BuildControlConfig.fetchById");
			query.setParameter("ids", ids);
			lstFedRates = (List<BuildControlConfig>) query.getResultList();
			return lstFedRates;
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
			return null;
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

	}

//	@Override
//	public List<BuildControlConfig> getBuildControlConfigsByUserIds(List<Long> superUserIds, List<Long> systemIds,
//			List<Long> userIds) {
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<BuildControlConfig> query = criteriaBuilder.createQuery(BuildControlConfig.class);
//		Root<BuildControlConfig> root = query.from(BuildControlConfig.class);
//
//		Predicate superUserPredicate = root.get("controlUser").in(superUserIds);
//		Predicate systemPredicate = root.get("controlUser").in(systemIds);
//		Predicate userPredicate = root.get("controlUser").in(userIds);
//
//		Predicate finalPredicate = criteriaBuilder.or(superUserPredicate, systemPredicate, userPredicate);
//
//		query.where(finalPredicate);
//
//		return entityManager.createQuery(query).getResultList();
//	}

}
