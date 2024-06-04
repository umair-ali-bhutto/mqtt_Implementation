package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
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
import com.ag.mportal.entity.EcrLog;
import com.ag.mportal.model.EcrModel;
import com.ag.mportal.services.impl.ConfigEcrRoutingServiceImpl;
import com.ag.mportal.services.impl.EcrLogsServiceImpl;
import com.google.gson.Gson;

@RestController
public class EcrGetPaymentStatus {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	ConfigEcrRoutingServiceImpl configEcrRoutingServiceImpl;

	@Autowired
	EcrLogsServiceImpl ecrLogsServiceImpl;


	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@SuppressWarnings({ "unchecked", "null" })
	@PostMapping("/ecrGetPaymentStatus")
	public JSONObject retrieveDiscountRate(@RequestBody String requestService,
			@RequestHeader("token_auth") String tokenAuth, @RequestHeader("user_code") String userCode,
			@RequestHeader("user_id") int userId, HttpServletRequest request) throws Exception {
		String ipAddress = request.getHeader("IP");
		String mid = "";
		String tid = "";
		String serial = "";
		String pymtStatus = "";
		String orderId = "";
		EcrModel eLog = new EcrModel();
		String[] resp = new String[2];


		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();
		
		
		AgLogger.logInfo( "@@@@ "+requestService);
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
					orderId = retrieveDiscountReqModel.getOrderID();

					EcrLog tempRec = ecrLogsServiceImpl.fetchPaymentStatusByOrderId(orderId);
					if (tempRec != null) {
						pymtStatus = paymentStatus(tempRec.getHostResponseCode());
							
						eLog.setMid(tempRec.getMid());
						eLog.setTid(tempRec.getTid());
						eLog.setSerialNumber(retrieveDiscountReqModel.getSerialNumber());
						eLog.setModel(retrieveDiscountReqModel.getModel());
						eLog.setTxnAmount(tempRec.getAmount());
						eLog.setConsumerName(tempRec.getConsumerName());
						eLog.setOrderID(tempRec.getOrderId());
						eLog.setTxnTime(retrieveDiscountReqModel.getTxnDate());
						eLog.setTxnDate(retrieveDiscountReqModel.getTxnTime());
						eLog.setType(tempRec.getRecType());
						eLog.setHostRspCode(tempRec.getHostResponseCode());
						eLog.setPymtStatus(pymtStatus);
						resp[0] = "0000";
						resp[1] = "Success.";
						
	
						
					} else {
						resp[0] = "0004";
						resp[1] = "Invalid Order ID";
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
			if(resp[0].equals("0000")) {
				job.put("Mid", eLog.getMid());
				job.put("Tid", eLog.getTid());
				job.put("SerialNumber", eLog.getSerialNumber());
				job.put("Model", eLog.getModel());
				job.put("TxnAmount", eLog.getTxnAmount());
				job.put("ConsumerName", eLog.getConsumerName());
				job.put("OrderID", eLog.getOrderID());
				job.put("TxnTime", eLog.getTxnTime());
				job.put("TxnDate", eLog.getTxnDate());
				job.put("Type", eLog.getType());
				job.put("HostRspCod", eLog.getHostRspCode());
				job.put("PymtStatus", eLog.getPymtStatus());
			}

			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("EcrGetOrderStatus");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}

		return job;

	}

	public String paymentStatus(String hostRespCode) {
		String status = "";
		if (hostRespCode != null) {
			if (hostRespCode.equalsIgnoreCase("00")) {
				status = "Success";
			} else {
				status = "Failure";
			}
		} else {
			status = "Failure";
		}
		return status;
	}


}
