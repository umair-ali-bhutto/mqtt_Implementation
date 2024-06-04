package com.ag.fuel.util;

import java.util.HashMap;

public class ISO8583ErrorMap {
	public HashMap<String, String> errorCodes = new HashMap<String, String>();

	public ISO8583ErrorMap() {
		errorCodes.put("00", "APPROVED TRANSACTION");
		errorCodes.put("01", "PLEASE CALL");
		errorCodes.put("02", "REFERRAL");
		errorCodes.put("03", "ERROR-CALL HELP - SN");
		errorCodes.put("04", "PICKUP CARD");
		errorCodes.put("05", "DO NOT HONOUR");
		errorCodes.put("12", "ERROR-CALL HELP - TR");
		errorCodes.put("13", "ERROR-CALL HELP - AM");
		errorCodes.put("14", "ERROR-CALL HELP - RE");
		errorCodes.put("19", "RE-ENTER TRANSACTION");
		errorCodes.put("25", "ERROR-CALL HELP - NT");
		errorCodes.put("30", "ERROR-CALL HELP - FE");
		errorCodes.put("31", "CALL HELP - NS");
		errorCodes.put("41", "PLEASE CALL - LC");
		errorCodes.put("43", "PLEASE CALL - CC");
		errorCodes.put("51", "DECLINED");
		errorCodes.put("54", "EXPIRED CARD");
		errorCodes.put("55", "INCORRECT PIN - IP");
		errorCodes.put("56", "NO CARD RECORD (No registered account for the card record)");
		errorCodes.put("58", "INVALID TRANSACTION");
		errorCodes.put("61", "EXCEED LIMIT");
		errorCodes.put("76", "ERROR-CALL HELP ? DC");
		errorCodes.put("77", "RECONCILE ERROR");
		errorCodes.put("78", "INVOICE NOT FOUND");
		errorCodes.put("79", "BATCH ALREADY OPEN");
		errorCodes.put("80", "BAD BATCH NUMBER");
		errorCodes.put("85", "BATCH NOT FOUND");
		errorCodes.put("89", "BAD TERMINAL ID");
		errorCodes.put("91", "ERROR-CALL HELP ? NA");
		errorCodes.put("94", "ERROR-CALL HELP ? SQ");
		errorCodes.put("95", "BATCH TRANSFER, WAIT");
		errorCodes.put("96", "ERROR-CALL HELP ? SE");
		errorCodes.put("D0", "CUSTOMER INACTIVE");
		errorCodes.put("D1", "PERPAID TXN AGAINST POSTPAID ACCOUNT");
		errorCodes.put("D2", "INVALID MOBILE NUMBER");
		errorCodes.put("D3", "CUSTOMER NOT FOUND");
	}
}
