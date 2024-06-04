package com.ag.loy.adm.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.model.RequestModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.loy.adm.entity.VwTxnDetails;
import com.ag.loy.adm.service.VwTxnDetailsService;
import com.opencsv.CSVWriter;

@Service
public class VwTxnDetailsServiceImpl implements VwTxnDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<VwTxnDetails> fetchAll() {
		List<VwTxnDetails> lst = new ArrayList<VwTxnDetails>();
		try {
			Query cb = entityManager.createNamedQuery("VwTxnDetails.fetchAll");
			lst = (List<VwTxnDetails>) cb.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	@Override
	public List<VwTxnDetails> fetchByParams(String userId, String devBatch, String accountId, String fromDate,
			String toDate, String customerId, String corporateId, String invoice, String cardNumber, String rrn,
			String terminalId) {

		try {
			String queryString = "select * from VW_TXN_DETAILS @whereClause";

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
				whereClause += DBUtil.getDateQueryParam("TXN_DATE") + " BETWEEN '" + UtilAccess.dateFormatter(fromDate)
						+ "' and '" + UtilAccess.dateFormatter(toDate) + "' ";
			}

			if (!Objects.isNull(invoice)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " SOURCE_REF ='" + invoice + "' ";
			}
			if (!Objects.isNull(cardNumber)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " USERID ='" + cardNumber + "' ";
			}
			if (!Objects.isNull(rrn)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " RRN ='" + rrn + "' ";
			}
			if (!Objects.isNull(terminalId)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " DEV_TID ='" + terminalId + "' ";
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
			AgLogger.logInfo(VwTxnDetailsServiceImpl.class, queryString);

			Query query = entityManager.createNativeQuery(queryString, VwTxnDetails.class);
			List<VwTxnDetails> lst = query.getResultList();
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] downloadReport(List<VwTxnDetails> lst, RequestModel rm) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date d = new java.util.Date();

		String rootReportPath = AppProp.getProperty("root.report.path") + rm.getUserid();
		File rootReport = new File(AppProp.getProperty("root.report.path") + rm.getUserid());
		if (!rootReport.exists()) {
			rootReport.mkdirs();
		}
		String reportFormat = "csv";

		String fileName = "ViewTransactionDetails" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);
		try {
			FileWriter outputfile = new FileWriter(result);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			if (!Objects.isNull(lst)) {
				data.add(new String[] { "S.NO", "Corporate", "Account ID", "Card #", "Name", "Customer Type",
						"Merchant ID", "Merchant Name", "Terminal ID", "RRN", "Transaction Date", "Transaction Type",
						"Debit / Credit", "Product", "Transaction Channel", "Transaction Amount", "Point", "Balance",
						"Transaction Status", "Settled", "Settled Date", });

				int i = 0;

				for (VwTxnDetails txnDetails : lst) {
					i++;
					data.add(new String[] { i + "", txnDetails.getCorpName() + "", txnDetails.getAccId() + "",
							txnDetails.getUserid() + "", txnDetails.getUserdispname() + "",
							txnDetails.getCusttypedesc() + "", txnDetails.getDevMid() + "",
							txnDetails.getMerName() + "", txnDetails.getDevTid() + "", txnDetails.getRrn() + "",
							txnDetails.getTxnDate() + "", txnDetails.getTxnType() + "", txnDetails.getDrcrFlag() + "",
							txnDetails.getpName() + "", txnDetails.getChid() + "", txnDetails.getDevAmount() + "",
							txnDetails.getTxnAmount() + "", txnDetails.getOtherbalance() + "",
							txnDetails.getTxnStatus() + "", txnDetails.getSettled() + "",
							txnDetails.getSettledDate() + "",

					});
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
