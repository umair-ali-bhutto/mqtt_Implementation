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
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.EcrLogsService;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.util.TxnParserUtil;

@Component("com.mportal.ws.classes.WsWebEcrTxnReport")
public class WsWebEcrTxnReport implements Wisher {

	@Autowired
	EcrLogsService ecrLogsService;

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
			List<KeyValueModel> channelsList = new ArrayList<KeyValueModel>();

			String channels = AppProp.getProperty("web.ecr.report.channel");

			if (channels.contains(",")) {
				String[] value = channels.split(",");
				for (String channel : value) {
					KeyValueModel mdl = new KeyValueModel();
					mdl.setKey(channel);
					mdl.setValue(channel);
					channelsList.add(mdl);
				}
			} else {
				KeyValueModel mdl = new KeyValueModel();
				mdl.setKey(channels);
				mdl.setValue(channels);
				channelsList.add(mdl);
			}
			HashMap<Object, Object> obj = new HashMap<Object, Object>();
			obj.put("channelsList", channelsList);

			response.setCode("0000");
			response.setMessage("Success");
			response.setData(obj);
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
			List<String> channelList = new ArrayList<String>();

			String merchantId = null, channels = null, settled = null, merchantName = null, fromDate = null,
					toDate = null, terminalId = null, batchNum = null, authID = null, currentPage = null, flag = null,
					totalRecords = null;

			String channel = rm.getChannel() == null ? null : rm.getChannel().toString();

			int rows;
			int pageNumber;

			if (!rm.getChannel().equalsIgnoreCase("android") && !rm.getChannel().equalsIgnoreCase("ios")) {
				System.out.println("IF");
				merchantId = rm.getAdditionalData().get("merchantId") == null ? null
						: rm.getAdditionalData().get("merchantId").toString();
				channels = rm.getAdditionalData().get("channel") == null ? null
						: rm.getAdditionalData().get("channel").toString();
				fromDate = rm.getAdditionalData().get("fromdate") == null ? null
						: rm.getAdditionalData().get("fromdate").toString();
				toDate = rm.getAdditionalData().get("todate") == null ? null
						: rm.getAdditionalData().get("todate").toString();
				terminalId = rm.getAdditionalData().get("terminalId") == null ? null
						: rm.getAdditionalData().get("terminalId").toString();
				batchNum = rm.getAdditionalData().get("bacthNum") == null ? null
						: rm.getAdditionalData().get("bacthNum").toString();
				authID = rm.getAdditionalData().get("authIDs") == null ? null
						: rm.getAdditionalData().get("authIDs").toString();
				settled = rm.getAdditionalData().get("settled") == null ? null
						: rm.getAdditionalData().get("settled").toString();

				merchantName = rm.getAdditionalData().get("merchantName") == null ? null
						: rm.getAdditionalData().get("merchantName").toString();

				currentPage = rm.getAdditionalData().get("currentPage") == null ? null
						: rm.getAdditionalData().get("currentPage").toString();

				flag = rm.getAdditionalData().get("flag") == null ? null
						: rm.getAdditionalData().get("flag").toString();

				totalRecords = null;

				rows = Integer.parseInt(AppProp.getProperty("web.ecr.report.rows").toString());
				pageNumber = Integer.parseInt(currentPage);
				
				System.out.println(fromDate);
				System.out.println(toDate);
				System.out.println(terminalId);
				System.out.println(merchantId);
			} else {
				flag = "N";
				rows = 0;
				pageNumber = 0;
			}

//			if (channel.equalsIgnoreCase("android")) {
//				if (settled.equalsIgnoreCase("yes")) {
//					settled = "Y";
//				} else if (settled.equalsIgnoreCase("no")) {
//					settled = "N";
//				} else {
//					settled = "All";
//				}
//			}

//			if (!Objects.isNull(merchantId) || (!Objects.isNull(fromDate) && !Objects.isNull(toDate))
//					|| !Objects.isNull(terminalId)) {

//			if (!rm.getChannel().equalsIgnoreCase("android") && !rm.getChannel().equalsIgnoreCase("ios")) {
//				if (!Objects.isNull(merchantId) || (!Objects.isNull(fromDate) && !Objects.isNull(toDate))
//						|| !Objects.isNull(terminalId)) {
//					return UtilAccess.generateResponse("8888", "Please Select Date or Merchant ID or Terminal ID");
//				}
//			}

			if (fromDate != null && toDate != null) {
				Date finFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);
				Date finToDate = new SimpleDateFormat("dd-MM-yyyy").parse(toDate);
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

			List<ReportModel> lstReportItems = ecrLogsService.fetchWebEcrTxnReport(midList, channelList, tid, frDate,
					tDate, sett, btch, ath, rows, pageNumber);
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
					totalRecords = ecrLogsService.fetchWebEcrDetailsCount(midList, channelList, tid, frDate, tDate,
							sett, btch, ath, "DETAILS");
					obj.put("totalRecords", totalRecords);
				}

				obj.put("details", tempList);
				// Y for yes N for no
				if (!rm.getChannel().equalsIgnoreCase("android") && !rm.getChannel().equalsIgnoreCase("ios")) {
					obj.put("downloadEnabled", "Y");

					obj.put("rows", rows);
				}

				return UtilAccess.generateResponse("0000", "SUCCESS", obj);
			} else {
				if (!Objects.isNull(channel) && channel.toLowerCase().equals("android")) {
					return UtilAccess.generateResponse("0000", "No Record Found.");
				} else {
					return UtilAccess.generateResponse("8888", "No Record Found.");
				}

			}
//			} else {
//				return UtilAccess.generateResponse("8888", "Please Select Date or Merchant ID or Terminal ID");
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}