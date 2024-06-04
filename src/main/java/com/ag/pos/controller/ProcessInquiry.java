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
import com.ag.generic.service.UserLoginService;

@RestController
public class ProcessInquiry {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@SuppressWarnings("unchecked")
	@PostMapping({ "/InquireService", "/baflInquireService" })
	public JSONObject doProcessInquiry(@RequestBody String requestService, HttpServletRequest request) {
		String ipAddress = request.getHeader("IP");

		if (ipAddress == null) {

			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();
		String userName = "";
		String password = "";
		String mid = "N/A";
		String tid = "N/A";
		String Serial = "N/A";
		UserLogin user = new UserLogin();
		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
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
					mid = (String) onj.get("MID");
				} catch (Exception e) {

				}
				try {
					tid = (String) onj.get("TID");
				} catch (Exception e) {

				}
				user = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(user)) {

				} else {
					job.put("code", "1111");
					job.put("msg", "Credential Not Found");
				}
			} else {
				job.put("code", "1111");
				job.put("msg", "Invalid Credentials");
			}
		} catch (Exception e) {
			e.printStackTrace();
			job.put("code", "1111");
			job.put("msg", "Exception.");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId(String.valueOf(user.getUserId()));
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setMid(user.getMid());
			adt.setTxnType("InquireService");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(Serial);
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return job;
	}

}
