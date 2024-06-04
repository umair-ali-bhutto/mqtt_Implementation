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

import com.ag.generic.model.KeyValueModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.model.DonationReportModel;
import com.ag.mportal.model.OfflineDetailsReportModel;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.DonationService;
import com.ag.mportal.services.OfflineSaleService;
import com.ag.mportal.util.TxnParserUtil;

@Component("com.mportal.ws.classes.WsDonationTxnDetailsReport")
public class WsDonationTxnDetailsReport implements Wisher {


	@Autowired
	DonationService donationService;

	@Autowired
	UtilService utilService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());

		ResponseModel response = new ResponseModel();

		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {
			case "INIT":
				response = initRequest(rm);
				break;
			case "FETCH":
				response = fetchRequest(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	ResponseModel initRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
//			List<KeyValueModel> flightsList = new ArrayList<KeyValueModel>();
//
//			String flights = AppProp.getProperty("web.ecr.report.channel");
//
//			if (flights.contains(",")) {
//				String[] value = flights.split(",");
//				for (String flight : value) {
//					KeyValueModel mdl = new KeyValueModel();
//					mdl.setKey(flight);
//					mdl.setValue(flight);
//					flightsList.add(mdl);
//				}
//			} else {
//				KeyValueModel mdl = new KeyValueModel();
//				mdl.setKey(flights);
//				mdl.setValue(flights);
//				flightsList.add(mdl);
//			}
//			HashMap<Object, Object> obj = new HashMap<Object, Object>();
//			obj.put("flightsList", flightsList);

			response.setCode("0000");
			response.setMessage("Success");
			// response.setData(obj);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9991");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel fetchRequest(RequestModel rm) {
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

				int rows = Integer.parseInt(AppProp.getProperty("donation.report.rows").toString());

				int pageNumber = Integer.parseInt(currentPage);

				List<DonationReportModel> lstReportItems = donationService.fetchDonationTxnDetailsReport(midList,
						donorMerchantList, tid, frDate, tDate, btch, ath, rows, pageNumber);
				if (!Objects.isNull(lstReportItems) && !lstReportItems.isEmpty()) {

					
					List<DonationReportModel> tempList = new ArrayList<DonationReportModel>();
					for (DonationReportModel model : lstReportItems) {
						
						tempList.add(TxnParserUtil.parseDonationData(model));
					}

					HashMap<Object, Object> obj = new HashMap<Object, Object>();

					if (flag.equals("Y")) {
						totalRecords = donationService.fetchDonationTxnDetailsReportCount(midList,
								donorMerchantList, tid, frDate, tDate, btch, ath);
						obj.put("totalRecords", totalRecords);
					}

					obj.put("details", tempList);
					// Y for yes N for no
					obj.put("downloadEnabled", "Y");

					obj.put("rows", rows);

					return UtilAccess.generateResponse("0000", "SUCCESS", obj);
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