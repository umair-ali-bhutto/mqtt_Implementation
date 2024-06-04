package com.ag.metro.services.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.model.RequestModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.metro.entity.MetroTxnDetail;
import com.ag.metro.services.MetroTxnDetailService;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.model.UtilReport;
import com.opencsv.CSVWriter;

@Service
public class MetroTxnDetailServiceImpl implements MetroTxnDetailService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<MetroTxnDetail> fetchResultTopup(String cardType, String store, String batchNumber, Date dateFrom,
			Date dateTo) {
		List<MetroTxnDetail> lst = new ArrayList<MetroTxnDetail>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String m = "";
			
			if (dateFrom != null && dateTo != null) {
				m += " AND (" + DBUtil.getDateQueryParam("TXN_DATE") + " between '" + sdf.format(dateFrom) + "' AND '"
						+ sdf.format(dateTo) + "') ";
			}

			if (cardType != null) {
				m += " AND CARD_BIN = '" + cardType + "' ";
			}

			if (store != null) {
				m += " AND MID = '" + store + "' ";
			}

			if (batchNumber != null) {
				m += " AND BATCH_NO = '" + batchNumber + "' ";
			}
			
			String quer = "SELECT * FROM METRO_TXN_DETAILS WHERE ID IS NOT NULL AND TXN_TYPE = 'TOPUP' "+m+" ORDER BY TXN_DATE DESC";
			
			AgLogger.logDebug("", " Query: " + quer);

			Query cb = entityManager.createNativeQuery(quer,MetroTxnDetail.class);
			lst = (List<MetroTxnDetail>) cb.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return lst;
	}


	@Override
	public List<MetroTxnDetail> fetchResultRedemption(String cardType, String store, String txnStatus, Date dateFrom,
			Date dateTo) {
		List<MetroTxnDetail> lst = new ArrayList<MetroTxnDetail>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String m = "";
			if (dateFrom != null && dateTo != null) {
				m += " AND (" + DBUtil.getDateQueryParam("TXN_DATE") + " between '" + sdf.format(dateFrom) + "' AND '"
						+ sdf.format(dateTo) + "') ";
			}
			

			if (cardType != null) {
				m += " AND CARD_BIN = '" + cardType + "' ";
			}

			if (store != null) {
				m += " AND MID = '" + store + "' ";
			}

			if (txnStatus != null) {
				m += " AND (SETTLED ='" + txnStatus + "') ";
			}
			String quer = "SELECT * FROM METRO_TXN_DETAILS WHERE ID IS NOT NULL AND TXN_TYPE = 'REDEEM' "+m+" ORDER BY TXN_DATE DESC";
			AgLogger.logDebug("", " Query: " + quer);

			Query cb = entityManager.createNativeQuery(quer,MetroTxnDetail.class);
			
			lst = (List<MetroTxnDetail>) cb.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	
	public String[] createReportforTopup(List<MetroTxnDetail> lstReportItems,RequestModel rm) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date d = new java.util.Date();

		String rootReportPath = AppProp.getProperty("root.report.path") + rm.getUserid();
		//String rootReportPath = "/home/umair/Desktop/" + rm.getUserid();
		File rootReport = new File(AppProp.getProperty("root.report.path") + rm.getUserid());
		if (!rootReport.exists()) {
			rootReport.mkdirs();
		}

		String reportFormat = "csv";

		String fileName = "MetroTopUpSummary" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);

		try {
			FileWriter outputfile = new FileWriter(result);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			if (!Objects.isNull(lstReportItems)) {
				data.add(new String[] { "S.NO", "Store", "Batch Number", "Card Number", "Customer ID", 
						"Top-up Date", "Top-up Amount", "Top-up Status", "New Expiry", "Top-up By", "Remarks" });

				int i = 0;
				for (MetroTxnDetail txnSummary : lstReportItems) {
					i++;
					data.add(new String[] { i + "", txnSummary.getDbaName() + "", txnSummary.getBatchNo() + "", txnSummary.getCardNo() + "",
							 txnSummary.getDc1() + "", txnSummary.getTxnDate() + "", txnSummary.getTxnAmt() + "", txnSummary.getTopupStatus() + "",
							 txnSummary.getCardExpiry() + "", txnSummary.getTopupBy() + "", txnSummary.getMemberId() + ""});
				}

			}

			writer.writeAll(data);

			// closing writer connection
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception while creating report", e);
		}

		String[] blob = null;
		try {
			blob = new String[2];
			blob[0] = UtilAccess.convertFileToBlob(result.getAbsolutePath());
			blob[1] = fileName;
		} catch (IOException e) {
			e.printStackTrace();
			blob = null;
			AgLogger.logerror(getClass(), "Exception while creating blob", e);
		}
		return blob;
	}
	
	
	
	public String[] createReportforRedemption(List<MetroTxnDetail> lstReportItems,RequestModel rm) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date d = new java.util.Date();

		String rootReportPath = AppProp.getProperty("root.report.path") + rm.getUserid();

		File rootReport = new File(AppProp.getProperty("root.report.path") + rm.getUserid());
		if (!rootReport.exists()) {
			rootReport.mkdirs();
		}

		String reportFormat = "csv";

		String fileName = "MetroRedemptionSummary" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);

		try {
			FileWriter outputfile = new FileWriter(result);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			if (!Objects.isNull(lstReportItems)) {
				data.add(new String[] { "S.NO", "Store", "Card Number", "RRN", 
						"Transaction Date", "Transaction Amount", "Balance", "Transaction Status" });

				int i = 0;
				for (MetroTxnDetail txnSummary : lstReportItems) {
					i++;
					data.add(new String[] { i + "", txnSummary.getDbaName() + "", txnSummary.getCardNo() + "",
							 txnSummary.getDc1() + "", txnSummary.getTxnDate() + "", txnSummary.getTxnAmt() + "",
							 txnSummary.getBalance() + "", txnSummary.getSettled() + ""});
				}

			}

			writer.writeAll(data);

			// closing writer connection
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception while creating report", e);
		}

		String[] blob = null;
		try {
			blob = new String[2];
			blob[0] = UtilAccess.convertFileToBlob(result.getAbsolutePath());
			blob[1] = fileName;
		} catch (IOException e) {
			e.printStackTrace();
			blob = null;
			AgLogger.logerror(getClass(), "Exception while creating blob", e);
		}
		return blob;
	}
	
	

}
