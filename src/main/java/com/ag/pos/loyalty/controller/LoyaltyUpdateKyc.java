package com.ag.pos.loyalty.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.OtpLogging;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;

@RestController
public class LoyaltyUpdateKyc {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	OTPLoggingService oTPLoggingService;

	@PostMapping({ "/LoyaltyUpdateKyc" })
	public ResponseModel doProcessFileAuth(@RequestBody JSONObject requestService, HttpServletRequest request) {
		return doProcessData(requestService, request);
	}

	public ResponseModel doProcessData(JSONObject requestService, HttpServletRequest request) {
		ResponseModel response = new ResponseModel();
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		try {
			UserLogin user = userLoginService.validateUserPassword(requestService.get("userName").toString(),
					requestService.get("password").toString());
			if (user != null) {

				UserLogin userMerchant = userLoginService.fetchUserByMid(requestService.get("mid").toString(),
						user.getCorpId());

				OtpLogging otp = oTPLoggingService.validateOtp(requestService.get("otp").toString(),
						userMerchant.getUserId() + "");
				if (otp != null) {
					AgLogger.logInfo("OTP Validated");
					String cardNumber = requestService.get("cardNumber").toString();
					String mobile = requestService.get("mobile").toString();
					String email = requestService.get("email").toString();
					String address = requestService.get("address").toString();

					AgLogger.logInfo("@@@@ cardNumber:" + cardNumber + " | " + "mobile:" + mobile + " | " + "email:"
							+ email + " | " + "address:" + address);

					response.setCode("0000");
					response.setMessage("KYC Updated Successfully.");

				} else {
					response.setCode("0001");
					response.setMessage("INVALID OTP.");
				}

			} else {
				response.setCode("9991");
				response.setMessage("Invalid Credentials.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setTid("N/A");
			adt.setEntryDate(time);
			adt.setRequest(requestService.toString());
			adt.setResponse(new Gson().toJson(response));
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("Loyalty Update KYC");
			adt.setMid("N/A");
			adt.setSerialNum("N/A");
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return response;
	}

}
