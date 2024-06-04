package com.ag.mportal.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;


import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;

import com.ag.mportal.entity.TxnDetail;

import com.ag.mportal.services.PreAuthReportService;

@Service
public class PreAuthReportServiceImpl implements PreAuthReportService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TxnDetail> fetchPreAuthCompletionReport(List<String> merchant, Date from, Date to, String auth) {
		try {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				String whereClause = new String();

				if (from != null && to != null) {
					whereClause += " AND (" + DBUtil.getDateQueryParam("ENTRY_DATE") + " between '" + sdf.format(from)
							+ "' AND '" + sdf.format(to) + "') ";
				}

				if (merchant.size() != 0) {
					String jk = "";
					for (String mer : merchant) {
						jk += "'" + mer + "',";
					}

					if (jk.length() != 0) {
						jk = jk.substring(0, jk.length() - 1);
					}

					whereClause += " AND MERCHANT_ID IN (" + jk + ")";
				}

				if (auth != null) {
					whereClause += "  AND AUTH_ID_N = '" + auth + "' ";
				}


				String quer = "SELECT * FROM  TXN_DETAILS WHERE TYPE IN ('PRE AUTH','COMPLETION') "+whereClause;

				AgLogger.logDebug(TxnDetailsServiceImpl.class, quer);

				Query cb = entityManager.createNativeQuery(quer, TxnDetail.class);
				@SuppressWarnings("unchecked")
				List<TxnDetail> lst = cb.getResultList();
				return lst;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		} catch (NoResultException nre) {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
