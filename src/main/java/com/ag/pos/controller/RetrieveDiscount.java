package com.ag.pos.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.discount.BAFLDiscount;
import com.ag.mportal.discount.GenericDiscount;
import com.ag.mportal.entity.DiscountLog;
import com.ag.mportal.services.DiscountLogService;
import com.ag.mportal.util.DiscountUtil;
import com.google.gson.Gson;

@RestController
public class RetrieveDiscount {

	@Autowired
	DiscountLogService discountLogService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	BAFLDiscount BAFLDiscount;

	@Autowired
	GenericDiscount GenericDiscount;

	long discId = 0;
	double discAmount = 0;
	double netAmount = 0;
	double discRate = 0;

	@SuppressWarnings("unchecked")
	@PostMapping("/retrieveDiscountRate")
	public JSONObject retrieveDiscountRate(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {

		JSONObject req = (JSONObject) JSONValue.parse(requestService);
		AgLogger.logInfo("RETRIEVE DISCOUNT REQUEST:" + req.toString());
		JSONObject job = new JSONObject();
		Timestamp transactionTime = null;
		String txnAmount = "0.0";
		String mid = "";
		String tid = "";
		String cardNumberMasked = "";
		String productId = "";
		String serialNumber = "";
		String bin = "";
		String currency = "";
		String status = "";

		try {
			String corpId = req.get("CorpID").toString();
			currency = DiscountUtil.getCurrencyFromCode("PKR"); // currency
			status = DiscountUtil.getDiscountStatusFromStatus("ACTIVE"); // status
			String txnDate = req.get("TxnDate").toString();
			String txnTime = req.get("TxnTime").toString();

			Date date = new java.util.Date();
			String dateTime = "";
			int year = Calendar.getInstance().get(Calendar.YEAR);
			try {
				dateTime += txnDate + year;
			} catch (Exception e) {
				dateTime += "0101" + year;
			}

			try {
				dateTime += txnTime;
			} catch (Exception e) {
				dateTime += "000000";
			}
			try {
				date = new SimpleDateFormat("MMddyyyyHHmmss").parse(dateTime);
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);
			}

			transactionTime = new Timestamp(date.getTime());
			txnAmount = req.get("Amount").toString();
			mid = req.get("MID").toString();
			tid = req.get("TID").toString();
			cardNumberMasked = req.get("FieldOne").toString();
			productId = req.get("ProductID").toString();
			serialNumber = req.get("SerialNumber").toString();
			bin = req.get("Bin").toString();
			String userName = req.get("UserName").toString();
			String password = req.get("PASSWORD").toString();

			UserLogin userMdl = userLoginService.validateUserPassword(userName, password);
			if (!Objects.isNull(userMdl)) {
				ResponseModel rm;
				String corpIdUser = userMdl.getCorpId();

				switch (corpId) {
				case "00004":
					AgLogger.logInfo("BAFL DISCOUNT.");
					rm = BAFLDiscount.processDiscount(serialNumber, corpIdUser, txnAmount, mid, tid, cardNumberMasked,
							productId, bin, currency, status, transactionTime, txnDate);
					break;

				default:
					AgLogger.logInfo("GENERIC DISCOUNT.");
					rm = GenericDiscount.processDiscount(serialNumber, corpIdUser, txnAmount, mid, tid,
							cardNumberMasked, productId, bin, currency, status, transactionTime, txnDate);
					break;
				}

				job = parseDiscount(rm);

			} else {

				job.put("code", "9999");
				job.put("message", "User Validation Failed.");
			}

		} catch (Exception e) {
			e.printStackTrace();

			job.put("code", "9999");
			job.put("message", "Something Went Wrong");
		} finally {
			if (job.get("code").toString().equals("0000")) {
				DiscountLog log = new DiscountLog();
				log.setDiscId(discId);
				log.setTxnDate(transactionTime);
				log.setTxnAmount(Double.parseDouble(txnAmount));
				log.setCid(cardNumberMasked);
				log.setCidType(null);
				log.setDiscountAmount(discAmount);
				log.setDiscountAvailed(0);
				log.setNetAmount(netAmount);
				log.setMid(mid);
				log.setTid(tid);
				log.setBatchNumber(null);
				log.setInvoiceNumber("");
				log.setRrn(null);
				log.setTxnId(0);
				log.setMerchantBased(0);
				log.setEntryOn(new Timestamp(new Date().getTime()));
				log.setSerialNumber(serialNumber);
				log.setTxnCurrency(Integer.parseInt(currency));
				log.setDiscRate(discRate);
				long logId = discountLogService.insert(log);

				job.put("recId", logId);

			}

		}
		return job;
	}

	@SuppressWarnings("unchecked")
	private JSONObject parseDiscount(ResponseModel rm) {
		AgLogger.logInfo(new Gson().toJson(rm));
		JSONObject j = new JSONObject();
		if (rm.getCode().equals("0000")) {
			j.put("code", rm.getCode());
			j.put("message", rm.getMessage());

			j.put("discountName", rm.getData().get("discountName").toString());
			j.put("netAmount", rm.getData().get("netAmount").toString());

			j.put("discountAmnt", rm.getData().get("discountAmnt").toString());
			j.put("discountPercentage", rm.getData().get("discountPercentage").toString());
			j.put("discId", rm.getData().get("discId").toString());
			discId = Long.parseLong(rm.getData().get("discId").toString());
			discAmount = Double.parseDouble(rm.getData().get("discountAmnt").toString());
			netAmount = Double.parseDouble(rm.getData().get("netAmount").toString());
			//discRate =  Double.parseDouble(rm.getData().get("discountPercentage").toString());
			if (rm.getData().get("discountPercentage").toString().equals("N/A")) {
			    discRate = 0;
			} else {
			    discRate = Double.parseDouble(rm.getData().get("discountPercentage").toString());
			}
			

		} else {
			j.put("code", rm.getCode());
			j.put("message", rm.getMessage());
		}
		return j;
	}

}
