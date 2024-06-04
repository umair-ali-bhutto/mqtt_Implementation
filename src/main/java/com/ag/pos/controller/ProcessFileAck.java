package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;

@RestController
public class ProcessFileAck {
	
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	AuditLogService auditLogService;
	
	@Autowired
	UtilService utilService;

	@SuppressWarnings("unchecked")
	@PostMapping({ "/servicefileack", "/baflservicefileack" })
	public JSONObject doProcessFileAck(@RequestBody String requestService, HttpServletRequest request) {
		String ipAddress = request.getHeader("IP");
		String[] m = null;

		if (ipAddress == null) {

			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();
		String resp = "Acknowledged";
		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				m = utilService.responseServiceFileAck(requestService.toString());
				AgLogger.logInfo("RESPONSE " + m[0] + "|" + m[1]);
				if (m[0].equals("0000")) {
					resp = "Acknowledged";
				}
				job.put("Reponsemessage", resp);
			} else {
				job.put("Reponsemessage", "Invalid Credentials");
			}
		} catch (Exception e) {
			e.printStackTrace();
			job.put("Reponsemessage", "Exception.");
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
			adt.setTxnType("servicefileack");
			adt.setMid(m[2]);
			adt.setTid(m[3]);
			adt.setSerialNum(m[4]);
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
