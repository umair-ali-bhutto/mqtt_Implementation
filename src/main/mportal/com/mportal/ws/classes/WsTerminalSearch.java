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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.services.TxnLogsService;
import com.opencsv.CSVWriter;

@Component("com.mportal.ws.classes.WsTerminalSearch")
public class WsTerminalSearch implements Wisher {

	@Autowired
	TxnLogsService txnLogsService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String serial = null;
			String tid = null;
			String mid = null;

			String merchantId = rm.getAdditionalData().get("merchantId") == null ? null
					: rm.getAdditionalData().get("merchantId").toString();
			String terminalId = rm.getAdditionalData().get("terminalId") == null ? null
					: rm.getAdditionalData().get("terminalId").toString();
			String serialNumber = rm.getAdditionalData().get("serialNumber") == null ? null
					: rm.getAdditionalData().get("serialNumber").toString();

			if (!Objects.isNull(merchantId) || !Objects.isNull(terminalId) || !Objects.isNull(serialNumber)) {

				if (!Objects.isNull(merchantId) && !merchantId.equals("")) {
					mid = merchantId;
				}

				if (!Objects.isNull(terminalId) && terminalId.length() != 0) {
					tid = terminalId;
				}

				if (!Objects.isNull(serialNumber)) {
					serial = serialNumber;
				}

				List<TxnLog> lst = txnLogsService.fetchMqttTxnLogDetails(mid, tid, serial);

				if (lst.size() == 0) {
					return UtilAccess.generateResponse("1111", "No Record Found");
				}

				String[] blob = createReport(rm, lst);
				HashMap<Object, Object> obj = new HashMap<Object, Object>();

				List<TxnLog> first = new ArrayList<TxnLog>();
				first.add(lst.get(0));

				obj.put("row", first);
				obj.put("lst", lst);

				if (!Objects.isNull(blob)) {
					obj.put("blob", blob[0]);
					obj.put("fileName", blob[1]);
				}

				return UtilAccess.generateResponse("0000", "SUCCESS", obj);

			}

			else {
				return UtilAccess.generateResponse("8888", "Please Select Merchant ID or Terminal ID");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public String[] createReport(RequestModel rm, List<TxnLog> lst) {
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date d = new java.util.Date();

		String rootReportPath = AppProp.getProperty("root.report.path") + rm.getUserid();
		File rootReport = new File(rootReportPath);
		if (!rootReport.exists()) {
			rootReport.mkdirs();
		}

		String reportFormat = "csv";

		String fileName = "Terminal_Search_Report" + sd.format(d) + "." + reportFormat;
		File result = new File(rootReportPath + "/" + fileName);

		try {
			FileWriter outputfile = new FileWriter(result);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			if (lst.size() != 0) {

				data.add(new String[] { "S. #", "MERCHANT_ID", "TERMINAL_ID", "SERIAL_NUMBER", "MODEL", "ACTIVITY_DATE",
						"IMEI", "TELCO", "LONGITUDE", "LATITUDE", "SAF_COUNTER", "MERCHANT_NAME", "ADDRESS",
						"COMMUNICATION_MODE", "SIGNAL_STRENGTH", "CPU_MEMORY", "BATTERY", "OS", "APP_VERSION" });

				int i = 0;
				for (TxnLog log : lst) {
					i++;
					data.add(new String[] { i + "", log.getMerchantId() + "", log.getTerminalId() + "",
							log.getSerialNumber() + "", log.getModel() + "", log.getActivityDate() + "",
							log.getImei() + "", log.getTelco() + "", log.getLongitude() + "", log.getLatitude() + "",
							log.getFieldone() + "", log.getFieldtwo() + "", log.getFieldthree() + "",
							log.getFieldfour() + "", log.getSignalStrength() + "", log.getCpuMemory() + "",
							log.getBattery() + "", log.getOs() + "", log.getAppVersion() + "" });
				}
			}

			writer.writeAll(data);
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