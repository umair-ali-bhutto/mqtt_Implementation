package com.ag.pos.loyalty.controller;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.main.qr.EmvQrModel;
import org.main.qr.GenerateEmvQr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.LovService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@RestController
public class LoyaltyQRGenerator {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	LovService lovService;

	@PostMapping({ "/LoyaltyQrGen" })
	public ResponseEntity<byte[]> generateQr(@RequestBody JSONObject requestService, HttpServletRequest request) {

		try {

			AgLogger.logInfo("LOYALTY QR REQUEST|" + requestService.toString());

			String Amex = requestService.get("Amex").toString();
			String Amex2 = requestService.get("Amex2").toString();
			String CountryCode = requestService.get("CountryCode").toString();
			String CustomPan = requestService.get("CustomPan").toString();
			String Discover = requestService.get("Discover").toString();
			String Discover2 = requestService.get("Discover2").toString();
			String EmvCo = requestService.get("EmvCo").toString();
			String EmvCo2 = requestService.get("EmvCo2").toString();
			String Jcb = requestService.get("Jcb").toString();
			String Jcb2 = requestService.get("Jcb2").toString();
			String MasterCard = requestService.get("MasterCard").toString();
			String MasterCard2 = requestService.get("MasterCard2").toString();
			String MerchantCategoryCode = requestService.get("MerchantCategoryCode").toString();
			String MerchantCity = requestService.get("MerchantCity").toString();
			String MerchantName = requestService.get("MerchantName").toString();
			String PayloadFormatIndicator = requestService.get("PayloadFormatIndicator").toString();
			String PointOfInitiationMethod = requestService.get("PointOfInitiationMethod").toString();
			String TransactionAmount = requestService.get("TransactionAmount").toString();
			String TransactionCurrency = requestService.get("TransactionCurrency").toString();
			String Upi = requestService.get("Upi").toString();
			String Upi2 = requestService.get("Upi2").toString();
			String VisaCardPan = requestService.get("VisaCardPan").toString();
			String VisaCardPan2 = requestService.get("VisaCardPan2").toString();
			String Mid = requestService.get("Mid").toString();
			String Tid = requestService.get("Tid").toString();
			String SerialNumber = requestService.get("SerialNumber").toString();

			AgLogger.logInfo("GENERATE EMV QR AG REQUEST @@@@@ Amex: " + Amex + ", Amex2: " + Amex2 + ", CountryCode: "
					+ CountryCode + ", CustomPan: " + CustomPan + ", Discover: " + Discover + ", Discover2: "
					+ Discover2 + ", EmvCo: " + EmvCo + ", EmvCo2: " + EmvCo2 + ", Jcb: " + Jcb + ", Jcb2: " + Jcb2
					+ ", MasterCard: " + MasterCard + ", MasterCard2: " + MasterCard2 + ", MerchantCategoryCode: "
					+ MerchantCategoryCode + ", MerchantCity: " + MerchantCity + ", MerchantName: " + MerchantName
					+ ", PayloadFormatIndicator: " + PayloadFormatIndicator + ", PointOfInitiationMethod: "
					+ PointOfInitiationMethod + ", TransactionAmount: " + TransactionAmount + ", TransactionCurrency: "
					+ TransactionCurrency + ", Upi: " + Upi + ", Upi2: " + Upi2 + ", VisaCardPan: " + VisaCardPan
					+ ", VisaCardPan2: " + VisaCardPan2 + ", Mid: " + Mid + ", Tid: " + Tid + ", SerialNumber: "
					+ SerialNumber);

			EmvQrModel mdl = new EmvQrModel();
			mdl.setAmex((Objects.isNull(Amex) || Amex.isEmpty()) ? "amex12345678912" : Amex);
			mdl.setAmex2((Objects.isNull(Amex2) || Amex2.isEmpty()) ? "amex21234567891" : Amex2);
			mdl.setCountryCode((Objects.isNull(CountryCode) || CountryCode.isEmpty()) ? "CN" : CountryCode);
			mdl.setCustomPan((Objects.isNull(CustomPan) || CustomPan.isEmpty()) ? "98765432112345" : CustomPan);
			mdl.setDiscover((Objects.isNull(Discover) || Discover.isEmpty()) ? "discover1234567" : Discover);
			mdl.setDiscover2((Objects.isNull(Discover2) || Discover2.isEmpty()) ? "discover2123456" : Discover2);
			mdl.setEmvCo((Objects.isNull(EmvCo) || EmvCo.isEmpty()) ? "emvCo1234567891" : EmvCo);
			mdl.setEmvCo2((Objects.isNull(EmvCo2) || EmvCo2.isEmpty()) ? "emvCo2123456789" : EmvCo2);
			mdl.setJcb((Objects.isNull(Jcb) || Jcb.isEmpty()) ? "jcb123456789123" : Jcb);
			mdl.setJcb2((Objects.isNull(Jcb2) || Jcb2.isEmpty()) ? "jcb212345678912" : Jcb2);
			mdl.setMasterCard((Objects.isNull(MasterCard) || MasterCard.isEmpty()) ? "masterCard12345" : MasterCard);
			mdl.setMasterCard2(
					(Objects.isNull(MasterCard2) || MasterCard2.isEmpty()) ? "masterCard21234" : MasterCard2);
			mdl.setMerchantCategoryCode(
					(Objects.isNull(MerchantCategoryCode) || MerchantCategoryCode.isEmpty()) ? "4111"
							: MerchantCategoryCode);
			mdl.setMerchantCity((Objects.isNull(MerchantCity) || MerchantCity.isEmpty()) ? "BEIJING" : MerchantCity);
			mdl.setMerchantName(
					(Objects.isNull(MerchantName) || MerchantName.isEmpty()) ? "BEST TRANSPORT" : MerchantName);
			mdl.setPayloadFormatIndicator(
					(Objects.isNull(PayloadFormatIndicator) || PayloadFormatIndicator.isEmpty()) ? "01"
							: PayloadFormatIndicator);
			mdl.setPointOfInitiationMethod(
					(Objects.isNull(PointOfInitiationMethod) || PointOfInitiationMethod.isEmpty()) ? "12"
							: PointOfInitiationMethod);
			mdl.setTransactionAmount((Objects.isNull(TransactionAmount) || Amex.isEmpty()) ? "0.1" : TransactionAmount);
			mdl.setTransactionCurrency((Objects.isNull(TransactionCurrency) || TransactionCurrency.isEmpty()) ? "586"
					: TransactionCurrency);
			mdl.setUpi((Objects.isNull(Upi) || Upi.isEmpty()) ? "upi123456789123" : Upi);
			mdl.setUpi2((Objects.isNull(Upi2) || Upi2.isEmpty()) ? "upi212345678912" : Upi2);
			mdl.setVisaCardPan(
					(Objects.isNull(VisaCardPan) || VisaCardPan.isEmpty()) ? "visa12345678912" : VisaCardPan);
			mdl.setVisaCardPan2(
					(Objects.isNull(VisaCardPan) || VisaCardPan.isEmpty()) ? "visa21234567891" : VisaCardPan2);
			mdl.setMid((Objects.isNull(Mid) || Mid.isEmpty()) ? "9999999999999999" : Mid);
			mdl.setTid((Objects.isNull(Tid) || Tid.isEmpty()) ? "999999" : Tid);
			mdl.setSerialNumber((Objects.isNull(SerialNumber) || SerialNumber.isEmpty()) ? "888888" : SerialNumber);
			EmvQrModel s = new GenerateEmvQr().getGeneratedQr(mdl);

			AgLogger.logInfo("LOYALTY QR RESPONSE CODE: " + s.getResponseCode());
			AgLogger.logInfo("LOYALTY QR RESPONSE MESSAGE: " + s.getResponseMessage());

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(s.getResponseMessage(), BarcodeFormat.QR_CODE, 400, 400);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
			AgLogger.logInfo("QR Code Image Generated Successfully.");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(outputStream.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			ResponseModel response = new ResponseModel();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
			return ResponseEntity.ok().body(gson.toJson(response).getBytes());
		}

	}

}
