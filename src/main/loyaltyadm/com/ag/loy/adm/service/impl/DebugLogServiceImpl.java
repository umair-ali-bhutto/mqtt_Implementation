package com.ag.loy.adm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.DebugLog;
import com.ag.loy.adm.service.DebugLogService;

@Service
public class DebugLogServiceImpl implements DebugLogService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DebugLog> fetchAllByCorpId(String fromDateTime, String toDateTime, String debugMessage) {
		try {

			String queryString = "select * from debug_log @whereClause";

			String whereClause = "";

			if (!Objects.isNull(fromDateTime) && !Objects.isNull(toDateTime)) {
				whereClause += "DEV_BATCH BETWEEN '" + fromDateTime + "' AND '" + toDateTime + "' ";
			}

			if (!Objects.isNull(debugMessage)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " DEBUG_MESSAGE like'%" + debugMessage + "%'";
			}
			AgLogger.logInfo(DebugLog.class, queryString);

			Query query = entityManager.createNativeQuery(queryString, DebugLog.class);
			ArrayList<DebugLog> lst = (ArrayList<DebugLog>) query.getResultList();
			return lst;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING AccountPayments: ", ex);
		}
		return null;
	}

}