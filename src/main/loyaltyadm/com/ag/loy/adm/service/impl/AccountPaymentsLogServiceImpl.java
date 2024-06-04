package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.loy.adm.entity.AccountPaymentsLog;
import com.ag.loy.adm.service.AccountPaymentsLogService;

@Service
public class AccountPaymentsLogServiceImpl implements AccountPaymentsLogService {

	@Autowired
	UtilAccess utilAccess;
	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public ArrayList<AccountPaymentsLog> fetchAll(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("AccountPaymentsLog.findAll",
					AccountPaymentsLog.class);
			query.setParameter("corpid", corpId);
			ArrayList<AccountPaymentsLog> lst = (ArrayList<AccountPaymentsLog>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPaymentsLog: ", ex);
		}
		return null;
	}
	
	@Override
	public ArrayList<AccountPaymentsLog> fetch(String userId, String devBatch, String accountId, String fromDate,
			String toDate, String customerId, String corporateId) {
		try {

			String queryString = "select * from account_payments_log @whereClause";

			String whereClause = "";

			if (!Objects.isNull(devBatch)) {
				whereClause += "DEV_BATCH = '" + devBatch + "' ";
			}

			if (!Objects.isNull(accountId)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " ACC_ID='" + accountId + "' ";
			}

			if (!Objects.isNull(fromDate) && !Objects.isNull(toDate)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += DBUtil.getDateQueryParam("TXN_DATE") + " BETWEEN '" + utilAccess.dateFormatter(fromDate) + "' and '" + utilAccess.dateFormatter(toDate) + "' ";
			}

			if (!Objects.isNull(customerId)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}

				whereClause += " CID='" + customerId + "' ";
			}

			if (!Objects.isNull(corporateId)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " CORPID='" + corporateId + "' ";
			}

			if (!whereClause.isEmpty()) {
				String st = " WHERE " + whereClause;
				queryString = queryString.replace("@whereClause", st);
			} else {
				queryString = queryString.replace("@whereClause", "");
			}

			AgLogger.logInfo(AccountPaymentsLogServiceImpl.class, queryString);

			Query query = entityManager.createNativeQuery(queryString, AccountPaymentsLog.class);
			ArrayList<AccountPaymentsLog> lst = (ArrayList<AccountPaymentsLog>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPayments: ", ex);
		}
		return null;
	}


}