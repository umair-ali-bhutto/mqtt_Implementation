package com.ag.pos.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.encoding.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.db.proc.DBProceduresGeneric;
import com.ag.db.proc.GenericDbProcModel;
import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.services.impl.TxnDetailsServiceImpl;

@RestController
public class ValidateCompletionCall {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	TxnDetailsServiceImpl txnDetailsServiceImpl;

	@SuppressWarnings("unchecked")
	@PostMapping("/validateCompletion")
	public JSONObject retrieveDiscountRate(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		String ipAddress = request.getHeader("IP");
		String userName = "";
		String password = "";
		String[] resp = new String[2];

		String mid = "";
		String tid = "";
		String serial = "";
		String authCode = "";
		String minAmount = "";
		String maxAmount = "";
		String fieldOne = "";
		String fieldTwo = "";
		String cardTxnType = "";
		String amountExceedMessage = "Max Amount Exceeded.";
		String dateTimeCompletion = "";

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();

		try {
			if (requestService.length() != 0) {
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);
				userName = (String) onj.get("UserName");
				password = (String) onj.get("PASSWORD");
				serial = (String) onj.get("serialNumber");
				mid = (String) onj.get("MID");
				tid = (String) onj.get("TID");
				authCode = (String) onj.get("AuthCode");
				int year = Calendar.getInstance().get(Calendar.YEAR);
				try {
					dateTimeCompletion += (String) onj.get("TxnDate") + year;
				} catch (Exception e) {
					dateTimeCompletion += "0101" + year;
				}

//				try {
//					dateTimeCompletion += (String) onj.get("TxnTime");
//				} catch (Exception e) {
//					dateTimeCompletion += "000000";
//				}

				UserLogin user = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(user)) {
					String[] respResult = txnDetailsServiceImpl.getdetailsForPreAuth(mid, serial, authCode);
					if (respResult[0].equals("00000")) {

						String pCardNumber = respResult[2];
						String pCardExpiry = respResult[3];
						String pTxnAmount = respResult[5];
						String pTxnDate = respResult[6];
						String pIsPaid = respResult[8];
						String pPaidDate = respResult[9];
						String pmid = respResult[10];
						String ptid = respResult[11];
						AgLogger.logInfo("..." + pIsPaid);
						if (pIsPaid.equals("N")) {
							GenericDbProcModel fm = DBProceduresGeneric.decrpytCardNumber("00000",
									"MP_Data_Enc_Key_AES256", "000000123456789", Base64.decode(pCardNumber));
							if (fm.getVrsp_code().equals("0000")) {
								GenericDbProcModel gl = DBProceduresGeneric.retrievePreAuth("00000", mid, serial,
										new java.sql.Date(new java.util.Date().getTime()));
								if (gl.getVrsp_code().equals("0000")) {

									JSONObject onjk = (JSONObject) JSONValue.parse(gl.getVmessage());
									String procId = onjk.get("ID").toString();
									String procExpDays = onjk.get("ExpDays").toString();
									String procPercentageMax = onjk.get("MaxPercentage").toString();
									String procPercentageMin = onjk.get("MinPercentage").toString();
									String procSubSegment = onjk.get("MSegment").toString();
									String procValidateTID = onjk.get("ValidateTID").toString();
									if (procValidateTID.equals("Y")) {
										if (ptid.equals(tid)) {
											boolean bValidateExpiry = validateExpiryDays(procExpDays, pTxnDate,
													dateTimeCompletion);
											if (bValidateExpiry) {
												resp[0] = "0000";
												resp[1] = "SUCCESS.";
												Double amntCalMin = (Double.parseDouble(pTxnAmount))
														- ((Double.parseDouble(pTxnAmount)
																* Double.parseDouble(procPercentageMin)) / 100);
												minAmount = String.valueOf(amntCalMin);
												Double amntCal = (Double.parseDouble(pTxnAmount))
														+ ((Double.parseDouble(pTxnAmount)
																* Double.parseDouble(procPercentageMax)) / 100);
												maxAmount = String.valueOf(amntCal);
												fieldOne = fm.getVmessage();
												fieldTwo = pCardExpiry;
												cardTxnType = procSubSegment;
											} else {
												resp[0] = "0007";
												resp[1] = "Transaction Expired.";
											}

										} else {
											resp[0] = "0006";
											resp[1] = "Terminal ID not Authorised for this Transaction.";
										}
									} else {
										boolean bValidateExpiry = validateExpiryDays(procExpDays, pTxnDate,
												dateTimeCompletion);
										if (bValidateExpiry) {
											resp[0] = "0000";
											resp[1] = "SUCCESS.";
											Double amntCalMin = (Double.parseDouble(pTxnAmount))
													- ((Double.parseDouble(pTxnAmount)
															* Double.parseDouble(procPercentageMin)) / 100);
											minAmount = String.valueOf(amntCalMin);
											Double amntCal = (Double.parseDouble(pTxnAmount))
													+ ((Double.parseDouble(pTxnAmount)
															* Double.parseDouble(procPercentageMax)) / 100);
											maxAmount = String.valueOf(amntCal);
											fieldOne = fm.getVmessage();
											fieldTwo = pCardExpiry;
											cardTxnType = procSubSegment;
										} else {
											resp[0] = "0007";
											resp[1] = "Transaction Expired.";
										}
									}

								} else {
									resp[0] = "0005";
									resp[1] = "No Configuration Found.";
								}

							} else {
								resp[0] = "0004";
								resp[1] = "Something Went Wrong.";
							}
						} else {
							resp[0] = "0004";
							resp[1] = "Transaction Already PAID ON " + pPaidDate;
						}

					} else {
						resp[0] = "0002";
						resp[1] = respResult[1];
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

			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("ValidateCompletion");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);

			job.put("code", resp[0]);
			job.put("message", resp[1]);
			job.put("maxAmount", maxAmount);
			job.put("minAmount", minAmount);
			job.put("fieldDataOne", fieldOne);
			job.put("fieldDataTwo", fieldTwo);
			job.put("TxnMode", cardTxnType);
			job.put("amountExceedMessage", amountExceedMessage);

		}

		AgLogger.logInfo(job.toJSONString() + "..........");
		return job;

	}

	private boolean validateExpiryDays(String numDays, String txnDate, String completionDate) {
		boolean bValid = false;
		try {
			int numDaysCheck = Integer.parseInt(numDays);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sdf.parse(txnDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DAY_OF_MONTH, numDaysCheck);
			AgLogger.logInfo(cal.getTime() + "...");

			SimpleDateFormat sdfm = new SimpleDateFormat("MMddyyyy");
			Date dCompletion = sdfm.parse(completionDate);
			if (dCompletion.before(cal.getTime())) {
				bValid = true;
			} else {
				bValid = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			bValid = false;
		}
		return bValid;
	}

}
