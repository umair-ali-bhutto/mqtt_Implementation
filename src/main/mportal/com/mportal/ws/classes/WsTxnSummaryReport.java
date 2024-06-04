package com.mportal.ws.classes;

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
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.services.TxnDetailsService;

@Component("com.mportal.ws.classes.WsTxnSummaryReport")
public class WsTxnSummaryReport implements Wisher {

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

			String merchantId = rm.getAdditionalData().get("merchantId") == null ? null
					: rm.getAdditionalData().get("merchantId").toString();
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

						// mId = merchantId;
						// midList = new ArrayList<String>();

						// midList.add(mId);
					}
				}

				List<TxnSummary> trxnModel = txnDetailsService.fetchTxnSummary(midList, tid, frDate, tDate, sett, btch,
						ath);

//					TxnSummaryModel txnSummary = UtilAccess.getTxnSummaryModel(trxnModel);

				if (trxnModel != null) {

//						if (!Objects.isNull(txnSummary)) {

					HashMap<Object, Object> obj = new HashMap<Object, Object>();
//							obj.put("summary", txnSummary);
					obj.put("summaryAmended", trxnModel);
					// Y for yes N for no
					obj.put("downloadEnabled", "Y");
					return UtilAccess.generateResponse("0000", "SUCCESS", obj);
//						} else {
//							if (!Objects.isNull(channel) && channel.toLowerCase().equals("android")) {
//								return UtilAccess.generateResponse("0000", "No Record Found.");
//							} else {
//								return UtilAccess.generateResponse("8888", "No Record Found.");
//							}
					//
//						}
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

}