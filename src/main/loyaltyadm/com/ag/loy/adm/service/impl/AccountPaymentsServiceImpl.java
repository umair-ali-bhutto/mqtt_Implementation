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
import com.ag.loy.adm.entity.AccountPayments;
import com.ag.loy.adm.service.AccountPaymentsService;

@Service
public class AccountPaymentsServiceImpl implements AccountPaymentsService {

	@Autowired
	UtilAccess utilAccess;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ArrayList<AccountPayments> fetchAll(String corpId) {
		try {
			Query query = entityManager.createNamedQuery("AccountPayments.findAll", AccountPayments.class);
			query.setParameter("corpid", corpId);
			ArrayList<AccountPayments> lst = (ArrayList<AccountPayments>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPayments: ", ex);
		}
		return null;
	}

	@Override
	public ArrayList<AccountPayments> fetch(String userId, String devBatch, String accountId, String fromDate,
			String toDate, String customerId, String corporateId)
	{
		try {

			String queryString = "select * from account_payments @whereClause";

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
				whereClause += DBUtil.getDateQueryParam("TXN_DATE") + " BETWEEN '" + utilAccess.dateFormatter(fromDate)
						+ "' and '" + utilAccess.dateFormatter(toDate) + "' ";
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

			AgLogger.logInfo(AccountPaymentsServiceImpl.class, queryString);

			Query query = entityManager.createNativeQuery(queryString, AccountPayments.class);
			ArrayList<AccountPayments> lst = (ArrayList<AccountPayments>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPayments: ", ex);
		}
		return null;
	}


}