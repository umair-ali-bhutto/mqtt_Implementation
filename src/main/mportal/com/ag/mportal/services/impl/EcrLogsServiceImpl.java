package com.ag.mportal.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.EcrLog;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.model.UtilReport;
import com.ag.mportal.services.EcrLogsService;
import com.opencsv.CSVWriter;

@Service
public class EcrLogsServiceImpl implements EcrLogsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtilAccess utilAccess;

	@Override
	@Transactional
	public long insert(EcrLog tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return 0l;
		}
	}

	@Override
	@Transactional
	public void update(EcrLog tcn) {
		try {
			entityManager.merge(tcn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public EcrLog fetchByID(String mid, String tid, long id) {
		EcrLog lstFedRates = null;
		Session sess = null;
		try {
			Query query = entityManager.createNamedQuery("EcrLog.fetchAllById");
			query.setParameter("id", id);
			query.setParameter("mid", mid);
			query.setParameter("tid", tid);
			query.setParameter("state", "ENQUIRY");
			lstFedRates = (EcrLog) query.setMaxResults(1).getSingleResult();
			return lstFedRates;
		} catch (Exception e) {
			//AgLogger.logerror(getClass(), "Exception", e);
			return null;
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

	}

	@Override
	public EcrLog fetchByIDforPaymentSchedular(String mid, String tid, long id) {
		EcrLog lstFedRates = null;
		Session sess = null;
		try {
			Query query = entityManager.createNamedQuery("EcrLog.fetchAllById");
			query.setParameter("id", id);
			query.setParameter("mid", mid);
			query.setParameter("tid", tid);
			query.setParameter("state", "PAYMENT_FAILED");
			lstFedRates = (EcrLog) query.setMaxResults(1).getSingleResult();
			return lstFedRates;
		} catch (Exception e) {
			//AgLogger.logerror(getClass(), "Exception", e);
			return null;
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

	}

	@Override
	public List<EcrLog> fetchByMidTid(String mid, String tid) {
		List<EcrLog> lstFedRates = null;
		Session sess = null;
		try {
			Query query = entityManager.createNamedQuery("EcrLog.fetchAllRecByMIDTID");
			query.setParameter("mid", mid);
			query.setParameter("tid", tid);
			query.setParameter("state", "ENQUIRY");
			lstFedRates = (List<EcrLog>) query.getResultList();
			return lstFedRates;
		} catch (Exception e) {
			//AgLogger.logerror(getClass(), "Exception", e);
			return null;
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

	}

	@Override
	public EcrLog fetchByMidTidOrder(String mid, String tid) {
		EcrLog lstFedRates = null;
		Session sess = null;
		try {
			Query query = entityManager.createNamedQuery("EcrLog.fetchAllRecByMIDTIDOrder");
			query.setParameter("mid", mid);
			query.setParameter("tid", tid);
			query.setParameter("state", "ENQUIRY");
			lstFedRates = (EcrLog) query.setMaxResults(1).getSingleResult();
			return lstFedRates;
		} catch (Exception e) {
			//AgLogger.logerror(getClass(), "Exception", e);
			return null;
		} finally {
			if (sess != null && (sess.isOpen() || sess.isConnected()))
				sess.close();
		}

	}

	@Override
	public EcrLog fetchByOrderId(String orderId) {
		EcrLog mdl = null;
		try {
			Query query = entityManager.createNamedQuery("EcrLog.fetchAllRecByOrderId");
			query.setParameter("orderId", orderId);
			mdl = (EcrLog) query.setMaxResults(1).getSingleResult();
			return mdl;
		} catch (NoResultException e) {
			return null;
		}

	}
	
	@Override
	public EcrLog fetchPaymentByMidTid(String mid, String tid) {
		EcrLog mdl = null;
		try {
			Query query = entityManager.createNamedQuery("EcrLog.fetchAllPaymentByMidTid");
			query.setParameter("mid", mid);
			query.setParameter("tid", tid);
			mdl = (EcrLog) query.setMaxResults(1).getSingleResult();
			return mdl;
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public EcrLog fetchPaymentStatusByOrderId(String orderId) {
		EcrLog mdl = null;
		try {
			Query query = entityManager.createNamedQuery("EcrLog.fetchByOrderId");
			query.setParameter("orderId", orderId);
			mdl = (EcrLog) query.setMaxResults(1).getSingleResult();
			return mdl;
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public String fetchWebEcrDetailsCount(List<String> merchant, List<String> channelList, String terminal, Date from,
			Date to, String setled, String batch, String auth, String reportType) {
		String count = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("td.ENTRY_DATE") + " between '" + sdf.format(from)
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

				whereClause += " AND td.MERCHANT_ID IN (" + jk + ")";
			}

			if (terminal != null) {
				whereClause += "  AND td.TERMINAL_ID = '" + terminal + "' ";
			}

			if (setled != null) {
				whereClause += "  AND td.SETTELED = '" + setled + "' ";
			}

			if (batch != null) {
				whereClause += "  AND td.BATCH_NUMBER = '" + batch + "' ";
			}

			if (auth != null) {
				whereClause += "  AND td.AUTH_ID_N = '" + auth + "' ";
			}

			if (channelList.size() != 0) {
				String channels = "";
				for (String channel : channelList) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				whereClause += " AND el.CORPORATE IN (" + channels + ")";
			}

			if (reportType.equals("DETAILS")) {
				whereClause += " AND td.TYPE IN ( " + AppProp.getProperty("web.ecr.txn.summary.types") + " )";
			} else if (reportType.equals("SETTLEMENT")) {
				whereClause += " AND td.TYPE IN ( " + AppProp.getProperty("web.ecr.settlement.txn.report.types") + " )";
			}

			String quer = DBUtil.getWebEcrTxnDetailsCountQuery();
			quer = quer.replace("@whereClause", whereClause);

			AgLogger.logDebug(TxnDetailsServiceImpl.class, quer);

			Query cb = entityManager.createNativeQuery(quer);
			BigDecimal result = (BigDecimal) cb.getSingleResult();
			int res = result.intValue();

			count = String.valueOf(res);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<ReportModel> fetchWebEcrSettlementReport(List<String> merchant, List<String> channelList,
			String terminal, Date from, Date to, String setled, String batch, String auth, int numberOfRows,
			int pageNumber) {
		List<ReportModel> lstReportItems = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("td.ENTRY_DATE") + " between '" + sdf.format(from)
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

				whereClause += " AND td.MERCHANT_ID IN (" + jk + ")";
			}

			if (terminal != null) {
				whereClause += "  AND td.TERMINAL_ID = '" + terminal + "' ";
			}

			if (setled != null) {
				whereClause += "  AND td.SETTELED = '" + setled + "' ";
			}

			if (batch != null) {
				whereClause += "  AND td.BATCH_NUMBER = '" + batch + "' ";
			}

			if (auth != null) {
				whereClause += "  AND td.AUTH_ID_N = '" + auth + "' ";
			}

			if (channelList.size() != 0) {
				String channels = "";
				for (String channel : channelList) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				whereClause += " AND el.CORPORATE IN (" + channels + ")";
			}
			whereClause += " AND td.TYPE IN ( " + AppProp.getProperty("web.ecr.settlement.txn.report.types") + " )";

			String quer = DBUtil.getWebEcrTxnDetailsQuery();
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
	}

	@Override
	public List<ReportModel> fetchWebEcrTxnReport(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth, int numberOfRows, int pageNumber) {
		List<ReportModel> lstReportItems = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("td.ENTRY_DATE") + " between '" + sdf.format(from)
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

				whereClause += " AND td.MERCHANT_ID IN (" + jk + ")";
			}

			if (terminal != null) {
				whereClause += "  AND td.TERMINAL_ID = '" + terminal + "' ";
			}

			if (setled != null) {
				whereClause += "  AND td.SETTELED = '" + setled + "' ";
			}

			if (batch != null) {
				whereClause += "  AND td.BATCH_NUMBER = '" + batch + "' ";
			}

			if (auth != null) {
				whereClause += "  AND td.AUTH_ID_N = '" + auth + "' ";
			}

			if (channelList.size() != 0) {
				String channels = "";
				for (String channel : channelList) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				whereClause += " AND el.CORPORATE IN (" + channels + ")";
			}
			whereClause += " AND td.TYPE IN ( " + AppProp.getProperty("web.ecr.txn.summary.types") + " )";

			String quer = DBUtil.getWebEcrTxnDetailsQuery();
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
	}
	
	@Override
	public List<ReportModel> fetchKEBillPaymentReport(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth, String ack, int numberOfRows, int pageNumber) {
		List<ReportModel> lstReportItems = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("td.ENTRY_DATE") + " between '" + sdf.format(from)
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

				whereClause += " AND td.MERCHANT_ID IN (" + jk + ")";
			}

			if (terminal != null) {
				whereClause += "  AND td.TERMINAL_ID = '" + terminal + "' ";
			}

			if (setled != null) {
				whereClause += "  AND td.SETTELED = '" + setled + "' ";
			}

			if (batch != null) {
				whereClause += "  AND td.BATCH_NUMBER = '" + batch + "' ";
			}

			if (auth != null) {
				whereClause += "  AND td.AUTH_ID_N = '" + auth + "' ";
			}
			
			if (auth != null) {
				whereClause += "  AND td.AUTH_ID_N = '" + auth + "' ";
			}
			
			if (ack != null) {
				String remarks = null;
				if(ack.equalsIgnoreCase("Y")) {
					remarks = "0000|Success";
				}else {
					remarks = "0006|Something Went Wrong.";
				}
				whereClause += "  AND el.REMARKS = '" + remarks + "' ";
			}

		
			whereClause += " AND td.TYPE IN ( " + AppProp.getProperty("ke.bill.payment.types") + " )";

			String quer = DBUtil.getKEBillPaymentQuery();
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
	}
	

	@Override
	public List<TxnSummary> fetchWebEcrTxnSummary(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth) {
		List<TxnSummary> fileUploadProp = null;
		List<Object[]> objs = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String quer = DBUtil.getWebEcrTxnSummaryQuery();

			String m = "";
			if (from != null && to != null) {

				m += " AND (" + DBUtil.getDateQueryParam("td.ENTRY_DATE") + " between '" + sdf.format(from) + "' AND '"
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

				m += " AND td.MERCHANT_ID IN (" + jk + ")";
			}

			if (terminal != null) {
				m += " AND  td.TERMINAL_ID = '" + terminal + "' ";
			}

			if (auth != null) {
				m += " AND  td.AUTH_ID_N = '" + auth + "' ";
			}

			if (batch != null) {
				m += " AND  td.BATCH_NUMBER = '" + batch + "' ";
			}

			if (setled != null) {
				m += " AND  td.SETTELED = '" + setled + "' ";
			}

			if (channelList.size() != 0) {
				String channels = "";
				for (String channel : channelList) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				m += " AND el.CORPORATE IN (" + channels + ")";
			}

			m += " AND td.TYPE IN ( " + AppProp.getProperty("web.ecr.txn.summary.types") + " )";
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

				for (Map.Entry<String, TxnSummary> entry : map.entrySet()) {
					String key = entry.getKey();
					TxnSummary value = entry.getValue();
					System.out.println("Key: " + key + ", Value.txntype: " + value.getTxnType() + ", Value.cnt: "
							+ value.getCnt() + ", Value.amt: " + value.getAmount());
				}

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
	public void createTxnSummaryDetailCsvFile(List<TxnSummary> txnSummaryModel, List<ReportModel> txnDetails,
			String filePath) {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			if (!Objects.isNull(txnSummaryModel)) {
				data.add(new String[] { "Summary:" });
				data.add(new String[] { "S.NO", "Txn Type", "Count", "Amount" });

				int i = 0;
				for (TxnSummary txnSummary : txnSummaryModel) {
					i++;
					data.add(new String[] { i + "", txnSummary.getTxnType() + "", txnSummary.getCnt() + "",
							UtilAccess.exponent(txnSummary.getAmount() + "") });
				}
				if (txnDetails != null) {
					data.add(new String[] { " " });
					data.add(new String[] { "Details:" });
				}
			}

			if (txnDetails != null) {
				data.add(new String[] { "S.NO", "Card Number", "Type", "Order ID", "Channel Name", "Customer Name",
						"Scheme", "BIN", "MID", "TID", "Txn. Date", "Batch Number", "Invoice Number", "STAN",
						"POS Entry Mode", "Txn. Type", "RRN", "Txn. Amount", "MDR On Us", "MDR Off Us", "FED",
						"Net Amount", "AUTH ID", "Settled", "Status", "Reason", "Tip Amount", "Adjusted Amount",
						"IMSI" });
				int k = 0;
				for (ReportModel m : txnDetails) {
					k++;
					data.add(new String[] { k + "", m.getCardNumber() + "", m.getCardType() + "", m.getAddress4() + "",
							m.getAddress2() + "", m.getCustomerName() + "", m.getScheme() + "", m.getBin() + "",
							m.getMerchantId() + "", m.getTerminalId(), m.getEntryDate() + " " + m.getEntryTime() + "",
							m.getBatchNumber() + "", m.getInvoiceNumber() + "", m.getAuthId() + "",
							m.getPosEntryMode() + "", m.getType() + "", m.getRefNum() + "", m.getTxnAmount() + "",
							m.getMdrOnUs() + "", m.getMdrOffUs() + "", m.getFedRate() + "", m.getNetAmount() + "",
							m.getAuthIdN() + "", m.getSetteled() + "", m.getStatus() + "",
							((Objects.isNull(m.getReason()) || m.getReason().isEmpty()) ? "N/A" : m.getReason()) + "",
							((Objects.isNull(m.getTipAmount()) || m.getTipAmount().isEmpty()) ? "0.00"
									: m.getTipAmount()) + "",
							((Objects.isNull(m.getAdjAmount()) || m.getAdjAmount().isEmpty()) ? "0.00"
									: m.getAdjAmount()),
							m.getImsi() });
				}

			}
			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			AgLogger.logerror(UtilReport.class, "Exception while creating txn detail csv : ", e);
		}
	}

	@Override
	public void createSettlementTxnDetailCsvFile(List<ReportModel> txnDetails, String filePath) {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();

			data.add(new String[] { "S.NO", "Card Number", "Type", "Order ID", "Channel Name", "Customer Name",
					"Scheme", "PEM", "MID", "TID", "Merchant Name", "Merchant Address", "TR Date/Time", "Curreny",
					"TR Amount", "MDR On Us", "MDR Off Us", "FED", "Net Amount", "AUTH ID", "Batch No.", "Settled On",
					"Payment Date" });
			int k = 0;
			for (ReportModel m : txnDetails) {
				k++;
				data.add(new String[] { k + "", m.getCardNumber() + "", m.getCardType() + "", m.getAddress4() + "",
						m.getAddress2() + "", m.getCustomerName() + "", m.getScheme() + "", m.getPemValue(),
						m.getMerchantId() + "", m.getTerminalId() + "", m.getMerchantName(),
						m.getMerchantAddress() + "", m.getFormattedDate() + " " + m.getEntryTime(), m.getCurrency(),
						m.getTxnAmount(), m.getMdrOnUs(), m.getMdrOffUs(), m.getFedRate(), m.getNetAmount(),
						m.getAuthIdN(), m.getBatchNumber(),
						Objects.isNull(m.getSetteledDate()) ? "" : m.getSetteledDate(),
						Objects.isNull(m.getPaymentDate()) ? "" : m.getPaymentDate() });
			}

			writer.writeAll(data);

			writer.close();
		} catch (IOException e) {
			AgLogger.logerror(UtilReport.class, "Exception while creating settlement csv : ", e);
		}
	}	
	
	@Override
	public void createKEBillPaymentTxnDetailCsvFile(List<ReportModel> txnDetails, String filePath) {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();

			data.add(new String[] { "S.NO", "Card Number", "MID", "TID", "TransactionDate", "Batch #",
					"Bill Amount", "Txn Amount", "Settled", "Consumer Number", "Consumer Name", "Ack", "Billing Month", "RRN", "Txn Time", "Approval Code" });
			int k = 0;
			for (ReportModel m : txnDetails) {
				String type = m.getType().equalsIgnoreCase("0000|Success") ? "Y"
					    : m.getType().equalsIgnoreCase("0006|Something Went Wrong.") ? "N"
					    	    : null;
				
				 LocalDate date = LocalDate.parse(m.getMerchantName().substring(0, 10));
			     String formattedDate = date.format(DateTimeFormatter.ofPattern("MMM-yyyy"));

				k++;
				data.add(new String[] { k + "", m.getCardNumber() + "", m.getMerchantId() + "", m.getTerminalId() + "",
						m.getEntryDate() + "", m.getBatchNumber() + "", m.getTxnAmount() + "",  m.getTxnAmount(),
						m.getSetteled() + "", m.getAddress4() + "", m.getAddress2(),
						type + "", formattedDate+"",m.getRefNum() + "", m.getEntryTime()+"", m.getAuthIdN() + "" });
			}

			writer.writeAll(data);

			writer.close();
		} catch (IOException e) {
			AgLogger.logerror(UtilReport.class, "Exception while creating settlement csv : ", e);
		}
	}

	@Override
	public void createSettlementTxnDetailExcelFile(List<ReportModel> txnDetails, String filePath) {
		File file = new File(filePath);

		try {

			ArrayList<String> merchantsCounts = new ArrayList<String>();

			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");

			CellStyle headerStyle = xssfWorkbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFFont font = xssfWorkbook.createFont();
			font.setBold(true);
			headerStyle.setFont(font);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);

			int rowNum = 0;
			int cellNum = 0;
			int totalCellCounts = 0;

			rowNum = printReportTitle(xssfSheet, rowNum, xssfWorkbook, AppProp.getProperty("settlement.title"));

			rowNum += 5;

			String[] settlementHeaders = new String[] { "Sr #", "Card Number", "Type", "Order ID", "Channel Name",
					"Customer Name", "Scheme", "PEM", "MID", "TID", "Merchant Name", "Merchant Address", "Txn Date",
					"Curreny", "Txn Amount", "MDR On Us", "MDR Off Us", "FED", "Net Amount", "AUTH ID", "Batch No.",
					"Settled On", "Settled Date", "Payment Date" };

			Row row = xssfSheet.createRow(rowNum++);
			cellNum = 0;
			//
			for (String settlementheader : settlementHeaders) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(settlementheader);
				cell.setCellStyle(headerStyle);
				totalCellCounts = cellNum;
			}

			int k = 0;
			for (ReportModel m : txnDetails) {
				k++;
				String[] r = new String[] { k + "", m.getCardNumber() + "", m.getCardType() + "", m.getAddress4() + "",
						m.getAddress2() + "", m.getCustomerName() + "", m.getScheme() + "", m.getPemValue(),
						m.getMerchantId() + "", m.getTerminalId() + "", m.getMerchantName(),
						m.getMerchantAddress() + "", m.getEntryDate() + " " + m.getEntryTime(), m.getCurrency(),
						m.getTxnAmount(), m.getMdrOnUs(), m.getMdrOffUs(), m.getFedRate(), m.getNetAmount(),
						m.getAuthIdN(), m.getBatchNumber(),
						Objects.isNull(m.getSetteledDate()) ? "" : m.getSetteledDate(),
						Objects.isNull(m.getSettledDateWithoutTime()) ? "" : m.getSettledDateWithoutTime(),
						Objects.isNull(m.getPaymentDate()) ? "" : m.getPaymentDate() };
				merchantsCounts.add(m.getMerchantName());
				row = xssfSheet.createRow(rowNum++);
				cellNum = 0;

				for (String txnDetailData : r) {
					Cell cell = row.createCell(cellNum);
					cell.setCellValue(txnDetailData);
					cellNum++;
				}
			}

			xssfSheet.addMergedRegion(new CellRangeAddress(0, 3, 0, totalCellCounts - 1));

			Row subDetailRow = xssfSheet.createRow(5);

			printMerchantName(merchantsCounts, subDetailRow, xssfSheet, xssfWorkbook);
			printOnCellExcelReport(totalCellCounts - 1, subDetailRow, xssfSheet);

			FileOutputStream out = new FileOutputStream(file);
			xssfWorkbook.write(out);
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			AgLogger.logerror(UtilReport.class, "Exception while creating settlement excel : ", e);
		}
	}

	public static int printReportTitle(XSSFSheet xssfSheet, int rowNum, XSSFWorkbook xssfWorkbook, String title) {
		int cellNum = 0;
		Row headingRow = xssfSheet.createRow(rowNum++);
		Cell headingCell = headingRow.createCell(cellNum++);
		headingCell.setCellValue(title);
		CellStyle cellStyle = headingCell.getCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setUnderline(XSSFFont.U_SINGLE);
		font.setFontHeightInPoints((short) 24);
		cellStyle.setFont(font);
		headingCell.setCellStyle(cellStyle);
		return rowNum;
	}

	public static void printMerchantName(ArrayList<String> merchantsCounts, Row subDetailRow, XSSFSheet xssfSheet,
			XSSFWorkbook xssfWorkbook) {
		Set<String> set = new HashSet<String>(merchantsCounts);
		int cellNum = 0;
		if (set.size() == 1) {
			List<String> merchantName = new ArrayList<>(set);
			Cell merchantNamecell = subDetailRow.createCell(cellNum);
			CellStyle merchantNameStyle = xssfWorkbook.createCellStyle();
			XSSFFont font = xssfWorkbook.createFont();
			font.setBold(true);
			merchantNameStyle.setFont(font);
			merchantNamecell.setCellValue("Merchant Name: " + merchantName.get(0));
			merchantNamecell.setCellStyle(merchantNameStyle);
			xssfSheet.addMergedRegion(new CellRangeAddress(5, 5, cellNum, cellNum + 5));
		}
	}

	public static void printOnCellExcelReport(int totalCellCounts, Row subDetailRow, XSSFSheet xssfSheet) {
		int cellNum = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
		cellNum = totalCellCounts - 3;
		Cell printOncell = subDetailRow.createCell(cellNum);
		String date = sdf.format(new Date());
		printOncell.setCellValue("Report Print On: " + date);
		CellStyle printOnStyle = printOncell.getCellStyle();
		printOnStyle.setAlignment(HorizontalAlignment.CENTER);
		xssfSheet.addMergedRegion(new CellRangeAddress(5, 5, cellNum, totalCellCounts));
	}

}
