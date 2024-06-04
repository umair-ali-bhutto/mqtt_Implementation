package com.mportal.ws.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.ag.mportal.model.DMSReportReportModel;
import com.ag.mportal.services.DiscountMasterService;

@Component("com.mportal.ws.classes.WsDiscountReport")
public class WsDiscountReport implements Wisher {

	@Autowired
	DiscountMasterService discountMasterService;

	@Autowired
	UtilService utilService;

	@Override
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
			case "PROCESS":
				response = doProcessReq(rm);
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

			String corpid = rm.getCorpId();

			List<KeyValueModel> lstDiscName = discountMasterService.fetchLstDiscountNameByCorpId(corpid);

			HashMap<Object, Object> o = new HashMap<Object, Object>();
			o.put("discName", lstDiscName);
			response.setData(o);
			response.setCode("0000");
			response.setMessage("Success.");

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	public ResponseModel doProcessReq(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			Date frDate = null, tDate = null;
			String disName = null;
			String discStatus = null;
			List<String> midList = new ArrayList<String>();
			List<String> discNameList = null;

			String merchantId = rm.getAdditionalData().get("merchantId") == null ? null
					: rm.getAdditionalData().get("merchantId").toString();
			String discNameId = rm.getAdditionalData().get("discountName") == null ? null
					: rm.getAdditionalData().get("discountName").toString();
			String fromdate = rm.getAdditionalData().get("fromdate") == null ? null
					: rm.getAdditionalData().get("fromdate").toString();
			String todate = rm.getAdditionalData().get("todate") == null ? null
					: rm.getAdditionalData().get("todate").toString();
			String disStatus = rm.getAdditionalData().get("disStatus") == null ? null
					: rm.getAdditionalData().get("disStatus").toString();
			String channel = rm.getChannel() == null ? null : rm.getChannel().toString();
			String merchantName = rm.getAdditionalData().get("merchantName") == null ? null
					: rm.getAdditionalData().get("merchantName").toString();

			String currentPage = rm.getAdditionalData().get("currentPage") == null ? null
					: rm.getAdditionalData().get("currentPage").toString();

			String flag = rm.getAdditionalData().get("flag") == null ? null
					: rm.getAdditionalData().get("flag").toString();

			String totalRecords = null;

//			if (channel.equalsIgnoreCase("android")) {
			if (disStatus.equalsIgnoreCase("Active")) {
				disStatus = "1";
			} else if (disStatus.equalsIgnoreCase("InActive")) {
				disStatus = "0";
			} else {
				disStatus = "All";
			}
//			}

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
				if (!Objects.isNull(disStatus) && !disStatus.equals("All")) {
					discStatus = disStatus;
				}

				if (!Objects.isNull(discNameId) && discNameId.length() != 0) {
					disName = discNameId;
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

				if (disName != null) {
					if (disName.contains("[")) {
						disName = disName.replace("[", "");
					}
					if (disName.contains("]")) {
						disName = disName.replace("]", "");
					}
					discNameList = new ArrayList<String>(Arrays.asList(disName.split(",")));
				}

				// int rows =
				// Integer.parseInt(AppProp.getProperty("discount.txn.details.rows").toString());

				int rows = 10;

				int pageNumber = Integer.parseInt(currentPage);

				List<DMSReportReportModel> lstReportItems = discountMasterService.fetchDiscountTxnDetailsReport(midList,
						frDate, tDate, discStatus, discNameList, rows, pageNumber);
				if (!Objects.isNull(lstReportItems) && !lstReportItems.isEmpty()) {
//					List<DMSReportReportModel> tempList = new ArrayList<DMSReportReportModel>();
//					for (DMSReportReportModel model : lstReportItems) {
//						tempList.add(TxnParserUtil.parseDiscountData(model));
//					}

					HashMap<Object, Object> obj = new HashMap<Object, Object>();

//					if (flag.equals("Y")) {
//						totalRecords = discountMasterService.fetchDiscountTxnDetailsCount(midList, tid, frDate, tDate, sett, btch,
//								ath, "DETAILS");
//						obj.put("totalRecords", totalRecords);
//					}

					obj.put("details", lstReportItems);
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
				return UtilAccess.generateResponse("8888", "Please Select Date or Merchant ID");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}