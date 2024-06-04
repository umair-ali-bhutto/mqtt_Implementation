package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
import com.ag.mportal.entity.EcrSaf;
import com.ag.mportal.model.EcrModel;
import com.ag.mportal.model.EcrRespModel;
import com.ag.mportal.services.impl.ConfigEcrRoutingServiceImpl;
import com.ag.mportal.services.impl.EcrLogsServiceImpl;
import com.ag.mportal.services.impl.EcrSafServiceImpl;
import com.google.gson.Gson;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

@RestController
public class EcrPaymentAlfa {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	ConfigEcrRoutingServiceImpl configEcrRoutingServiceImpl;

	@Autowired
	EcrLogsServiceImpl ecrLogsServiceImpl;

	@Autowired
	EcrSafServiceImpl ecrSafServiceImpl;

	@SuppressWarnings("unchecked")
	@PostMapping("/ecrPaymentAlfa")
	public JSONObject retrieveDiscountRate(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		String ipAddress = request.getHeader("IP");
		String userName = "";
		String password = "";
		String[] resp = new String[2];

		String txnAmount = "N/A";
		String rrn = "N/A";

		String mid = "";
		String tid = "";
		String serial = "";
		String status = "FAILED";
		EcrLog eLogModel = null;

		int ecrId = 0;

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();

		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				AgLogger.logInfo(requestService + "@@@@");
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);
				userName = (String) onj.get("UserName");
				password = (String) onj.get("PASSWORD");
				try {
					ecrId = Integer.parseInt((String) onj.get("id"));
				} catch (Exception e) {
					e.printStackTrace();
					ecrId = 0;
				}
				UserLogin user = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(user)) {
					EcrModel retrieveDiscountReqModel = new Gson().fromJson(onj.toString(), EcrModel.class);
					mid = retrieveDiscountReqModel.getMid();
					tid = retrieveDiscountReqModel.getTid();
					serial = retrieveDiscountReqModel.getSerialNumber();

					txnAmount = retrieveDiscountReqModel.getTxnAmount();
					rrn = retrieveDiscountReqModel.getRRN();

					eLogModel = ecrLogsServiceImpl.fetchByID(mid, tid, ecrId);
					if (eLogModel != null) {
						ConfigEcrRouting crfRoutings = configEcrRoutingServiceImpl.fetchByMidTid(mid, tid,
								retrieveDiscountReqModel.getType());
						if (crfRoutings != null) {
							ecrId = (int) crfRoutings.getId();
							retrieveDiscountReqModel.setConsumerNumber(eLogModel.getConsumerNumber());
							retrieveDiscountReqModel.setConsumerName(eLogModel.getConsumerName());
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							retrieveDiscountReqModel.setDueDate(sdf.format(eLogModel.getDueDate()));
							retrieveDiscountReqModel.setOrderID(eLogModel.getOrderId());
							EcrRespModel respMdl = fetchPayment(crfRoutings, retrieveDiscountReqModel);
							if (respMdl.getCode().equals("0000")) {
								resp[0] = respMdl.getCode();
								resp[1] = respMdl.getMessage();
								txnAmount = respMdl.getTxnAmount();
								status = "SUCCESS";
								eLogModel.setState("PAYMENT_DONE");
								eLogModel.setRrn(rrn);
								eLogModel.setPaymentAmount(txnAmount);
								eLogModel.setPaymentDate(new Timestamp(new java.util.Date().getTime()));
								eLogModel.setPaymentStatus(status);
								eLogModel.setHostResponseCode(retrieveDiscountReqModel.getHostRspCode());
								// eLogModel.setRecType("PAID");
								eLogModel.setRemarks(resp[0] + "|" + resp[1]);
								eLogModel.setResponseData(respMdl.getResponse());

								// clob

							} else {
								resp[0] = "0006";
								resp[1] = respMdl.getMessage();
								eLogModel.setRrn(rrn);
								eLogModel.setPaymentAmount(txnAmount);
								eLogModel.setPaymentDate(new Timestamp(new java.util.Date().getTime()));
								// eLogModel.setRecType("PAYMENT_ACK_FAILED");
								eLogModel.setState("PAYMENT_FAILED");
								eLogModel.setPaymentStatus("PAYMENT_ACK_FAILED");
								eLogModel.setHostResponseCode(retrieveDiscountReqModel.getHostRspCode());
								eLogModel.setRemarks(resp[0] + "|" + resp[1]);
								eLogModel.setResponseData(respMdl.getResponse());

								// clob

								// SAF IMPLEMENTATIION.
								if (crfRoutings.getSaf() == 1) {
									Long timeInSeconds = 60l;
									String retriesTimes = crfRoutings.getSafRetriesConfig();
									if (retriesTimes.contains(",")) {
										timeInSeconds = Long.parseLong(retriesTimes.split(",")[0]);
									} else {
										timeInSeconds = Long.parseLong(retriesTimes);
									}

									LocalDateTime currentTimestamp = LocalDateTime.now();
									LocalDateTime timestamp = currentTimestamp.plus(timeInSeconds, ChronoUnit.SECONDS);
									Timestamp newTimestamp = Timestamp.valueOf(timestamp);

									EcrSaf ecrSaf = new EcrSaf();
									ecrSaf.setEcrLogId((int) eLogModel.getId());
									ecrSaf.setConfigEcrRoutingId(ecrId);
									ecrSaf.setRequestData(new Gson().toJson(retrieveDiscountReqModel));
									ecrSaf.setRequestHeader(null);
									ecrSaf.setMaxRetry(crfRoutings.getSafRetries());
									ecrSaf.setMinRetry(0);
									ecrSaf.setLastRun(null);
									ecrSaf.setNextRun(newTimestamp);
									ecrSaf.setStatus("PROCESS");
									ecrSaf.setResponseData(null);
									ecrSafServiceImpl.insert(ecrSaf);

								}
							}

						} else {
							resp[0] = "0003";
							resp[1] = "No Routing Found against MID/TID.";
							eLogModel.setRrn(rrn);
							eLogModel.setPaymentAmount(txnAmount);
							eLogModel.setPaymentDate(new Timestamp(new java.util.Date().getTime()));
							// eLogModel.setRecType("PAYMENT_ACK_FAILED");
							eLogModel.setPaymentStatus("PAYMENT_ACK_FAILED");
							eLogModel.setHostResponseCode(retrieveDiscountReqModel.getHostRspCode());
							eLogModel.setRemarks(resp[0] + "|" + resp[1]);
						}
					} else {
						resp[0] = "0004";
						resp[1] = "Invalid ECR Id or Payment has already done.";
					}

				} else {
					resp[0] = "0001";
					resp[1] = "Invalid User Name or Password.";
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

			if (eLogModel != null) {
				ecrLogsServiceImpl.update(eLogModel);
			}
		}

		return job;

	}

	EcrRespModel fetchPayment(ConfigEcrRouting routing, EcrModel emModel) {
		EcrRespModel mdl = new EcrRespModel();
		try {
			if (emModel.getHostRspCode().equals("00")) {
				emModel.setFieldOne("Paid");
			} else {
				emModel.setFieldOne("Rejected");
			}
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
			emModel.setFieldOne("Rejected");
			e.printStackTrace();
			mdl.setCode("0099");
			mdl.setMessage("Something Went Wrong.");
		}
		return mdl;
	}

}
