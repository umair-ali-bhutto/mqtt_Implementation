package com.ag.mportal.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.entity.VwPreauth;
import com.ag.mportal.services.TxnLogsService;

@Service
public class TxnLogsServiceImpl implements TxnLogsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insertLog(TxnLog que) {
		try {
			entityManager.persist(que);
			return que.getId();
		} catch (NoResultException nre) {
			return (Long) null;
		}
	}

	@Override
	public List<TxnLog> fetchTxnLogDetails(List<String> merchant, String terminal, Date from, Date to,
			String[] posUpdate, int numberOfRows, int pageNumber) {
		List<TxnLog> txnLog = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {

			String sql = AppProp.getProperty("fetch.txn.logs.query");

			if (from != null && to != null) {
				sql += " AND (" + DBUtil.getDateQueryParam("ACTIVITY_DATE") + " between '" + sdf.format(from)
						+ "' AND '" + sdf.format(to) + "') ";
			}

			if (terminal != null && !terminal.equals("")) {
				sql += " AND TERMINAL_ID = '" + terminal + "' ";
			}

			if (merchant != null) {
				if (merchant.size() != 0) {
					String jk = "";
					for (String mer : merchant) {
						jk += "'" + mer + "',";
					}

					if (jk.length() != 0) {
						jk = jk.substring(0, jk.length() - 1);
					}

					sql += " AND MERCHANT_ID IN (" + jk + ")";
				}
			}

			if (posUpdate != null) {
				if (posUpdate.length != 0) {
					String a = String.join("','", posUpdate);
					if (a != null) {
						sql += " AND ACTIVITY_TYPE in ('" + a + "') ";
					}
				}
			}

			sql += " ORDER BY ID DESC";

			if (numberOfRows != 0) {
				int startingValue = 0;
				int endingValue = 0;
				sql = sql + " OFFSET @startingValue ROWS FETCH NEXT @endingValue ROWS ONLY ";
				startingValue = numberOfRows * pageNumber;
				endingValue = numberOfRows;
				sql = sql.replace("@startingValue", String.valueOf(startingValue));
				sql = sql.replace("@endingValue", String.valueOf(endingValue));
			}

			AgLogger.logDebug(getClass(), sql);
			Query cb = entityManager.createNativeQuery(sql, TxnLog.class);
			txnLog = (List<TxnLog>) cb.getResultList();
			return txnLog;

		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String fetchTxnLogDetailsCount(List<String> merchant, String terminal, Date from, Date to,
			String[] posUpdate) {
		String count = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {

			String sql = AppProp.getProperty("fetch.txn.logs.count.query");

			if (from != null && to != null) {
				sql += " AND (" + DBUtil.getDateQueryParam("ACTIVITY_DATE") + " between '" + sdf.format(from)
						+ "' AND '" + sdf.format(to) + "') ";
			}

			if (terminal != null && !terminal.equals("")) {
				sql += " AND TERMINAL_ID = '" + terminal + "' ";
			}

			if (merchant != null) {
				if (merchant.size() != 0) {
					String jk = "";
					for (String mer : merchant) {
						jk += "'" + mer + "',";
					}

					if (jk.length() != 0) {
						jk = jk.substring(0, jk.length() - 1);
					}

					sql += " AND MERCHANT_ID IN (" + jk + ")";
				}
			}

			if (posUpdate != null) {
				if (posUpdate.length != 0) {
					String a = String.join("','", posUpdate);
					if (a != null) {
						sql += " AND ACTIVITY_TYPE in ('" + a + "') ";
					}
				}
			}

			AgLogger.logDebug(getClass(), sql);
			Query cb = entityManager.createNativeQuery(sql);
			int res = (int) cb.getSingleResult();
			count = String.valueOf(res);

			return count;

		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<TxnLog> fetchMqttTxnLogDetails(String merchant, String terminal, String serialNumber) {

		List<TxnLog> lst = new ArrayList<TxnLog>();
		try {

			String sql = AppProp.getProperty("terminal.search.query");
			String whereClause = "";

			if (terminal != null && !terminal.trim().equals("")) {
				whereClause += " AND TERMINAL_ID = '" + terminal + "' ";
			}
			if (merchant != null && !merchant.trim().equals("")) {
				whereClause += " AND MERCHANT_ID = '" + merchant + "' ";
			}
			if (serialNumber != null && !serialNumber.trim().equals("")) {
				whereClause += " AND SERIAL_NUMBER = '" + serialNumber + "' ";
			}

			sql = sql.replace("@WHERECLAUSE", whereClause);

			AgLogger.logInfo("TERMINAL SEARCH QUERY | " + sql);
			Query cb = entityManager.createNativeQuery(sql, TxnLog.class);
			lst = (List<TxnLog>) cb.getResultList();
		} catch (NoResultException nre) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

//	@Override
//	public List<TxnLog> fetchTxnLogDetailsVaryDB(String merchant, String terminal, Date from, Date to,
//			String posUpdate) {
//		try {
//		List<TxnLog> fileUploadProp = null;
//		TxnLog txnLogs = new TxnLog();
//		txnLogs.setActivityType("Differen_DB");
//		txnLogs.setMerchantId("000000000000000");
//		txnLogs.setTerminalId("0000000000000");
//		// txnLogs.setLogDate(new Timestamp(currentDate.getTime()));
//		txnLogs.setModel("J1900");
//		txnLogs.setSerialNumber("1234567891011");
//		txnLogs.setActivityRemarks("NEWLY ADDED");
//
//		fileUploadProp = new ArrayList<TxnLog>();
//		fileUploadProp.add(txnLogs);
//		return fileUploadProp;
//		} catch (NoResultException nre) {
//			return null;
//		}
//	}

}
