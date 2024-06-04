package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.QrPayments;
import com.ag.mportal.services.QrPaymentsService;
import com.google.gson.Gson;

@RestController
public class QrPaymentAck {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	QrPaymentsService qrPaymentsService;

	@Autowired
	AuditLogServiceImpl auditLogServiceImpl;

	@PostMapping({ "/qrPaymentAck", "/baflQrPaymentAck" })
	public ResponseModel ackQrPayment(@RequestBody String requestService, HttpServletRequest request) throws Exception {
		ResponseModel response = new ResponseModel();
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);
				if (onj != null) {
					response = responseServiceQrPayment(onj.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), "Exception In ackQrPayment: ", e);
			response.setCode("9999");
			response.setMessage("Failed");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(new Gson().toJson(response));
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("qrPaymentAck");
			//adt.setMid(response.getData().get("mid").toString());
			//adt.setTid(response.getData().get("tid").toString());
			//adt.setSerialNum(response.getData().get("serial").toString());
			adt.setCorpId("N/A");

			auditLogServiceImpl.insertAuditLog(adt);

		}
		AgLogger.logInfo("Response responseServiceQrPaymentAck DATA: " + new Gson().toJson(response));
		return response;
	}

	@Transactional
	public ResponseModel responseServiceQrPayment(String xmls) {
		AgLogger.logInfo("REQUEST responseServiceQrPaymentAck DATA: " + xmls);
		org.json.JSONObject onj = new org.json.JSONObject(xmls);

		ResponseModel response = new ResponseModel();
		HashMap<Object, Object> mk = new HashMap<Object, Object>();

		String serialNumber = "";
		String tid = "";
		String mid = "";

		String userName = "";
		String password = "";

		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		try {
			try {
				userName = (String) onj.get("userName");
			} catch (Exception e) {
			}
			try {
				password = (String) onj.get("password");
			} catch (Exception e) {
			}

			UserLogin userMdl = userLoginService.validateUserPassword(userName, password);

			if (!Objects.isNull(userMdl)) {

				try {
					serialNumber = (String) onj.get("serialNumber");
				} catch (Exception e) {

				}

				try {
					tid = (String) onj.get("tid");
				} catch (Exception e) {

				}

				try {
					mid = (String) onj.get("mid");
				} catch (Exception e) {

				}
				mk.put("mid", mid);
				mk.put("tid", tid);
				mk.put("serial", serialNumber);
				AgLogger.logDebug("", mid + "|" + tid + "|" + serialNumber);
				QrPayments qp = qrPaymentsService.fetch(mid, tid, serialNumber);
				if (qp != null) {
					qp.setIsResponseSent(1);
					qp.setResponseSentDate(time);
					qp.setStatus("Success");
					qrPaymentsService.update(qp);
					SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat sdt = new SimpleDateFormat("hh:mm:ss a");

//					mk.put("txnAmount", qp.getTxnAmount());
//					mk.put("txnRef", qp.getTxnRef());
//					mk.put("txnDate", );
//					mk.put("txnTime", sdt.format(qp.getPaymentDate()));
//					
//					mk.put("srcAccount", qp.getSrcAccount());
//					mk.put("toAccount", qp.getToAccount());
//					mk.put("srcName", qp.getSrcName());
//					mk.put("toName", qp.getToName());

					mk.put("amount", qp.getTxnAmount());
					mk.put("awardPoints", qp.getPointsAwdRedemed());
					mk.put("txnType", qp.getType());
					mk.put("transactionAmount", qp.getTxnAmount());
					mk.put("cardHolderName", qp.getSrcName());
					mk.put("rrn", qp.getTxnRef());
					mk.put("mid", qp.getMid());
					mk.put("tid", qp.getTid());
					mk.put("txnDate", sd.format(qp.getPaymentDate()));
					mk.put("txnTime", sdt.format(qp.getPaymentDate()));
					mk.put("appCode", getAuthId());

					mk.put("cardType", qp.getCardType());
					mk.put("invoiceNumber", qp.getInvoiceNumber());
					mk.put("batchNumber", qp.getBatchNumber());
					mk.put("cardExpiry", qp.getCardExpiry());
					mk.put("track2", qp.getCardNumber() + "" + qp.getCardExpiry());
					mk.put("cardNumber", qp.getCardNumber());
					mk.put("issuerName", qp.getIssuerName());
					mk.put("productId", qp.getProductId());
					mk.put("cardScheme", qp.getCardScheme());
					mk.put("qrExpiry", qp.getQrExpiry());

					mk.put("balanceAmount", "0.0");
					mk.put("balancePoints", "0.0");

					response.setCode("0000");
					response.setMessage("Success.");
					response.setData(mk);

				} else {
					response.setCode("0001");
					response.setMessage("No Transaction Found.");
					response.setData(mk);
				}

			} else {
				response.setCode("9997");
				response.setMessage("VALIDATION FAILED" + userName + " | " + password);
				response.setData(mk);
			}
		} catch (Exception e) {
			response.setCode("9998");
			response.setMessage("FAILED");
		}

		return response;
	}

	public static String getAuthId() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

}
