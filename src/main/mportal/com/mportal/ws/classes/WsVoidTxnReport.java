package com.mportal.ws.classes;

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
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.util.TxnParserUtil;

@Component("com.mportal.ws.classes.WsVoidTxnReport")
public class WsVoidTxnReport implements Wisher {

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

			String merchantId, fromDate, toDate, terminalId, batchNum, authID, currentPage = null, flag = null,
					totalRecords = null;
			String channel = rm.getChannel() == null ? null : rm.getChannel();

			int rows;
			int pageNumber;

			if (!rm.getChannel().equalsIgnoreCase("android") && !rm.getChannel().equalsIgnoreCase("ios")) {
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
				currentPage = rm.getAdditionalData().get("currentPage") == null ? null
						: rm.getAdditionalData().get("currentPage").toString();

				flag = rm.getAdditionalData().get("flag") == null ? null
						: rm.getAdditionalData().get("flag").toString();

				totalRecords = null;

				rows = Integer.parseInt(AppProp.getProperty("txn.details.rows").toString());

				pageNumber = Integer.parseInt(currentPage);
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
				flag = "N";
				rows = 0;
				pageNumber = 0;

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
						if (channel.equalsIgnoreCase("android") || channel.equalsIgnoreCase("ios")) {
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
//						mId = merchantId;
//						midList = new ArrayList<String>();
//						midList.add(mId);
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
						tDate, sett, btch, ath, AppProp.getProperty("void.txn.report.types"), rows, pageNumber);

				if (!Objects.isNull(lstReportItems) && !lstReportItems.isEmpty()) {

					int i = 0;
					List<ReportModel> tempList = new ArrayList<ReportModel>();
					for (ReportModel model : lstReportItems) {
						i++;
						model.setSerialNumber(i + "");
						tempList.add(TxnParserUtil.parseData(model));
					}

					HashMap<Object, Object> obj = new HashMap<Object, Object>();

					if (flag.equals("Y")) {
						totalRecords = txnDetailsService.fetchTxnDetailsCount(midList, tid, frDate, tDate, sett, btch,
								ath, "VOID");
						obj.put("totalRecords", totalRecords);
					}

					obj.put("details", tempList);
					// Y for yes N for no
					obj.put("downloadEnabled", "Y");

					obj.put("rows", rows);

					return UtilAccess.generateResponse("0000", "SUCCESS", obj);
				} else {
					if (!Objects.isNull(channel)
							&& (channel.toLowerCase().equals("android") || channel.toLowerCase().equals("ios"))) {
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