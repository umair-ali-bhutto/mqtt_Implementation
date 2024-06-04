package com.mportal.ws.classes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.model.DonationReportModel;
import com.ag.mportal.model.OfflineDetailsReportModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.services.DonationService;
import com.ag.mportal.services.OfflineSaleService;

@Component("com.mportal.ws.classes.WsDonationTxnDetailsReportDownload")
public class WsDonationTxnDetailsReportDownload implements Wisher {

	@Autowired
	DonationService donationService;

	@Autowired
	UtilService utilService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			Date frDate = null, tDate = null;
			String tid = null;
			String btch = null;
			String ath = null;
			String sett = null;
			List<String> midList = new ArrayList<String>();
			List<String> donorMerchantList = new ArrayList<String>();

			String merchantId = rm.getAdditionalData().get("merchantId") == null ? null
					: rm.getAdditionalData().get("merchantId").toString();
			String donorMerchantId = rm.getAdditionalData().get("donorMerchantId") == null ? null
					: rm.getAdditionalData().get("donorMerchantId").toString();
			String fromdate = rm.getAdditionalData().get("fromdate") == null ? null
					: rm.getAdditionalData().get("fromdate").toString();
			String todate = rm.getAdditionalData().get("todate") == null ? null
					: rm.getAdditionalData().get("todate").toString();
			String terminalId = rm.getAdditionalData().get("terminalId") == null ? null
					: rm.getAdditionalData().get("terminalId").toString();
			String batchNum = rm.getAdditionalData().get("bacthNum") == null ? null
					: rm.getAdditionalData().get("bacthNum").toString();
			String authID = rm.getAdditionalData().get("authIDs") == null ? null
					: rm.getAdditionalData().get("authIDs").toString();
			String channel = rm.getChannel() == null ? null : rm.getChannel().toString();

			if (!Objects.isNull(merchantId) || (!Objects.isNull(fromdate) && !Objects.isNull(todate))
					|| !Objects.isNull(terminalId)) {

				if (fromdate != null && todate != null) {
					Date finFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
					Date finToDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
					frDate = finFromDate;
					tDate = finToDate;
					long diff = finToDate.getTime() - finFromDate.getTime();
					diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					AgLogger.logInfo("txn rpt diffrence ..........." + diff);
					if (diff > Long.parseLong(AppProp.getProperty("rpt.max.days"))) {
						return UtilAccess.generateResponse("8888", "Data can not be fetched more than "
								+ Long.parseLong(AppProp.getProperty("rpt.max.days")) + " days.");
					}
				}

				if (!Objects.isNull(terminalId) && terminalId.length() != 0) {
					tid = terminalId;
				}

				if (!Objects.isNull(batchNum) && batchNum.length() != 0) {
					btch = batchNum;
				}

				if (!Objects.isNull(authID) && authID.length() != 0) {
					ath = authID;
				}

				if (!Objects.isNull(merchantId) && !merchantId.equals("All")) {
					midList = new ArrayList<String>();
					if (channel.equalsIgnoreCase("android")) {
						midList.add(merchantId);
					} else {
						if (merchantId.contains("[")) {
							merchantId = merchantId.replace("[", "");
						}
						if (merchantId.contains("]")) {
							merchantId = merchantId.replace("]", "");
						}
						String[] tempmid = merchantId.split(", ");

						if (tempmid.length <= Integer.parseInt(AppProp.getProperty("view.link.merchant.cnt"))) {
							String a = String.join("','", tempmid);
							midList.add(a);
						} else {
							return UtilAccess.generateResponse("8881", "You Cannot Select More Than "
									+ AppProp.getProperty("view.link.merchant.cnt") + " Merchant IDs");
						}
					}

				}

				if (!Objects.isNull(donorMerchantId)) {
					donorMerchantList = new ArrayList<String>();

					if (donorMerchantId.contains("[")) {
						donorMerchantId = donorMerchantId.replace("[", "");
					}
					if (donorMerchantId.contains("]")) {
						donorMerchantId = donorMerchantId.replace("]", "");
					}
					String[] tempmid = donorMerchantId.split(", ");

					String a = String.join("','", tempmid);
					donorMerchantList.add(a);

				}

				List<DonationReportModel> lstReportItems = donationService.fetchDonationTxnDetailsReport(midList,
						donorMerchantList, tid, frDate, tDate, btch, ath, 0, 0);

				if (!Objects.isNull(lstReportItems) && !lstReportItems.isEmpty()) {
					HashMap<Object, Object> obj = new HashMap<Object, Object>();
					String[] blob = createReport(rm, lstReportItems);
					if (!Objects.isNull(blob)) {

						String tempdownloadSize = AppProp.getProperty("txn.detail.download.size");
						String tempFileSize = blob[2];

						double downloadSize = Double.parseDouble(tempdownloadSize);
						double fileSize = Double.parseDouble(tempFileSize);

						if (fileSize <= downloadSize) {
							obj.put("blob", blob[0]);
							obj.put("fileName", blob[1]);
							return UtilAccess.generateResponse("0000", "SUCCESS.", obj);
						} else {
							String initPath = AppProp.getProperty("download.report.path");
							String reportFormat = AppProp.getProperty("txn.report.format");

							obj.put("url", initPath + rm.getUserid() + "/" + blob[1] + "&type=" + reportFormat);
							obj.put("displayName", blob[1]);
							return UtilAccess.generateResponse("5555", "Success", obj);
						}
					} else {
						return UtilAccess.generateResponse("0000", "Blob is Null.");
					}
				}

			} else {
				return UtilAccess.generateResponse("8888", "Please Select Date or Merchant ID or Terminal ID");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public String[] createReport(RequestModel rm, List<DonationReportModel> lstReportItems) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date d = new java.util.Date();

//		String rootReportPath = "/home/umair/Desktop/" + rm.getUserid();
//		File rootReport = new File("/home/umair/Desktop/" + rm.getUserid());
		String rootReportPath = AppProp.getProperty("root.report.path") + rm.getUserid();
		File rootReport = new File(AppProp.getProperty("root.report.path") + rm.getUserid());
		if (!rootReport.exists()) {
			rootReport.mkdirs();
		}

		String reportFormat = AppProp.getProperty("txn.report.format");
		if (Objects.isNull(reportFormat) || reportFormat.isEmpty()) {
			reportFormat = "csv";
		} else {
			reportFormat = reportFormat.trim();
		}

		String fileName = "TxnDetails" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);

		try {
			if (reportFormat.equals("csv")) {
				donationService.createDonationDetailCsvFile(lstReportItems, result.getAbsolutePath());
			} else {
				donationService.createDonationDetailCsvFile(lstReportItems, result.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception while creating report", e);
		}

		String[] blob = null;
		try {
			blob = new String[3];
			blob[0] = UtilAccess.convertFileToBlob(result.getAbsolutePath());
			blob[1] = fileName;
			blob[2] = String.valueOf(captureFileSize(result));
		} catch (IOException e) {
			e.printStackTrace();
			blob = null;
			AgLogger.logerror(getClass(), "Exception while creating blob", e);
		}
		return blob;
	}

	public double captureFileSize(File file) {
		long fileSize = file.length();
		double fileSizeMB = (double) fileSize / (1024 * 1024);
		System.out.println("File size: " + fileSizeMB + " MB");
		return fileSizeMB;
	}

}