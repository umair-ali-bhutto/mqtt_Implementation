package com.mportal.ws.classes;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.model.UtilReport;
import com.ag.mportal.services.TxnLogsService;

@Component("com.mportal.ws.classes.WsVcTxnLogsReportDownload")
public class WsVcTxnLogsReportDownload implements Wisher {

	@Autowired
	TxnLogsService txnLogsService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			Date frDate = null, tDate = null;
			String mId = null;
			String tid = null;
			List<String> midList = new ArrayList<String>();
			String[] posedit = null;

			String merchantId = rm.getAdditionalData().get("merchantId") == null ? null
					: rm.getAdditionalData().get("merchantId").toString();
			String fromdate = rm.getAdditionalData().get("fromdate") == null ? null
					: rm.getAdditionalData().get("fromdate").toString();
			String todate = rm.getAdditionalData().get("todate") == null ? null
					: rm.getAdditionalData().get("todate").toString();
			String terminalId = rm.getAdditionalData().get("terminalId") == null ? null
					: rm.getAdditionalData().get("terminalId").toString();
			String dropDownValuePOS = rm.getAdditionalData().get("dropDownValuePOS") == null ? null
					: rm.getAdditionalData().get("dropDownValuePOS").toString();

			if (!Objects.isNull(merchantId) || (!Objects.isNull(fromdate) && !Objects.isNull(todate))
					|| !Objects.isNull(terminalId)) {

				if (fromdate != null && todate != null) {
					Date finFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
					Date finToDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
					frDate = finFromDate;
					tDate = finToDate;
				}

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

				if (!Objects.isNull(terminalId) && terminalId.length() != 0) {
					tid = terminalId;
				}
				if (!Objects.isNull(dropDownValuePOS)) {
					if (dropDownValuePOS.contains("[")) {
						dropDownValuePOS = dropDownValuePOS.replace("[", "");
					}
					if (dropDownValuePOS.contains("]")) {
						dropDownValuePOS = dropDownValuePOS.replace("]", "");
					}
					posedit = dropDownValuePOS.split(", ");
				}

				List<TxnLog> txnLogs = txnLogsService.fetchTxnLogDetails(midList, tid, frDate, tDate, posedit, 0, 0);

				if (Objects.isNull(txnLogs) || txnLogs.isEmpty()) {
					return UtilAccess.generateResponse("1111", "No Record Found");
				}

				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				String[] blob = createReport(rm, txnLogs);
				if (!Objects.isNull(blob)) {

					String tempdownloadSize = AppProp.getProperty("vc.txn.logs.download.size");
					String tempFileSize = blob[2];

					double downloadSize = Double.parseDouble(tempdownloadSize);
					double fileSize = Double.parseDouble(tempFileSize);

					if (fileSize <= downloadSize) {
						obj.put("blob", blob[0]);
						obj.put("fileName", blob[1]);
						return UtilAccess.generateResponse("0000", "SUCCESS.", obj);
					} else {
						String initPath = AppProp.getProperty("download.report.path");
						String reportFormat = AppProp.getProperty("terminal.report.format");

						obj.put("url", initPath + rm.getUserid() + "/" + blob[1] + "&type=" + reportFormat);
						obj.put("displayName", blob[1]);
						return UtilAccess.generateResponse("5555", "Success", obj);
					}

				}

				else {
					return UtilAccess.generateResponse("0000", "Blob is Null.");
				}

			}

			else {
				return UtilAccess.generateResponse("8888", "Please Select Date or Merchant ID or Terminal ID");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public String[] createReport(RequestModel rm, List<TxnLog> lstReportItems) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyy hh:mm:ss");
		Date d = new java.util.Date();

		String rootReportPath = AppProp.getProperty("txncsv.path") + rm.getUserid();
		File rootReport = new File(AppProp.getProperty("txncsv.path") + rm.getUserid());
		if (!rootReport.exists()) {
			rootReport.mkdirs();
		}

		String reportFormat = AppProp.getProperty("terminal.report.format");
		if (Objects.isNull(reportFormat) || reportFormat.isEmpty()) {
			reportFormat = "csv";
		} else {
			reportFormat = reportFormat.trim();
		}

		String fileName = "TerminalActivity" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);

		try {
			if (reportFormat.equals("csv")) {
				UtilReport.createTerminalCsvFile(lstReportItems, result.getAbsolutePath());
			} else {
				UtilReport.createTerminalExcelFile(lstReportItems, result.getAbsolutePath());
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