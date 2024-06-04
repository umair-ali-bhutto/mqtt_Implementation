package com.ag.loy.adm.service.impl;

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
import com.ag.loy.adm.entity.CurrencyMaster;
import com.ag.loy.adm.service.CurrencyMasterService;

@Service
public class CurrencyMasterServiceImpl implements CurrencyMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Long saveCurrencyMaster(CurrencyMaster sdt) {
		try {
			entityManager.persist(sdt);
			return Long.parseLong(sdt.getCurrency());
		} catch (Exception nre) {
			return 0l;
		}
	}

	@Override
	public CurrencyMaster fetchAllRecordById(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("CurrencyMaster.fetchAllRecordById");
			cb.setParameter("corpId", corpId);
			cb.setParameter("currency", id);
			return (CurrencyMaster) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<CurrencyMaster> fetchAllRecord(String corpId) {
		List<CurrencyMaster> lst = new ArrayList<CurrencyMaster>();
		try {
			Query cb = entityManager.createNamedQuery("CurrencyMaster.fetchAllRecord");
			cb.setParameter("corpId", corpId);
			lst = (List<CurrencyMaster>) cb.getResultList();
			if (lst.size() == 0) {
				//Fetching Default CorpId If No Record Found.
				String defaultCorpIdQuery = AppProp.getProperty("loy.default.corpid");
				defaultCorpIdQuery = defaultCorpIdQuery.replaceAll("@CORPORATEID", corpId);
				AgLogger.logInfo("DEFAULT CORPID QUERY || "+defaultCorpIdQuery);
				Query cb2 = entityManager.createNativeQuery(defaultCorpIdQuery);
				String defaultCorpId  = (String) cb2.getSingleResult();
				
				Query cb3 = entityManager.createNamedQuery("CurrencyMaster.fetchAllRecord");
				cb3.setParameter("corpId", defaultCorpId);
				lst = (List<CurrencyMaster>) cb3.getResultList();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

}