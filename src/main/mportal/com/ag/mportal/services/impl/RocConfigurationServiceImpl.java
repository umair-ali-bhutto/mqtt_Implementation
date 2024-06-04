package com.ag.mportal.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.mportal.services.RocConfigurationService;

@Service
public class RocConfigurationServiceImpl implements RocConfigurationService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String fetch(String txnType, String pem, String posType, String corpId) {
		String reportPath = null;
		String whereClause = " IS_ACTIVE=1 ";

		whereClause += " AND TXN_TYPE='" + txnType + "'";

		whereClause += " AND PEM='" + pem + "'";

		whereClause += " AND POS_TYPE='" + posType + "'";

		whereClause += " AND CORP_ID='" + corpId + "'";

		String quer = DBUtil.getRocConfigQuery();
		quer = quer.replace("@whereClause", whereClause);

		AgLogger.logDebug(RocConfigurationServiceImpl.class, quer);
		Query cb = entityManager.createNativeQuery(quer);

		try {
			List<Object> lst = cb.getResultList();

			if (!lst.isEmpty()) {
				reportPath = lst.get(0).toString();
			}

		} catch (NoResultException nre) {
			reportPath = null;
		}

		return reportPath;
	}

}
