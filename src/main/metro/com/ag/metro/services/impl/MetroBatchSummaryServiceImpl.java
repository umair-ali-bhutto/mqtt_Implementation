package com.ag.metro.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.metro.entity.MetroBatchSummary;
import com.ag.metro.services.MetroBatchSummaryService;

@Service
public class MetroBatchSummaryServiceImpl implements MetroBatchSummaryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public int insertMetroBatchSummary(MetroBatchSummary adt) {
		try {
			entityManager.persist(adt);
			AgLogger.logDebug("", "@@@@ ID" + adt.getId());
			return adt.getId();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return 0;
		}

	}

	@Override
	public String getBatchNumber() {
		try {
			String sqlQuery = "select substr(to_char(sysdate,'YYMM')||'-'||lpad(nvl(max(to_number(substr(batch_no,-4,4))),0)+1,4,'0'),1,9) "
					+ " from Metro_batch_summary " + " where substr(batch_no,1,4) = to_char(sysdate,'YYMM')";
			AgLogger.logInfo(sqlQuery);
			Query cb = entityManager.createNativeQuery(sqlQuery);
			Object usersLogin = (Object) cb.getSingleResult();
			AgLogger.logInfo(usersLogin.toString() + "...........");
			return usersLogin.toString();
//		String s = (String) entityManager.createQuery(sqlQuery).getSingleResult();
//		AgLogger.logInfo(s);
		} catch (Exception e) {
			return "N/A";
		}

	}

	@Override
	public String getExpiryDate(String shelfLife) {
		try {
			String sqlQuery = "select to_char(Calc_Metro_exp_date(sysdate," + shelfLife + "),'dd-Mon-yyyy') from dual";
			// String sqlQuery = "select to_char(Calc_Metro_exp_date(sysdate),'dd-Mon-yyyy')
			// from dual";
			AgLogger.logInfo(sqlQuery);
			Query cb = entityManager.createNativeQuery(sqlQuery);
			Object exp = (Object) cb.getSingleResult();
			AgLogger.logInfo("EXPIRY:"+exp.toString());
			return exp.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "N/A";
		}

	}

}
