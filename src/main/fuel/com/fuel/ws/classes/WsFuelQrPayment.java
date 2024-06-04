package com.fuel.ws.classes;

import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;
import org.main.qr.DecodeEmvQr;
import org.main.qr.EmvQrModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.util.ISO8583ErrorMap;
import com.ag.generic.entity.OtpLogging;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.QrPayments;
import com.ag.mportal.model.ISO8583Model;
import com.ag.mportal.services.QrPaymentsService;
import com.ag.mportal.util.String_to_bcd;

@Component("com.fuel.ws.classes.WsFuelQrPayment")
public class WsFuelQrPayment implements Wisher {

	@Autowired
	UserLoginService userLoginService;
	@Autowired
	QrPaymentsService qrPaymentsService;
	@Autowired
	OTPLoggingService loggingService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String otp = rm.getAdditionalData().get("otp").toString();
			String mobileNumber = rm.getAdditionalData().get("mobileNumber").toString();
			AgLogger.logInfo("mobileNumber:" + mobileNumber + "|otp:" + otp);
			OtpLogging otpRes = loggingService.validateOtp(otp, mobileNumber);
			if (otpRes != null) {
				String Qr = (String) rm.getAdditionalData().get("qr");
				EmvQrModel mod = new DecodeEmvQr().getGeneratedQr(Qr);
				String qrAddditonalData = mod.getMerchantCity();
				String[] recievedData = qrAddditonalData.split("\\|");

				Timestamp currentTime = new Timestamp(System.currentTimeMillis());

				Timestamp qrExpiryTimestamp = new Timestamp(
						new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(recievedData[2]).getTime());
				long timeDifferenceMillis = currentTime.getTime() - qrExpiryTimestamp.getTime();
				long minutesPassed = timeDifferenceMillis / (60 * 1000);

				String expiryTime = AppProp.getProperty("qr.expiry.minutes");
				if (minutesPassed <= Long.parseLong(expiryTime)) {

					UserLogin sender = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
					UserLogin receiver = userLoginService.validetUserWithoutCorpId(mod.getMid());

					QRModel mdl = new QRModel();
					mdl.setApp("000000");
					mdl.setBatchNumber(recievedData[5]);
					mdl.setCardNumber(rm.getAdditionalData().get("cardNumber").toString());
					mdl.setCardExpiry(rm.getAdditionalData().get("cardExpiry").toString());
					mdl.setCurrency("PKR");
					mdl.setInvoiceNumber(recievedData[3]);
					mdl.setIssuerName(recievedData[4]);
					mdl.setMerchantAccount("N/A");
					mdl.setMerchantAddress(receiver.getAdditionalData());
					mdl.setMerchantCity(receiver.getCity());
					mdl.setMid(mod.getMid());
					mdl.setPointsRedeemed("0.0");
					mdl.setPin(rm.getAdditionalData().get("pin").toString());
					mdl.setProductId(recievedData[0]);
					mdl.setProductName(recievedData[7]);
					mdl.setQrExpiry(qrExpiryTimestamp);
					mdl.setRecieverName(receiver.getUserName());
					mdl.setSenderId(rm.getUserid());
					mdl.setSenderName(sender.getUserName());
					mdl.setSerialNumber(mod.getSerialNumber());
					mdl.setSrcAccount("0");
					mdl.setTid(mod.getTid());
					mdl.setTxnAmount(mod.getTransactionAmount());
					mdl.setTxnDate(new java.sql.Date(new Date().getTime()));
					mdl.setTxnReference(UtilAccess.rrn());
					mdl.setTxnType(recievedData[1]);
					mdl.setStan(recievedData[8]);
					mdl.setCardCompanyName("BYCO TEST"); // unknown SIF

					// ISO REQUEST 1
					ISO8583Model isoRequest = generateISORequest(mdl);

					// ISO PROCESS 2
					ISO8583Model isoResponse = doProcess(isoRequest);

					ISO8583ErrorMap errorMap = new ISO8583ErrorMap();

					if (isoResponse.getCode().equals("00")) {
						// Parser to QRModel 3
						QRModel parsedModel = parseISOResponse(isoResponse,mdl.getTxnAmount());
						mdl.setBalance(parsedModel.getBalance());
						mdl.setBalanceAmount(parsedModel.getBalanceAmount());
						mdl.setProductPrice(parsedModel.getProductPrice());
						mdl.setProductQuantity(parsedModel.getProductQuantity());
						mdl.setRemarks(errorMap.errorCodes.get(isoResponse.getCode()));

						// QR REQUEST 4
						doProcessQrEntry(mdl);

						// QR Response 5
						response = processResponse(mdl);
					}
					// Failure Response
					else {
						response.setCode(isoResponse.getCode());
						response.setMessage(isoResponse.getCode().length() == 4 ? isoResponse.getMessage()
								: errorMap.errorCodes.get(isoResponse.getCode()));
					}

				} else {
					response.setCode("0001");
					response.setMessage("The QR has been expired.");

					AgLogger.logInfo("It has been more than " + AppProp.getProperty("fuel.qr.expiry.minutes")
							+ " minutes, the QR code has expired.");

				}

			} else {
				response.setCode("0002");
				response.setMessage("Invalid OTP.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	void doProcessQrEntry(QRModel model) {
		QrPayments qp = new QrPayments();

		qp.setBatchNumber(model.getBatchNumber());
		qp.setCurrency(model.getCurrency());
		qp.setCardExpiry(parseCardExpiryToTimestamp(model.getCardExpiry()));
		qp.setCardScheme("AG");
		qp.setEntryDate(new Timestamp(new Date().getTime()));
		qp.setIsResponseSent(0);
		qp.setInvoiceNumber(model.getInvoiceNumber());
		qp.setIssuerName(model.getIssuerName());
		qp.setMid(model.getMid());
		qp.setPaymentDate(model.getTxnDate());
		qp.setProductId(model.getProductId());
		qp.setQrExpiry(model.getQrExpiry());
		qp.setResponseSentDate(null);
		qp.setSerialNumber(model.getSerialNumber());
		qp.setSrcName(model.getSenderName());
		qp.setStatus(null);
		qp.setType(model.getTxnType());
		qp.setStype("QR_PAYMENT_SENT");
		qp.setTid(model.getTid());
		qp.setToAccount(model.getMerchantAccount());
		qp.setToName(model.getRecieverName());
		qp.setTxnAmount(model.getTxnAmount());
		qp.setTxnRef(model.getTxnReference());
		qp.setCardType("Dip");
		qp.setBalance(model.getBalance());
		qp.setSrcAccount(model.getSrcAccount());
		qp.setRemarks(model.getRemarks());
		qp.setPointsAwdRedemed(model.getPointsRedeemed());
		qp.setCardNumber(model.getCardNumber());

		qrPaymentsService.save(qp);

	}

	ResponseModel processResponse(QRModel model) {
		ResponseModel response = new ResponseModel();
		HashMap<Object, Object> mp = new HashMap<Object, Object>();
		mp.put("txnAmount", model.getTxnAmount());
		mp.put("txnRef", model.getTxnReference());
		mp.put("txnDate", new SimpleDateFormat("dd/MM/yyyy").format(model.getTxnDate()));
		mp.put("txnTime", new SimpleDateFormat("hh:mm:ss a").format(model.getTxnDate()));
		mp.put("mid", model.getMid());
		mp.put("tid", model.getTid());
		mp.put("srcAccount", model.getSrcAccount());
		mp.put("toAccount", model.getMerchantAccount());
		mp.put("srcName", model.getSenderName());
		mp.put("toName", model.getRecieverName());
		mp.put("batchNumber", model.getBatchNumber());
		mp.put("cardNumber", model.getCardNumber());
		mp.put("rrn", model.getTxnReference());
		mp.put("app", model.getApp());
		mp.put("balanceAmount", model.getBalanceAmount());
		mp.put("productName", model.getProductName());
		mp.put("productPrice", model.getProductPrice());
		mp.put("productQuantity", model.getProductQuantity());
		mp.put("cardCompanyName", model.getCardCompanyName());
		mp.put("merchantAddress", model.getMerchantAddress());
		mp.put("merchantCity", model.getMerchantCity());
		mp.put("invoiceNumber", model.getInvoiceNumber());

		response.setCode("0000");
		response.setMessage("SUCCESS");
		response.setData(mp);
		return response;
	}

	ISO8583Model generateISORequest(QRModel model) {
		ISOMsg mRequest = new ISOMsg();
		try {

			GenericPackager packager = new GenericPackager(AppProp.getProperty("fuel.qr.packager.path"));
			mRequest.setPackager(packager);
			mRequest.set(0, "0200");
			mRequest.set(3, "000000");
			// amount
//			mRequest.set(4, "000000063544");
			mRequest.set(4, amountParser(model.getTxnAmount()));

			// stan
//			mRequest.set(11, "000302");
			mRequest.set(11, model.getStan());

			mRequest.set(22, "0052");
			mRequest.set(24, AppProp.getProperty("fuel.qr.payment.nii"));
			mRequest.set(25, "00");

			mRequest.set(35,
					"32" + model.getCardNumber() + "D" + parseCardExpiry(model.getCardExpiry()) + "D0000000000");
//			mRequest.set(35, "32" + "8096152900000016D2706D0000000000");

			mRequest.set(41, model.getTid());
			mRequest.set(42, model.getMid());
//			mRequest.set(41, "00122931");
//			mRequest.set(42, "000762390019991");

//			mRequest.set(52, calculatePinBlock("8096152900000016", model.getPin()));
			mRequest.set(52, calculatePinBlock(model.getCardNumber(), model.getPin()));

			mRequest.set(62, "000302");
//			mRequest.set(63, "2597                         01000000063544                                                                                                                                                                                                  ");
			mRequest.set(63,
					"0237023539372020202020202020202020202020202020202020202020202030313030303030303036333534342020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020");

			String header = AppProp.getProperty("fuel.qr.payment.header");
			ISO8583Model mdl = new ISO8583Model();
			mdl.setHeader(header);
			mdl.setIsoMessageRequest(mRequest);
			mdl.setLoggerEnable(true);
//		    mdl.setNacIp("172.191.1.101");
//		    mdl.setNacPort(5555);
			mdl.setNacIp("202.143.120.251");
			mdl.setNacPort(7006);
			mdl.setPeckager(AppProp.getProperty("fuel.qr.packager.path"));
			return mdl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	ISO8583Model doProcess(ISO8583Model mdlRequest) {
		try {
			mdlRequest.setCode("0001");
			mdlRequest.setMessage("Failed.");
			Logger logger = new Logger();
			if (mdlRequest.isLoggerEnable()) {
				logger.addListener(new SimpleLogListener(System.out));
			}
			byte[] fHeader = new byte[5];
			fHeader = String_to_bcd.convert(mdlRequest.getHeader());
			ISOPackager newspecpackager = new GenericPackager(mdlRequest.getPeckager());
			AgLogger.logDebug("NAC", "DIALING TO " + mdlRequest.getNacIp() + "|" + mdlRequest.getNacPort());
			PostChannel channel = new PostChannel(mdlRequest.getNacIp(), mdlRequest.getNacPort(), newspecpackager);
			channel.setHeader(fHeader);
			channel.setTimeout(10000);
			if (mdlRequest.isLoggerEnable()) {
				((LogSource) channel).setLogger(logger, "test-channel");
			}
			channel.connect();
			AgLogger.logDebug("NAC", "CONNECTED TO " + mdlRequest.getNacIp() + "|" + mdlRequest.getNacPort());
			channel.send(mdlRequest.getIsoMessageRequest());
			mdlRequest.setIsoMessageResponse(channel.receive());
			AgLogger.logDebug("NAC", "DISCONNECTED FROM " + mdlRequest.getNacIp() + "|" + mdlRequest.getNacPort());
			mdlRequest.setCode(mdlRequest.getIsoMessageResponse().getString(39));
			channel.disconnect();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			mdlRequest.setCode("9992");
			mdlRequest.setMessage("Timeout Occured.");
		} catch (Exception e) {
			e.printStackTrace();
			mdlRequest.setCode("9991");
			mdlRequest.setMessage("Something Went Wrong.");
		}
		return mdlRequest;
	}

	String calculatePinBlock(String cardNumber, String pin) {
		try {
			String clearCardNumber = StringUtils.leftPad(cardNumber.substring(3, cardNumber.length() - 1), 16, "0");
			String clearPin = StringUtils.rightPad("0" + pin.length() + pin, 16, "F");

			long num1 = Long.parseLong(clearCardNumber, 16);
			long num2 = Long.parseLong(clearPin, 16);
			long result = num1 ^ num2;
			String clearPinBlock = "0" + Long.toHexString(result).toUpperCase();

			DESKeySpec desKeySpec = new DESKeySpec(Hex.decodeHex("1313131313131313"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] plainTextBytes = Hex.decodeHex(clearPinBlock);
			byte[] encryptedBytes = cipher.doFinal(plainTextBytes);

			return Hex.encodeHexString(encryptedBytes).toUpperCase();
			// return "057DE1B9B0BC9388";
		} catch (Exception e) {
			e.printStackTrace();
			return "FFFFFFFFFFFFFFFF";
		}
	}

	QRModel parseISOResponse(ISO8583Model isomdl,String temp) {
		QRModel model = new QRModel();

		model.setBalance("10.0");
		model.setBalanceAmount("10.0");
		model.setProductPrice(temp);
		model.setProductQuantity("1.0");
		return model;
	}

	String amountParser(String amount) {
		try {
			int passLeng = amount.substring(amount.lastIndexOf(".") + 1, amount.length()).length();
			if (passLeng == 1) {
				amount = amount + "0";
			} else if (passLeng > 2) {
				amount = amount.substring(0, amount.length() - 1);
			}
			String k = amount.replace(".", "");
			String zs = "";
			for (int z = k.length(); z < 12; z++) {
				zs += "0";
			}
			return zs + k;
		} catch (Exception e) {
			e.printStackTrace();
			return amount;
		}
	}

	String parseCardExpiry(String expiry) {
		return expiry.split("/")[1] + expiry.split("/")[0];
	}

	Timestamp parseCardExpiryToTimestamp(String expiry) {
		return Timestamp.from(
				YearMonth.of(Integer.parseInt(expiry.split("/")[1]) + 2000, Integer.parseInt(expiry.split("/")[0]))
						.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	class QRModel {
		private String app;
		private String balance;
		private String balanceAmount;
		private String batchNumber;
		private String cardCompanyName;
		private String cardNumber;
		private String cardExpiry;
		private String currency;
		private String invoiceNumber;
		private String issuerName;
		private String merchantAccount;
		private String merchantAddress;
		private String merchantCity;
		private String mid;
		private String pointsRedeemed;
		private String pin;
		private String productId;
		private String productName;
		private String productPrice;
		private String productQuantity;
		private Timestamp qrExpiry;
		private String recieverName;
		private String remarks;
		private String senderId;
		private String senderName;
		private String serialNumber;
		private String srcAccount;
		private String stan;
		private String tid;
		private String txnAmount;
		private java.sql.Date txnDate;
		private String txnReference;
		private String txnType;

		public String getApp() {
			return app;
		}

		public void setApp(String app) {
			this.app = app;
		}

		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
		}

		public String getBalanceAmount() {
			return balanceAmount;
		}

		public void setBalanceAmount(String balanceAmount) {
			this.balanceAmount = balanceAmount;
		}

		public String getBatchNumber() {
			return batchNumber;
		}

		public void setBatchNumber(String batchNumber) {
			this.batchNumber = batchNumber;
		}

		public String getCardCompanyName() {
			return cardCompanyName;
		}

		public void setCardCompanyName(String cardCompanyName) {
			this.cardCompanyName = cardCompanyName;
		}

		public String getCardNumber() {
			return cardNumber;
		}

		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}

		public String getCardExpiry() {
			return cardExpiry;
		}

		public void setCardExpiry(String cardExpiry) {
			this.cardExpiry = cardExpiry;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public String getInvoiceNumber() {
			return invoiceNumber;
		}

		public void setInvoiceNumber(String invoiceNumber) {
			this.invoiceNumber = invoiceNumber;
		}

		public String getIssuerName() {
			return issuerName;
		}

		public void setIssuerName(String issuerName) {
			this.issuerName = issuerName;
		}

		public String getMerchantAccount() {
			return merchantAccount;
		}

		public void setMerchantAccount(String merchantAccount) {
			this.merchantAccount = merchantAccount;
		}

		public String getMerchantAddress() {
			return merchantAddress;
		}

		public void setMerchantAddress(String merchantAddress) {
			this.merchantAddress = merchantAddress;
		}

		public String getMerchantCity() {
			return merchantCity;
		}

		public void setMerchantCity(String merchantCity) {
			this.merchantCity = merchantCity;
		}

		public String getMid() {
			return mid;
		}

		public void setMid(String mid) {
			this.mid = mid;
		}

		public String getPointsRedeemed() {
			return pointsRedeemed;
		}

		public void setPointsRedeemed(String pointsRedeemed) {
			this.pointsRedeemed = pointsRedeemed;
		}

		public String getPin() {
			return pin;
		}

		public void setPin(String pin) {
			this.pin = pin;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getProductPrice() {
			return productPrice;
		}

		public void setProductPrice(String productPrice) {
			this.productPrice = productPrice;
		}

		public String getProductQuantity() {
			return productQuantity;
		}

		public void setProductQuantity(String productQuantity) {
			this.productQuantity = productQuantity;
		}

		public Timestamp getQrExpiry() {
			return qrExpiry;
		}

		public void setQrExpiry(Timestamp qrExpiry) {
			this.qrExpiry = qrExpiry;
		}

		public String getRecieverName() {
			return recieverName;
		}

		public void setRecieverName(String recieverName) {
			this.recieverName = recieverName;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public String getSenderId() {
			return senderId;
		}

		public void setSenderId(String senderId) {
			this.senderId = senderId;
		}

		public String getSenderName() {
			return senderName;
		}

		public String getStan() {
			return stan;
		}

		public void setStan(String stan) {
			this.stan = stan;
		}

		public void setSenderName(String senderName) {
			this.senderName = senderName;
		}

		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		public String getSrcAccount() {
			return srcAccount;
		}

		public void setSrcAccount(String srcAccount) {
			this.srcAccount = srcAccount;
		}

		public String getTid() {
			return tid;
		}

		public void setTid(String tid) {
			this.tid = tid;
		}

		public String getTxnAmount() {
			return txnAmount;
		}

		public void setTxnAmount(String txnAmount) {
			this.txnAmount = txnAmount;
		}

		public java.sql.Date getTxnDate() {
			return txnDate;
		}

		public void setTxnDate(java.sql.Date txnDate) {
			this.txnDate = txnDate;
		}

		public String getTxnReference() {
			return txnReference;
		}

		public void setTxnReference(String txnReference) {
			this.txnReference = txnReference;
		}

		public String getTxnType() {
			return txnType;
		}

		public void setTxnType(String txnType) {
			this.txnType = txnType;
		}

	}

}