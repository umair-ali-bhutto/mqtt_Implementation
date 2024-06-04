package com.ag.generic.service.impl;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.OtpLogging;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.util.AgLogger;

@Service
public class OTPLoggingServiceImpl implements OTPLoggingService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void generateOtp(String imei, String mobileNo, String chemistId, String otps) {

		OtpLogging otp = new OtpLogging();
		otp.setImei(imei);
		otp.setMobileNo(mobileNo);
		otp.setOtp(otps);
		otp.setOtpTime(new Timestamp(new java.util.Date().getTime()));
		otp.setProductName("MPORTAL");
		otp.setStatus("ACTIVE");
		otp.setChemistId(chemistId);
		entityManager.persist(otp);
	}

	@Override
	@Transactional
	public void generateOtpWithCorpId(String imei, String mobileNo, String chemistId, String otps, String corpId,
			String productName) {

		OtpLogging otp = new OtpLogging();
		otp.setImei(imei);
		otp.setMobileNo(mobileNo);
		otp.setOtp(otps);
		otp.setOtpTime(new Timestamp(new java.util.Date().getTime()));
		otp.setProductName(productName);
		otp.setStatus("ACTIVE");
		otp.setChemistId(chemistId);
		otp.setCorpId(corpId);
		entityManager.persist(otp);
	}

	@Override
	@Transactional
	public void deactiveOtp(String chemistId) {
		try {
			Query cb = entityManager.createNamedQuery("OTPLogging.validateOtpByChemistId").setParameter("chemistId",
					chemistId);
			OtpLogging otpd = (OtpLogging) cb.setMaxResults(1).getSingleResult();
			if (otpd != null) {
				otpd.setStatus("INACTIVE");
				entityManager.merge(otpd);
			}
		} catch (NoResultException nre) {

		}
	}

	@Override
	@Transactional
	public OtpLogging validateOtp(String otp, String chemistId) {
		try {
			Query cb = entityManager.createNamedQuery("OTPLogging.validateOtp").setParameter("chemistId", chemistId)
					.setParameter("otp", otp);

			OtpLogging otpd = (OtpLogging) cb.getSingleResult();
			if (otpd != null) {
				double timeDifferenceMillis = new Timestamp(System.currentTimeMillis()).getTime()
						- otpd.getOtpTime().getTime();
				double minutesPassed = timeDifferenceMillis / (60 * 1000);

				String expiryTime = AppProp.getProperty("otp.expiry.minutes");

				if (minutesPassed <= Double.parseDouble(expiryTime)) {
					otpd.setStatus("INACTIVE");
					entityManager.merge(otpd);
					return otpd;
				} else {
					AgLogger.logInfo("More than " + AppProp.getProperty("otp.expiry.minutes")
							+ " minutes have passed, OTP Expired.");
					otpd.setStatus("INACTIVE");
					entityManager.merge(otpd);
					return null;
				}
			}
			return null;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public OtpLogging validateOtpFuel(String otp, String mobileNumber, String corpId, String prdName) {
		try {
			Query cb = entityManager.createNamedQuery("OTPLogging.validateOtpFuel")
					.setParameter("mobileNo", mobileNumber).setParameter("otp", otp).setParameter("corpId", corpId)
					.setParameter("productName", prdName);

			OtpLogging otpd = (OtpLogging) cb.getSingleResult();
			if (otpd != null) {
				double timeDifferenceMillis = new Timestamp(System.currentTimeMillis()).getTime()
						- otpd.getOtpTime().getTime();
				double minutesPassed = timeDifferenceMillis / (60 * 1000);

				String expiryTime = AppProp.getProperty("otp.expiry.minutes");

				if (minutesPassed <= Double.parseDouble(expiryTime)) {
					otpd.setStatus("INACTIVE");
					entityManager.merge(otpd);
					return otpd;
				} else {
					AgLogger.logInfo("More than " + AppProp.getProperty("otp.expiry.minutes")
							+ " minutes have passed, OTP Expired.");
					otpd.setStatus("INACTIVE");
					entityManager.merge(otpd);
					return null;
				}
			}
			return null;
		} catch (NoResultException nre) {
			return null;
		}
	}

}
