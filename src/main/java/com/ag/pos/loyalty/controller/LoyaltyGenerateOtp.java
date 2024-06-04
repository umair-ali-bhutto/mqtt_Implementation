package com.ag.pos.loyalty.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.SendNotifciation;
import com.google.gson.Gson;

@RestController
public class LoyaltyGenerateOtp {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	OTPLoggingService oTPLoggingService;

	@Autowired
	SendNotifciation sendNotifciation;

	@PostMapping({ "/LoyaltyGenerateOtp" })
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

				String otp = generateOtp(requestService.get("imei").toString(), userMerchant.getMsisdn(), userMerchant.getUserId()+"");
				AgLogger.logInfo(userMerchant.getMsisdn() + " OTP is:*****");
				createNotification(userMerchant.getUserCode(), userMerchant.getCorpId(), otp);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
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
			adt.setTxnType("Loyalty Generate OTP");
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

	public String generateOtp(String imei, String mobileNo, String chemistId) {
		Random ran = new Random(System.currentTimeMillis());
		int random = ran.nextInt(999999);
		String o = String.format("%06d", random);
		oTPLoggingService.generateOtp(imei, mobileNo, chemistId, o);
		return o;

	}

	public void createNotification(String userCode, String corpId, String otp) {
		UserLogin user = userLoginService.validetUser(userCode, corpId);

		SendNotificationModel sdm = new SendNotificationModel();

		sdm.setPass("N/A");
		sdm.setUserName(user.getUserCode());
		sdm.setMerchantName(user.getUserName());
		sdm.setReciverId(user.getUserId());

		sdm.setClosedBy(null);
		sdm.setRequestComplDate(null);
		sdm.setClosureDate(null);
		sdm.setComplaintNum(null);
		sdm.setResolution(null);
		sdm.setAccountOpeningDate(null);
		sdm.setOtp(otp);

		sendNotifciation.doTask("999", "0001", "00007", sdm, user.getUserId());
	}

}
