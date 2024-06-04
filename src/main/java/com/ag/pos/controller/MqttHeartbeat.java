package com.ag.pos.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.JSONArray;
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
import com.ag.generic.entity.UserSetting;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.entity.TxnLogError;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.services.TxnLogsService;

@RestController
@Transactional
public class MqttHeartbeat {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	TxnLogsService txnLogsService;

	@Autowired
	TxnDetailsService txnDetailsService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	UtilService utilService;

	@SuppressWarnings("unchecked")
	@PostMapping("/mqttHeartbeat")
	public JSONObject processMqttHeartbeat(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		AgLogger.logInfo("MQTT HEARBEAT REQUEST | " + requestService);
		JSONObject res = new JSONObject();
		String ipAddress = request.getHeader("IP");
		String username = "";
		String password = "";
		String mid = "";
		String tid = "";
		String serialNumber = "";
		String model = "";
		String imei = "";
		String telco = "";
		String longitude = "";
		String latitude = "";
		String fieldOne = "0";
		String fieldTwo = "";
		String fieldThree = "";
		String fieldFour = "";
		String fieldFive = "";
		String fieldSix = "SUCCESS";// remarks
		String communicationMode = "";
		String signalStrength = "";
		String firmware = "";
		String cpuMemory = "";
		String battery = "";
		String os = "";
		String appVersion = "";
		String acquirer = "";

		String header1 = "";
		String header2 = "";
		String header3 = "";
		String header4 = "";

		try {
			JSONObject req = (JSONObject) JSONValue.parse(requestService);
			JSONObject terminalData = (JSONObject) req.get("terminalData");

			username = terminalData.containsKey("userName") ? terminalData.get("userName").toString() : "";
			password = terminalData.containsKey("password") ? terminalData.get("password").toString() : "";

			UserLogin user = userLoginService.validateUserPassword(username, password);
			String userValidated = "INVALID USER";
			if (!Objects.isNull(user)) {
				userValidated = "USER VALIDATED";
			}

			mid = terminalData.containsKey("MID") ? terminalData.get("MID").toString() : "";
			tid = terminalData.containsKey("TID") ? terminalData.get("TID").toString() : "";
			serialNumber = terminalData.containsKey("SerialNumber") ? terminalData.get("SerialNumber").toString() : "";
			model = terminalData.containsKey("Model") ? terminalData.get("Model").toString() : "";
			telco = terminalData.containsKey("telco") ? terminalData.get("telco").toString() : "";
			longitude = terminalData.containsKey("longitude") ? terminalData.get("longitude").toString() : "";
			latitude = terminalData.containsKey("latitude") ? terminalData.get("latitude").toString() : "";
			imei = terminalData.containsKey("imsiNumber") ? terminalData.get("imsiNumber").toString() : "";

			communicationMode = terminalData.containsKey("communicationMode")
					? terminalData.get("communicationMode").toString()
					: "";

			fieldOne = terminalData.containsKey("SAFCounter") ? terminalData.get("SAFCounter").toString() : "";

			fieldTwo = terminalData.containsKey("MERCHANTNAME") ? terminalData.get("MERCHANTNAME").toString() : "";

			header1 = terminalData.containsKey("HEADER_1") ? terminalData.get("HEADER_1").toString() : "";
			header2 = terminalData.containsKey("HEADER_2") ? terminalData.get("HEADER_2").toString() : "";
			header3 = terminalData.containsKey("HEADER_3") ? terminalData.get("HEADER_3").toString() : "";
			header4 = terminalData.containsKey("HEADER_4") ? terminalData.get("HEADER_4").toString() : "";

			fieldThree = header1 + " " + header2 + " " + header3 + " " + header4; // address

			fieldFour = communicationMode;

			firmware = terminalData.containsKey("firmware")
					? terminalData.containsKey("firmware") ? terminalData.get("firmware").toString() : ""
					: "";

			signalStrength = terminalData.containsKey("sstrength") ? terminalData.get("sstrength").toString() : "";
			cpuMemory = terminalData.containsKey("CPUMemory") ? terminalData.get("CPUMemory").toString() : "";
			battery = terminalData.containsKey("Battery") ? terminalData.get("Battery").toString().trim() : "";
			os = terminalData.containsKey("OS") ? terminalData.get("OS").toString() : "";
			appVersion = terminalData.containsKey("APPVersion") ? terminalData.get("APPVersion").toString() : "";
			acquirer = terminalData.containsKey("Acquirer") ? terminalData.get("Acquirer").toString() : "";

			fieldFive = userValidated;

			// SAF COUNTER
			if (!fieldOne.equals("0")) {
				JSONArray txnData = new JSONArray(req.get("TxnData").toString());
				for (int i = 0; i < txnData.length(); i++) {
					String data = txnData.getString(i) + "||" + acquirer + "||" + model + "||" + serialNumber;
					AgLogger.logInfo("REQUEST DATA MQTT SERVICE TXN: " + data);

					String[] parsedData = data.split("\\|\\|");
					// INDEXED FOR FUTURE REFERENCE --UMAIR.ALI
					// [0]Bin
					// [1]FieldOne
					// [2]AuthID
					// [3]Amount
					// [4]TxnDate
					// [5]TxnTime
					// [6]POSEntryMode
					// [7]BatchNo
					// [8]RRN
					// [9]Stan
					// [10]TxnType
					// [11]CardScheme
					// [12]TipAmount
					// [13]AdjAmount
					// [14]TVR
					// [15]AID
					// [16]CardType
					// [17]CardExpiry
					// [18]FIELDONEDATA
					// [19]PREAUTHPAID
					// [20]PREAUTHPAIDDATE
					// [21]mid
					// [22]tid
					// [23]providersId
					// [24]type
					// [25]INVOICENUMBER
					// [26]CUSTOMERNAME
					// [27]imsiNumber
					// [28]longitude
					// [29]latitude
					// [30]preAuthCardExpiry
					// [31]marketSegment
					// [32]ecr_amount
					// [33]ecr_due_date
					// [34]ecr_consumer_no
					// [35]ecr_log_id
					// [36]Acquirer
					// [37]Model
					// [38]SerialNumber

					String[] response = responseServiceTxn(parsedData, mid, tid);
					fieldSix = response[1];
					int txnLogId = Integer.parseInt(response[2]);
					if (!response[2].equalsIgnoreCase("0")) {

						List<MerchantTerminalDetail> lst = new ArrayList<MerchantTerminalDetail>();
						MerchantTerminalDetail mtd = null;

						// WLAN
						if (communicationMode.equalsIgnoreCase("WiFi")) {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-WLANINFO");
							mtd.setDataName("latitude".toUpperCase());
							mtd.setDataValue(parsedData[29].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-WLANINFO");
							mtd.setDataName("longitude".toUpperCase());
							mtd.setDataValue(parsedData[28].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);
						}
//						// ETHERNET
//						else if (communicationMode.equalsIgnoreCase("Ethernet")) {
//							mtd = new MerchantTerminalDetail();
//							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
//							mtd.setMid(mid);
//							mtd.setTid(tid);
//							mtd.setType("TRANSACTION-ETHERNETINFO");
//							mtd.setDataName("latitude".toUpperCase());
//							mtd.setDataValue(parsedData[29].toUpperCase());
//							mtd.setTxnLogsId(txnLogId);
//							lst.add(mtd);
//
//							mtd = new MerchantTerminalDetail();
//							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
//							mtd.setMid(mid);
//							mtd.setTid(tid);
//							mtd.setType("TRANSACTION-ETHERNETINFO");
//							mtd.setDataName("longitude".toUpperCase());
//							mtd.setDataValue(parsedData[28].toUpperCase());
//							mtd.setTxnLogsId(txnLogId);
//							lst.add(mtd);
//						}
						// SIM
//						else if (communicationMode.equalsIgnoreCase("gsm")) {
						else {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-GSMINFO");
							mtd.setDataName("imsiNumber".toUpperCase());
							mtd.setDataValue(parsedData[27].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-GSMINFO");
							mtd.setDataName("latitude".toUpperCase());
							mtd.setDataValue(parsedData[29].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-GSMINFO");
							mtd.setDataName("longitude".toUpperCase());
							mtd.setDataValue(parsedData[28].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-GSMINFO");
							mtd.setDataName("sstrength".toUpperCase());
							mtd.setDataValue(signalStrength.toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-GSMINFO");
							mtd.setDataName("telco".toUpperCase());
							mtd.setDataValue(telco.toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);
						}

						// PRE-AUTH
						if (parsedData[19].trim().length() != 0) {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PREAUTHINFO");
							mtd.setDataName("FIELDONEDATA".toUpperCase());
							GenericDbProcModel f = DBProceduresGeneric.encryptCardNumber("00000",
									"MP_Data_Enc_Key_AES256", "000000123456789", parsedData[18].toUpperCase());
							if (f.getVrsp_code().equals("0000")) {
								mtd.setDataValue(f.getVmessage());
							} else {
								mtd.setDataValue("N-A");
							}
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PREAUTHINFO");
							mtd.setDataName("CardExpiry".toUpperCase());
							mtd.setDataValue(parsedData[30].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PREAUTHINFO");
							mtd.setDataName("PREAUTHPAID".toUpperCase());
							mtd.setDataValue(parsedData[19].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PREAUTHINFO");
							mtd.setDataName("PREAUTHPAIDDATE".toUpperCase());
							mtd.setDataValue(parsedData[20]);
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);
						}

						// DONATION BILLERS (providers info)
						if (parsedData[23].trim().length() != 0) {
							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PROVIDERSINFO");
							mtd.setDataName("mid".toUpperCase());
							mtd.setDataValue(parsedData[21].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PROVIDERSINFO");
							mtd.setDataName("tid".toUpperCase());
							mtd.setDataValue(parsedData[22].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PROVIDERSINFO");
							mtd.setDataName("providersId".toUpperCase());
							mtd.setDataValue(parsedData[23].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);

							mtd = new MerchantTerminalDetail();
							mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							mtd.setMid(mid);
							mtd.setTid(tid);
							mtd.setType("TRANSACTION-PROVIDERSINFO");
							mtd.setDataName("type".toUpperCase());
							mtd.setDataValue(parsedData[24].toUpperCase());
							mtd.setTxnLogsId(txnLogId);
							lst.add(mtd);
						}

						// ADDITIONAL-INFO
						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("CPUMemory".toUpperCase());
						mtd.setDataValue(cpuMemory.toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("Battery".toUpperCase());
						mtd.setDataValue(battery.toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("OS".toUpperCase());
						mtd.setDataValue(os.toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("APPVersion".toUpperCase());
						mtd.setDataValue(appVersion.toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("MERCHANTNAME".toUpperCase());
						mtd.setDataValue(fieldTwo.toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("HEADER_1".toUpperCase());
						mtd.setDataValue(header1.toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("HEADER_2".toUpperCase());
						mtd.setDataValue(header2.toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("HEADER_3".toUpperCase());
						mtd.setDataValue(header3.toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("HEADER_4".toUpperCase());
						mtd.setDataValue(header4.toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("SAF".toUpperCase());
						mtd.setDataValue("Y".toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("MARKET_SEGMENT".toUpperCase());
						mtd.setDataValue(parsedData[31].toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("ECR_AMOUNT".toUpperCase());
						mtd.setDataValue(parsedData[32].toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("ECR_DUE_DATE".toUpperCase());
						mtd.setDataValue(parsedData[33].toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("ECR_CONSUMER_NO".toUpperCase());
						mtd.setDataValue(parsedData[34].toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						mtd = new MerchantTerminalDetail();
						mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						mtd.setMid(mid);
						mtd.setTid(tid);
						mtd.setType("TRANSACTION-ADDINFO");
						mtd.setDataName("ECR_LOG_ID".toUpperCase());
						mtd.setDataValue(parsedData[35].toUpperCase());
						mtd.setTxnLogsId(txnLogId);
						lst.add(mtd);

						utilService.saveToMerchantTerminalDetails(lst);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			fieldSix = e.getMessage() != null ? e.getMessage() : "Something Went Wrong.";
		} finally {

			TxnLog txnLog = insertTxnLog(AppProp.getProperty("mqtt.heartbeat"), "Mqtt Heart Beat : " + "Success", mid,
					tid, model, serialNumber, null, imei, telco, longitude, latitude, fieldOne, fieldTwo, fieldThree,
					fieldFour, fieldFive, fieldSix, signalStrength, firmware, cpuMemory, battery, os, appVersion);

			txnLogsService.insertLog(txnLog);

			res.put("code", "0000");
			res.put("message", "Success.");

			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(new Timestamp(new Date().getTime()));
			adt.setRequest(requestService);
			adt.setResponse(res.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("MQTT-HERTBEAT");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serialNumber);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}

		AgLogger.logInfo("MQTT HEARBEAT RESPONSE | " + res.toString());
		return res;

	}

	TxnLog insertTxnLog(String activityType, String activityRemarks, String mid, String tid, String model,
			String serialNumber, String posDateTime, String imei, String telco, String longitude, String latitude,
			String fieldOne, String fielTwo, String fieldThree, String fieldFour, String fieldFive, String fieldSix,
			String signalStrength, String firmware, String cpuMemory, String battery, String os, String appVersion) {
		TxnLog txn = new TxnLog();
		txn.setActivityRemarks(activityRemarks);
		txn.setActivityType(activityType);
		txn.setActivityDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		txn.setMerchantId(mid);
		txn.setModel(model);
		txn.setScheme("-");
		txn.setSerialNumber(serialNumber);
		txn.setTerminalId(tid);
		txn.setPosdatetime(posDateTime);
		txn.setImei(imei);
		txn.setTelco(telco);
		txn.setLongitude(longitude);
		txn.setLatitude(latitude);
		txn.setFieldone(fieldOne);
		txn.setFieldtwo(fielTwo);
		txn.setFieldthree(fieldThree);
		txn.setFieldfour(fieldFour);
		txn.setFieldfive(fieldFive);
		txn.setFieldsix(fieldSix);
		txn.setSignalStrength(signalStrength);
		txn.setFirmware(firmware);
		txn.setCpuMemory(cpuMemory);
		txn.setBattery(battery);
		txn.setOs(os);
		txn.setAppVersion(appVersion);

		AgLogger.logInfo("TXN LOGS SAVED " + mid + "|" + tid + "|" + activityType);
		return txn;

	}

	String[] responseServiceTxn(String[] dataRecord, String mid, String tid) {

		String[] kl = new String[3];
		kl[0] = "0000";
		kl[1] = "SUCCESS";
		kl[2] = "0";

		String TType = "";

		String cardNumber = null, merchantID = null, terminalID = null, txnID = null, amount = null, response = "0000",
				status = "SUCCESS", type = null, settled = "N", paid = "N", reversal = "N", batchNumber = null,
				finResult = "SUCCESS", refNumber = null, ccType = "N/A", msg = null, authID = null, orignalRef = "N/A",
				invoiceNumber = null, postMode = null, model = null, cardsheme = null, serialNumber = null,
				authIDN = null, fieldOne = null, reason = "N/A", tipAmount = "0.00", adjAmount = "0.00",
				quantityType = null, prdName = null, prdCode = null, tvr = null, aid = null, customerName = null,
				cardType = null;

		Date dfs = new java.util.Date();
		try {

			Date cardExpiry = null;

			int rec = 0, custId = 0, quantity = 0;

			String dateTime = "";

			try {
				cardNumber = dataRecord[0];// bin
			} catch (Exception e) {
			}

			try {
				authIDN = dataRecord[2];
			} catch (Exception e) {
			}

			try {
				fieldOne = dataRecord[1];
			} catch (Exception e) {
			}

			try {
				amount = dataRecord[3];
			} catch (Exception e) {
			}

			try {
				merchantID = mid;
			} catch (Exception e) {
			}

			try {
				terminalID = tid;
			} catch (Exception e) {
			}

			int year = Calendar.getInstance().get(Calendar.YEAR);
			try {
				dateTime += dataRecord[4] + year;// txnDate
			} catch (Exception e) {
				dateTime += "0101" + year;
			}

			try {
				dateTime += dataRecord[5];
			} catch (Exception e) {
				dateTime += "000000";
			}

			try {
				postMode = dataRecord[6];
			} catch (Exception e) {
			}

			try {
				batchNumber = dataRecord[7];
			} catch (Exception e) {
			}

			try {
				refNumber = dataRecord[8];// rrn
			} catch (Exception e) {
			}

			try {
				invoiceNumber = dataRecord[25];
			} catch (Exception e) {
			}

			try {
				authID = dataRecord[9];
			} catch (Exception e) {
			}

			try {
				type = dataRecord[10];
				TType = type;
			} catch (Exception e) {
			}

			try {
				msg = dataRecord[36];
			} catch (Exception e) {
			}

			try {
				model = dataRecord[37];
			} catch (Exception e) {
			}

			try {
				cardsheme = dataRecord[11];
			} catch (Exception e) {
			}

			try {
				serialNumber = dataRecord[38];
			} catch (Exception e) {
			}

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmss");
				dfs = sdf.parse(dateTime);
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);
			}

			try {
				String tempStatus = "SUCCESS";
				status = Objects.isNull(tempStatus) || tempStatus.isEmpty() ? "SUCCESS" : tempStatus;
			} catch (Exception e) {
			}

			try {
				String tempReason = "N/A";
				reason = Objects.isNull(tempReason) || tempReason.isEmpty() ? "N/A" : tempReason;
			} catch (Exception e) {
			}

			try {
				String tempTipAmt = dataRecord[12];
				tipAmount = Objects.isNull(tempTipAmt) || tempTipAmt.isEmpty() ? "0.00" : tempTipAmt;
			} catch (Exception e) {
			}

			try {
				String tempAdjAmount = dataRecord[13];
				adjAmount = Objects.isNull(tempAdjAmount) || tempAdjAmount.isEmpty() ? "0.00" : tempAdjAmount;
			} catch (Exception e) {
			}

			try {
				String tempQuantity = "N/A";
				quantity = Objects.isNull(tempQuantity) || tempQuantity.isEmpty() ? 0 : Integer.parseInt(tempQuantity);
			} catch (Exception e) {
			}

			try {
				String tempQuantityType = "N/A";
				quantityType = Objects.isNull(tempQuantityType) || tempQuantityType.isEmpty() ? null : tempQuantityType;
			} catch (Exception e) {
			}

			try {
				String tempPrdName = "N/A";
				prdName = Objects.isNull(tempPrdName) || tempPrdName.isEmpty() ? null : tempPrdName;
			} catch (Exception e) {
			}

			try {
				String tempPrdCode = "N/A";
				prdCode = Objects.isNull(tempPrdCode) || tempPrdCode.isEmpty() ? null : tempPrdCode;
			} catch (Exception e) {
			}

			try {
				String tempTvr = dataRecord[14];
				tvr = Objects.isNull(tempTvr) || tempTvr.isEmpty() ? null : tempTvr;
			} catch (Exception e) {
			}

			try {
				String tempAid = dataRecord[15];
				aid = Objects.isNull(tempAid) || tempAid.isEmpty() ? null : tempAid;
			} catch (Exception e) {
			}

			try {
				String tempCustomerName = dataRecord[26];
				customerName = Objects.isNull(tempCustomerName) || tempCustomerName.isEmpty() ? null : tempCustomerName;
			} catch (Exception e) {
			}

			try {
				String tempCardType = dataRecord[16];
				cardType = Objects.isNull(tempCardType) || tempCardType.isEmpty() ? null : tempCardType;
			} catch (Exception e) {
			}

			try {
				String tempCardExpiry = dataRecord[17];
				SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");

				cardExpiry = sdf.parse(tempCardExpiry);
			} catch (Exception e) {
				// AgLogger.logerror(getClass(), "Exception Card Expiry....", e);
			}

			String curreny = "PKR";
			boolean isExist = txnDetailsService.checkExistanceOfTxnDetail(merchantID, terminalID, batchNumber,
					refNumber, authID, type);

			if (!isExist) {
				AgLogger.logInfo(
						"ServiceTxn TXN NOT EXISTS -> MID:" + merchantID + "| TID:" + terminalID + "| BatchNumber:"
								+ batchNumber + "| RRN:" + refNumber + "| AuthID:" + authID + "| Type:" + type);
				UserLogin userMdlRequest = userLoginService.validetUserWithoutCorpId(merchantID);
				if (!Objects.isNull(userMdlRequest)) {
					List<UserSetting> lstUserSetting = userSettingService
							.fetchSettingByUserLoginId(userMdlRequest.getUserId());
					if (lstUserSetting.size() != 0) {
						HashMap<String, String> mapUserSetting = UtilAccess.lstToMapUserSetting(lstUserSetting);

						Double mdrOffus = UtilAccess.calcTxnMdrOffus(Double.valueOf(amount),
								!Objects.isNull(mapUserSetting.get("MDR_OFF_US"))
										? Double.valueOf(mapUserSetting.get("MDR_OFF_US"))
										: 0.0,
								!Objects.isNull(mapUserSetting.get("MDR_ON_US"))
										? Double.valueOf(mapUserSetting.get("MDR_ON_US"))
										: 0.0,
								!Objects.isNull(mapUserSetting.get("FED_RATES"))
										? Double.valueOf(mapUserSetting.get("FED_RATES"))
										: 0.0);

						Double mdrOnnus = UtilAccess.calcTxnMdrOnus(Double.valueOf(amount),
								!Objects.isNull(mapUserSetting.get("MDR_OFF_US"))
										? Double.valueOf(mapUserSetting.get("MDR_OFF_US"))
										: 0.0,
								!Objects.isNull(mapUserSetting.get("MDR_ON_US"))
										? Double.valueOf(mapUserSetting.get("MDR_ON_US"))
										: 0.0,
								!Objects.isNull(mapUserSetting.get("FED_RATES"))
										? Double.valueOf(mapUserSetting.get("FED_RATES"))
										: 0.0);

						Double fedRate = UtilAccess.calcTxnFed(Double.valueOf(amount), mdrOffus, mdrOnnus,
								!Objects.isNull(mapUserSetting.get("FED_RATES"))
										? Double.valueOf(mapUserSetting.get("FED_RATES"))
										: 0.0);

						Double netAmount = UtilAccess.calcTxnNetAmount(Double.valueOf(amount), mdrOffus, mdrOnnus,
								fedRate);

						TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(cardNumber, merchantID, terminalID, txnID,
								amount, response, status, type, settled, paid, reversal, batchNumber, finResult,
								refNumber, ccType, rec, msg, authID, orignalRef, invoiceNumber, postMode, custId, dfs,
								model, cardsheme, serialNumber, authIDN, fieldOne, reason, tipAmount, adjAmount,
								quantityType, prdName, prdCode, quantity, netAmount, mdrOnnus, mdrOffus, fedRate,
								curreny, tvr, aid, cardExpiry, cardType, customerName);

						entityManager.persist(txnLogCustId);
						int id = txnLogCustId.getId();

						kl[2] = String.valueOf(id);
					} else {
						TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(cardNumber, merchantID, terminalID, txnID,
								amount, response, status, type, settled, paid, reversal, batchNumber, finResult,
								refNumber, ccType, rec, msg, authID, orignalRef, invoiceNumber, postMode, custId, dfs,
								model, cardsheme, serialNumber, authIDN, fieldOne, reason, tipAmount, adjAmount,
								quantityType, prdName, prdCode, quantity, Double.valueOf(amount), 0.0, 0.0, 0.0,
								curreny, tvr, aid, cardExpiry, cardType, customerName);
						entityManager.persist(txnLogCustId);
						int id = txnLogCustId.getId();

						kl[2] = String.valueOf(id);
					}

				} else {
					TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(cardNumber, merchantID, terminalID, txnID,
							amount, response, status, type, settled, paid, reversal, batchNumber, finResult, refNumber,
							ccType, rec, msg, authID, orignalRef, invoiceNumber, postMode, custId, dfs, model,
							cardsheme, serialNumber, authIDN, fieldOne, reason, tipAmount, adjAmount, quantityType,
							prdName, prdCode, quantity, Double.valueOf(amount), 0.0, 0.0, 0.0, curreny, tvr, aid,
							cardExpiry, cardType, customerName);
					entityManager.persist(txnLogCustId);
					int id = txnLogCustId.getId();

					kl[2] = String.valueOf(id);
				}

			} else {
				AgLogger.logInfo("ServiceTxn TXN EXISTS -> MID " + merchantID + "| TID " + terminalID + "| BN "
						+ batchNumber + "| RN" + refNumber + "| AuthID " + authID + "| Type" + type);
				TxnLogError txnLog = UtilAccess.doLogForNonExistTxnRecord(cardNumber, merchantID, terminalID, txnID,
						amount, response, status, type, settled, paid, reversal, batchNumber, finResult, refNumber,
						ccType, rec, msg, authID, orignalRef, invoiceNumber, postMode, custId, dfs, model, cardsheme,
						serialNumber, authIDN, fieldOne, reason, tipAmount, adjAmount, quantityType, prdName, prdCode,
						quantity, tvr, aid, cardExpiry, cardType, customerName);
				entityManager.persist(txnLog);
			}

			AgLogger.logInfo(kl[0] + "@@@@" + TType);
			if (kl[0].equals("0000") && TType.equals("COMPLETION")) {
				TxnDetail tds = txnDetailsService.fetchTxnDetailPreAuth(merchantID, terminalID, authIDN);
				if (tds != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					txnDetailsService.updateRecordPreAuth(tds.getId() + "", "Y", sdf.format(dfs));
				} else {
					AgLogger.logInfo("No Record Found against " + merchantID + "|" + terminalID + "|" + authIDN);
				}

			}

			AgLogger.logInfo(kl[0] + "@@@@" + TType);
			if (kl[0].equals("0000") && TType.equals("VOID")) {
				AgLogger.logInfo(kl[0] + "@@@@ VOID TXN" + TType);
				TxnDetail tds = txnDetailsService.fetchTxnDetailVoidTxn(merchantID, terminalID, authIDN);
				if (tds != null) {
					txnDetailsService.updateRecordPreAuth(tds.getId() + "", "N", "00-00-0000 00:00:00");
				} else {
					AgLogger.logInfo("No Record Found against " + merchantID + "|" + terminalID + "|" + authIDN);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			kl[0] = "9999";
			kl[1] = e.getMessage();
			kl[2] = "0";
		}

		return kl;
	}

}
