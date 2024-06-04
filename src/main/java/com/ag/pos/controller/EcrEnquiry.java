package com.ag.pos.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.EcrUtil;
import com.ag.mportal.entity.ConfigEcrRouting;
import com.ag.mportal.entity.EcrLog;
import com.ag.mportal.model.EcrModel;
import com.ag.mportal.model.EcrRespModel;
import com.ag.mportal.services.impl.ConfigEcrRoutingServiceImpl;
import com.ag.mportal.services.impl.EcrLogsServiceImpl;
import com.google.gson.Gson;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

@RestController
public class EcrEnquiry {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	ConfigEcrRoutingServiceImpl configEcrRoutingServiceImpl;

	@Autowired
	EcrLogsServiceImpl ecrLogsServiceImpl;

	@SuppressWarnings("unchecked")
	@PostMapping("/ecrEnquiry")
	public JSONObject retrieveDiscountRate(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		String ipAddress = request.getHeader("IP");
		String userName = "";
		String password = "";
		String[] resp = new String[2];

		String consumerNumber = "N/A";
		String txnAmount = "N/A";
		String dueDate = "00/00/0000";
		String consumerName = "N/A";
		String remarks = "N/A";
		String id = "0";

		String mid = "";
		String tid = "";
		String serial = "";
		String status = "FAILED";
		String state = "ENQUIRY";
		String recType = "BILL_ENQUIRY";

		int ecrId = 0;

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();

		try {
			if (requestService.length() != 0) {
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);
				userName = (String) onj.get("UserName");
				password = (String) onj.get("PASSWORD");
				UserLogin user = userLoginService.validateUserPassword(userName, password);
				EcrModel retrieveDiscountReqModel = new Gson().fromJson(onj.toString(), EcrModel.class);
				mid = retrieveDiscountReqModel.getMid();
				tid = retrieveDiscountReqModel.getTid();
				serial = retrieveDiscountReqModel.getSerialNumber();
				if (!Objects.isNull(user)) {
					ConfigEcrRouting crfRoutings = configEcrRoutingServiceImpl.fetchByMidTid(mid, tid,
							retrieveDiscountReqModel.getType());
					if (crfRoutings != null) {
						ecrId = (int) crfRoutings.getId();

						consumerNumber = retrieveDiscountReqModel.getConsumerNumber();
						// if(consumerNumber.equals("123")) {
						EcrRespModel respMdl = fetchEnquiry(crfRoutings, retrieveDiscountReqModel);
						if (respMdl.getCode().equals("0000")) {
							resp[0] = respMdl.getCode();
							resp[1] = respMdl.getMessage();
							txnAmount = respMdl.getTxnAmount();
							dueDate = respMdl.getDueDate();
							consumerName = respMdl.getConsumerName();
							remarks = respMdl.getRemarks();
							status = "SUCCESS";
						} else {
							resp[0] = "0006";
							resp[1] = respMdl.getMessage();
						}
//						}else {
//							resp[0] = "0006";
//							resp[1] = "Invalid Consumer Number.";
//							
//						}

					} else {
						resp[0] = "0005";
						resp[1] = "No Routing Found against MID/TID.";
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
			Date dueDateData = null;
			try {
				dueDateData = new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);
			} catch (Exception e) {

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
			adt.setTxnType("EcrEnquiry");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);

			EcrLog eLog = new EcrLog();
			eLog.setAmount(txnAmount);
			eLog.setConsumerName(consumerName);
			eLog.setConsumerNumber(consumerNumber);
			eLog.setDueDate(dueDateData);
			eLog.setMid(mid);
			eLog.setRemarks(resp[0] + "|" + resp[1]);
			eLog.setRrn(null);
			eLog.setEnquiryStatus(status);
			eLog.setTid(tid);
			eLog.setPaymentAmount(null);
			eLog.setPaymentDate(null);
			eLog.setPaymentStatus(null);
			eLog.setState(state);
			eLog.setEntryDate(time);
			eLog.setRecType(recType);
			eLog.setConfigEcrId(ecrId);
			eLog.setResponseData(remarks);
			long idL = ecrLogsServiceImpl.insert(eLog);

			id = String.valueOf(idL);

			job.put("code", resp[0]);
			job.put("message", resp[1]);
			job.put("consumerNumber", consumerNumber);
			job.put("txnAmount", txnAmount);
			job.put("dueDate", dueDate);
			job.put("consumerName", consumerName);
			job.put("remarks", remarks);
			job.put("id", id);

		}

		return job;

	}

	EcrRespModel fetchEnquiry(ConfigEcrRouting routing, EcrModel emModel) {
		EcrRespModel mdl = new EcrRespModel();
		try {
			
			String requestData = routing.getRequestTemplete();
			String tempModel = requestData;
			requestData = EcrUtil.convertObjectToJSONMapper(requestData, emModel);
			AgLogger.logInfo(requestData + ".........");
			routing.setRequestTemplete(requestData);
			GroovyClassLoader classLoader = new GroovyClassLoader();
			String groovyScript = routing.getNetworkHandler();
			Class<?> groovy = classLoader.parseClass(groovyScript.toString());
			GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
			Object output = groovyObj.invokeMethod("perform", routing);
			mdl = (EcrRespModel) output;
			routing.setRequestTemplete(tempModel);
			classLoader.close();
		} catch (Exception e) {
			e.printStackTrace();
			mdl.setCode("0099");
			mdl.setMessage("Something Went Wrong.");
		}
		return mdl;
	}

}
