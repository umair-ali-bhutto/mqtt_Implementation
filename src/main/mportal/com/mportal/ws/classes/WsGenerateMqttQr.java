package com.mportal.ws.classes;

import java.util.Objects;
import org.main.qr.EmvQrModel;
import org.main.qr.GenerateEmvQr;
import org.springframework.stereotype.Component;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.MqttUtil;
import com.ag.generic.util.UtilAccess;

import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor.mode_return;

@Component("com.mportal.ws.classes.WsGenerateMqttQr")
public class WsGenerateMqttQr implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			String Amex = Objects.isNull(rm.getAdditionalData().get("Amex")) ? ""
					: rm.getAdditionalData().get("Amex").toString();
			String Amex2 = Objects.isNull(rm.getAdditionalData().get("Amex2")) ? ""
					: rm.getAdditionalData().get("Amex2").toString();
			String CountryCode = Objects.isNull(rm.getAdditionalData().get("CountryCode")) ? ""
					: rm.getAdditionalData().get("CountryCode").toString();
			String CustomPan = Objects.isNull(rm.getAdditionalData().get("CustomPan")) ? ""
					: rm.getAdditionalData().get("CustomPan").toString();
			String Discover = Objects.isNull(rm.getAdditionalData().get("Discover")) ? ""
					: rm.getAdditionalData().get("Discover").toString();
			String Discover2 = Objects.isNull(rm.getAdditionalData().get("Discover2")) ? ""
					: rm.getAdditionalData().get("Discover2").toString();
			String EmvCo = Objects.isNull(rm.getAdditionalData().get("EmvCo")) ? ""
					: rm.getAdditionalData().get("EmvCo").toString();
			String EmvCo2 = Objects.isNull(rm.getAdditionalData().get("EmvCo2")) ? ""
					: rm.getAdditionalData().get("EmvCo2").toString();
			String Jcb = Objects.isNull(rm.getAdditionalData().get("Jcb")) ? ""
					: rm.getAdditionalData().get("Jcb").toString();
			String Jcb2 = Objects.isNull(rm.getAdditionalData().get("Jcb2")) ? ""
					: rm.getAdditionalData().get("Jcb2").toString();
			String MasterCard = Objects.isNull(rm.getAdditionalData().get("MasterCard")) ? ""
					: rm.getAdditionalData().get("MasterCard").toString();
			String MasterCard2 = Objects.isNull(rm.getAdditionalData().get("MasterCard2")) ? ""
					: rm.getAdditionalData().get("MasterCard2").toString();
			String MerchantCategoryCode = Objects.isNull(rm.getAdditionalData().get("MerchantCategoryCode")) ? ""
					: rm.getAdditionalData().get("MerchantCategoryCode").toString();
			String MerchantCity = Objects.isNull(rm.getAdditionalData().get("MerchantCity")) ? ""
					: rm.getAdditionalData().get("MerchantCity").toString();
			String MerchantName = Objects.isNull(rm.getAdditionalData().get("MerchantName")) ? ""
					: rm.getAdditionalData().get("MerchantName").toString();
			String PayloadFormatIndicator = Objects.isNull(rm.getAdditionalData().get("PayloadFormatIndicator")) ? ""
					: rm.getAdditionalData().get("PayloadFormatIndicator").toString();
			String PointOfInitiationMethod = Objects.isNull(rm.getAdditionalData().get("PointOfInitiationMethod")) ? ""
					: rm.getAdditionalData().get("PointOfInitiationMethod").toString();
			String TransactionAmount = Objects.isNull(rm.getAdditionalData().get("TransactionAmount")) ? ""
					: rm.getAdditionalData().get("TransactionAmount").toString();
			String TransactionCurrency = Objects.isNull(rm.getAdditionalData().get("TransactionCurrency")) ? ""
					: rm.getAdditionalData().get("TransactionCurrency").toString();
			String Upi = Objects.isNull(rm.getAdditionalData().get("Upi")) ? ""
					: rm.getAdditionalData().get("Upi").toString();
			String Upi2 = Objects.isNull(rm.getAdditionalData().get("Upi2")) ? ""
					: rm.getAdditionalData().get("Upi2").toString();
			String VisaCardPan = Objects.isNull(rm.getAdditionalData().get("VisaCardPan")) ? ""
					: rm.getAdditionalData().get("VisaCardPan").toString();
			String VisaCardPan2 = Objects.isNull(rm.getAdditionalData().get("VisaCardPan2")) ? ""
					: rm.getAdditionalData().get("VisaCardPan2").toString();
			String Mid = Objects.isNull(rm.getAdditionalData().get("Mid")) ? ""
					: rm.getAdditionalData().get("Mid").toString();
			String Tid = Objects.isNull(rm.getAdditionalData().get("Tid")) ? ""
					: rm.getAdditionalData().get("Tid").toString();
			String SerialNumber = Objects.isNull(rm.getAdditionalData().get("SerialNumber")) ? ""
					: rm.getAdditionalData().get("SerialNumber").toString();

			AgLogger.logInfo("GENERATE MQTT QR REQUEST @@@@@ Amex: " + Amex + ", Amex2: " + Amex2 + ", CountryCode: "
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

			AgLogger.logInfo("MQTT QR RESPONSE CODE: " + s.getResponseCode());
			AgLogger.logInfo("MQTT QR: " + s.getResponseMessage());

			if (Mid.equals("000769970015311")) {

				if (mdl.getPointOfInitiationMethod().equals("12")) {
					new MqttUtil().changeQR(UtilAccess.rrn(), Mid,
							MerchantName + " (PKR " + mdl.getTransactionAmount() + ")", "POS-QR-NEW",
							s.getResponseMessage());
				} else {
					new MqttUtil().changeQR(UtilAccess.rrn(), Mid, MerchantName, "POS-QR-NEW", s.getResponseMessage());
				}

				response.setCode("0000");
				response.setMessage("Qr Generated Successfully.");
			} else {
				if (mdl.getPointOfInitiationMethod().equals("12")) {
					new MqttUtil().changeQR(UtilAccess.rrn(), Mid,
							MerchantName + " (PKR " + mdl.getTransactionAmount() + ")", "POS-QR",
							s.getResponseMessage());
				} else {
					new MqttUtil().changeQR(UtilAccess.rrn(), Mid, MerchantName, "POS-QR", s.getResponseMessage());
				}
//				new MqttUtil().changeQR(UtilAccess.rrn(), Mid, MerchantName, "", s.getResponseMessage());
				response.setCode("0000");
				response.setMessage("Qr Generated Successfully.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;

	}

}