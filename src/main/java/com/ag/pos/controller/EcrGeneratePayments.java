package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.JwtTokenUtil;
import com.ag.mportal.entity.ConfigEcrRouting;
import com.ag.mportal.entity.EcrLog;
import com.ag.mportal.model.EcrModel;
import com.ag.mportal.services.impl.ConfigEcrRoutingServiceImpl;
import com.ag.mportal.services.impl.EcrLogsServiceImpl;
import com.google.gson.Gson;

@RestController
public class EcrGeneratePayments {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	ConfigEcrRoutingServiceImpl configEcrRoutingServiceImpl;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	EcrLogsServiceImpl ecrLogsServiceImpl;

	@SuppressWarnings("unchecked")
	@PostMapping("/ecrGeneratePayment")
	public JSONObject retrieveDiscountRate(@RequestBody String requestService,
			@RequestHeader("token_auth") String tokenAuth, @RequestHeader("user_code") String userCode,
			@RequestHeader("user_id") int userId, HttpServletRequest request) throws Exception {
		String ipAddress = request.getHeader("IP");
		String[] resp = new String[2];
		String mid = "";
		String tid = "";
		String serial = "";
		String orderId = "";

		String status = "SUCCESS";
		String state = "ENQUIRY";
		String recType = "ENQUIRY";

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();

		try {
			if (requestService.length() != 0) {
				// AgLogger.logInfo(tokenAuth);
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				boolean b = jwtTokenUtil.validateToken(tokenAuth, userCode, userId);

				if (b) {
					JSONObject onj = (JSONObject) JSONValue.parse(requestService);

					EcrModel retrieveDiscountReqModel = new Gson().fromJson(onj.toString(), EcrModel.class);
					
					mid = retrieveDiscountReqModel.getMid();
					tid = retrieveDiscountReqModel.getTid();
					serial = retrieveDiscountReqModel.getSerialNumber();
					orderId = retrieveDiscountReqModel.getOrderID();
					String dateTime = "";
					int year = Calendar.getInstance().get(Calendar.YEAR);
					try {
						dateTime += retrieveDiscountReqModel.getTxnDate() + year;
					} catch (Exception e) {
						dateTime += "0101" + year;
					}

					try {
						dateTime += retrieveDiscountReqModel.getTxnTime();
					} catch (Exception e) {
						dateTime += "000000";
					}

					Date dfs = new java.util.Date();
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmss");
						dfs = sdf.parse(dateTime);
					} catch (Exception e) {
						AgLogger.logerror(getClass(), " EXCEPTION  ", e);
					}

					EcrLog tempRec = ecrLogsServiceImpl.fetchPaymentByMidTid(mid,tid);
					if (tempRec == null) {

						ConfigEcrRouting crfRoutings = configEcrRoutingServiceImpl.fetchByMidTid(mid, tid,
								retrieveDiscountReqModel.getType());
						if (crfRoutings != null) {
							String tempAmount = retrieveDiscountReqModel.getTxnAmount();
							if(tempAmount.contains(".")) {
								tempAmount = tempAmount.replace(".", "");
							}
							double intValue = Double.parseDouble(tempAmount);							
							
							if(tempAmount!=null && tempAmount.length()<=12 && intValue >=1) {
								resp[0] = "0000";
								resp[1] = "SUCCESS.";
								EcrLog eLog = new EcrLog();
								eLog.setAmount(retrieveDiscountReqModel.getTxnAmount());
								eLog.setConsumerName(retrieveDiscountReqModel.getConsumerName());
								eLog.setConsumerNumber(retrieveDiscountReqModel.getConsumerNumber());
								eLog.setDueDate(new Timestamp(dfs.getTime()));
								eLog.setMid(mid);
								eLog.setRemarks(resp[0] + "|" + resp[1]);
								eLog.setRrn(null);
								eLog.setEnquiryStatus(status);
								eLog.setTid(tid);
								eLog.setPaymentAmount(null);
								eLog.setPaymentDate(null);
								eLog.setPaymentStatus(null);
								eLog.setState(state);
								eLog.setEntryDate(new Timestamp(dfs.getTime()));
								eLog.setRecType(recType);
								eLog.setConfigEcrId(0);
								eLog.setHostResponseCode(null);
								eLog.setOrderId(orderId);
								eLog.setTxnDetailId(0l);
								eLog.setCorporate(crfRoutings.getCorporate());
								ecrLogsServiceImpl.insert(eLog);
							}else {
								resp[0] = "000355";
								resp[1] = "Amount validation failed.";
							}
							

							
						} else {
							resp[0] = "0003";
							resp[1] = "Invalid MID TID Configuration";
						}

					} else {
						resp[0] = "0004";
						resp[1] = "Payment with order id "+tempRec.getOrderId()+" is in process.";
					}

				} else {
					resp[0] = "0001";
					resp[1] = "Invalid Authentication";
				}

			} else {
				resp[0] = "0002";
				resp[1] = "Invalid Data";
			}
		} catch (Exception e) {
			AgLogger.logerror(AckDiscountAvailed.class, " EXCEPTION  ", e);
			resp[0] = "9999";
			resp[1] = "Failed";
		} finally {
			job.put("code", resp[0]);
			job.put("message", resp[1]);

			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("EcrPayment");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}

		return job;

	}

}
