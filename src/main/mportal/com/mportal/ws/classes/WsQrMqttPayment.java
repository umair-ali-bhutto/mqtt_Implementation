package com.mportal.ws.classes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.main.qr.DecodeEmvQr;
import org.main.qr.EmvQrModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresLoyaltyEngine;
import com.ag.db.proc.ProcPerfromTransactionModel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.MqttUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.loy.adm.entity.CustomerUserMaster;
import com.ag.loy.adm.entity.UserStatusMaster;
import com.ag.loy.adm.service.CustomerUserMasterService;
import com.ag.loy.adm.service.UserStatusService;
import com.ag.mportal.entity.QrPayments;
import com.ag.mportal.services.QrPaymentsService;

@Component("com.mportal.ws.classes.WsQrMqttPayment")
public class WsQrMqttPayment implements Wisher {

	@Autowired
	private UtilService utilService;
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	QrPaymentsService qrPaymentsService;
	@Autowired
	UserSettingService userSettingService;
	@Autowired
	CustomerUserMasterService customerUserMasterService;
	@Autowired
	UserStatusService userStatusService;

	@SuppressWarnings("unchecked")
	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		UserLogin sender = null;
		UserLogin receiver = null;

		try {
			String user = rm.getUserid();
			String Qr = (String) rm.getAdditionalData().get("desc");

			AgLogger.logInfo("QR REQUEST RECIEVED @@@@ " + Qr);

			String[] val = validateQR(Qr);
			EmvQrModel mod = new DecodeEmvQr().getGeneratedQr(Qr);

			// 000201010212520441115303586540436.05802PK5913X990 EMV
			// Demo604402|AWARDS|22-09-2023
			// 12:15|121436|SIF|0003566249504501159999999999999990208999999990310V1E024707598380016Digital
			// Pass PAN01149876543211234563041119

			AgLogger.logInfo("@@MID: " + mod.getMid());
			AgLogger.logInfo("@@TID: " + mod.getTid());

			String txnAmt = mod.getTransactionAmount();
			sender = userLoginService.validetUserid(Integer.parseInt(user));

			// UMAIR
			receiver = userLoginService.validetUserWithoutCorpId(mod.getMid());
			// receiver = userLoginService.validetUserWithoutCorpId("999999999999999");

			AgLogger.logInfo(val[0].equals("0000") + "|" + receiver);
			if (val[0].equals("0000") && receiver != null) {

				CustomerUserMaster c = customerUserMasterService.findAllByUidCorpId(sender.getUserCode(),
						sender.getCorpId());

				if (c != null) {

					UserStatusMaster usm = userStatusService.fetchByUsertype(sender.getCorpId(), c.getUsertype());

					if (usm != null && c.getStatus().equals(usm.getId().getStatus())) {

						String senderName = sender.getUserName();
						String receiverName = receiver.getUserName();
						String pCorpID = sender.getCorpId();

						String corpId = sender.getCorpId();

						String pProcCode = AppProp.getProperty("pProcCode." + corpId);
						String pPID = AppProp.getProperty("pPID." + corpId);
						String pFID = AppProp.getProperty("pFID." + corpId);
						String pCHID = AppProp.getProperty("pCHID." + corpId);

						// UMAIR
//						String pMID = mod.getMid();
//						String pTID = mod.getTid();

						String pMID = "999999999999999";
						String pTID = "99999999";

						String serialNum = mod.getSerialNumber();
						Date pTxnDate = new Date(new java.util.Date().getTime());

						AgLogger.logInfo("merchantCity:" + mod.getMerchantCity());
						String test = mod.getMerchantCity();

						// UMAIR
//						String[] recievedData = test.split("\\|");
						String[] recievedData = test.split("\\!");

						String prodId = recievedData[0];
						String type = recievedData[1];
						String qrExpiry = recievedData[2];
						String invoiceNumber = recievedData[3];
						String issuer = recievedData[4];
						String batchNumber = recievedData[5];

						Float pTxnAmount = 0f;

						AgLogger.logInfo("Initiation Method:" + mod.getPointOfInitiationMethod() + "|Txn Amount:"
								+ mod.getTransactionAmount());

						// UMAIR
//						if (mod.getPointOfInitiationMethod().equals("12")) {
//							pTxnAmount = Float.parseFloat(mod.getTransactionAmount());
//						} else {
//							pTxnAmount = Float.parseFloat(AppProp.getProperty("pTxnAmount." + corpId));
//						}

						AgLogger.logInfo("TXN-AMOUNT: " + rm.getAdditionalData().get("txnAmount").toString());
						pTxnAmount = Float.parseFloat(rm.getAdditionalData().get("txnAmount").toString());

						String pCur = AppProp.getProperty("pCur." + corpId);
						String myCID = sender.getUserCode();

						AgLogger.logInfo("@@@ myCID:" + myCID);

						String pCIDType = AppProp.getProperty("pCIDType." + corpId);
						String myRef = UtilAccess.rrn();
						String merchantAcc = "N/A";
						SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

						SimpleDateFormat sdt = new SimpleDateFormat("hh:mm:ss a");

						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
						java.util.Date parsedDate = dateFormat.parse(qrExpiry);
						Timestamp qrExpiryTimestamp = new Timestamp(parsedDate.getTime());

						Timestamp currentTime = new Timestamp(System.currentTimeMillis());
						long timeDifferenceMillis = currentTime.getTime() - qrExpiryTimestamp.getTime();
						long minutesPassed = timeDifferenceMillis / (60 * 1000);

						String expiryTime = AppProp.getProperty("qr.expiry.minutes");

						// UMAIR
//						if (minutesPassed <= Long.parseLong(expiryTime)) {

//						ProcPerfromTransactionModel s = DBProceduresLoyaltyEngine.processAwardTxn("00003", "AWARD_TRANSACTION",
//								"Regular", "00", "POS", "999999999999999", "99999999", pTxnDate, pTxnAmount, "586", "8097370000000012", "CARD", myRef,
//								"000356");

						ProcPerfromTransactionModel s = DBProceduresLoyaltyEngine.processAwardTxn(pCorpID, pProcCode,
								pPID, pFID, pCHID, pMID, pTID, pTxnDate, pTxnAmount, pCur, myCID, pCIDType, myRef,
								batchNumber);

						if (s.getpRespCode() == "0000") {
							AgLogger.logInfo(getClass(), "Receiver Number:" + receiver.getMsisdn());

							String smsString = AppProp.getProperty("QrPaymentSenderMessage.text");
							if (sender.getMsisdn() != null) {
								utilService.doSendSmsOnly(sender.getMsisdn(), smsString + "  " + txnAmt, corpId);
							}
							String smsStringRec = "";
							if (receiver.getMsisdn() != null) {
								smsStringRec = AppProp.getProperty("QrPaymentReceiverMessage.text");
								utilService.doSendSmsOnly(receiver.getMsisdn(), smsStringRec + "  " + txnAmt, corpId);
							}

							java.util.Date date = new java.util.Date();
							Timestamp time = new Timestamp(date.getTime());
							QrPayments qp = new QrPayments();
							qp.setBalance(s.getpBalance());
							qp.setBatchNumber(batchNumber);
							qp.setCurrency(pCur);
							// contactless magstripe chip
							qp.setCardType("Magstripe");
							qp.setCardExpiry(new Timestamp(System.currentTimeMillis()));
							qp.setCardNumber(sender.getUserCode());
							qp.setCardScheme("AG");
							qp.setEntryDate(time);
							qp.setIsResponseSent(0);
							qp.setInvoiceNumber(invoiceNumber);
							qp.setIssuerName(issuer);
							qp.setMid(pMID);
							qp.setPaymentDate(pTxnDate);
							qp.setPointsAwdRedemed(s.getpRetAmount());
							qp.setProductId(prodId);
							qp.setQrExpiry(qrExpiryTimestamp);
							qp.setRemarks(s.getMessage());
							qp.setResponseSentDate(null);
							qp.setSerialNumber(serialNum);
							qp.setSrcAccount(c.getId().getUserid());
							qp.setSrcName(senderName);
							qp.setStatus(null);
							qp.setType(type);
							qp.setStype("QR_PAYMENT_SENT");
							qp.setTid(pTID);
							qp.setToAccount(merchantAcc);
							qp.setToName(receiverName);
							qp.setTxnAmount(txnAmt);
							qp.setTxnRef(myRef);

							qrPaymentsService.save(qp);

							JSONObject job_sent = new JSONObject();
							job_sent.put("stype", "QR_PAYMENT_SENT");
							job_sent.put("txnAmount", pTxnAmount);
							job_sent.put("txnRef", s.getpTxnRef());
							job_sent.put("txnDate", sd.format(pTxnDate));
							job_sent.put("txnTime", sdt.format(pTxnDate));
							job_sent.put("mid", pMID);
							job_sent.put("tid", pTID);
							job_sent.put("srcAccount", c.getId().getUserid());
							job_sent.put("srcName", senderName);
							job_sent.put("toName", receiverName);
							job_sent.put("toAccount", merchantAcc);
							utilService.doSendAppNotificationOnly(sender.getUserId(), smsString, smsString,
									"QR_PAYMENT_SENT", job_sent);

							JSONObject job_rec = new JSONObject();
							job_rec.put("stype", "QR_PAYMENT_RECEIVED");
							job_rec.put("txnAmount", pTxnAmount);
							job_rec.put("txnRef", s.getpTxnRef());
							job_rec.put("txnDate", sd.format(pTxnDate));
							job_rec.put("txnTime", sdt.format(pTxnDate));
							job_rec.put("mid", pMID);
							job_rec.put("tid", pTID);
							job_rec.put("srcAccount", c.getId().getUserid());
							job_rec.put("toAccount", merchantAcc);
							job_rec.put("srcName", senderName);
							job_rec.put("toName", receiverName);
							utilService.doSendAppNotificationOnly(receiver.getUserId(), smsStringRec, smsStringRec,
									"QR_PAYMENT_RECEIVED", job_rec);

							HashMap<Object, Object> mp = new HashMap<Object, Object>();
							mp.put("txnAmount", pTxnAmount);
							mp.put("txnRef", s.getpTxnRef());
							mp.put("txnDate", sd.format(pTxnDate));
							mp.put("txnTime", sdt.format(pTxnDate));
							mp.put("mid", pMID);
							mp.put("tid", pTID);
							mp.put("srcAccount", c.getId().getUserid());
							mp.put("toAccount", merchantAcc);
							mp.put("srcName", senderName);
							mp.put("toName", receiverName);

							response.setCode(s.getpRespCode());
							response.setMessage(s.getMessage());
							response.setData(mp);

							if (mod.getMid().equals("000769970015311")) {
								new MqttUtil().AnnouncePayment(myRef, pTxnAmount, null, "POS-QR-NEW");
							} else {
								new MqttUtil().AnnouncePayment(myRef, pTxnAmount, null, "POS-QR");
							}

							new MqttUtil().AnnounceVerifonePayment(myRef, pTxnAmount, "VF-QR-DEVICE");

							new MqttUtil().AnnounceVerifonePayment(myRef, pTxnAmount, "VF-QR-DEVICE-N");
							
//							String signed = calculateHmacSHA256(
//									"appkey=8EAD7367D75E1B577844&timestamp="
//											+ new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())
//											+ "&nonce=" + UtilAccess.rrn() + "&amount=" + pTxnAmount
//											+ "&template=739&device_id=M10-10141DP1560",
//									"531F4345D643D89BC434C1F9BCD83896EE7ED373");
//
//							AgLogger.logInfo(signed);
//
//							JSONObject obj = new JSONObject();
//							obj.put("appkey", "8EAD7367D75E1B577844");
//							obj.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
//							obj.put("nonce", UtilAccess.rrn());
//							obj.put("amount", pTxnAmount);
//							obj.put("template", 739);
//							obj.put("device_id", "M10-10141DP1560");
//							obj.put("sign", signed);
//
//							String res = doPost("https://api.cloudentify.com/v1/push/message", obj.toString());
//
//							AgLogger.logInfo(res);

						} else {
							response.setCode(s.getpRespCode());
							response.setMessage(s.getMessage());

						}

						// UMAIR
//						} else {
//							response.setCode("0001");
//							response.setMessage("QR Expired.");
//
//							AgLogger.logInfo(
//									"More than " + AppProp.getProperty("qr.expiry.minutes") + " minutes have passed, Qr Expired.");
//
//						}

					} else {
						response.setCode("0002");
						response.setMessage("No Active Card Exists Against User Id.");
					}

				} else {
					response.setCode("0003");
					response.setMessage("No Active Card Exists Against User Id.");
				}

			} else {
				response.setCode("0004");
				response.setMessage("Invalid Data or No Merchant Found.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	public static String[] validateQR(String QR) {
		String[] res = new String[7];
		res[0] = "0000";
		res[1] = "Success";
		res[2] = "MNAME";
		res[3] = "MCITY";
		res[4] = "PAN";
		res[5] = "MID";
		res[6] = "TID";

		return res;

	}

	private static String calculateHmacSHA256(String data, String key)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		hmacSHA256.init(secretKey);
		byte[] hmacData = hmacSHA256.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(hmacData);
	}

	public static String doPost(String url, String text) throws Exception {
		String msg = "";
		try {

			HttpResponse httpResponse = null;
			HttpPost httpPost = new HttpPost(url);
			Header contentType = new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
			httpPost.addHeader(contentType);
			StringEntity entity = new StringEntity(text, "UTF-8");
			entity.setContentType(contentType);
			httpPost.setEntity(entity);
			HttpClient httpClient = HttpClientBuilder.create().build();
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception();
			}
			msg = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {

			throw new Exception();
		}
		return msg;
	}

}