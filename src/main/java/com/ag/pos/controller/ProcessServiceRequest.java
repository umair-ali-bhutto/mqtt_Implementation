package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.OtpLogging;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;


@RestController
public class ProcessServiceRequest {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	OTPLoggingService otpLoggingService;

	@PostMapping({ "/ServiceRequest", "/baflServiceRequest" })
	public JSONObject doProcessServiceRequest(@RequestBody String requestService, HttpServletRequest request) {
		String ipAddress = request.getHeader("IP");

		if (ipAddress == null) {

			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();
		String[] m = null;
		String message = "success";
		String code = "0000";
		UserLogin userMdl = new UserLogin();
		String mid = "N/A";
		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);
				String otpAuth = "0";
				String userName = "";
				String password = "";
				mid = (String) onj.get("MID");

				try {
					userName = (String) onj.get("UserName");
				} catch (Exception e) {

				}

				try {
					password = (String) onj.get("PASSWORD");
				} catch (Exception e) {

				}

				userMdl = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(userMdl)) {

					// String otpPin = "1";
					String otpPin = (String) onj.get("otp");
					if (otpPin != null) {
						OtpLogging otp = otpLoggingService.validateOtp(String.valueOf(userMdl.getUserId()), otpPin);
						otpAuth = "1";

						if (!Objects.isNull(userMdl) && otpAuth == "1" && otp != null) {
							AgLogger.logInfo("Service Requests for POS RECIEVED .." + requestService);
							m = responseServiceRequest(requestService.toString());
							code = m[0];
							message = m[1];

						} else {
							code = "0001";
							message = "unauthorised";
						}

					} else {
						code = "0001";
						message = "otp incorrect";
					}
				} else {
					code = "0001";
					message = "unauthorised";
				}
			} else {
				code = "0001";
				message = "unauthorised";
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = "0002";
			message = "error";
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId(String.valueOf(userMdl.getUserId()));
			adt.setEntryDate(time);
			adt.setMid(mid);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("ServiceRequest");
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

		job.put("code", code);
		job.put("message", message);
		return job;
	}

	public String[] responseServiceRequest(String xmls) {
		AgLogger.logInfo("REQUEST SERVICE REQUEST : " + xmls);
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);
		JSONArray txnSimDetails = new JSONArray(onj.get("SimData").toString());
		JSONObject simData = (JSONObject) txnSimDetails.get(0);
		String[] kl = new String[5];
		kl[0] = "0000";
		kl[1] = "success";
		String auditMid = "N/A";
		String auditTid = "N/A";
		String auditSerial = "N/A";

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
		String fieldSeven = "";
		String model = "";
		String serialNumber = "";
		String tid = "";
		String mid = "";
		String pass = "";
		String userName = "";
		String password = "";
		String type = "";
		String otp = "";
		String ref = String.valueOf(generateRandom(12));

		try {

			try {
				userName = (String) onj.get("UserName");
			} catch (Exception e) {

			}

			try {
				password = (String) onj.get("PASSWORD");
			} catch (Exception e) {

			}

			UserLogin user = userLoginService.validateUserPassword(userName, password);

			if (!Objects.isNull(user)) {
				try {
					model = (String) onj.get("MODEL");
				} catch (Exception e) {

				}
//				try {
//				String otpPin = (String) onj.get("otp");
//				String otpAuth ="0"; 
//				} catch (Exception e) {
//
//				}

				try {
					serialNumber = (String) onj.get("SERIAL");
					auditSerial = serialNumber;
				} catch (Exception e) {

				}

				try {
					tid = (String) onj.get("TID");
					auditTid = tid;
				} catch (Exception e) {

				}
				try {
					type = (String) onj.get("TYPE");

				} catch (Exception e) {

				}

				try {
					mid = (String) onj.get("MID");
					auditMid = mid;
				} catch (Exception e) {

				}

				try {
					imei = (String) simData.get("imei");
				} catch (Exception e) {

				}

				try {
					telco = (String) simData.get("telco");
				} catch (Exception e) {

				}

				try {
					latitude = (String) simData.get("latitude");
				} catch (Exception e) {

				}

				try {
					longitude = (String) simData.get("longitude");
				} catch (Exception e) {

				}

				try {
					fieldOne = (String) simData.get("fieldOne");
				} catch (Exception e) {

				}

				try {
					fielTwo += (String) simData.get("fieldTwo");
				} catch (Exception e) {

				}

				try {
					fieldThree = (String) simData.get("fieldThree");
				} catch (Exception e) {

				}

				try {
					fieldFour = (String) simData.get("fieldFour");
				} catch (Exception e) {

				}

				try {
					fieldFive = (String) simData.get("fieldFive");
				} catch (Exception e) {

				}

				try {
					fieldSix = (String) simData.get("fieldSix");
				} catch (Exception e) {

				}

				try {
					fieldSeven = (String) simData.get("fieldSeven");
				} catch (Exception e) {

				}

//				try {
//					iccid = (String) simData.get("iccid");
//				} catch (Exception e) {
//
//				}
				kl[2] = auditMid;
				kl[3] = auditTid;
				kl[4] = auditSerial;
				switch (type) {
				case "001":
					kl[1] = "success";

					break;
				case "002":
					kl[1] = "success";

					break;
				case "003":
					kl[1] = "success";

					break;

				default:
					kl[0] = "003";
					kl[1] = "invalid type";

					break;
				}

				if (type != null && model != null && mid != null && tid != null && serialNumber != null) {
					// String[] df = DBProcedureAwards.proc_inv_request(type, mid, tid, null, model,
					// serialNumber, null, null, null, ref, ref,kl[0] , kl[1]);

				} else {
					kl[0] = "0001";
					kl[1] = "missing values ";

				}

			} else {
				kl[0] = "0001";
				kl[1] = "unauthorised " + userName + "|" + password;
			}

		} catch (Exception e) {
			kl[0] = "0002";
			kl[1] = "error";
		} finally {
		}

		return kl;
	}
	
	public static long generateRandom(int length) {
		Random random = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}

}
