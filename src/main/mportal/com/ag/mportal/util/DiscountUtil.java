package com.ag.mportal.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.DiscountBin;

public class DiscountUtil {

	public enum CurrencyCode {
		PKR("PKR", "0586"), USD("USD", "0840");

		private final String code;
		private final String isoNumber;

		CurrencyCode(String code, String isoNumber) {
			this.code = code;
			this.isoNumber = isoNumber;
		}

		public String getCode() {
			return code;
		}

		public String getIsoNumber() {
			return isoNumber;
		}
	}

	/*
	 * METHOD To Get IsoCode From Given Currency Code
	 */
	public static String getCurrencyFromCode(String currencyCode) {
		for (CurrencyCode currency : CurrencyCode.values()) {
			if (currency.getCode().equalsIgnoreCase(currencyCode)) {
				return currency.getIsoNumber();
			}
		}
		return "";
	}

	/*
	 * METHOD To Get Currency From Given IsoNumber
	 */
	public static String getCurrencyFromIsoNumber(String isoNumber) {
		for (CurrencyCode currency : CurrencyCode.values()) {
			if (currency.getIsoNumber().equalsIgnoreCase(isoNumber)) {
				return currency.getCode();
			}
		}
		return "";
	}

	public enum DiscountStatus {
		ACTIVE("ACTIVE", "1"), INACTIVE("INACTIVE", "0"), EXPIRED("EXPIRED", "2");

		private final String status;
		private final String code;

		DiscountStatus(String status, String code) {
			this.status = status;
			this.code = code;
		}

		public String getStatus() {
			return status;
		}

		public String getCode() {
			return code;
		}

	}

	/*
	 * METHOD To Get Status From Given Code
	 */
	public static String getDiscountStatusFromCode(String code) {
		for (DiscountStatus status : DiscountStatus.values()) {
			if (status.getCode().equalsIgnoreCase(code)) {
				return status.getStatus();
			}
		}
		return "";
	}

	/*
	 * METHOD To Get Code From Given Status
	 */
	public static String getDiscountStatusFromStatus(String status) {
		for (DiscountStatus discountStatus : DiscountStatus.values()) {
			if (discountStatus.getStatus().equalsIgnoreCase(status)) {
				return discountStatus.getCode();
			}
		}
		return "";
	}

	public enum DiscountType {
		AMOUNT("Amount", "AMOUNT"), RATE("Rate", "PERCENTAGE");

		private final String code;
		private final String description;

		DiscountType(String code, String description) {
			this.code = code;
			this.description = description;
		}

		public String getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}

	/*
	 * METHOD To Get Description From Given Code
	 */
	public static String getDiscountTypeFromCode(String code) {
		for (DiscountType type : DiscountType.values()) {
			if (type.getCode().equalsIgnoreCase(code)) {
				return type.getDescription();
			}
		}
		return "";
	}

	/*
	 * METHOD To Get Code From Given Description
	 */
	public static String getDiscountTypeFromDescription(String description) {
		for (DiscountType discountType : DiscountType.values()) {
			if (discountType.getDescription().equalsIgnoreCase(description)) {
				return discountType.getCode();
			}
		}
		return "";
	}

	public static String calculateDiscount(String orignalAmount, String discountType, double discountValue) {
		try {

			if (discountType.equals(DiscountType.AMOUNT.description)) {
				return String.valueOf(discountValue);
			} else if (discountType.equals(DiscountType.RATE.description)) {
				double dTxnAMount = Double.parseDouble(orignalAmount);
				double dPercentage = discountValue;
				double discountAmount = dTxnAMount * (dPercentage / 100);
				double roundOffAmt = Math.round(discountAmount * 100.0) / 100.0;
				return String.valueOf(roundOffAmt);
			} else {
				AgLogger.logInfo("Invalid Discount Type:" + discountType);
				return orignalAmount;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return orignalAmount;
		}
	}

	public static String calculateDiscountedAmount(String orignalAmount, String discount) {
		try {
			double amt = Double.parseDouble(orignalAmount) - Double.parseDouble(discount);
			double roundOffAmt = Math.round(amt * 100.0) / 100.0;
			return String.valueOf(roundOffAmt < 0 ? orignalAmount : roundOffAmt);
		} catch (Exception e) {
			e.printStackTrace();
			return orignalAmount;
		}
	}

	public static Predicate<DiscountBin> binInRange(long bin) {
		return binObj -> bin >= Long.parseLong(binObj.getBinFrom()) && bin <= Long.parseLong(binObj.getBinTo());
	}

	public static int getDayIndexOfTheWeek(String date) {
		int day = 0;
		try {
			Calendar cal = Calendar.getInstance();
			int currentYear = cal.get(Calendar.YEAR);
			Date parsedDate = new SimpleDateFormat("MMddyyyy").parse(date + currentYear);
			cal.setTime(parsedDate);
			day = cal.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day;

	}

	public static String getDayOfTheWeek(String date) {
		String day = "";
		try {
			Calendar cal = Calendar.getInstance();
			int currentYear = cal.get(Calendar.YEAR);
			LocalDate localDate = LocalDate.parse(date + currentYear,
					java.time.format.DateTimeFormatter.ofPattern("MMddyyyy"));
			DayOfWeek dayOfWeek = localDate.getDayOfWeek();
			day = dayOfWeek.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day;

	}

	public static String[] getCurrentWeekDates(Date date) {
		try {
			String[] s = new String[2];
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date != null ? date : new Date());
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			s[0] = df.format(c.getTime());
			c.add(Calendar.DATE, 6);
			s[1] = df.format(c.getTime());
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
