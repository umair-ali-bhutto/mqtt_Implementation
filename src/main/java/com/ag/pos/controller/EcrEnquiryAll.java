package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.EcrLog;
import com.ag.mportal.model.EcrModel;
import com.ag.mportal.services.impl.EcrLogsServiceImpl;
import com.google.gson.Gson;

@RestController
public class EcrEnquiryAll {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	EcrLogsServiceImpl ecrLogsServiceImpl;

	@SuppressWarnings("unchecked")
	@PostMapping("/ecrEnquiryAll")
	public JSONObject retrieveDiscountRate(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		String ipAddress = request.getHeader("IP");
		String userName = "";
		String password = "";
		String[] resp = new String[2];

		String mid = "";
		String tid = "";
		String serial = "";

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();
		JSONArray jarray = new JSONArray();
		

		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);
				userName = (String) onj.get("UserName");
				password = (String) onj.get("PASSWORD");
				UserLogin user = userLoginService.validateUserPassword(userName, password);
				EcrModel retrieveDiscountReqModel = new Gson().fromJson(onj.toString(), EcrModel.class);
				mid = retrieveDiscountReqModel.getMid();
				tid = retrieveDiscountReqModel.getTid();
				serial = retrieveDiscountReqModel.getSerialNumber();
				if (!Objects.isNull(user)) {
					List<EcrLog> eLog = ecrLogsServiceImpl.fetchByMidTid(mid, tid);
					if(eLog.size()!=0) {
						resp[0] = "0000";
						resp[1] = "SUCCESS.";
						for(EcrLog e:eLog) {
							JSONObject jobTemp = new JSONObject();
							jobTemp.put("consumerNumber", e.getConsumerNumber());
							jobTemp.put("txnAmount", e.getAmount());
							jobTemp.put("dueDate", e.getDueDate());
							jobTemp.put("consumerName", e.getConsumerName());
							jobTemp.put("remarks", e.getRemarks());
							jobTemp.put("id", e.getId());
							jarray.add(jobTemp);
						}
					}else {
						resp[0] = "0003";
						resp[1] = "No Records Found.";
					}

				} else {
					resp[0] = "0001";
					resp[1] = "Invalid User Name or Password.";
				}
			} else {
				resp[0] = "0004";
				resp[1] = "Invalid Data";
			}
		} catch (Exception e) {
			AgLogger.logerror(AckDiscountAvailed.class, " EXCEPTION  ", e);
			resp[0] = "9999";
			resp[1] = "Failed";
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("EcrEnquiryAll");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);

			job.put("code", resp[0]);
			job.put("message", resp[1]);
			if(resp[0].equals("0000")) {
				job.put("data", jarray);
			}

		}

		return job;

	}

}
