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
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAG;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.model.TxnSummaryModel;
import com.ag.mportal.model.UtilReport;
import com.ag.mportal.services.EcrLogsService;
import com.ag.mportal.services.TxnDetailsService;

@Component("com.mportal.ws.classes.WsWebEcrTxnSummaryReportDownload")
public class WsWebEcrTxnSummaryReportDownload implements Wisher {

	@Autowired
	EcrLogsService ecrLogsService;

	@Autowired
	UtilService utilService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			Date frDate = null, tDate = null;
			String mId = null;
			String tid = null;
			String btch = null;
			String ath = null;
			String sett = null;
			List<String> midList = new ArrayList<String>();
			List<String> channelList = new ArrayList<String>();

			String merchantId = rm.getAdditionalData().get("merchantId") == null ? null
					: rm.getAdditionalData().get("merchantId").toString();
			String channels = rm.getAdditionalData().get("channel") == null ? null
					: rm.getAdditionalData().get("channel").toString();
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
			String settled = rm.getAdditionalData().get("settled") == null ? null
					: rm.getAdditionalData().get("settled").toString();
			String channel = rm.getChannel() == null ? null : rm.getChannel().toString();
			String merchantName = rm.getAdditionalData().get("merchantName") == null ? null
					: rm.getAdditionalData().get("merchantName").toString();

			if (channel.equalsIgnoreCase("android")) {
				if (settled.equalsIgnoreCase("yes")) {
					settled = "Y";
				} else if (settled.equalsIgnoreCase("no")) {
					settled = "N";
				} else {
					settled = "All";
				}
			}

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
				if (!Objects.isNull(settled) && !settled.equals("All")) {
					sett = settled;
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

				if (merchantName != null) {
					midList = utilService.fetchMidsByName(merchantName);
					if (midList.size() == 0) {
						midList = new ArrayList<String>();
						midList.add("0");
					}
				} else {
					if (!Objects.isNull(merchantId) && !merchantId.equals("All")) {

						midList = new ArrayList<String>();

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
				if (!Objects.isNull(channels) && channels.length() != 0) {
					channelList = new ArrayList<String>();
					if (channel.equalsIgnoreCase("android")) {
						channelList.add(channels);
					} else {
						if (channels.contains("[")) {
							channels = channels.replace("[", "");
						}
						if (channels.contains("]")) {
							channels = channels.replace("]", "");
						}
						String[] tempchannel = channels.split(", ");

						String finalChannel = String.join("','", tempchannel);
						channelList.add(finalChannel);
						System.out.println(finalChannel);

					}
				}

				List<TxnSummary> trxnModel = ecrLogsService.fetchWebEcrTxnSummary(midList, channelList, tid, frDate,
						tDate, sett, btch, ath);

				if (trxnModel != null) {

					HashMap<Object, Object> obj = new HashMap<Object, Object>();

					String[] blob = createReport(rm, null, trxnModel);
					if (!Objects.isNull(blob)) {

						String tempdownloadSize = AppProp.getProperty("txn.summary.download.size");
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
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public String[] createReport(RequestModel rm, List<ReportModel> lstReportItems, List<TxnSummary> trxnModel) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date d = new java.util.Date();

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

		String fileName = "TxnSummary" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);

		try {
			if (reportFormat.equals("csv")) {
				ecrLogsService.createTxnSummaryDetailCsvFile(trxnModel, lstReportItems, result.getAbsolutePath());
			} else {
				ecrLogsService.createTxnSummaryDetailCsvFile(trxnModel, lstReportItems, result.getAbsolutePath());
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