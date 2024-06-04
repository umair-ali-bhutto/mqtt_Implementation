package com.ag.mportal.services.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.generic.model.KeyValueModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.DiscountMaster;
import com.ag.mportal.model.DMSReportReportModel;
import com.ag.mportal.model.DiscountMasterSlabModel;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.DiscountMasterService;

@Service
public class DiscountMasterServiceImpl implements DiscountMasterService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	UtilAccess utilAccess;

	@Override
	@Transactional
	public void insert(DiscountMaster tcn) {
		try {
			entityManager.persist(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Insert Exception in DiscountMaster: ", ex);
		}
	}

	@Override
	@Transactional
	public void update(DiscountMaster tcn) {
		try {
			entityManager.merge(tcn);
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Update Exception in DiscountMaster: ", ex);
		}
	}

	@Override
	public List<DiscountMaster> fetchLstDiscountMaster(String name, String status, String startDate, String endDate) {
		List<DiscountMaster> lst = new ArrayList<DiscountMaster>();
		try {
			String whereClause = "";
			if (!Objects.isNull(name)) {
				whereClause += " LOWER(DISC_NAME) LIKE '%" + name.toLowerCase() + "%' ";
			}

			if (!Objects.isNull(startDate) && !Objects.isNull(endDate)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " ((START_DATE BETWEEN '" + startDate + "' AND '" + endDate + "') OR (END_DATE BETWEEN '"
						+ startDate + "' AND '" + endDate + "')) ";
			}

			if (!Objects.isNull(status) && !status.equalsIgnoreCase("all")) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " DISC_STATUS= '" + status + "' ";
			}

			String queryString = AppProp.getProperty("select.discount.master.query");

			if (!whereClause.isEmpty()) {
				String st = " WHERE " + whereClause;
				queryString = queryString.replace("@whereClause", st);
			} else {
				queryString = queryString.replace("@whereClause", "");
			}

			queryString = queryString + " ORDER BY ENTRY_ON DESC";

			AgLogger.logDebug(DiscountMasterServiceImpl.class, queryString);

			Query cb = entityManager.createNativeQuery(queryString, DiscountMaster.class);

			lst = (List<DiscountMaster>) cb.getResultList();

			AgLogger.logDebug(getClass(), "FETCHED LIST DISCOUNT MASTERS SIZE: " + lst.size());

		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING DISCOUNT MASTERS: ", ex);
			ex.printStackTrace();
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountMasterSlabModel> fetchAll(String corpId, Timestamp txnDate, String currency, String txnAmount,
			String status) {
		List<DiscountMasterSlabModel> lst = new ArrayList<DiscountMasterSlabModel>();
		try {
			String query = "SELECT * FROM DISC_MASTER a,DISC_SLAB b where a.ID = b.DISC_ID AND a.CORP_ID = '" + corpId
					+ "' AND ('" + txnDate + "' between a.START_DATE AND a.END_DATE) AND CURRENCY = '" + currency
					+ "' AND ISNULL(" + txnAmount
					+ ",0) between ISNULL(b.AMOUNT_FROM,0) and ISNULL(b.AMOUNT_TO,0) AND a.DISC_STATUS = '" + status
					+ "'";
			AgLogger.logInfo("DiscountMasterSlabModel Query: " + query);
			Query q = entityManager.createNativeQuery(query, DiscountMasterSlabModel.class);

			lst = (List<DiscountMasterSlabModel>) q.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return lst;
	}

	@Override
	public DiscountMaster fetchById(long id) {
		DiscountMaster mdl = new DiscountMaster();
		try {
			Query q = entityManager.createNamedQuery("DiscountMaster.fetchById", DiscountMaster.class)
					.setParameter("id", id);
			mdl = (DiscountMaster) q.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
		}
		return mdl;
	}

	@Override
	public String fetchDiscountTxnDetailsCount(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, String reportType) {
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
	public List<String> fetchTidsOnMerchantId(String mid) {
		List<String> tidList = new ArrayList<String>();
		try {
			String query = "select DISTINCT value from parameter where PARNAMELOC='TERMINALID' and PARTID in (select PARTID from parameter where PARNAMELOC='MERCHANTID' and value = '"
					+ mid + "')";
			AgLogger.logInfo("DISCOUNT FETCH TID BY MID QUERY:" + query);
			Query q = entityManager.createNativeQuery(query);
			List<String> tempTids = (List<String>) q.getResultList();
			for (String tid : tempTids) {
				tidList.add(tid + " (" + mid + ")");
			}
		} catch (NoResultException e) {
			AgLogger.logInfo("NO TIDS FOUND ON MID:" + mid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tidList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KeyValueModel> fetchLstDiscountNameByCorpId(String corpId) {
		List<KeyValueModel> lstSlab = null;
		List<KeyValueModel> fileUploadProp = null;
		List<Object[]> objs = null;
		try {
			String query = "select a.ID , a.DISC_NAME from DISC_MASTER a where a.DISC_STATUS ='1' and a.CORP_ID = '"
					+ corpId + "'";

			Query cb = entityManager.createNativeQuery(query);
			objs = (List<Object[]>) cb.getResultList();
			fileUploadProp = utilAccess.convertObjToKeyValueModel(objs);

			if (!Objects.isNull(fileUploadProp) && !fileUploadProp.isEmpty()) {
				return fileUploadProp;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FETCHING BIN BY DISCOUNT ID: ", ex);
		}

		return null;
	}

	@Override
	public List<DMSReportReportModel> fetchDiscountTxnDetailsReport(List<String> merchant, Date from, Date to,
			String discStatus, List<String> disName, int numberOfRows, int pageNumber) {
		try {
			List<DMSReportReportModel> lstReportItems = null;

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				String whereClause = new String();

				if (from != null && to != null) {
					whereClause += " AND (" + DBUtil.getDateQueryParam("dl.ENTRY_ON") + " between '" + sdf.format(from)
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

					whereClause += " AND MID IN (" + jk + ")";
				}

				if (discStatus != null) {
					whereClause += "  AND dm.DISC_STATUS = '" + discStatus + "' ";
				}
				if (disName != null) {
					if (disName.size() != 0) {
						String jk = "";
						for (String mer : disName) {
							jk += "'" + mer + "',";
						}

						if (jk.length() != 0) {
							jk = jk.substring(0, jk.length() - 1);
						}

						whereClause += " AND dm.ID IN (" + jk + ")";
					}
				}
				String quer = DBUtil.getDiscountTxnDetailsQuery();
				quer = quer.replace("@whereClause", whereClause);

				AgLogger.logDebug(DiscountMasterServiceImpl.class, quer);

				Query cb = entityManager.createNativeQuery(quer);
				@SuppressWarnings("unchecked")
				List<Object[]> lst = cb.getResultList();

				if (!lst.isEmpty()) {
					lstReportItems = UtilAccess.dmsReportList(lst);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReportItems;
		} catch (NoResultException nre) {
			return null;
		}
	}
}
