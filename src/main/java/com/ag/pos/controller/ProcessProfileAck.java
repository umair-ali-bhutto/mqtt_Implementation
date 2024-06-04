package com.ag.pos.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.services.TxnLogsService;

@RestController
public class ProcessProfileAck {

	@Autowired
	UtilService utilService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	TxnLogsService txnLogsService;

	@SuppressWarnings("unchecked")
	@PostMapping({ "/serviceprofileack", "/baflserviceprofileack" })
	public JSONObject doProcessProfileAck(@RequestBody String requestService, HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getHeader("IP");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
		}
		String[] m = null;
		
		JSONObject job = new JSONObject();
		String resp = "Acknowledged";
		try {
			if (requestService.length() != 0) {
				m = responseServiceProfileAck(requestService.toString());
				AgLogger.logInfo("RESPONSE " + m[0] + "|" + m[1]);
				if (m[0].equals("0000")) {
					resp = "Acknowledged";
				}
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
			adt.setResponse(resp);
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("serviceprofileack");
			adt.setMid(m[2]);
			adt.setTid(m[3]);
			adt.setSerialNum(m[1]);
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		job.put("Reponsemessage", resp);
		return job;
	}

	@Transactional
	public String[] responseServiceProfileAck(String xmls) {
		AgLogger.logInfo("REQUEST ACK DATA: " + xmls);
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);
		String[] kl = new String[4];
		kl[0] = "0000";
		String auditSerial = "N/A";
		String auditMid = "N/A";
		String auditTid = "N/A";
		try {

			String posDateTime = "";
			String imei = "";
			String telco = "";
			String longitude = "";
			String latitude = "";
			String fieldOne = "";
			String fielTwo = "";
			String fieldThree = "";
			String fieldFour = "";
			String fieldFive = "";
			String fieldSix = "";
			String model = "";
			String serialNumber = "";
			String tid = "";
			String mid = "";
			String appName = "";

			try {
				posDateTime = (String) onj.get("PosDateTime");
			} catch (Exception e) {

			}

			try {
				imei = (String) onj.get("imei");
			} catch (Exception e) {

			}

			try {
				telco = (String) onj.get("telco");
			} catch (Exception e) {

			}

			try {
				longitude = (String) onj.get("longitude");
			} catch (Exception e) {

			}

			try {
				latitude = (String) onj.get("latitude");
			} catch (Exception e) {

			}

			try {
				fieldOne = (String) onj.get("fieldOne");
			} catch (Exception e) {

			}

			try {
				fielTwo += (String) onj.get("fieldTwo");
			} catch (Exception e) {

			}

			try {
				fieldThree = (String) onj.get("fieldThree");
			} catch (Exception e) {

			}

			try {
				fieldFour = (String) onj.get("fieldFour");
			} catch (Exception e) {

			}

			try {
				fieldFive = (String) onj.get("fieldFive");
			} catch (Exception e) {

			}

			try {
				fieldSix = (String) onj.get("fieldSix");
			} catch (Exception e) {

			}

			try {
				model = (String) onj.get("Model");
			} catch (Exception e) {

			}

			try {
				serialNumber = (String) onj.get("SerialNumber");
				auditSerial = serialNumber;
			} catch (Exception e) {

			}

			try {
				tid = (String) onj.get("TID");
				auditTid = tid;
			} catch (Exception e) {

			}

			try {
				appName = (String) onj.get("APPNAME");
			} catch (Exception e) {

			}

			try {
				mid = (String) onj.get("MID");
				auditMid = mid;
			} catch (Exception e) {

			}

			utilService.updateVerisys(mid, tid, appName, model, serialNumber);
			TxnLog txnS = UtilAccess.insertTxnLogs(AppProp.getProperty("act.profile.ack"),
					"PROFILE RESPONSE : SUCCESS ", mid, tid, model, serialNumber, posDateTime, imei, telco, longitude,
					latitude, fieldOne, fielTwo, fieldThree, fieldFour, fieldFive, fieldSix);

			txnLogsService.insertLog(txnS);

			kl[2] = auditMid;
			kl[3] = auditTid;
			kl[1] = auditSerial;

		} catch (Exception e) {
			e.printStackTrace();
			kl[0] = "9999";
		}
		return kl;
	}

}
