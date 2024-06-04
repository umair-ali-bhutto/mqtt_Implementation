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
import com.ag.mportal.entity.ProvidersConfig;
import com.ag.mportal.model.RetrieveProvidersReqModel;
import com.ag.mportal.services.ProvidersConfigService;
import com.google.gson.Gson;

@RestController
public class ProcessProvidersConfig {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	ProvidersConfigService ProvidersConfigService;

	@SuppressWarnings("unchecked")
	@PostMapping("/retrieveProviders")
	public JSONObject retrieveDiscountRate(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		String ipAddress = request.getHeader("IP");
		String userName = "";
		String password = "";
		String mid = "";
		String tid = "";
		String serial = "";
		String type = "";
		List<ProvidersConfig> lstResult = null;
		String[] resp = new String[2];
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();

		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);
				userName = (String) onj.get("UserName");
				password = (String) onj.get("PASSWORD");
				UserLogin user = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(user)) {
					RetrieveProvidersReqModel retrieveProvidersReqModel = new Gson().fromJson(onj.toString(),
							RetrieveProvidersReqModel.class);
					mid = retrieveProvidersReqModel.getMID();
					tid = retrieveProvidersReqModel.getTID();
					serial = retrieveProvidersReqModel.getSerialNumber();

					lstResult = ProvidersConfigService.fetchAllRecord();
					if (lstResult.size() != 0) {
						resp[0] = "0000";
						resp[1] = "SUCCESS";
					} else {
						resp[0] = "0003";
						resp[1] = "No Record Found.";
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
			job.put("code", resp[0]);
			job.put("message", resp[1]);
			if (lstResult!=null) {
				JSONArray jarray = new JSONArray();
				for (ProvidersConfig d : lstResult) {
					JSONObject jObj = new JSONObject();
					jObj.put("ProvidersId", d.getId());
					jObj.put("accountTitle", d.getAccountTitle());
					jObj.put("accountNumber", d.getAccountNumber());
					jObj.put("description", d.getDescription());
					jObj.put("minAmount", d.getMinAmount());
					jObj.put("maxAmount", d.getMaxAmount());
					jObj.put("img", "");
					jObj.put("txnMid", d.getTxnMid());
					jObj.put("txnTid", d.getTxnTid());
					jObj.put("typeDesc", d.getTypeDescription());
					jObj.put("merchantName", d.getMerchantName());
					jObj.put("merchantAdd1", d.getMerchantAdd1());
					jObj.put("merchantAdd2", d.getMerchantAdd2());
					jObj.put("merchantAdd3", d.getMerchantAdd3());
					jObj.put("merchantAdd4", d.getMerchantAdd4());
					jarray.add(jObj);
				}

				job.put("data", jarray);
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
			adt.setTxnType("retrieveProviders");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}

		return job;

	}

}
