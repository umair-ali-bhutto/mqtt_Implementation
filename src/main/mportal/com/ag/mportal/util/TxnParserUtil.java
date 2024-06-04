package com.ag.mportal.util;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.model.DonationReportModel;
import com.ag.mportal.model.OfflineDetailsReportModel;
import com.ag.mportal.model.ReportModel;

public class TxnParserUtil {

	public static ReportModel parseData(ReportModel data) {

		try {

			// data.setEntryDate(null);
			// data.setMerchantId(null);
			// data.setTerminalId(null);
			// data.setTxnAmount(null);
			// data.setEntryTime(null);
			// data.setSerialNumber(null);
			// data.setCardNumber(null);
			// data.setType(null);
			// data.setBatchNumber(null);
			// data.setSetteled(null);
			// data.setAddress2(null);
			// data.setAddress3(null);
			// data.setAddress4(null);
			// data.setAddress1(null);

			// data.setTxnDetailId(null);
			// data.setMerchantName(null);

			data.setBin(null);

			data.setMerchantAddress(null);
			data.setResponse(null);
			data.setStatus(null);
			data.setTxnId(null);
			data.setReversal(null);
			data.setSetteledDate(null);
			data.setPaid(null);
			data.setPaidDate(null);
			data.setResultStatus(null);
			data.setRefNum(null);
			data.setCardCcType(null);
			data.setReversalDate(null);
			data.setBatchSettleRecords(null);
			data.setMsg(null);
			data.setAuthId(null);
			data.setOrgRefNumber(null);
			data.setInvoiceNumber(null);
			data.setCustomerId(null);
			data.setPosEntryMode(null);
			data.setModel(null);
			data.setScheme(null);
			data.setTxnSerialNumber(null);
			data.setAuthIdN(null);
			data.setFieldOne(null);
			data.setSettlementResponse(null);
			data.setReason(null);
			data.setAdjAmount(null);
			data.setQuantityType(null);
			data.setPrdName(null);
			data.setPrdCode(null);
			data.setTipAmount(null);
			data.setCurrency(null);
			data.setQuantity(null);
			data.setMdrOnUs(null);
			data.setMdrOffUs(null);
			data.setFedRate(null);
			data.setNetAmount(null);
			data.setTvr(null);
			data.setAid(null);
			data.setCustomerName(null);
			data.setCardType(null);
			data.setCardExpiry(null);
			data.setPem(null);
			data.setPaymentDate(null);

			data.setEntryTimeStamp(null);
			data.setPemValue(null);
			data.setFormattedDate(null);
			data.setAdditionalData(null);
			data.setImsi(null);
			data.setWlan(null);
			data.setSettledDateWithoutTime(null);
		} catch (Exception e) {
			AgLogger.logerror(null, "EXCEPTION IN TXN PARSER", e);
		}

		return data;
	}

	public static OfflineDetailsReportModel parseOfflineData(OfflineDetailsReportModel data) {

		try {
			data.setFieldOne(null);
			data.setAuthid(null);
			data.setCustomername(null);
			data.setScheme(null);
			data.setModel(null);
			data.setCardType(null);
			data.setCardExpiry(null);
			data.setSerialNumber(null);
			data.setRrn(null);
			data.setMdrOnus(null);
			data.setSettledDate(null);
			data.setMdrOffus(null);
			data.setAdjAmount(null);
			data.setInvoiceNum(null);
			data.setFed(null);
			data.setTimAmount(null);
			data.setPosEntryMode(null);
			data.setNetAmount(null);
			data.setTvr(null);
			data.setAid(null);

		} catch (Exception e) {
			AgLogger.logerror(null, "EXCEPTION IN TXN PARSER", e);
		}

		return data;
	}

	public static DonationReportModel parseDonationData(DonationReportModel data) {

		try {
			data.setType(null);
			data.setFieldOne(null);
			data.setAuthid(null);
			data.setCustomername(null);
			data.setScheme(null);
			data.setModel(null);
			data.setCardType(null);
			data.setRrn(null);
			data.setMdrOnus(null);
			data.setSettledDate(null);
			data.setMdrOffus(null);
			data.setAdjAmount(null);
			data.setInvoiceNum(null);
			data.setFed(null);
			data.setTimAmount(null);
			data.setPosEntryMode(null);
			data.setNetAmount(null);
			data.setTvr(null);
			data.setAid(null);
			data.setImsiNo(null);
			data.setAppVer(null);
			data.setDonationTid(null);
			data.setDonationAccount(null);

		} catch (Exception e) {
			AgLogger.logerror(null, "EXCEPTION IN TXN PARSER", e);
		}

		return data;
	}

}
