package com.mportal.ws.classes;

import java.io.File;
import java.io.FileWriter;
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

import com.ag.mportal.entity.VwPreauth;
import com.ag.mportal.services.VwPreauthService;
import com.opencsv.CSVWriter;

@Component("com.mportal.ws.classes.WsPreAuthReportDownload")
public class WsPreAuthReportDownload implements Wisher {

	@Autowired
	VwPreauthService preauthService;

	@Autowired
	UtilService utilService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			Date frDate = null, tDate = null;
			List<String> midList = new ArrayList<String>();

			String merchantId = rm.getAdditionalData().get("merchantId") == null ? null
					: rm.getAdditionalData().get("merchantId").toString();
			String fromdate = rm.getAdditionalData().get("fromdate") == null ? null
					: rm.getAdditionalData().get("fromdate").toString();
			String todate = rm.getAdditionalData().get("todate") == null ? null
					: rm.getAdditionalData().get("todate").toString();
			String authID = rm.getAdditionalData().get("authIDs") == null ? null
					: rm.getAdditionalData().get("authIDs").toString();
			String status = rm.getAdditionalData().get("status") == null ? null
					: rm.getAdditionalData().get("status").toString();

			if (!Objects.isNull(merchantId) || (!Objects.isNull(fromdate) && !Objects.isNull(todate))) {

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

				List<VwPreauth> lst = preauthService.fetchByParams(midList, frDate, tDate, authID, status, 0, 0);

				if (lst.size() != 0) {
					HashMap<Object, Object> obj = new HashMap<Object, Object>();
					String[] blob = createReport(lst, rm);
					if (!Objects.isNull(blob)) {
						String tempdownloadSize = AppProp.getProperty("preauth.download.size");
						String tempFileSize = blob[2];

						double downloadSize = Double.parseDouble(tempdownloadSize);
						double fileSize = Double.parseDouble(tempFileSize);

						if (fileSize <= downloadSize) {
							obj.put("blob", blob[0]);
							obj.put("fileName", blob[1]);
							return UtilAccess.generateResponse("0000", "SUCCESS.", obj);
						} else {
							String initPath = AppProp.getProperty("download.report.path");
							String reportFormat = "csv";

							obj.put("url", initPath + rm.getUserid() + "/" + blob[1] + "&type=" + reportFormat);
							obj.put("displayName", blob[1]);
							return UtilAccess.generateResponse("5555", "Success", obj);
						}

					} else {
						return UtilAccess.generateResponse("0000", "Blob is Null.");
					}

				} else {
					response.setCode("0002");
					response.setMessage("No Data Found.");
				}
			} else {
				response.setCode("0001");
				response.setMessage("Please Enter Proper Data.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}
		return response;
	}

	public String[] createReport(List<VwPreauth> lstReportItems, RequestModel rm) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date d = new java.util.Date();

		String rootReportPath = AppProp.getProperty("root.report.path") + rm.getUserid();
		File rootReport = new File(rootReportPath);
		if (!rootReport.exists()) {
			rootReport.mkdirs();
		}

		String reportFormat = "csv";

		String fileName = "Pre_Auth_Completion_Report" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);

		try {
			FileWriter outputfile = new FileWriter(result);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			if (!Objects.isNull(lstReportItems)) {

				data.add(new String[] { "S. #", "Transaction Type", "Card#", "Merchant ID", "Terminal ID",
						"Transaction Date", "Transaction Amount", "Auth ID", "Transaction Status",
						"Pre Auth Expiry Date", "RRN", "Invoice #", "POS Entry Mode", "Settled Date", "IMSI Number",
						"Application Version" });

				int i = 0;
				for (VwPreauth VwPreauth : lstReportItems) {
					i++;
					data.add(new String[] { i + "", VwPreauth.getType() + "", VwPreauth.getFieldOne() + "",
							VwPreauth.getMerchantid() + "", VwPreauth.getTerminalid() + "",
							VwPreauth.getEntrydate() + "", VwPreauth.getTxnamount() + "", VwPreauth.getAuthid() + "",
							VwPreauth.getAuthStatus() + "", VwPreauth.getAuthExp() + "", VwPreauth.getRrn() + "",
							VwPreauth.getInvoiceNum() + "", VwPreauth.getPosEntryMode() + "",
							VwPreauth.getSettledDate() + "", VwPreauth.getImsiNo() + "", VwPreauth.getAppVer() + "", });
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
