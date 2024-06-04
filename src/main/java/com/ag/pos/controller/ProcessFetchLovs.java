package com.ag.pos.controller;


import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.LovService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;

@RestController
public class ProcessFetchLovs {
	
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	AuditLogService auditLogService;
	
	@Autowired
	LovService lovService;
	
	@SuppressWarnings("unchecked")
	@PostMapping({ "/FetchLOVS", "/BaflFetchLOVS" })
	public JSONObject doProcessFetchLovs(@RequestBody String requestService, HttpServletRequest request) {
		
		String ipAddress = request.getHeader("IP");
		 
        if(ipAddress== null){
 
            ipAddress = request.getRemoteAddr();
        }
        System.out.println(ipAddress);
		JSONObject job = new JSONObject();
		String refNum = String.valueOf(System.currentTimeMillis());
		AgLogger.logInfoService(getClass(), refNum, "doProcessFetchLovs", requestService);
		String userName = "";
		String password = "";
		String mid="N/A";
		String tid="N/A";
		String Serial="N/A";
		UserLogin user= new UserLogin();
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
					List<LovMaster> lstLov = lovService.fetchLovs(user.getCorpId());
					List<LovDetail> ld = lovService.fetchLovsDetailsAll(user.getCorpId());
					job.put("lovs", lstLov);
					job.put("lovsDetails", ld);
					job.put("Reponsemessage", "SUCCESS");

				} else {
					job.put("Reponsemessage", "Credential Not Found");
				}
			} else {
				job.put("Reponsemessage", "Invalid Credentials");
			}

		} catch (Exception e) {
			e.printStackTrace();
			job.put("code", "1111");
			job.put("Reponsemessage", "Exception.");
		}finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest( requestService);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("FetchLOVS");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(Serial);
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		AgLogger.logInfoService(getClass(), refNum, "doProcessFetchLovs", job.toString());
		return job;
	}

}
