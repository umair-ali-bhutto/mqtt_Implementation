package com.ag.mportal.services.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.BatchSettlement;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.Paymentconfiguration;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.model.DonationReportModel;
import com.ag.mportal.model.OfflineDetailsReportModel;
import com.ag.mportal.model.OfflineSaleTxnBatchModel;
import com.ag.mportal.model.OfflineSaleTxnBatchModel.AdditionalData;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.model.UtilReport;
import com.ag.mportal.services.BatchSettlementService;
import com.ag.mportal.services.DonationService;
import com.ag.mportal.services.OfflineSaleService;
import com.ag.mportal.services.PaymentConfigurationService;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.services.TxnLogsService;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;

@Service
public class DonationServiceImpl implements DonationService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtilAccess utilAccess;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	TxnLogsService txnLogsService;

	@Autowired
	TxnDetailsService txnDetailsService;

	@Autowired
	BatchSettlementService batchSettlementService;

	@Autowired
	PaymentConfigurationService paymentConfigurationService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	UtilService utilService;

	@Override
	public List<DonationReportModel> fetchDonationTxnDetailsReport(List<String> merchant, List<String> donorMerchant,
			String terminal, Date from, Date to, String batch, String auth, int numberOfRows, int pageNumber) {
		List<DonationReportModel> lstReportItems = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("ENTRYDATE") + " between '" + sdf.format(from)
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

				whereClause += " AND MERCHANTID IN (" + jk + ")";
			}

			if (terminal != null) {
				whereClause += "  AND TERMINALID = '" + terminal + "' ";
			}

			if (batch != null) {
				whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
			}

			if (auth != null) {
				whereClause += "  AND AUTHID = '" + auth + "' ";
			}

			if (donorMerchant.size() != 0) {
				String channels = "";
				for (String channel : donorMerchant) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				whereClause += " AND DONATION_MID IN (" + channels + ")";
			}
//			whereClause += " AND TYPE IN ( " + AppProp.getProperty("offline.txn.summary.types") + " )";

			String quer = DBUtil.getDonationTxnDetailsQuery();
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
				lstReportItems = UtilAccess.donationReportList(lst);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lstReportItems;
	}

	@Override
	public String fetchDonationTxnDetailsReportCount(List<String> merchant, List<String> donorMerchant, String terminal,
			Date from, Date to, String batch, String auth) {
		String count = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String whereClause = new String();

			if (from != null && to != null) {
				whereClause += " AND (" + DBUtil.getDateQueryParam("ENTRYDATE") + " between '" + sdf.format(from)
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

				whereClause += " AND MERCHANTID IN (" + jk + ")";
			}

			if (terminal != null) {
				whereClause += "  AND TERMINALID = '" + terminal + "' ";
			}

			if (batch != null) {
				whereClause += "  AND BATCH_NUMBER = '" + batch + "' ";
			}

			if (auth != null) {
				whereClause += "  AND AUTHID = '" + auth + "' ";
			}

			if (donorMerchant.size() != 0) {
				String channels = "";
				for (String channel : donorMerchant) {
					channels += "'" + channel + "',";
				}

				if (channels.length() != 0) {
					channels = channels.substring(0, channels.length() - 1);
				}

				whereClause += " AND DONATION_MID IN (" + channels + ")";
			}

			String quer = DBUtil.getDonationTxnDetailsCountQuery();
			quer = quer.replace("@whereClause", whereClause);

			AgLogger.logDebug(TxnDetailsServiceImpl.class, quer);

			Query cb = entityManager.createNativeQuery(quer);
			int result = (int) cb.getSingleResult();

			count = String.valueOf(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void createDonationDetailCsvFile(List<DonationReportModel> txnDetails, String filePath) {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();

			data.add(new String[] { "Details:" });

			if (txnDetails != null) {

				data.add(new String[] { "S.NO", "MID", "TID", "Txn. Date", "Invoice Number", "STAN", "POS Entry Mode",
						"Txn. Type", "RRN", "Txn. Amount", "MDR On Us", "MDR Off Us", "FED", "Net Amount", "Settled",
						"Tip Amount", "Adjusted Amount", "IMSI", "Donation MID", "Donation TID",
						"Donation Account Title", "Donation Account" });
				int k = 0;
				for (DonationReportModel m : txnDetails) {
					k++;
					data.add(new String[] { k + "", m.getMerchantid() + "", m.getTerminalid() + "",
							m.getEntrydate() + "", m.getInvoiceNum() + "", m.getAuthid() + "", m.getPosEntryMode() + "",
							m.getType() + "", m.getRrn() + "", m.getTxnamount() + "", m.getMdrOnus() + "",
							m.getMdrOffus() + "", m.getFed() + "", m.getNetAmount() + "", m.getSettledDate() + "",
							m.getTimAmount() + "", m.getAdjAmount() + "", m.getImsiNo() + "", m.getDonationMid() + "",
							m.getDonationTid() + "", m.getDonationAccountTitle() + "", m.getDonationAccount() + "" });
				}

//				data.add(new String[] { "S.NO", "Type", "Card NU", "Merchantid  ", "Terminalid  ",
//						"Entrydate", "Txnamount  ", "Authid  ", "Customername  ", "Scheme", "Model", "Card_type", "Rrn",
//						"Mdr_onus", "Settled_date", "Mdr_offus", "Adj_amount", "Invoice_num", "Fed", "Tim_amount",
//						"Pos_entry_mode", "Net_amount", "Tvr", "Aid", "Donation MID", "Donation TID",
//						"Donation Account Title", "Donation Account" });
//				int k = 0;
//				for (DonationReportModel m : txnDetails) {
//					k++;
//					data.add(new String[] { k + "", m.getTxndetailid() + "", m.getType() + "", m.getFieldOne() + "",
//							m.getMerchantid() + "", m.getTerminalid() + "", m.getEntrydate() + "",
//							m.getTxnamount() + "", m.getAuthid() + "", m.getCustomername() + "", m.getScheme() + "",
//							m.getModel() + "", m.getCardType() + "", m.getRrn() + "", m.getMdrOnus() + "",
//							m.getSettledDate() + "", m.getMdrOffus() + "", m.getAdjAmount() + "",
//							m.getInvoiceNum() + "", m.getFed() + "", m.getTimAmount() + "", m.getPosEntryMode() + "",
//							m.getNetAmount() + "", m.getTvr() + "", m.getAid() + "", m.getDonationMid() + "",
//							m.getDonationTid() + "", m.getDonationAccountTitle() + "", m.getDonationAccount() + "" });
//				}

			}
			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			AgLogger.logerror(UtilReport.class, "Exception while creating txn detail csv : ", e);
		}
	}

}
