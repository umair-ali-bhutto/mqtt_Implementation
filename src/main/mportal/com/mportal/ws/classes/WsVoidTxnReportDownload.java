package com.mportal.ws.classes;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.UtilReport;
import com.ag.mportal.services.TxnDetailsService;

@Component("com.mportal.ws.classes.WsVoidTxnReportDownload")
public class WsVoidTxnReportDownload implements Wisher {

	@Autowired
	TxnDetailsService txnDetailsService;

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

			String merchantId, fromDate, toDate, terminalId, batchNum, authID = null;
			String channel = rm.getChannel() == null ? null : rm.getChannel();

			if (!rm.getChannel().equalsIgnoreCase("android")) {
				merchantId = Objects.isNull(rm.getAdditionalData().get("merchantId")) ? null
						: rm.getAdditionalData().get("merchantId").toString();
				fromDate = Objects.isNull(rm.getAdditionalData().get("fromdate")) ? null
						: rm.getAdditionalData().get("fromdate").toString();
				toDate = Objects.isNull(rm.getAdditionalData().get("todate")) ? null
						: rm.getAdditionalData().get("todate").toString();
				terminalId = Objects.isNull(rm.getAdditionalData().get("terminalId")) ? null
						: rm.getAdditionalData().get("terminalId").toString();
				batchNum = Objects.isNull(rm.getAdditionalData().get("batchNumber")) ? null
						: rm.getAdditionalData().get("batchNumber").toString();
				authID = Objects.isNull(rm.getAdditionalData().get("authIDs")) ? null
						: rm.getAdditionalData().get("authIDs").toString();

			} else {

				merchantId = Objects.isNull(rm.getAdditionalData().get("merchantId")) ? null
						: rm.getAdditionalData().get("merchantId").toString();
				fromDate = Objects.isNull(rm.getAdditionalData().get("fromdate")) ? null
						: rm.getAdditionalData().get("fromdate").toString();
				toDate = Objects.isNull(rm.getAdditionalData().get("todate")) ? null
						: rm.getAdditionalData().get("todate").toString();
				terminalId = Objects.isNull(rm.getAdditionalData().get("terminalId")) ? null
						: rm.getAdditionalData().get("terminalId").toString();
				batchNum = Objects.isNull(rm.getAdditionalData().get("bacthNum")) ? null
						: rm.getAdditionalData().get("bacthNum").toString();
				authID = Objects.isNull(rm.getAdditionalData().get("authIDs")) ? null
						: rm.getAdditionalData().get("authIDs").toString();

			}

			String merchantName = rm.getAdditionalData().get("merchantName") == null ? null
					: rm.getAdditionalData().get("merchantName").toString();

			if (!Objects.isNull(merchantId) || (!Objects.isNull(fromDate) && !Objects.isNull(toDate))
					|| !Objects.isNull(terminalId)) {

				if (fromDate != null && toDate != null) {
					Date finFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);
					Date finToDate = new SimpleDateFormat("dd-MM-yyyy").parse(toDate);
					frDate = finFromDate;
					tDate = finToDate;
				}

				if (merchantName != null) {
					midList = utilService.fetchMidsByName(merchantName);
					if (midList.size() == 0) {
						midList = new ArrayList<String>();
						midList.add("0");
					}
				} else {
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

				List<ReportModel> lstReportItems = txnDetailsService.fetchVoidTxnDetailsForReport(midList, tid, frDate,
						tDate, sett, btch, ath, AppProp.getProperty("void.txn.report.types"), 0, 0);

				if (!Objects.isNull(lstReportItems)) {
					HashMap<Object, Object> obj = new HashMap<Object, Object>();
					String[] blob = createReport(rm, lstReportItems);
					if (!Objects.isNull(blob)) {

						String tempdownloadSize = AppProp.getProperty("void.txn.download.size");
						String tempFileSize = blob[2];

						double downloadSize = Double.parseDouble(tempdownloadSize);
						double fileSize = Double.parseDouble(tempFileSize);

						if (fileSize <= downloadSize) {
							obj.put("blob", blob[0]);
							obj.put("fileName", blob[1]);
							return UtilAccess.generateResponse("0000", "SUCCESS.", obj);
						} else {
							String initPath = AppProp.getProperty("download.report.path");
							String reportFormat = AppProp.getProperty("void.report.format");

							obj.put("url", initPath + rm.getUserid() + "/" + blob[1] + "&type=" + reportFormat);
							obj.put("displayName", blob[1]);
							return UtilAccess.generateResponse("5555", "Success", obj);
						}

					} else {
						return UtilAccess.generateResponse("0000", "Blob is Null.");
					}
				} else {
					if (!Objects.isNull(channel) && channel.toLowerCase().equals("android")) {
						return UtilAccess.generateResponse("0000", "No Record Found.");
					} else {
						return UtilAccess.generateResponse("8888", "No Record Found.");
					}
				}
			} else {
				return UtilAccess.generateResponse("8888", "Please Select Date or Merchant ID or Terminal ID");
			}
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public String[] createReport(RequestModel rm, List<ReportModel> lstReportItems) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date d = new java.util.Date();

		String rootReportPath = AppProp.getProperty("root.report.path") + rm.getUserid();
		File rootReport = new File(AppProp.getProperty("root.report.path") + rm.getUserid());
		if (!rootReport.exists()) {
			rootReport.mkdirs();
		}
		String reportFormat = AppProp.getProperty("void.report.format");
		if (Objects.isNull(reportFormat) || reportFormat.isEmpty()) {
			reportFormat = "csv";
		} else {
			reportFormat = reportFormat.trim();
		}
		String fileName = "VoidTxnReport" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);

		try {
			if (reportFormat.equals("csv")) {
				UtilReport.createVoidTxnDetailCsvFile(lstReportItems, result.getAbsolutePath());
			} else {
				UtilReport.createVoidTxnDetailExcelFile(lstReportItems, result.getAbsolutePath());
			}
		} catch (ParseException e) {
			AgLogger.logerror(getClass(), "Exception while creating report", e);
		}

		String[] blob = null;
		try {
			blob = new String[3];
			blob[0] = UtilAccess.convertFileToBlob(result.getAbsolutePath());
			blob[1] = fileName;
			blob[2] = String.valueOf(captureFileSize(result));
		} catch (IOException e) {
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