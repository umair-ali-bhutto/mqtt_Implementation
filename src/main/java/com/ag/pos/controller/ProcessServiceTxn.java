package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.JMSSenderService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;

@RestController
public class ProcessServiceTxn {

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	public UserLoginService userLoginService;

	@Autowired
	JMSSenderService jmsSenderServiceImpl;

	@SuppressWarnings("unchecked")
	@PostMapping({ "/servicetxn", "/baflservicetxn" })
	public JSONObject doProcessServiceTxn(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getHeader("IP");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
		}
		
		
		JSONObject job = new JSONObject();
		String resp = "Acknowledged";
		String userName = "";
		String password = "";
		String mid = "N/A";
		String tid = "N/A";
		String serial = "N/A";
		try {
			AgLogger.logInfo("Initial Request Recieved : " + requestService);
			if (requestService.length() != 0) {
				String tempRequest = "";
				try {
					tempRequest = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
					// Changes Made Only for V200 so that = sign in call can be omitted. @anwar
					if (tempRequest.charAt(tempRequest.length() - 1) == '=') {
						tempRequest = tempRequest.substring(0, requestService.length() - 1);
					}
					requestService = tempRequest;
				} catch (Exception e) {
					
				}
				//AgLogger.logInfo("........." + requestService);
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);

				try {
					userName = (String) onj.get("UserName");
				} catch (Exception e) {

				}

				try {
					password = (String) onj.get("PASSWORD");
				} catch (Exception e) {

				}
				try {
					serial = (String) onj.get("SerialNumber");
				} catch (Exception e) {

				}

				try {
					mid = (String) onj.get("MID");
				} catch (Exception e) {

				}
				try {
					tid = (String) onj.get("TID");
				} catch (Exception e) {

				}

				UserLogin userMdl = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(userMdl)) {
					AgLogger.logInfo("TXN REQUEST RECIEVED .." + requestService);
					jmsSenderServiceImpl.send(requestService.toString());
					resp = "Acknowledged";
				} else {
					resp = "VALIDATION FAILED " + userName + "|" + password;
				}

			} else {
				resp = "Invalid Credentials";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp = "Exception.";
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(resp);
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("servicetxn");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}

		job.put("Reponsemessage", resp);
		AgLogger.logInfo("........." + job.toJSONString());
		return job;
	}

}
