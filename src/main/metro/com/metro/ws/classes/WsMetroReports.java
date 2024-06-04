package com.metro.ws.classes;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.JasperUtil;

@Component("com.metro.ws.classes.WsMetroReports")
public class WsMetroReports implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String reportType = Objects.isNull(rm.getAdditionalData().get("reportType")) ? null
					: rm.getAdditionalData().get("reportType").toString();

			String cardType = Objects.isNull(rm.getAdditionalData().get("cardType")) ? null
					: rm.getAdditionalData().get("cardType").toString();

			String reportName = Objects.isNull(rm.getAdditionalData().get("reportName")) ? null
					: rm.getAdditionalData().get("reportName").toString();

			String storeName = Objects.isNull(rm.getAdditionalData().get("storeName")) ? null
					: rm.getAdditionalData().get("storeName").toString();

			String dateFrom = Objects.isNull(rm.getAdditionalData().get("dateFrom")) ? null
					: rm.getAdditionalData().get("dateFrom").toString();

			String dateTo = Objects.isNull(rm.getAdditionalData().get("dateTo")) ? null
					: rm.getAdditionalData().get("dateTo").toString();

			String batchNumber = Objects.isNull(rm.getAdditionalData().get("batchNumber")) ? null
					: rm.getAdditionalData().get("batchNumber").toString();

			if (reportName != null && reportType != null && storeName != null && cardType != null && dateFrom != null
					&& dateTo != null && batchNumber != null) {

				File fg = new File(reportName);

				if (fg.exists()) {
					String[] srpt = generateReport(reportName, cardType, batchNumber, reportName, reportType, storeName,
							dateFrom, dateTo);
					if (srpt[0].equals("0000")) {
						response.setCode("0000");
						response.setMessage(srpt[1]);
						HashMap<Object, Object> ojh = new HashMap<Object, Object>();
						ojh.put("reportFileName", srpt[2]);
						ojh.put("reportFile", srpt[3]);
						response.setData(ojh);
					} else {
						response.setCode("0002");
						response.setMessage(srpt[1]);
					}
				} else {
					response.setCode("0004");
					response.setMessage("Report XML File Not Found.");
				}

			} else {
				response.setCode("0001");
				response.setMessage("Please Enter Data.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();

			response.setCode("9999");
			response.setMessage("SERVICE ERROR");
		}

		return response;

	}

	private String[] generateReport(String jxmlPath, String cardType, String batchNumber, String reportName,
			String reportType, String StoreName, String fromDate, String toDate) {
		String[] s = new String[4];
		s[0] = "0001";
		s[1] = "N/A";
		s[2] = "-";
		s[3] = "-";
		try {
			String[] k = JasperUtil.generateMetroReports(jxmlPath, cardType, batchNumber, reportName, reportType,
					StoreName, fromDate, toDate);

			if (k[0].equals("0000")) {
				s[0] = "0000";
				s[1] = "SUCCESS";
				s[2] = k[1];
				s[3] = k[2];
			} else {
				s[0] = "0003";
				s[1] = k[1];
				s[2] = "-";
				s[3] = "-";
			}

		} catch (Exception e) {
			e.printStackTrace();
			s[0] = "0001";
			s[1] = "N/A";
			s[2] = "-";
			s[3] = "-";
		}

		return s;
	}

}
