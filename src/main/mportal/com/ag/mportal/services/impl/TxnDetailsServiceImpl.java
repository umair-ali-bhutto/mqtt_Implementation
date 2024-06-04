package com.ag.mportal.services.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.db.proc.DBProceduresGeneric;
import com.ag.db.proc.GenericDbProcModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.services.TxnDetailsService;

@Service
public class TxnDetailsServiceImpl implements TxnDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtilAccess utilAccess;

	@Override
	@Transactional
	public long insertLog(TxnDetail que) throws Exception {
		try {
			entityManager.persist(que);
			return que.getId();
		} catch (NoResultException nre) {
			return 0l;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TxnSummary> fetchTxnSummary(List<String> merchant, String terminal, Date from, Date to, String setled,
			String batch, String auth) {
		List<TxnSummary> fileUploadProp = null;
		List<Object[]> objs = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String quer = DBUtil.fetchTxnSummary();
			String m = "";
			if (from != null && to != null) {

				m += " AND (" + DBUtil.getDateQueryParam("ENTRY_DATE") + " between '" + sdf.format(from) + "' AND '"
						+ sdf.format(to) + "') ";
			}
			if (merchant.size() != 0) {
				String jk = "";
				for (String mer : merchant) {
					jk += "'" + mer + "',";
				}

				if (jk.length() != 0) {
					jk = jk.substring(0, jk.length() - 1);
				}

				m += " AND MERCHANT_ID IN (" + jk + ")";
			}

			if (terminal != null) {
				m += " AND  TERMINAL_ID = '" + terminal + "' ";
			}

			if (auth != null) {
				m += " AND  AUTH_ID_N = '" + auth + "' ";
			}

			if (batch != null) {
				m += " AND  BATCH_NUMBER = '" + batch + "' ";
			}

			if (setled != null) {
				m += " AND  SETTELED = '" + setled + "' ";
			}

			m += " AND TYPE IN ( " + AppProp.getProperty("txn.summary.types") + " )";
			quer = quer.replaceAll("@PARAM", m);
			AgLogger.logDebug("", "TxnSummary Query: " + quer);

			Query cb = entityManager.createNativeQuery(quer);
			objs = (List<Object[]>) cb.getResultList();

			fileUploadProp = utilAccess.convertObjToTxnSummaryModel(objs);

			if (fileUploadProp != null) {
				TxnSummary tempTotal = new TxnSummary();
				tempTotal.setTxnType("TOTAL");

				Map<String, TxnSummary> map = fileUploadProp.stream()
						.collect(Collectors.toMap(TxnSummary::getTxnType, Function.identity()));

				Integer totalCount = UtilAccess.getCount(map, AppProp.getProperty("calc.total.count.txn.summary"));
				tempTotal.setCnt(!Objects.isNull(totalCount) ? totalCount : 0);

				Float totalAmount = UtilAccess.getAmount(map, AppProp.getProperty("calc.total.amount.txn.summary"));
				tempTotal.setAmount(!Objects.isNull(totalAmount) ? totalAmount : 0f);

				fileUploadProp.add(tempTotal);

			}

		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception while TxnSummary: ", e);
		}
		return fileUploadProp;
	}

	@Override
	@Transactional
	public void updateTxn(TxnDetail tcn) {
		try {
			entityManager.merge(tcn);
		} catch (NoResultException nre) {

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TxnSummary> fetchTxnSummaryUpd(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth) {
		List<TxnSummary> fileUploadProp = null;
		List<Object[]> objs = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String quer = DBUtil.fetchTxnSummary();
			String m = "";
			if (from != null && to != null) {
				m += " AND (" + DBUtil.getDateQueryParam("ENTRY_DATE") + " between '" + sdf.format(from) + "' AND '"
						+ sdf.format(to) + "') ";
			}
			if (merchant != null) {
				String jk = "";
				for (String mer : merchant) {
					jk += "'" + mer + "',";
				}

				if (jk.length() != 0) {
					jk = jk.substring(0, jk.length() - 1);
				}

				m += " AND MERCHANT_ID in (" + jk + ")";
			}

			if (terminal != null) {
				m += " AND  TERMINAL_ID = '" + terminal + "' ";
			}

			if (auth != null) {
				m += " AND  AUTH_ID_N = '" + auth + "' ";
			}

			if (batch != null) {
				m += " AND  BATCH_NUMBER = '" + batch + "' ";
			}

			if (setled != null) {
				m += " AND  SETTELED = '" + setled + "' ";
			}

			m += " AND TYPE IN ( " + AppProp.getProperty("txn.summary.types") + " )";

			quer = quer.replaceAll("@PARAM", m);
			AgLogger.logDebug("", "TxnSummary Query: " + quer);

			Query cb = entityManager.createNativeQuery(quer);
			objs = (List<Object[]>) cb.getResultList();

			fileUploadProp = utilAccess.convertObjToTxnSummaryModel(objs);

			if (fileUploadProp != null) {
				TxnSummary tempTotal = new TxnSummary();
				tempTotal.setTxnType("TOTAL");

				Map<String, TxnSummary> map = fileUploadProp.stream()
						.collect(Collectors.toMap(TxnSummary::getTxnType, Function.identity()));

				Integer totalCount = UtilAccess.getCount(map, AppProp.getProperty("calc.total.count.txn.summary"));
				tempTotal.setCnt(!Objects.isNull(totalCount) ? totalCount : 0);

				Float totalAmount = UtilAccess.getAmount(map, AppProp.getProperty("calc.total.amount.txn.summary"));
				tempTotal.setAmount(!Objects.isNull(totalAmount) ? totalAmount : 0f);

				fileUploadProp.add(tempTotal);

			}

		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception while TxnSummary: ", e);

		}
		return fileUploadProp;
	}

	@Override
	public boolean checkExistanceOfTxnDetail(String mId, String tId, String batchNumber, String refNumber,
			String authId, String type) {
		AgLogger.logDebug(getClass(), "checking Existance Of TxnDetail");
		try {
			Query cb = entityManager.createNamedQuery("TxnDetail.checkExistanceOfTxnDetail").setParameter("type", type)
					.setParameter("mid", mId).setParameter("tid", tId).setParameter("batchno", batchNumber)
					.setParameter("refno", refNumber).setParameter("authid", authId);
			@SuppressWarnings("unchecked")
			List<TxnDetail> txn = (List<TxnDetail>) cb.getResultList();
			if (txn.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (NoResultException nre) {
			return false;
		}

	}

	@Override
	public List<ReportModel> fetchTxnDetailsReport(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, int numberOfRows, int pageNumber) {
		try {
			List<ReportModel> lstReportItems = null;

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

				if (terminal != null) {
					whereClause += "  AND TERMINAL_ID = '" + terminal + "' ";
				}

				if (setled != null) {
					whereClause += "  AND SETTELED = '" + setled + "' ";
				}

				if (batch != null) {
					whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
				}

				if (auth != null) {
					whereClause += "  AND AUTH_ID_N = '" + auth + "' ";
				}

				whereClause += " AND TYPE IN ( " + AppProp.getProperty("txn.summary.types") + " )";

				String quer = DBUtil.getTxnDetailsQuery();
				quer = quer.replace("@whereClause", whereClause);

				if (numberOfRows != 0) {
					int startingValue = 0;
					int endingValue = 0;

					// FOR SQL
					if (DBUtil.getDialect() == 1) {
						quer = quer + " OFFSET @startingValue ROWS FETCH NEXT @endingValue ROWS ONLY ";
						startingValue = numberOfRows * pageNumber;
						endingValue = numberOfRows;
					} else {
						// FOR ORACLE (rn Alias for ROWNUM)
//						quer = "SELECT * FROM (" + quer + ") WHERE rn BETWEEN @startingValue AND @endingValue ";
						startingValue = 1 + (numberOfRows * pageNumber);
						endingValue = numberOfRows * (pageNumber + 1);
					}

					quer = quer.replace("@startingValue", String.valueOf(startingValue));
					quer = quer.replace("@endingValue", String.valueOf(endingValue));
				}

				AgLogger.logDebug(TxnDetailsServiceImpl.class, quer);

				Query cb = entityManager.createNativeQuery(quer);
				@SuppressWarnings("unchecked")
				List<Object[]> lst = cb.getResultList();

				if (!lst.isEmpty()) {
					lstReportItems = UtilAccess.reportList(lst);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReportItems;
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	@Override
	public List<ReportModel> fetchMarketSegmentReport(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, String mrktSeg, int numberOfRows, int pageNumber) {
		try {
			List<ReportModel> lstReportItems = null;

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

				if (terminal != null) {
					whereClause += "  AND TERMINAL_ID = '" + terminal + "' ";
				}

				if (setled != null) {
					whereClause += "  AND SETTELED = '" + setled + "' ";
				}

				if (batch != null) {
					whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
				}

				if (auth != null) {
					whereClause += "  AND AUTH_ID_N = '" + auth + "' ";
				}

				whereClause += " AND TYPE IN ( " + AppProp.getProperty("txn.summary.types") + " )";

				String quer = DBUtil.getMarketSegmentDetailsQuery();
				quer = quer.replace("@whereClause", whereClause);
				quer = quer.replace("@MarketSegmentWhereClause", mrktSeg);

				if (numberOfRows != 0) {
					int startingValue = 0;
					int endingValue = 0;

					// FOR SQL
					if (DBUtil.getDialect() == 1) {
						quer = quer + " OFFSET @startingValue ROWS FETCH NEXT @endingValue ROWS ONLY ";
						startingValue = numberOfRows * pageNumber;
						endingValue = numberOfRows;
					} else {
						// FOR ORACLE (rn Alias for ROWNUM)
//						quer = "SELECT * FROM (" + quer + ") WHERE rn BETWEEN @startingValue AND @endingValue ";
						startingValue = 1 + (numberOfRows * pageNumber);
						endingValue = numberOfRows * (pageNumber + 1);
					}

					quer = quer.replace("@startingValue", String.valueOf(startingValue));
					quer = quer.replace("@endingValue", String.valueOf(endingValue));
				}

				AgLogger.logDebug(TxnDetailsServiceImpl.class, quer);

				Query cb = entityManager.createNativeQuery(quer);
				@SuppressWarnings("unchecked")
				List<Object[]> lst = cb.getResultList();

				if (!lst.isEmpty()) {
					lstReportItems = UtilAccess.reportList(lst);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReportItems;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String fetchTxnDetailsCount(List<String> merchant, String terminal, Date from, Date to, String setled,
			String batch, String auth, String reportType) {
		try {
			String count = null;

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

				if (terminal != null) {
					whereClause += "  AND TERMINAL_ID = '" + terminal + "' ";
				}

				if (setled != null) {
					whereClause += "  AND SETTELED = '" + setled + "' ";
				}

				if (batch != null) {
					whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
				}

				if (auth != null) {
					whereClause += "  AND AUTH_ID_N = '" + auth + "' ";
				}

				if (reportType.equals("DETAILS")) {
					whereClause += " AND TYPE IN ( " + AppProp.getProperty("txn.summary.types") + " )";
				} else if (reportType.equals("VOID")) {
					whereClause += " AND TYPE IN ( " + AppProp.getProperty("void.txn.report.types") + " )";
				} else if (reportType.equals("SETTLEMENT")) {
					whereClause += " AND TYPE IN ( " + AppProp.getProperty("settlement.txn.report.types") + " )";
				}

				String quer = DBUtil.getMarketSegmentCountQuery();
				quer = quer.replace("@whereClause", whereClause);

				AgLogger.logDebug(TxnDetailsServiceImpl.class, quer);

				Query cb = entityManager.createNativeQuery(quer);

				int res = 0;

				// FOR SQL
				if (DBUtil.getDialect() == 1) {
					res = (int) cb.getSingleResult();
				} else {
					// FOR ORACLE
					BigDecimal result = (BigDecimal) cb.getSingleResult();
					res = Integer.valueOf(result.intValue());
				}

				count = String.valueOf(res);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	@Override
	public String fetchMarketSegmentCount(List<String> merchant, String terminal, Date from, Date to, String setled,
			String batch, String auth, String reportType) {
		try {
			String count = null;

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

				if (terminal != null) {
					whereClause += "  AND TERMINAL_ID = '" + terminal + "' ";
				}

				if (setled != null) {
					whereClause += "  AND SETTELED = '" + setled + "' ";
				}

				if (batch != null) {
					whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
				}

				if (auth != null) {
					whereClause += "  AND AUTH_ID_N = '" + auth + "' ";
				}

				if (reportType.equals("DETAILS")) {
					whereClause += " AND TYPE IN ( " + AppProp.getProperty("txn.summary.types") + " )";
				} else if (reportType.equals("VOID")) {
					whereClause += " AND TYPE IN ( " + AppProp.getProperty("void.txn.report.types") + " )";
				} else if (reportType.equals("SETTLEMENT")) {
					whereClause += " AND TYPE IN ( " + AppProp.getProperty("settlement.txn.report.types") + " )";
				}

				String quer = DBUtil.getTxnDetailsCountQuery();
				quer = quer.replace("@whereClause", whereClause);

				AgLogger.logDebug(TxnDetailsServiceImpl.class, quer);

				Query cb = entityManager.createNativeQuery(quer);

				int res = 0;

				// FOR SQL
				if (DBUtil.getDialect() == 1) {
					res = (int) cb.getSingleResult();
				} else {
					// FOR ORACLE
					BigDecimal result = (BigDecimal) cb.getSingleResult();
					res = Integer.valueOf(result.intValue());
				}

				count = String.valueOf(res);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<ReportModel> fetchSettlementTxnDetailsReport(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, int numberOfRows, int pageNumber) {
		try {
			List<ReportModel> lstReportItems = null;
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

				if (terminal != null) {
					whereClause += "  AND TERMINAL_ID = '" + terminal + "' ";
				}

				if (setled != null) {
					whereClause += "  AND SETTELED = '" + setled + "' ";
				}

				if (batch != null) {
					whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
				}

				if (auth != null) {
					whereClause += "  AND AUTH_ID_N = '" + auth + "' ";
				}

				whereClause += " AND TYPE IN ( " + AppProp.getProperty("settlement.txn.report.types") + " )";

				String quer = DBUtil.getTxnDetailsQuery();
				quer = quer.replace("@whereClause", whereClause);

				if (numberOfRows != 0) {
					int startingValue = 0;
					int endingValue = 0;

					// FOR SQL
					if (DBUtil.getDialect() == 1) {
						quer = quer + " OFFSET @startingValue ROWS FETCH NEXT @endingValue ROWS ONLY ";
						startingValue = numberOfRows * pageNumber;
						endingValue = numberOfRows;
					} else {
						// FOR ORACLE (rn Alias for ROWNUM)
						quer = "SELECT * FROM (" + quer + ") WHERE rn BETWEEN @startingValue AND @endingValue ";
						startingValue = 1 + (numberOfRows * pageNumber);
						endingValue = numberOfRows * (pageNumber + 1);
					}

					quer = quer.replace("@startingValue", String.valueOf(startingValue));
					quer = quer.replace("@endingValue", String.valueOf(endingValue));
				}

				// AgLogger.logInfo("", quer);
				AgLogger.logDebug(TxnLogsDetailServiceImpl.class, quer);
				Query cb = entityManager.createNativeQuery(quer);

				@SuppressWarnings("unchecked")
				List<Object[]> lst = cb.getResultList();

				if (!lst.isEmpty()) {
					lstReportItems = UtilAccess.reportList(lst);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
			return lstReportItems;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<ReportModel> fetchVoidTxnDetailsForReport(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, String type, int numberOfRows, int pageNumber) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Void Txns Records");

			List<ReportModel> lstReportItems = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			try {

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

				if (terminal != null) {
					whereClause += " AND TERMINAL_ID = '" + terminal + "' ";
				}

				if (setled != null) {
					whereClause += "  AND SETTELED = '" + setled + "' ";
				}

				if (batch != null) {
					whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
				}

				if (auth != null) {
					whereClause += "  AND AUTH_ID_N = '" + auth + "' ";
				}

				if (!Objects.isNull(type)) {
					whereClause += "  AND TYPE IN ( " + type + " ) ";
				}

				String sql = DBUtil.getTxnDetailsQuery();
				sql = sql.replace("@whereClause", whereClause);

				if (numberOfRows != 0) {
					int startingValue = 0;
					int endingValue = 0;

					// FOR SQL
					if (DBUtil.getDialect() == 1) {
						sql = sql + " OFFSET @startingValue ROWS FETCH NEXT @endingValue ROWS ONLY ";
						startingValue = numberOfRows * pageNumber;
						endingValue = numberOfRows;
					} else {
						// FOR ORACLE (rn Alias for ROWNUM)
						sql = "SELECT * FROM (" + sql + ") WHERE rn BETWEEN @startingValue AND @endingValue ";
						startingValue = 1 + (numberOfRows * pageNumber);
						endingValue = numberOfRows * (pageNumber + 1);
					}

					sql = sql.replace("@startingValue", String.valueOf(startingValue));
					sql = sql.replace("@endingValue", String.valueOf(endingValue));
				}

				AgLogger.logDebug(TxnLogsDetailServiceImpl.class, sql);

				Query cb = entityManager.createNativeQuery(sql);

				@SuppressWarnings("unchecked")
				List<Object[]> lst = cb.getResultList();
				if (!lst.isEmpty()) {
					lstReportItems = UtilAccess.reportList(lst);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
			return lstReportItems;

		} catch (NoResultException nre) {
			return null;
		}

	}

	@Override
	public ReportModel fetchTxnDetailById(String txnDetailId) {
		AgLogger.logDebug(getClass(), "Fetching Txns Records");

		List<ReportModel> lstReportItems = null;
		String whereClause = " ";

		try {

			whereClause += "  AND TXN_DETAILS.ID =" + txnDetailId;

			String sql = DBUtil.getTxnDetailsQuery();
			sql = sql.replace("@whereClause", whereClause);

			AgLogger.logDebug(TxnLogsDetailServiceImpl.class, sql);

			Query cb = entityManager.createNativeQuery(sql);

			@SuppressWarnings("unchecked")
			List<Object[]> lst = cb.getResultList();
			if (!lst.isEmpty()) {
				lstReportItems = UtilAccess.reportList(lst);
				return lstReportItems.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	@Override
	public ReportModel fetchTxnDetailByIdForRoc(String txnDetailId) {
		AgLogger.logDebug(getClass(), "Fetching Txns Records");

		List<ReportModel> lstReportItems = null;
		String whereClause = " ";

		try {

			whereClause += "  AND TXN_DETAILS.ID =" + txnDetailId;

			String sql = DBUtil.getRocTxnDetailsQuery();
			sql = sql.replace("@whereClause", whereClause);

			AgLogger.logDebug(TxnLogsDetailServiceImpl.class, sql);

			Query cb = entityManager.createNativeQuery(sql);

			@SuppressWarnings("unchecked")
			List<Object[]> lst = cb.getResultList();
			if (!lst.isEmpty()) {
				lstReportItems = UtilAccess.reportList(lst);
				return lstReportItems.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	@Override
	public TxnDetail fetchTxnDetail(String batch, String tid, String auth) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchTxnDetail" + batch + "|" + tid + "|" + auth);
			Query cb = entityManager.createNamedQuery("TxnDetail.fetchTxnDetailByATH").setParameter("status", "SUCCESS")
					.setParameter("batchNumber", batch).setParameter("invoiceNum", auth)
					.setParameter("terminalID", tid);

			return (TxnDetail) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public TxnDetail fetchTxnDetailVoid(String batch, String tid, String invoice) {
		try {
			// AgLogger.logDebug(getClass(), "Fetching Rec From fetchTxnDetailsVoid");
			Query cb = entityManager.createNamedQuery("TxnDetail.fetchTxnDetailsVoid").setParameter("status", "SUCCESS")
					.setParameter("batchNumber", batch).setParameter("terminalID", tid)
					.setParameter("invoiceNum", invoice).setParameter("type", "VOID");
			return (TxnDetail) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public TxnDetail fetchTxnDetailNonVoid(String batch, String tid, String invoice) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchTxnDetailsVoid");
			Query cb = entityManager.createNamedQuery("TxnDetail.fetchTxnDetailsNonVoid")
					.setParameter("status", "SUCCESS").setParameter("batchNumber", batch)
					.setParameter("terminalID", tid).setParameter("invoiceNum", invoice).setParameter("type", "VOID");
			return (TxnDetail) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<MerchantTerminalDetail> convertTxnDetails(String xmls, int txnLogId) {
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);
		String mid = "";
		String tid = "";
		try {
			mid = (String) onj.get("MID");
		} catch (Exception e) {

		}

		try {
			tid = (String) onj.get("TID");
		} catch (Exception e) {

		}
		List<MerchantTerminalDetail> DetailsList = new ArrayList<MerchantTerminalDetail>();

		MerchantTerminalDetail mtd = null;

		if (onj.containsKey("additionalData")) {
			JSONObject jArrayAdditionalData = (JSONObject) onj.get("additionalData");
			if (jArrayAdditionalData.containsKey("wlanInfo")) {
				try {
					JSONObject jobWlanInfo = (JSONObject) jArrayAdditionalData.get("wlanInfo");
					for (Iterator iterator = jobWlanInfo.keySet().iterator(); iterator.hasNext();) {
						String key = (String) iterator.next();
						String val = (String) jobWlanInfo.get(key);
						try {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-WLANINFO");
							mtd.setDataName(key.toUpperCase());
							mtd.setDataValue(val.toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							DetailsList.add(mtd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (jArrayAdditionalData.containsKey("ethernetInfo")) {
				try {
					JSONObject jobWlanInfo = (JSONObject) jArrayAdditionalData.get("ethernetInfo");
					for (Iterator iterator = jobWlanInfo.keySet().iterator(); iterator.hasNext();) {
						String key = (String) iterator.next();
						String val = (String) jobWlanInfo.get(key);
						try {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-ETHERNETINFO");
							mtd.setDataName(key.toUpperCase());
							mtd.setDataValue(val.toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							DetailsList.add(mtd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (jArrayAdditionalData.containsKey("gsmInfo")) {
				try {
					JSONObject jobWlanInfo = (JSONObject) jArrayAdditionalData.get("gsmInfo");
					for (Iterator iterator = jobWlanInfo.keySet().iterator(); iterator.hasNext();) {
						String key = (String) iterator.next();
						String val = (String) jobWlanInfo.get(key);
						try {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-GSMINFO");
							mtd.setDataName(key.toUpperCase());
							mtd.setDataValue(val.toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							DetailsList.add(mtd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (jArrayAdditionalData.containsKey("providersInfo")) {
				try {
					JSONObject jobWlanInfo = (JSONObject) jArrayAdditionalData.get("providersInfo");
					for (Iterator iterator = jobWlanInfo.keySet().iterator(); iterator.hasNext();) {
						String key = (String) iterator.next();
						String val = (String) jobWlanInfo.get(key);
						try {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PROVIDERSINFO");
							mtd.setDataName(key.toUpperCase());
							mtd.setDataValue(val.toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							DetailsList.add(mtd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (jArrayAdditionalData.containsKey("additionalinfo")) {
				try {
					JSONObject jobWlanInfo = (JSONObject) jArrayAdditionalData.get("additionalinfo");
					for (Iterator iterator = jobWlanInfo.keySet().iterator(); iterator.hasNext();) {
						String key = (String) iterator.next();
						String val = (String) jobWlanInfo.get(key);
						try {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-ADDINFO");
							mtd.setDataName(key.toUpperCase());
							mtd.setDataValue(val.toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							DetailsList.add(mtd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (jArrayAdditionalData.containsKey("preAuthInfo")) {
				try {
					JSONObject jobWlanInfo = (JSONObject) jArrayAdditionalData.get("preAuthInfo");
					for (Iterator iterator = jobWlanInfo.keySet().iterator(); iterator.hasNext();) {
						String key = (String) iterator.next();
						String val = (String) jobWlanInfo.get(key);
						try {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PREAUTHINFO");
							mtd.setDataName(key.toUpperCase());
							if (key.toUpperCase().equals("FIELDONEDATA")) {
								GenericDbProcModel f = DBProceduresGeneric.encryptCardNumber("00000",
										"MP_Data_Enc_Key_AES256", "000000123456789", val.toUpperCase());
								if (f.getVrsp_code().equals("0000")) {
									mtd.setDataValue(f.getVmessage());
								} else {
									mtd.setDataValue("N-A");
								}
							} else {
								mtd.setDataValue(val.toUpperCase());
							}
							mtd.setTxnLogsId(txnLogId);
							DetailsList.add(mtd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return DetailsList;
	}

	@Override
	public String[] getdetailsForPreAuth(String mid, String tid, String authCode) {

		String[] result = new String[12];
		result[0] = "00001";
		result[1] = "ERROR";
		try {

			String query = "SELECT * from VW_GET_PRE_AUTH_DETAILS WHERE MID = '" + mid + "' AND AUTH_ID = '" + authCode
					+ "' ORDER BY TXNDATE DESC";

			AgLogger.logDebug(TxnDetailsServiceImpl.class, query);

			Query cb = entityManager.createNativeQuery(query);
			Object[] lst = (Object[]) cb.setMaxResults(1).getSingleResult();
			if (lst != null) {
				result[0] = "00000";
				result[1] = "SUCCESS.";

				// card number
				result[2] = lst[0].toString();

				// card expiry
				result[3] = lst[1].toString();

				// txn details id
				result[4] = lst[2].toString();

				// txn amount
				result[5] = lst[3].toString();

				// txn date
				result[6] = lst[4].toString();

				// is paid
				result[8] = lst[5].toString();

				// paid date
				result[9] = lst[6].toString();

				// mid
				result[10] = lst[7].toString();

				// tid
				result[11] = lst[8].toString();

			} else {
				result[0] = "00001";
				result[1] = "NO RECORD FOUND AGAINST MID AND TID AND AUTH CODE.";
			}
		} catch (NoResultException nre) {
			result[0] = "00001";
			result[1] = "NO RECORD FOUND AGAINST MID AND TID AND AUTH CODE.";
		}
		return result;
	}

	@Override
	public TxnDetail fetchTxnDetailPreAuth(String mid, String tid, String auth) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchTxnDetail" + mid + "|" + tid + "|" + auth);
			Query cb = entityManager.createNamedQuery("TxnDetail.fetchTxnDetailByATHPreAuth")
					.setParameter("status", "SUCCESS").setParameter("mid", mid).setParameter("authIdN", auth);

			return (TxnDetail) cb.setMaxResults(1).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public TxnDetail fetchTxnDetailVoidTxn(String mid, String tid, String auth) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchTxnDetail" + mid + "|" + tid + "|" + auth);
			Query cb = entityManager.createNamedQuery("TxnDetail.fetchTxnDetailByATHVoid")
					.setParameter("status", "SUCCESS").setParameter("mid", mid).setParameter("authIdN", auth);

			return (TxnDetail) cb.setMaxResults(1).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void updateRecordPreAuth(String txnDetailsId, String paidFlag, String dateTime) {
		try {
			String query1 = "UPDATE MERCHANT_TERMINAL_DETAILS SET DATA_VALUE = '" + paidFlag + "' WHERE TXN_LOGS_ID = '"
					+ txnDetailsId + "' AND DATA_NAME='PREAUTHPAID'";
			Query query = entityManager.createNativeQuery(query1);
			query.executeUpdate();

			String query2 = "UPDATE MERCHANT_TERMINAL_DETAILS SET DATA_VALUE = '" + dateTime + "' WHERE TXN_LOGS_ID = '"
					+ txnDetailsId + "' AND DATA_NAME='PREAUTHPAIDDATE'";
			Query query23 = entityManager.createNativeQuery(query2);
			query23.executeUpdate();

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}

}
