package com.ag.generic.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.generic.entity.AgPushEmail;
import com.ag.generic.entity.AgPushSMS;
import com.ag.generic.entity.BroadcastMsg;
import com.ag.generic.entity.PlayersIdsPushNotification;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.PlayerIdsPushNotificationService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.BatchSettlement;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.Paymentconfiguration;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.entity.TxnLogError;
import com.ag.mportal.entity.TxnReciept;
import com.ag.mportal.services.BatchSettlementService;
import com.ag.mportal.services.MerchantTerminalDetailsService;
import com.ag.mportal.services.PaymentConfigurationService;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.services.TxnLogsService;

@Service
public class UtilServiceImpl implements UtilService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	TxnDetailsService txnDetailsService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	PaymentConfigurationService paymentConfigurationService;

	@Autowired
	BatchSettlementService batchSettlementService;

	@Autowired
	PlayerIdsPushNotificationService playerIdsPushNotificationService;

	@Autowired
	TxnLogsService txnLogsService;

	@Autowired
	MerchantTerminalDetailsService merchantTerminalDetailsService;

	@Override
	public String[] fetchMerchantUpd(String merchantID) {
		try {
			String[] sk = new String[6];
			sk[0] = "9999";
			sk[1] = "NO MERCHANT";
			sk[2] = "N/A";
			sk[3] = "N/A";
			sk[4] = "N/A";
			sk[5] = "N/A";

			String query = AppProp.getProperty("merchant.search");
			query = query.replaceAll("@PARAMMERCHANTID", merchantID);
			AgLogger.logDebug(getClass(), query);
			Query sq = entityManager.createNativeQuery(query);

			@SuppressWarnings("unchecked")
			List<Object[]> olm = (List<Object[]>) sq.getResultList();
			if (olm.size() != 0) {

				sk[0] = "0000";

				for (Object[] ok : olm) {
					String param = (String) ok[0];
					switch (param) {
					case "MERCHANTNAME":
						sk[1] = (String) ok[1];
						break;
					case "MERCHANTADDRESS1":
						sk[2] = (String) ok[1];
						break;
					case "MERCHANTADDRESS2":
						sk[3] = (String) ok[1];
						break;
					case "MERCHANTADDRESS3":
						sk[4] = (String) ok[1];
						break;
					case "MERCHANTADDRESS4":
						sk[5] = (String) ok[1];
						break;

					default:
						break;
					}
				}

			}
			return sk;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Transactional
	@Override
	public void insertTxnReciept(TxnReciept rxn) {
		try {
			entityManager.persist(rxn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public TxnReciept fetchTxnReciept(String rrn) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchTxnReciept");
			Query cb = entityManager.createNamedQuery("TxnReciept.fetchTxnReciept").setParameter("refNum", rrn);
			return (TxnReciept) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchProfile(String model, String tid, String serialNum, String appName) {
		try {
			List<Object[]> fileUploadProp = null;
			String query = DBUtil.fetchProfile();
			query = query.replaceAll("@MODEL", model);
			query = query.replaceAll("@APPNAME", appName);
			query = query.replaceAll("@TID", tid);
			AgLogger.logDebug(getClass(), query);
			Query sq = entityManager.createNativeQuery(query);

			fileUploadProp = (List<Object[]>) sq.getResultList();
			return fileUploadProp;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void updateVerisys(String mid, String tid, String appName, String model, String serial) {
		try {
			String query = DBUtil.updateVerisys();
			query = query.replaceAll("#MID", mid);
			query = query.replaceAll("#TID", tid);
			query = query.replaceAll("#SERIAL", serial);
			query = query.replaceAll("#MODEL", model);
			AgLogger.logDebug(getClass(), query);
			entityManager.createNativeQuery(query);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public String[] fetchMerchantTerminalUpd(String merchantID, String tid) {
		try {
			String[] sk = new String[4];
			sk[0] = "9999";
			sk[1] = "";
			sk[2] = "";
			sk[3] = "";

			String query = DBUtil.fetchMerchantTerminalUpdN();
			query = query.replaceAll("#MID", merchantID);
			query = query.replaceAll("#TID", tid);
			AgLogger.logDebug(getClass(), query);
			Query sq = entityManager.createNativeQuery(query);
			Object[] olm = (Object[]) sq.getSingleResult();
			if (olm != null) {
				sk[0] = "0000";

				sk[1] = (String) olm[0];
				sk[2] = (String) olm[1];
				sk[3] = (String) olm[2];
			}

			return sk;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String[] fetchMerchantTerminalUpd(String merchantID) {
		try {
			String[] sk = new String[4];
			sk[0] = "9999";
			sk[1] = "";
			sk[2] = "";
			sk[3] = "";

			String query = DBUtil.fetchMerchantTerminalUpd();
			query = query.replaceAll("#MID", merchantID);
			AgLogger.logDebug(getClass(), query);
			Query sq = entityManager.createNativeQuery(query);
			Object[] olm = (Object[]) sq.getSingleResult();
			if (olm != null) {

				sk[0] = "0000";

				sk[1] = (String) olm[0];
				sk[2] = (String) olm[1];
				sk[3] = (String) olm[2];
			}

			return sk;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String[] isAlreadyALinkMerchant(String merchantID) {
		try {
			String[] sk = new String[3];

			sk[0] = "0000";
			sk[1] = "SUCCESS";
			sk[2] = "N/A";

			Query cb = entityManager.createNamedQuery("ChainMerchants.isAlreadyALinkMerchant").setParameter("mid",
					merchantID);
			ChainMerchant cmt = (ChainMerchant) cb.setMaxResults(1).getSingleResult();

			if (cmt != null) {
				if (!cmt.getChainMerchantMid().equals(merchantID)) {

					sk[0] = "0001";
					sk[1] = "SUCCESS";
					sk[2] = cmt.getChainMerchantMid();
				}
			}
			return sk;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTidByMid(String merchantID) {
		List<String> strList = null;
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From getTidByMid");
			String query = AppProp.getProperty("fetch.tid.by.mid");
			query = query.replaceAll("@mid", merchantID);
			Query cb = entityManager.createNativeQuery(query);
			List<Object> ob = (List<Object>) cb.getResultList();
			if (ob.size() != 0) {
				strList = new ArrayList<String>();
				for (Object o : ob) {
					strList.add((String) o);
				}
			}
			return strList;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String[] getLocationsName(String regionCode, String cityCode, String countryCode) {
		try {
			String[] arr = new String[3];
			return arr;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Integer[] getStats() {
		try {
			Integer[] statsArr = new Integer[3];

			statsArr[0] = 0;
			statsArr[1] = 1;
			statsArr[2] = 2;

			return statsArr;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public String getDbDetails() {
		try {
			AgLogger.logDebug(getClass(), "Fetching db details");
			Query cb = entityManager.createNativeQuery(AppProp.getProperty("mw.show.db.version.query"));
			return (String) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public void doSendSmsOnly(String mobile, String smsText, String corpId) {
		mobile = mobile.substring(1, mobile.length());
		mobile = "92" + mobile;
		try {

			String headers = AppProp.getProperty("send.sms.header");
			String issuers = AppProp.getProperty("send.sms.issuer.id");

			String header = headers.contains(",")
					? Arrays.stream(headers.split(",")).filter(part -> part.split(":")[0].equals(corpId)).findFirst()
							.map(part -> part.split(":")[1]).orElse(headers.split(",")[0].split(":")[1])
					: headers.split(":")[0].equals("default") ? headers.split(":")[1] : headers;

			String issuerId = issuers.contains(",")
					? Arrays.stream(issuers.split(",")).filter(part -> part.split(":")[0].equals(corpId)).findFirst()
							.map(part -> part.split(":")[1]).orElse(issuers.split(",")[0].split(":")[1])
					: issuers.split(":")[0].equals("default") ? issuers.split(":")[1] : issuers;

			AgPushSMS smsB = new AgPushSMS();
			smsB.setAwdInd(null);
			smsB.setCardNumber("1111222233334444");
			smsB.setDropDate(new Timestamp(new java.util.Date().getTime()));
			smsB.setFrequency(null);
			smsB.setIsSent(0);
//			smsB.setIssuerId("999");
			smsB.setIssuerId(issuerId);
			// smsB.setMsgHeader("DigitalPASS");
//			smsB.setMsgHeader("939388");// need to change corp wise
			smsB.setMsgHeader(header);
			smsB.setPriority("1");
			smsB.setRdmInd(null);
			smsB.setRebAmt("0");
			smsB.setSentDate(null);
			smsB.setSmsNumber(mobile);
			smsB.setSmsText(smsText);
			smsB.setSource(null);
			smsB.setTermId(null);
			smsB.setTxnAmount("0");
			smsB.setTxnDate(new Timestamp(new java.util.Date().getTime()));
			smsB.setTxnRef("999999999999");
			smsB.setTxnType("S");
			entityManager.persist(smsB);
			AgLogger.logInfo("RECORD SAVED TO AG PUSH SMS....");
		} catch (Exception e) {
			AgLogger.logInfo("EXCEPTION " + e);
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void doSendEmailOnly(String email, String emailSub, String emailText, String txnRef, String corpId) {
		email = email.toLowerCase();

		try {

			String issuers = AppProp.getProperty("send.email.issuer.id");
			String issuerId = issuers.contains(",")
					? Arrays.stream(issuers.split(",")).filter(part -> part.split(":")[0].equals(corpId)).findFirst()
							.map(part -> part.split(":")[1]).orElse(issuers.split(",")[0].split(":")[1])
					: issuers.split(":")[0].equals("default") ? issuers.split(":")[1] : issuers;

			AgPushEmail emailsB = new AgPushEmail();
			emailsB.setDropDate(new Timestamp(new java.util.Date().getTime()));
			emailsB.setEmailId(email);
			emailsB.setEmailSubj(emailSub);
			emailsB.setEmailText(emailText);

			emailsB.setFrequency(null);
			emailsB.setIsSent(0);
//			emailsB.setIssuerId("777");
			emailsB.setIssuerId(issuerId);
			emailsB.setSentDate(null);
			emailsB.setSource("HTML");
			emailsB.setTxnRef(txnRef);
			entityManager.persist(emailsB);
			AgLogger.logInfo("RECORD SAVED TO AG PUSH EMAIL....");
		} catch (Exception e) {
			AgLogger.logInfo("EXCEPTION" + e);
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void doSendAppNotificationOnly(int userId, String apptext, String apptextDetail) {

		try {
			BroadcastMsg msg = new BroadcastMsg();
			msg.setIsSent("N");
			msg.setMessage(apptext);
			msg.setMessageDetail(apptextDetail);
			msg.setMessageTitle("NOTIFICATION");
			msg.setSentTo(userId + "");
			msg.setEntryDate(new Timestamp(new java.util.Date().getTime()));
			entityManager.persist(msg);
			AgLogger.logInfo("RECORD RETRIVED....");
		} catch (Exception e) {
			AgLogger.logInfo("EXCEPTION OTPLoggingDAOImpl saveOtp   " + e);
			e.printStackTrace();
		}

	}

	// unused
	@Override
	@Transactional
	public void doSendSmsEmail(String mobile, String smsText, String email, String emailSub, String emailText,
			String txnRef) {
		mobile = mobile.substring(1, mobile.length());
		email = email.toLowerCase();
		mobile = "92" + mobile;

		try {
			AgPushSMS smsB = new AgPushSMS();
			smsB.setAwdInd(null);
			smsB.setCardNumber("1111222233334444");
			smsB.setDropDate(new Timestamp(new java.util.Date().getTime()));
			smsB.setFrequency(null);
			smsB.setIsSent(0);
			smsB.setIssuerId("999");
			smsB.setMsgHeader("DigitalPASS");
			smsB.setPriority("1");
			smsB.setRdmInd(null);
			smsB.setRebAmt("0");
			smsB.setSentDate(null);
			smsB.setSmsNumber(mobile);
			smsB.setSmsText(smsText);
			smsB.setSource(null);
			smsB.setTermId(null);
			smsB.setTxnAmount("0");
			smsB.setTxnDate(new Timestamp(new java.util.Date().getTime()));
			smsB.setTxnRef("999999999999");
			smsB.setTxnType("S");
			entityManager.persist(smsB);

			AgPushEmail emailsB = new AgPushEmail();
			emailsB.setDropDate(new Timestamp(new java.util.Date().getTime()));
			emailsB.setEmailId(email);
			emailsB.setEmailSubj(emailSub);
			emailsB.setEmailText(emailText);

			emailsB.setFrequency(null);
			emailsB.setIsSent(0);
			emailsB.setIssuerId("777");
			emailsB.setSentDate(null);
			emailsB.setSource("HTML");
			emailsB.setTxnRef(txnRef);
			entityManager.persist(emailsB);

			AgLogger.logInfo("RECORD RETRIVED....");
		} catch (Exception e) {
			AgLogger.logInfo("EXCEPTION OTPLoggingDAOImpl saveOtp   " + e);
			e.printStackTrace();
		}

	}

	// unused
	@Override
	@Transactional
	public void doSendSmsEmailAppNotifications(String mobile, String smsText, String email, String emailSub,
			String emailText, int userId, String apptext, String apptextDetail) {
		mobile = mobile.substring(1, mobile.length());
		mobile = "92" + mobile;
		email = email.toLowerCase();

		try {

			AgPushSMS smsB = new AgPushSMS();
			smsB.setAwdInd(null);
			smsB.setCardNumber("1111222233334444");
			smsB.setDropDate(new Timestamp(new java.util.Date().getTime()));
			smsB.setFrequency(null);
			smsB.setIsSent(0);
			smsB.setIssuerId("999");
			smsB.setMsgHeader("DigitalPASS");
			smsB.setPriority("1");
			smsB.setRdmInd(null);
			smsB.setRebAmt("0");
			smsB.setSentDate(null);
			smsB.setSmsNumber(mobile);
			smsB.setSmsText(smsText);
			smsB.setSource(null);
			smsB.setTermId(null);
			smsB.setTxnAmount("0");
			smsB.setTxnDate(new Timestamp(new java.util.Date().getTime()));
			smsB.setTxnRef("999999999999");
			smsB.setTxnType("S");
			entityManager.persist(smsB);

			AgPushEmail emailsB = new AgPushEmail();
			emailsB.setDropDate(new Timestamp(new java.util.Date().getTime()));
			emailsB.setEmailId(email);
			emailsB.setEmailSubj(emailSub);
			emailsB.setEmailText(emailText);

			emailsB.setFrequency(null);
			emailsB.setIsSent(0);
			emailsB.setIssuerId("777");
			emailsB.setSentDate(null);
			emailsB.setSource("HTML");
			emailsB.setTxnRef("");
			entityManager.persist(emailsB);

			BroadcastMsg msg = new BroadcastMsg();
			msg.setIsSent("N");
			msg.setMessage(apptext);
			msg.setMessageDetail(apptextDetail);
			msg.setMessageTitle("NOTIFICATION");
			msg.setSentTo(userId + "");
			msg.setEntryDate(new Timestamp(new java.util.Date().getTime()));
			entityManager.persist(msg);

			AgLogger.logInfo("RECORD RETRIVED....");
		} catch (Exception e) {
			AgLogger.logInfo("EXCEPTION OTPLoggingDAOImpl saveOtp   " + e);
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void doSendEmailAppNotifications(String mobile, String smsText, String email, String emailSub,
			String emailText, int userId, String apptext, String apptextDetail) {
		mobile = mobile.substring(1, mobile.length());
		mobile = "92" + mobile;
		email = email.toLowerCase();

		try {
			AgPushEmail emailsB = new AgPushEmail();
			emailsB.setDropDate(new Timestamp(new java.util.Date().getTime()));
			emailsB.setEmailId(email);
			emailsB.setEmailSubj(emailSub);
			emailsB.setEmailText(emailText);

			emailsB.setFrequency(null);
			emailsB.setIsSent(0);
			emailsB.setIssuerId("777");
			emailsB.setSentDate(null);
			emailsB.setSource("HTML");
			emailsB.setTxnRef("");
			entityManager.persist(emailsB);

			BroadcastMsg msg = new BroadcastMsg();
			msg.setIsSent("N");
			msg.setMessage(apptext);
			msg.setMessageDetail(apptextDetail);
			msg.setMessageTitle("NOTIFICATION");
			msg.setSentTo(userId + "");
			msg.setEntryDate(new Timestamp(new java.util.Date().getTime()));
			entityManager.persist(msg);

			AgLogger.logInfo("RECORD RETRIVED....");
		} catch (Exception e) {
			AgLogger.logInfo("EXCEPTION OTPLoggingDAOImpl saveOtp   " + e);
			e.printStackTrace();
		}

	}

	// unused
	@Override
	@Transactional
	public void doSendSmsAppNotifications(String mobile, String smsText, int userId, String apptext,
			String apptextDetail) {
		mobile = mobile.substring(1, mobile.length());
		mobile = "92" + mobile;

		try {

			AgPushSMS smsB = new AgPushSMS();
			smsB.setAwdInd(null);
			smsB.setCardNumber("1111222233334444");
			smsB.setDropDate(new Timestamp(new java.util.Date().getTime()));
			smsB.setFrequency(null);
			smsB.setIsSent(0);
			smsB.setIssuerId("999");
			smsB.setMsgHeader("DigitalPASS");
			smsB.setPriority("1");
			smsB.setRdmInd(null);
			smsB.setRebAmt("0");
			smsB.setSentDate(null);
			smsB.setSmsNumber(mobile);
			smsB.setSmsText(smsText);
			smsB.setSource(null);
			smsB.setTermId(null);
			smsB.setTxnAmount("0");
			smsB.setTxnDate(new Timestamp(new java.util.Date().getTime()));
			smsB.setTxnRef("999999999999");
			smsB.setTxnType("S");
			entityManager.persist(smsB);

			BroadcastMsg msg = new BroadcastMsg();
			msg.setIsSent("N");
			msg.setMessage(apptext);
			msg.setMessageDetail(apptextDetail);
			msg.setMessageTitle("NOTIFICATION");
			msg.setSentTo(userId + "");
			msg.setEntryDate(new Timestamp(new java.util.Date().getTime()));
			entityManager.persist(msg);

			AgLogger.logInfo("RECORD RETRIVED....");
			AgLogger.logInfo(apptextDetail);
		} catch (Exception e) {
			AgLogger.logInfo("EXCEPTION OTPLoggingDAOImpl saveOtp   " + e);
			e.printStackTrace();
		}

	}

	@Transactional
	public String[] responseServiceTxn(String xmls) {
		AgLogger.logInfo("REQUEST DATA SERVICE TXN: " + xmls);
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);
		String[] kl = new String[3];
		kl[0] = "0000";
		kl[1] = "SUCCESS";
		kl[2] = "0";

		String userName = "";
		String password = "";
		String TType = "";

		String cardNumber = null, merchantID = null, terminalID = null, txnID = null, amount = null, response = "0000",
				status = "SUCCESS", type = null, settled = "N", paid = "N", reversal = "N", batchNumber = null,
				finResult = "SUCCESS", refNumber = null, ccType = "N/A", msg = null, authID = null, orignalRef = "N/A",
				invoiceNumber = null, postMode = null, model = null, cardsheme = null, serialNumber = null,
				authIDN = null, fieldOne = null, reason = "N/A", tipAmount = "0.00", adjAmount = "0.00",
				quantityType = null, prdName = null, prdCode = null, tvr = null, aid = null, customerName = null,
				cardType = null;

		Date dfs = new java.util.Date();
		try {

			try {
				userName = (String) onj.get("UserName");
			} catch (Exception e) {

			}

			try {
				password = (String) onj.get("PASSWORD");
			} catch (Exception e) {

			}

			UserLogin userMdl = userLoginService.validateUserPassword(userName, password);

			if (!Objects.isNull(userMdl)) {
				AgLogger.logInfo("USER AUTHETICATED ServiceTxn");

				Date cardExpiry = null;

				int rec = 0, custId = 0, quantity = 0;

				String dateTime = "";

				try {
					cardNumber = (String) onj.get("Bin");
				} catch (Exception e) {

				}

				try {
					authIDN = (String) onj.get("AuthID");
				} catch (Exception e) {

				}

				try {
					fieldOne = (String) onj.get("FieldOne");
				} catch (Exception e) {

				}

				try {
					amount = (String) onj.get("Amount");
				} catch (Exception e) {

				}

				try {
					merchantID = (String) onj.get("MID");
				} catch (Exception e) {

				}

				try {
					terminalID = (String) onj.get("TID");
				} catch (Exception e) {

				}
				int year = Calendar.getInstance().get(Calendar.YEAR);
				try {
					dateTime += (String) onj.get("TxnDate") + year;
				} catch (Exception e) {
					dateTime += "0101" + year;
				}

				try {
					dateTime += (String) onj.get("TxnTime");
				} catch (Exception e) {
					dateTime += "000000";
				}

				try {
					postMode = (String) onj.get("POSEntryMode");
				} catch (Exception e) {

				}

				try {
					batchNumber = (String) onj.get("BatchNo");
				} catch (Exception e) {

				}

				try {
					refNumber = (String) onj.get("RRN");
				} catch (Exception e) {

				}

				try {
					invoiceNumber = (String) onj.get("InvoiceNo");
				} catch (Exception e) {

				}

				try {
					authID = (String) onj.get("Stan");
				} catch (Exception e) {

				}

				try {
					type = (String) onj.get("TxnType");
					TType = type;
				} catch (Exception e) {

				}

				try {
					msg = (String) onj.get("Acquirer");
				} catch (Exception e) {

				}

				try {
					model = (String) onj.get("Model");
				} catch (Exception e) {

				}

				try {
					cardsheme = (String) onj.get("CardScheme");
				} catch (Exception e) {

				}

				try {
					serialNumber = (String) onj.get("SerialNumber");
				} catch (Exception e) {

				}

				try {
					SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmss");
					dfs = sdf.parse(dateTime);
				} catch (Exception e) {
					AgLogger.logerror(getClass(), " EXCEPTION  ", e);
				}

				try {
					String tempStatus = (String) onj.get("Status");
					status = Objects.isNull(tempStatus) || tempStatus.isEmpty() ? "SUCCESS" : tempStatus;
				} catch (Exception e) {

				}

				try {
					String tempReason = (String) onj.get("Reason");
					reason = Objects.isNull(tempReason) || tempReason.isEmpty() ? "N/A" : tempReason;
				} catch (Exception e) {

				}

				try {
					String tempTipAmt = (String) onj.get("TipAmount");
					tipAmount = Objects.isNull(tempTipAmt) || tempTipAmt.isEmpty() ? "0.00" : tempTipAmt;
				} catch (Exception e) {

				}

				try {
					String tempAdjAmount = (String) onj.get("AdjAmount");
					adjAmount = Objects.isNull(tempAdjAmount) || tempAdjAmount.isEmpty() ? "0.00" : tempAdjAmount;
				} catch (Exception e) {

				}

				try {
					String tempQuantity = (String) onj.get("Quantity");
					quantity = Objects.isNull(tempQuantity) || tempQuantity.isEmpty() ? 0
							: Integer.parseInt(tempQuantity);
				} catch (Exception e) {

				}

				try {
					String tempQuantityType = (String) onj.get("QuantityType");
					quantityType = Objects.isNull(tempQuantityType) || tempQuantityType.isEmpty() ? null
							: tempQuantityType;
				} catch (Exception e) {

				}

				try {
					String tempPrdName = (String) onj.get("PrdName");
					prdName = Objects.isNull(tempPrdName) || tempPrdName.isEmpty() ? null : tempPrdName;
				} catch (Exception e) {

				}

				try {
					String tempPrdCode = (String) onj.get("PrdCode");
					prdCode = Objects.isNull(tempPrdCode) || tempPrdCode.isEmpty() ? null : tempPrdCode;
				} catch (Exception e) {

				}
				try {
					String tempTvr = (String) onj.get("TVR");
					tvr = Objects.isNull(tempTvr) || tempTvr.isEmpty() ? null : tempTvr;
				} catch (Exception e) {

				}
				try {
					String tempAid = (String) onj.get("AID");
					aid = Objects.isNull(tempAid) || tempAid.isEmpty() ? null : tempAid;
				} catch (Exception e) {

				}
				try {
					String tempCustomerName = (String) onj.get("CustomerName");
					customerName = Objects.isNull(tempCustomerName) || tempCustomerName.isEmpty() ? null
							: tempCustomerName;
				} catch (Exception e) {

				}
				try {
					String tempCardType = (String) onj.get("CardType");
					cardType = Objects.isNull(tempCardType) || tempCardType.isEmpty() ? null : tempCardType;
				} catch (Exception e) {

				}
				try {
					String tempCardExpiry = (String) onj.get("CardExpiry");
					SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");

					cardExpiry = sdf.parse(tempCardExpiry);
				} catch (Exception e) {
					// AgLogger.logerror(getClass(), "Exception Card Expiry....", e);
				}

				boolean b = false;
				if (!b) {

					String curreny = "PKR";
					boolean isExist = txnDetailsService.checkExistanceOfTxnDetail(merchantID, terminalID, batchNumber,
							refNumber, authID, type);

					if (!isExist) {
						AgLogger.logInfo("ServiceTxn TXN NOT EXISTS -> MID " + merchantID + "| TID " + terminalID
								+ "| BN " + batchNumber + "| RN" + refNumber + "| AuthID " + authID + "| Type" + type);
						UserLogin userMdlRequest = userLoginService.validetUserWithoutCorpId(merchantID);
						if (!Objects.isNull(userMdlRequest)) {
							List<UserSetting> lstUserSetting = userSettingService
									.fetchSettingByUserLoginId(userMdlRequest.getUserId());
							if (lstUserSetting.size() != 0) {
								HashMap<String, String> mapUserSetting = UtilAccess.lstToMapUserSetting(lstUserSetting);

								Double mdrOffus = UtilAccess.calcTxnMdrOffus(Double.valueOf(amount),
										!Objects.isNull(mapUserSetting.get("MDR_OFF_US"))
												? Double.valueOf(mapUserSetting.get("MDR_OFF_US"))
												: 0.0,
										!Objects.isNull(mapUserSetting.get("MDR_ON_US"))
												? Double.valueOf(mapUserSetting.get("MDR_ON_US"))
												: 0.0,
										!Objects.isNull(mapUserSetting.get("FED_RATES"))
												? Double.valueOf(mapUserSetting.get("FED_RATES"))
												: 0.0);

								Double mdrOnnus = UtilAccess.calcTxnMdrOnus(Double.valueOf(amount),
										!Objects.isNull(mapUserSetting.get("MDR_OFF_US"))
												? Double.valueOf(mapUserSetting.get("MDR_OFF_US"))
												: 0.0,
										!Objects.isNull(mapUserSetting.get("MDR_ON_US"))
												? Double.valueOf(mapUserSetting.get("MDR_ON_US"))
												: 0.0,
										!Objects.isNull(mapUserSetting.get("FED_RATES"))
												? Double.valueOf(mapUserSetting.get("FED_RATES"))
												: 0.0);

								Double fedRate = UtilAccess.calcTxnFed(Double.valueOf(amount), mdrOffus, mdrOnnus,
										!Objects.isNull(mapUserSetting.get("FED_RATES"))
												? Double.valueOf(mapUserSetting.get("FED_RATES"))
												: 0.0);

								Double netAmount = UtilAccess.calcTxnNetAmount(Double.valueOf(amount), mdrOffus,
										mdrOnnus, fedRate);

								TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(cardNumber, merchantID, terminalID,
										txnID, amount, response, status, type, settled, paid, reversal, batchNumber,
										finResult, refNumber, ccType, rec, msg, authID, orignalRef, invoiceNumber,
										postMode, custId, dfs, model, cardsheme, serialNumber, authIDN, fieldOne,
										reason, tipAmount, adjAmount, quantityType, prdName, prdCode, quantity,
										netAmount, mdrOnnus, mdrOffus, fedRate, curreny, tvr, aid, cardExpiry, cardType,
										customerName);

								entityManager.persist(txnLogCustId);
								int id = txnLogCustId.getId();

								kl[2] = String.valueOf(id);
							} else {
								TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(cardNumber, merchantID, terminalID,
										txnID, amount, response, status, type, settled, paid, reversal, batchNumber,
										finResult, refNumber, ccType, rec, msg, authID, orignalRef, invoiceNumber,
										postMode, custId, dfs, model, cardsheme, serialNumber, authIDN, fieldOne,
										reason, tipAmount, adjAmount, quantityType, prdName, prdCode, quantity,
										Double.valueOf(amount), 0.0, 0.0, 0.0, curreny, tvr, aid, cardExpiry, cardType,
										customerName);
								entityManager.persist(txnLogCustId);
								int id = txnLogCustId.getId();

								kl[2] = String.valueOf(id);
							}

						} else {
							TxnDetail txnLogCustId = UtilAccess.doLogWithCustID(cardNumber, merchantID, terminalID,
									txnID, amount, response, status, type, settled, paid, reversal, batchNumber,
									finResult, refNumber, ccType, rec, msg, authID, orignalRef, invoiceNumber, postMode,
									custId, dfs, model, cardsheme, serialNumber, authIDN, fieldOne, reason, tipAmount,
									adjAmount, quantityType, prdName, prdCode, quantity, Double.valueOf(amount), 0.0,
									0.0, 0.0, curreny, tvr, aid, cardExpiry, cardType, customerName);
							entityManager.persist(txnLogCustId);
							int id = txnLogCustId.getId();

							kl[2] = String.valueOf(id);
						}

					} else {
						AgLogger.logInfo("ServiceTxn TXN EXISTS -> MID " + merchantID + "| TID " + terminalID + "| BN "
								+ batchNumber + "| RN" + refNumber + "| AuthID " + authID + "| Type" + type);
						TxnLogError txnLog = UtilAccess.doLogForNonExistTxnRecord(cardNumber, merchantID, terminalID,
								txnID, amount, response, status, type, settled, paid, reversal, batchNumber, finResult,
								refNumber, ccType, rec, msg, authID, orignalRef, invoiceNumber, postMode, custId, dfs,
								model, cardsheme, serialNumber, authIDN, fieldOne, reason, tipAmount, adjAmount,
								quantityType, prdName, prdCode, quantity, tvr, aid, cardExpiry, cardType, customerName);
						entityManager.persist(txnLog);
					}

				}

				else {
					kl[0] = "8888";
					kl[1] = "ALREADY EXISTS ON " + refNumber + "|" + terminalID + "|" + type;
					kl[2] = "0";
					AgLogger.logInfo("CODE: " + kl[0] + " MESSAGE:" + kl[1]);
				}
			}

			else {
				kl[0] = "9999";
				kl[1] = "VALIDATION FAILED " + userName + "|" + password;
				kl[2] = "0";
				AgLogger.logInfo("CODE: " + kl[0] + " MESSAGE:" + kl[1]);
			}

			AgLogger.logInfo(kl[0] + "@@@@" + TType);
			if (kl[0].equals("0000") && TType.equals("COMPLETION")) {
				TxnDetail tds = txnDetailsService.fetchTxnDetailPreAuth(merchantID, terminalID, authIDN);
				if (tds != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					txnDetailsService.updateRecordPreAuth(tds.getId() + "", "Y", sdf.format(dfs));
				} else {
					AgLogger.logInfo("No Record Found against " + merchantID + "|" + terminalID + "|" + authIDN);
				}

			}

			AgLogger.logInfo(kl[0] + "@@@@" + TType);
			if (kl[0].equals("0000") && TType.equals("VOID")) {
				AgLogger.logInfo(kl[0] + "@@@@ VOID TXN" + TType);
				TxnDetail tds = txnDetailsService.fetchTxnDetailVoidTxn(merchantID, terminalID, authIDN);
				if (tds != null) {
					txnDetailsService.updateRecordPreAuth(tds.getId() + "", "N", "00-00-0000 00:00:00");
				} else {
					AgLogger.logInfo("No Record Found against " + merchantID + "|" + terminalID + "|" + authIDN);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			kl[0] = "9999";
			kl[1] = e.getMessage();
			kl[2] = "0";
		}

		return kl;
	}

	public void processServiceSettelment(String xmls) {
		AgLogger.logInfo("REQUEST DATA SETTLEMENT : " + xmls);
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);
		String telco = "";
		String longitude = "";
		String latitude = "";
		String fieldOne = "";
		String fielTwo = "";
		String fieldThree = "";
		String fieldFour = "";
		String fieldFive = "";
		String fieldSix = "";
		String iccid = "";
		String appName = "";
		String userName = "";
		String password = "";

		try {
			JSONArray txnArray = new JSONArray(onj.get("Transaction").toString());
			JSONArray txnSummary = new JSONArray(onj.get("Summary").toString());
			JSONArray txnSimDetails = new JSONArray(onj.get("SimData").toString());
			// for validating to incorporate corpId
			org.json.JSONObject objk = null;
			org.json.JSONObject objkSummary = null;
			org.json.JSONObject objkTxnInfo = null;
			JSONArray txnInfo = null;

			try {
				objk = (org.json.JSONObject) txnSimDetails.get(0);
				objkSummary = (org.json.JSONObject) txnSummary.get(0);
				txnInfo = new JSONArray(onj.get("TxnInfo").toString());
				if (!Objects.isNull(txnInfo) && !txnInfo.isEmpty()) {
					objkTxnInfo = (org.json.JSONObject) txnInfo.get(0);
				}
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);
			}

			try {
				if (!Objects.isNull(objkTxnInfo)) {
					userName = (String) (txnInfo.getJSONObject(0).get("UserName"));
				} else {
					JSONArray ar = new JSONArray(onj.get("Transaction").toString());
					userName = (String) (ar.getJSONObject(0).get("UserName"));
				}
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);

			}

			try {
				if (!Objects.isNull(objkTxnInfo)) {
					password = (String) (txnInfo.getJSONObject(0).get("PASSWORD"));
				} else {
					JSONArray ar = new JSONArray(onj.get("Transaction").toString());
					password = (String) (ar.getJSONObject(0).get("PASSWORD"));
				}
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);

			}

			UserLogin userMdl = userLoginService.validateUserPassword(userName, password);
			// end incorporate corpId

			List<BatchSettlement> lst = new ArrayList<BatchSettlement>();
			for (int i = 0; i < txnArray.length(); i++) {
				AgLogger.logInfo(" BATCH SETTLEMTENT " + txnArray);
				org.json.JSONObject obj = txnArray.getJSONObject(i);
				org.json.JSONObject simData = (org.json.JSONObject) txnSimDetails.get(0);
				BatchSettlement btch = new BatchSettlement();

				String dateTime = "";

				try {
					btch.setBin((String) obj.get("BIN"));
				} catch (Exception e) {

				}

				try {
					btch.setAuthId((String) obj.get("AuthID"));
				} catch (Exception e) {

				}

				try {
					btch.setFieldOne((String) obj.get("FieldOne"));
				} catch (Exception e) {

				}

				try {
					btch.setAmount((String) obj.get("Amount"));
				} catch (Exception e) {

				}

				try {
					btch.setMid((String) obj.get("MID"));
				} catch (Exception e) {

				}

				try {
					btch.setTid((String) obj.get("TID"));
				} catch (Exception e) {

				}
				int year = Calendar.getInstance().get(Calendar.YEAR);
				try {
					dateTime += (String) obj.get("TxnDate") + year;
				} catch (Exception e) {
					dateTime += "0101" + year;
				}

				try {
					dateTime += (String) obj.get("TxnTime");
				} catch (Exception e) {
					dateTime += "000000";
				}

				try {
					btch.setEntryMode((String) obj.get("POSEntryMode"));
				} catch (Exception e) {

				}

				try {
					btch.setBatchNumber((String) obj.get("BatchNo"));
				} catch (Exception e) {

				}

				try {
					btch.setRrn((String) obj.get("RRN"));
				} catch (Exception e) {

				}

				try {
					btch.setInvoiceNumber((String) obj.get("InvoiceNo"));
				} catch (Exception e) {

				}

				try {
					btch.setStan((String) obj.get("Stan"));
				} catch (Exception e) {

				}

				try {
					btch.setTxnType((String) obj.get("TxnType"));
				} catch (Exception e) {

				}

				try {
					btch.setModel((String) obj.get("Model"));
				} catch (Exception e) {

				}

				try {
					btch.setSerialNumber((String) obj.get("SerialNumber"));
				} catch (Exception e) {

				}

				Date dfs = new java.util.Date();
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmss");
					dfs = sdf.parse(dateTime);
				} catch (Exception e) {
					AgLogger.logerror(getClass(), " EXCEPTION  ", e);
				}

				try {
					telco = (String) simData.get("telco");
				} catch (Exception e) {

				}

				try {
					latitude = (String) simData.get("latitude");
				} catch (Exception e) {

				}

				try {
					longitude = (String) simData.get("longitude");
				} catch (Exception e) {

				}

				try {
					fieldOne = (String) simData.get("fieldOne");
				} catch (Exception e) {

				}

				try {
					fielTwo = (String) simData.get("fieldTwo");
				} catch (Exception e) {

				}

				try {
					fieldThree = (String) simData.get("fieldThree");
				} catch (Exception e) {

				}

				try {
					fieldFour = (String) simData.get("fieldFour");
				} catch (Exception e) {

				}

				try {
					fieldFive = (String) simData.get("fieldFive");
				} catch (Exception e) {

				}

				try {
					fieldSix = (String) simData.get("fieldSix");
				} catch (Exception e) {

				}

				try {
					appName = (String) simData.get("APPNAME");
				} catch (Exception e) {

				}

				btch.setTxnDate(new Timestamp(dfs.getTime()));
				btch.setEntryDate(new Timestamp(new java.util.Date().getTime()));

				lst.add(btch);
			}

			insertBatchRec(lst, userMdl);
			TxnLog txnLog = UtilAccess.insertTxnLogs(AppProp.getProperty("act.batch"),
					"BATCH SETTLED PROCESSED : " + txnSummary.toString(), lst.get(0).getMid(), lst.get(0).getTid(),
					lst.get(0).getModel(), lst.get(0).getSerialNumber(), null, null, null, null, null, null, null, null,
					null, null, null);

			txnLogsService.insertLog(txnLog);
			Long l = txnLog.getId();
			Integer id = l.intValue();

			List<MerchantTerminalDetail> lstMerchantTerminalDetails = UtilAccess.createAndSaveMerchantTerminalDetails(
					lst.get(0).getMid(), lst.get(0).getTid(), telco, latitude, longitude, fieldOne, fielTwo, fieldThree,
					fieldFour, fieldFive, fieldSix, lst.get(0).getModel(), lst.get(0).getSerialNumber(), appName,
					AppProp.getProperty("act.batch"), iccid, id);

			for (MerchantTerminalDetail m : lstMerchantTerminalDetails) {
				merchantTerminalDetailsService.save(m);
			}

		} catch (JSONException e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}

	}

	public void insertBatchRec(List<BatchSettlement> lst, UserLogin userLogin) {

		for (BatchSettlement btch : lst) {
			try {
				TxnDetail txn = txnDetailsService.fetchTxnDetailNonVoid(btch.getBatchNumber(), btch.getTid(),
						btch.getInvoiceNumber());
				if (txn != null) {
					txn.setSetteled("Y");
					Date date = new Date();
					txn.setSetteledDate(new Timestamp(date.getTime()));
					txn.setPaymentDate(new Timestamp(getPaymentDate(date, userLogin.getCorpId()).getTime()));
					txnDetailsService.updateTxn(txn);
					TxnDetail txns = txnDetailsService.fetchTxnDetailVoid(btch.getBatchNumber(), btch.getTid(),
							btch.getInvoiceNumber());
					if (txns != null) {
						System.out.println(txns.getId() + ": void id");
						txns.setSetteled("Y");
						Date date2 = new Date();
						txn.setSetteledDate(new Timestamp(date2.getTime()));
						txn.setPaymentDate(new Timestamp(getPaymentDate(date2, userLogin.getCorpId()).getTime()));
						txnDetailsService.updateTxn(txns);
					}
					btch.setIsSetteled("Y");
				} else {
					btch.setIsSetteled("N");
				}
				batchSettlementService.insertBatch(btch);
			} catch (Exception e) {
				e.printStackTrace();
				btch.setIsSetteled("E");
				batchSettlementService.insertBatch(btch);
			}
		}
	}

	public Date getPaymentDate(Date date, String corpId) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(date);
			Paymentconfiguration paymentConfig = paymentConfigurationService.retrievePaymentConfigByDay(date.getDay(),
					corpId);
			c.add(Calendar.DAY_OF_MONTH, paymentConfig.getNumberOfDaysAdded());
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception ", e);
		}
		return c.getTime();
	}

	@Transactional
	public void doSendAppNotificationOnly(int userId, String apptext, String apptextDetail, String msgType,
			JSONObject content) {

		try {
			BroadcastMsg msg = new BroadcastMsg();
			msg.setIsSent("N");
			msg.setMessage(apptext);
			msg.setMessageDetail(apptextDetail);
			msg.setMessageTitle("NOTIFICATION");
			msg.setSentTo(userId + "");
			msg.setEntryDate(new Timestamp(new java.util.Date().getTime()));
			msg.setMsgType(msgType);
			msg.setContent(content.toString());
			entityManager.persist(msg);
			AgLogger.logInfo("RECORD INSERTED doSendAppNotificationOnly....");
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "EXCEPTION in doSendAppNotificationOnly", e);
			e.printStackTrace();
		}

	}

	@Transactional
	public void setPlayerIdsPushNotificationModel(int userLoginId, String playerId, String channel) {
		try {
			PlayersIdsPushNotification playerIdsPushNotification = playerIdsPushNotificationService
					.searchByUserLoginId(userLoginId);
			if (playerIdsPushNotification != null) {
				playerIdsPushNotificationService.updateByUserLoginId(userLoginId, playerId, channel);
			} else {
				PlayersIdsPushNotification p = new PlayersIdsPushNotification();
				p.setEntryDate(new Timestamp(new Date().getTime()));
				p.setUserLoginId(userLoginId);
				p.setPlayerId(playerId);
				p.setChannel(channel);
				playerIdsPushNotificationService.insert(p);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Transactional
	public String[] responseServiceFileAck(String xmls) {
		AgLogger.logInfo("REQUEST FILE ACK DATA: " + xmls);
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);
		String[] kl = new String[5];
		kl[0] = "0000";
		String auditMid = "N/A";
		String auditTid = "N/A";
		String auditSerial = "N/A";
		try {

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
			String model = "";
			String serialNumber = "";
			String tid = "";
			String mid = "";
			String appName = "";

			try {
				imei = (String) onj.get("imei");
			} catch (Exception e) {

			}

			try {
				telco = (String) onj.get("telco");
			} catch (Exception e) {

			}

			try {
				longitude = (String) onj.get("longitude");
			} catch (Exception e) {

			}

			try {
				latitude = (String) onj.get("latitude");
			} catch (Exception e) {

			}

			try {
				fieldOne = (String) onj.get("fieldOne");
			} catch (Exception e) {

			}

			try {
				fielTwo = (String) onj.get("fieldTwo");
			} catch (Exception e) {

			}

			try {
				fieldThree = (String) onj.get("fieldThree");
			} catch (Exception e) {

			}

			try {
				fieldFour = (String) onj.get("fieldFour");
			} catch (Exception e) {

			}

			try {
				fieldFive = (String) onj.get("fieldFive");
			} catch (Exception e) {

			}

			try {
				fieldSix = (String) onj.get("fieldSix");
			} catch (Exception e) {

			}

			try {
				model = (String) onj.get("Model");
			} catch (Exception e) {

			}

			try {
				serialNumber = (String) onj.get("SerialNumber");
				auditSerial = serialNumber;
			} catch (Exception e) {

			}

			try {
				tid = (String) onj.get("TID");
				auditTid = tid;
			} catch (Exception e) {

			}

			try {
				appName = (String) onj.get("APPNAME");
			} catch (Exception e) {

			}

			try {
				mid = (String) onj.get("MID");
				auditMid = mid;
			} catch (Exception e) {

			}

			TxnLog tnx = UtilAccess.insertTxnLogs(AppProp.getProperty("bundle.ack"), "FILE_ACK", mid, tid, model,
					serialNumber, null, null, null, null, null, null, null, null, null, null, null);

			entityManager.persist(tnx);

			List<MerchantTerminalDetail> lstM = UtilAccess.createAndSaveMerchantTerminalDetails(mid, tid, telco,
					latitude, longitude, fieldOne, fielTwo, fieldThree, fieldFour, fieldFive, fieldSix, model,
					serialNumber, appName, "FILE_ACK", null, null);

			for (MerchantTerminalDetail mk : lstM) {
				mk.setTxnLogsId((int) tnx.getId());
				entityManager.persist(mk);
			}

			kl[2] = auditMid;
			kl[3] = auditTid;
			kl[4] = auditSerial;

		} catch (Exception e) {
			e.printStackTrace();
			kl[0] = "9999";
		}
		return kl;
	}

	@Override
	public boolean isEditRights(String propValue, int groupCode) {
		boolean val = false;
		if (propValue.contains(",")) {
			String[] splt = propValue.split(",");
			for (String s : splt) {
				if (Integer.parseInt(s) == groupCode) {
					val = true;
				}
			}
		} else {
			if (Integer.parseInt(propValue) == groupCode) {
				val = true;
			}
		}

		return val;
	}

	@Override
	public void saveToMerchantTerminalDetails(List<MerchantTerminalDetail> lst) {
		try {
			for (MerchantTerminalDetail m : lst) {
				merchantTerminalDetailsService.save(m);
			}
		} catch (JSONException e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> fetchMidsByName(String name) {
		List<String> midList = null;
		try {
			String sqlQuery = AppProp.getProperty("db.name.search.query");
			sqlQuery = sqlQuery.replaceAll("@PARAMNAME", name);
			AgLogger.logDebug(getClass(), sqlQuery);
			Query sq = entityManager.createNativeQuery(sqlQuery);

			midList = (List<String>) sq.getResultList();
			return midList;
		} catch (NoResultException nre) {
			return null;
		}
	}

}
