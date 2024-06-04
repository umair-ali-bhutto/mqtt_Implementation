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

@Component("com.mportal.ws.classes.WsVcTxnLogsReport")
public class WsVcTxnLogsReport implements Wisher {

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

			String currentPage = rm.getAdditionalData().get("currentPage") == null ? null
					: rm.getAdditionalData().get("currentPage").toString();

			String flag = rm.getAdditionalData().get("flag") == null ? null
					: rm.getAdditionalData().get("flag").toString();

			String totalRecords = null;

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

				int rows = Integer.parseInt(AppProp.getProperty("txn.details.rows").toString());

				int pageNumber = Integer.parseInt(currentPage);

				List<TxnLog> txnLogs = txnLogsService.fetchTxnLogDetails(midList, tid, frDate, tDate, posedit, rows,
						pageNumber);

				if (Objects.isNull(txnLogs) || txnLogs.isEmpty()) {
					return UtilAccess.generateResponse("1111", "No Record Found");
				}

				HashMap<Object, Object> obj = new HashMap<Object, Object>();

				if (flag.equals("Y")) {
					totalRecords = txnLogsService.fetchTxnLogDetailsCount(midList, tid, frDate, tDate, posedit);
					obj.put("totalRecords", totalRecords);
				}
				// Y for yes N for no
				obj.put("downloadEnabled", "Y");
				obj.put("rows", rows);

				obj.put("logs", txnLogs);

				return UtilAccess.generateResponse("0000", "SUCCESS", obj);

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

}