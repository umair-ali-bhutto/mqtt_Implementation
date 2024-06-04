package com.ag.pos.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.DiscountBinProdConfig;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.services.DiscountBinProdConfigService;
import com.ag.mportal.services.MerchantTerminalDetailsService;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.services.TxnLogsService;
import com.ag.mportal.services.impl.MerConfMasterServiceImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class ProcessHeartBeat {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	TxnLogsService txnLogsService;

	@Autowired
	TxnDetailsService txnDetailsService;

	@Autowired
	UtilService utilService;

	@Autowired
	MerConfMasterServiceImpl merConfMasterServiceImpl;

	@Autowired
	MerchantTerminalDetailsService merchantTerminalDetailsService;

	@Autowired
	DiscountBinProdConfigService discountBinProdConfigService;

	@SuppressWarnings("unchecked")
	@PostMapping({ "/heartbeat", "/baflheartbeat" })
	@Transactional
	public JSONObject doProcessHeartBeat(@RequestBody String requestService, HttpServletRequest request) {
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();
		String userName = "";
		String password = "";
		String mid = "N/A";
		String tid = "N/A";
		String Serial = "N/A";
		String model = "";
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

		try {
			if (requestService.length() != 0) {
				AgLogger.logInfo("Request HeartBeat | " + requestService);
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);
				userName = (String) onj.get("UserName");
				password = (String) onj.get("PASSWORD");
				mid = (String) onj.get("MID");
				tid = (String) onj.get("TID");
				Serial = (String) onj.get("SerialNumber");
				model = (String) onj.get("Model");

				UserLogin user = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(user)) {
					JSONArray jArray = new JSONArray();

					List<String> types = new ArrayList<String>();

					types.add("OFFLINE_SALE");
					types.add("BUILD_CONTROL");
					types.add("DONATION");
					types.add("R1_R2");
					HashMap<String, HashMap<String, String>> dataTemp = merConfMasterServiceImpl
							.fetchRecordForHeartBeat(mid, tid, types);

					jArray.add(createDataObject("BATCHNO", fetchBatchNo(mid, tid)));
					jArray.add(createDataObject("ADVVER", fetchAddv(mid, tid)));
					jArray.add(createDataObject("ADVDWN", fetchAddvDwn(mid, tid)));
					jArray.add(createDataObject("HBSCHEDULER", fetchHeartBeatSchedular(mid, tid)));
					jArray.add(createDataObject("HBSCHEDULERT", fetchHeartBeatSchedularTime(mid, tid)));
					jArray.add(createDataObject("OSTMAXA", fetchOfflineSaleMaxAmount(mid, tid, dataTemp)));
					jArray.add(createDataObject("OSTMINA", fetchOfflineSaleMinAmount(mid, tid, dataTemp)));
					jArray.add(createDataObject("OSTC", fetchOfflineSaleTxnCount(mid, tid, dataTemp)));
					jArray.add(createDataObject("DONATION_CARD_RANGES", fetchDonationCardRanges(mid, tid, dataTemp)));
					jArray.add(createDataObject("DONATION_COUNT", fetchDonationCount(mid, tid, dataTemp)));
					jArray.add(createDataObject("DONATION_MAX_AMOUNT", fetchDonationMaxAmount(mid, tid, dataTemp)));
					jArray.add(createDataObject("DONATION_MIN_AMOUNT", fetchDonationMinAmount(mid, tid, dataTemp)));
					jArray.add(createDataObject("BUILD_CONTROL", fetchBuildControl(mid, tid, dataTemp, model)));
					jArray.add(createDataObject("DISCOUNT_BIN", fetchDiscountBin(model, user.getCorpId())));

					jArray.add(createDataObject("R1_DEBIT_THRESHOLD", fetchR1DebitThreshold(mid, tid, dataTemp)));
					jArray.add(createDataObject("R1_CREDIT_THRESHOLD", fetchR1CreditThreshold(mid, tid, dataTemp)));
					jArray.add(createDataObject("R2_DEBIT_THRESHOLD", fetchR2DebitThreshold(mid, tid, dataTemp)));
					jArray.add(createDataObject("R2_CREDIT_THRESHOLD", fetchR2CreditThreshold(mid, tid, dataTemp)));

					job.put("code", "0000");
					job.put("msg", "Success");
					job.put("data", jArray);

					AgLogger.logInfo("RESPONSE HeartBeat | " + job.toString());
				} else {
					job.put("code", "1111");
					job.put("msg", "Credential Not Found");
				}

			} else {
				job.put("code", "1111");
				job.put("msg", "Invalid Credentials");
			}
		} catch (Exception e) {
			e.printStackTrace();
			job.put("code", "1111");
			job.put("msg", "Exception.");
		} finally {
			java.util.Date date = new java.util.Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("heartbeat");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(Serial);
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			TxnLog txnLog = UtilAccess.insertTxnLogs(AppProp.getProperty("act.heartbeat"), "Heart Beat : " + "Success",
					mid, tid, model, Serial, null, imei, telco, longitude, latitude, fieldOne, fielTwo, fieldThree,
					fieldFour, fieldFive, fieldSix);

			txnLogsService.insertLog(txnLog);
			Long l = txnLog.getId();
			Integer id = l.intValue();

			List<MerchantTerminalDetail> tdLst = txnDetailsService.convertTxnDetails(requestService, id);
			if (tdLst.size() != 0) {
				utilService.saveToMerchantTerminalDetails(tdLst);
//				for (MerchantTerminalDetail mtd : tdLst) {
//					if (mtd.getType().equals("TRANSACTION-ADDINFO")) {
//						if (mtd.getDataName().equals("BATCHNUMBER")) {
//							kl[2] = mtd.getDataValue();
//						}
//						if (mtd.getDataName().equals("EVENT")) {
//							kl[3] = mtd.getDataValue();
//						}
//						if (mtd.getDataName().equals("ADVERTISMENTNUMBER")) {
//							kl[4] = mtd.getDataValue();
//						}
//					}
//				}
			}

		}
		return job;
	}

	@SuppressWarnings("unchecked")
	private static JSONObject createDataObject(String key, String value) {
		JSONObject dataObject = new JSONObject();
		dataObject.put(key, value);
		return dataObject;
	}

	// ---- CONTROLLS VALUE
	private String fetchBatchNo(String mid, String tid) {
		String defaultValue = (!AppProp.getProperty("hb.default.batch.no").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.batch.no")
				: "000021";
		String result = "";
		try {
			// Query
			String s = merConfMasterServiceImpl.fetchRecordForHeartBeatBatch(mid, tid);
			result = (s != null) ? s : defaultValue;
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchAddv(String mid, String tid) {
		String defaultValue = (!AppProp.getProperty("hb.default.addv").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.addv")
				: "1.9";
		String result = "";
		try {
			// Query
			result = "1.9";
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchAddvDwn(String mid, String tid) {
		String defaultValue = (!AppProp.getProperty("hb.default.addv.down").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.addv.down")
				: "Y";
		String result = "";
		try {
			// Query
			result = "Y";
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchHeartBeatSchedular(String mid, String tid) {
		String defaultValue = (!AppProp.getProperty("hb.default.schedular").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.schedular")
				: "Y";
		String result = "";
		try {
			// Query
			result = "Y";
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchHeartBeatSchedularTime(String mid, String tid) {
		String defaultValue = (!AppProp.getProperty("hb.default.schedular.time").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.schedular.time")
				: "1020000";
		String result = "";
		try {
			// Query
			result = "1020000";
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchBuildControl(String mid, String tid, HashMap<String, HashMap<String, String>> details,
			String model) {

		String result = "";

		if (model.startsWith("V2")) {
			String defaultValue = (!AppProp.getProperty("hb.default.build.control.v2").equalsIgnoreCase("N/A"))
					? AppProp.getProperty("hb.default.build.control.v2")
					: "18";
			try {
				String key = "BUILD_CONTROL";
				if (details.containsKey(key)) {
					HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
					for (Map.Entry<String, String> set : dataSet.entrySet()) {
						result += set.getValue() + ",";
					}
					result = result.substring(0, result.length() - 1) + ",18";
				} else {
					result = defaultValue;
				}
			} catch (Exception e) {
				result = defaultValue;
			}

			result = result.replace(",", "-");

		} else {
			String defaultValue = (!AppProp.getProperty("hb.default.build.control").equalsIgnoreCase("N/A"))
					? AppProp.getProperty("hb.default.build.control")
					: "PROFILE_DOWNLOAD,TMS_IP,MARKET_SEG,COM_WIFI,COM_GPRS";
			try {
				String key = "BUILD_CONTROL";
				if (details.containsKey(key)) {
					HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
					for (Map.Entry<String, String> set : dataSet.entrySet()) {
						result += set.getValue() + ",";
					}
					result = result.substring(0, result.length() - 1) + ",PROFILE_DOWNLOAD,COM_WIFI,COM_GPRS";
				} else {
					result = defaultValue;
				}
			} catch (Exception e) {
				result = defaultValue;
			}
		}
		return result;
	}

	private String fetchOfflineSaleMaxAmount(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("hb.default.offline.max.amount").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.offline.max.amount")
				: "5000";
		String result = "";
		try {
			String key = "OFFLINE_SALE";
			String subKey = "MAX_AMOUNT_THRESHOLD";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchOfflineSaleMinAmount(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("hb.default.offline.min.amount").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.offline.min.amount")
				: "0.10";
		String result = "";
		try {
			String key = "OFFLINE_SALE";
			String subKey = "MIN_AMOUNT_THRESHOLD";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchOfflineSaleTxnCount(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("hb.default.offline.txn.count").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.offline.txn.count")
				: "5";
		String result = "";
		try {
			String key = "OFFLINE_SALE";
			String subKey = "THRESHOLD_COUNT";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchR1DebitThreshold(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("r1.debit.threshold").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("r1.debit.threshold")
				: "50000";
		String result = "";
		try {
			String key = "R1_R2";
			String subKey = "R1_DEBIT_THRESHOLD";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchR1CreditThreshold(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("r1.credit.threshold").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("r1.credit.threshold")
				: "50000";
		String result = "";
		try {
			String key = "R1_R2";
			String subKey = "R1_CREDIT_THRESHOLD";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchR2DebitThreshold(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("r2.debit.threshold").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("r2.debit.threshold")
				: "500000";
		String result = "";
		try {
			String key = "R1_R2";
			String subKey = "R2_DEBIT_THRESHOLD";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchR2CreditThreshold(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("r2.credit.threshold").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("r2.credit.threshold")
				: "500000";
		String result = "";
		try {
			String key = "R1_R2";
			String subKey = "R2_CREDIT_THRESHOLD";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchDonationCardRanges(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("hb.default.donation.card.ranges").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.donation.card.ranges")
				: "3799330000:3799339999,4025810000:4025819999,4025820000:4025829999,4220700000:4220709999,4221100000:4221109999,4221780000:4221789999,4649540000:4649549999,4862480000:4862489999,5239690000:5239699999,4025837000:4025837999,4027937000:4027937999,4213397000:4213397999,2205497000:2205497999,4660627000:4660627999,4862477000:4862477999,4202517000:4202517999,6271000000:6271009999,4617587000:4617587999,6234020000:6234029999,6234030000:6234039999";
		String result = "";
		try {
			String key = "DONATION";
			String subKey = "DONATION_CARD_RANGES";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchDonationCount(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("hb.default.donation.count").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.donation.count")
				: "5";
		String result = "";
		try {
			String key = "DONATION";
			String subKey = "DONATION_COUNT";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchDonationMaxAmount(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("hb.default.donation.max.amount").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.donation.max.amount")
				: "20000";
		String result = "";
		try {
			String key = "DONATION";
			String subKey = "DONATION_MAX_AMOUNT";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchDonationMinAmount(String mid, String tid, HashMap<String, HashMap<String, String>> details) {
		String defaultValue = (!AppProp.getProperty("hb.default.donation.min.amount").equalsIgnoreCase("N/A"))
				? AppProp.getProperty("hb.default.donation.min.amount")
				: "10";
		String result = "";
		try {
			String key = "DONATION";
			String subKey = "DONATION_MIN_AMOUNT";
			if (details.containsKey(key)) {
				HashMap<String, String> dataSet = (HashMap<String, String>) details.get(key);
				result = (dataSet.containsKey(subKey)) ? dataSet.get(subKey) : defaultValue;
			} else {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	private String fetchDiscountBin(String model, String corpId) {
		String result = "";

		if (model.startsWith("X9")) {
			try {
				List<DiscountBinProdConfig> lst = discountBinProdConfigService.fetchByCorpId(corpId);
				if (lst == null || lst.isEmpty()) {
					result = "-";
				} else {
					JsonArray jsonArray = new JsonArray();
					for (DiscountBinProdConfig item : lst) {
						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("bin", item.getBin());
						jsonObject.addProperty("productId", item.getProductId());
						jsonObject.addProperty("productValue", item.getProductValue());
						jsonArray.add(jsonObject);
					}
					result = jsonArray.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// return result;
		}
		return result;
	}

}