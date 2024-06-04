package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.PreauthMaster;
import com.ag.mportal.entity.ProvidersConfig;
import com.ag.mportal.services.ProvidersConfigService;

@Service
public class ProvidersConfigServiceImpl implements ProvidersConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Long save(ProvidersConfig sdt) {
		try {
			entityManager.persist(sdt);
			return sdt.getId();
		} catch (NoResultException nre) {
			return 0l;
		}
	}

	@Override
	public ProvidersConfig fetchAllRecordById(long id) {
		ProvidersConfig discountLog = null;
		try {
			Query cb = entityManager.createNamedQuery("ProvidersConfig.fetchAllRecordById").setParameter("id", id);
			return (ProvidersConfig) cb.getSingleResult();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "getById Exception in ProvidersConfig: ", ex);
		}

		return discountLog;
	}

	@Override
	public List<ProvidersConfig> fetchAllRecord() {
		List<ProvidersConfig> discountLog = null;
		try {
			Query cb = entityManager.createNamedQuery("ProvidersConfig.fetchAllRecord");
			return (List<ProvidersConfig>) cb.getResultList();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "getById Exception in ProvidersConfig: ", ex);
		}

		return discountLog;
	}

	@Override
	@Transactional
	public void update(ProvidersConfig sdt) {
		try {
			entityManager.merge(sdt);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public List<ProvidersConfig> fetchAllRecordById(String mid, String tid, String serialNumber, String type) {
		List<ProvidersConfig> discountLog = null;
		try {
			Query cb = entityManager.createNamedQuery("ProvidersConfig.fetchAllRecordByMidTidType").setParameter("mid", mid)
					.setParameter("tid", tid).setParameter("serialNumber", serialNumber).setParameter("type", type);
			return (List<ProvidersConfig>) cb.getResultList();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "getById Exception in ProvidersConfig: ", ex);
		}

		return discountLog;
	}
	
	@Override
	public List<ProvidersConfig> fetchAllRecordByFilter(String mid, String tid, String donationAccount,String status) {
		List<ProvidersConfig> record = null;
		try {
			String m = "";


			if (mid != null && !mid.equals("null")) {
				m += " AND (TXN_MID = '" + mid + "') ";
			}
			if (tid != null && !tid.equals("null")) {
				m += " AND (TXN_TID = '" + tid + "') ";
			}
			if (donationAccount != null && !donationAccount.equals("null")) {
				m += " AND (ACCOUNT_NUMBER = '" + donationAccount + "') ";
			}
			if (status != null && !status.equals("null")) {
				m += " AND (IS_ACTIVE = '" + status + "') ";
			}
			
			String query = "SELECT * FROM PROVIDERS_CONFIG WHERE ID IS NOT NULL " + m + " ";
			Query cb = entityManager.createNativeQuery(query,ProvidersConfig.class);
			record =(List<ProvidersConfig>)  cb.getResultList();
			AgLogger.logDebug("", "Query: " + query);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "getById Exception in ProvidersConfig: ", ex);
		}

		return record;
	}

}
