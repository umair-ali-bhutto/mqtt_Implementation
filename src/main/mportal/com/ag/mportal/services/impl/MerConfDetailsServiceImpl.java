package com.ag.mportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.BuildControlConfig;
import com.ag.mportal.entity.MerConfDetails;
import com.ag.mportal.services.MerConfDetailsService;

@Service
public class MerConfDetailsServiceImpl implements MerConfDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insert(MerConfDetails tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return 0l;
		}
	}

	@Override
	@Transactional
	public void update(MerConfDetails tcn) {
		try {
			entityManager.merge(tcn);
		} catch (NoResultException nre) {

		}
	}
	
	@Override
	public List<MerConfDetails> fetchAllById(Long ids) {
		List<MerConfDetails> lstFedRates = null;
		Session sess = null;
		try {
			Query query = entityManager.createNamedQuery("MerConfDetails.fetchById");
			query.setParameter("ids", ids);
			AgLogger.logInfo(query+"");
			lstFedRates = (List<MerConfDetails>) query.getResultList();
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
	public List<BuildControlConfig> fetchRecById(Long ids) {
		List<BuildControlConfig> lstFedRates = null;
		Session sess = null;
		try {
			Query query = entityManager.createNamedQuery("MerConfDetails.fetchRecById");
			query.setParameter("ids", ids);
			AgLogger.logInfo(query+"");
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
	public List<MerConfDetails> fetchAllByRecId(long recId) {
		List<MerConfDetails> lst = new ArrayList<MerConfDetails>();
		try {
			String query = "SELECT * FROM MER_CONF_DETAILS WHERE REC_ID = " + recId;

			AgLogger.logInfo(query);

			Query cb = entityManager.createNativeQuery(query, MerConfDetails.class);

			lst = (List<MerConfDetails>) cb.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	@Override
	@Transactional
	public void delete(long ids) {
		try {
			String sqlss = "DELETE FROM MER_CONF_DETAILS WHERE REC_ID =" + ids ;
			Query cb = entityManager.createNativeQuery(sqlss);
			int res = cb.executeUpdate();
			AgLogger.logInfo(res + " Records Deleted From MER_CONF_DETAILS || QUERY : " + sqlss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
