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
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DiscountLog;
import com.ag.mportal.entity.DiscountMaster;
import com.ag.mportal.model.DiscountAvailedAckReqModel;
import com.ag.mportal.services.DiscountLogService;
import com.ag.mportal.services.DiscountMasterService;
import com.google.gson.Gson;

@RestController
public class AckDiscountAvailed {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	DiscountLogService discountLogService;

	@Autowired
	DiscountMasterService discountMasterService;

	@SuppressWarnings("unchecked")
	@PostMapping("/availdDiscountAck")
	public JSONObject availdDiscountAck(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();
		JSONObject onj = new JSONObject();
		String[] resp = new String[2];
		Gson gson = new Gson();
		if (requestService.length() != 0) {
			requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
			onj = (JSONObject) JSONValue.parse(requestService);

			try {

				AgLogger.logInfo("", "Request DiscountAcknowlegde => " + onj.toString());
				if (onj != null) {
					DiscountAvailedAckReqModel discountAvailedAckReqModel = gson.fromJson(onj.toString(),
							DiscountAvailedAckReqModel.class);
					if (Objects.isNull(discountAvailedAckReqModel.getRecId())
							|| discountAvailedAckReqModel.getRecId().isEmpty()
							|| Objects.isNull(discountAvailedAckReqModel.getStatus())
							|| discountAvailedAckReqModel.getStatus().isEmpty()
							|| Objects.isNull(discountAvailedAckReqModel.getRRN())
							|| discountAvailedAckReqModel.getRRN().isEmpty()) {
						AgLogger.logInfo("PARAMETER IS MISSING.");
						resp[0] = "0001";
						resp[1] = "SOME PROBLEM OCCURED.";
					} else {

						// Integer recId = Integer.valueOf(discountAvailedAckReqModel.getRecId());

						DiscountLog discountLog = discountLogService
								.getById(Long.parseLong(discountAvailedAckReqModel.getRecId()));
						if (discountLog != null) {
							discountLog.setUpdOn(new Timestamp(new Date().getTime()));
							if (discountAvailedAckReqModel.getStatus().equalsIgnoreCase("success")) {
								discountLog.setDiscAvailedDate(new Timestamp(new Date().getTime()));
								discountLog.setDiscountAvailed(1);
							} else {
								discountLog.setDiscAvailedDate(null);
								discountLog.setDiscountAvailed(0);
							}
							discountLog.setBatchNumber(discountAvailedAckReqModel.getBatchNo());
							discountLog.setInvoiceNumber(discountAvailedAckReqModel.getInvoiceNo());
							discountLog.setRrn(discountAvailedAckReqModel.getRRN());
							discountLog.setDiscStatus(discountAvailedAckReqModel.getStatus());
							// setTxnId
							discountLogService.update(discountLog);

							// Update Budgeting
							DiscountMaster master = discountMasterService.fetchById(discountLog.getDiscId());

							AgLogger.logInfo("IsBudgetCapping: " + (master.getBugdetCapping() == 1 ? "TRUE" : "FALSE")
									+ " Available Amt:" + master.getBudgetAmountAvbl() + " Discount Amt:"
									+ discountLog.getDiscountAmount() + " Total Substracted Amt:"
									+ (master.getBudgetAmountAvbl() - discountLog.getDiscountAmount()));

							if (master.getBugdetCapping() == 1) {
								if (master.getBudgetAmountAvbl() - discountLog.getDiscountAmount() >= 0) {
									master.setBudgetAmountAvbl(
											master.getBudgetAmountAvbl() - discountLog.getDiscountAmount());
									discountMasterService.update(master);
									resp[0] = "0000";
									resp[1] = "SUCCESS";
								} else {
									resp[0] = "0002";
									resp[1] = "DISCOUNT LIMIT EXCEEDED";
								}
							} else {
								resp[0] = "0000";
								resp[1] = "SUCCESS";
							}

						} else {
							resp[0] = "0001";
							resp[1] = "INVALID RECORD ID.";
						}

					}
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
				adt.setTxnType("availdDiscountAck");
				adt.setMid("");
				adt.setTid("");
				adt.setSerialNum("");
				adt.setCorpId("N/A");

				auditLogService.insertAuditLog(adt);
				AgLogger.logInfo("", "Response DiscountAcknowlegde => " + job.toString());

			}
		}

		return job;

	}

}
