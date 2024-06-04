package com.ag.generic.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplChannelConfig;
import com.ag.generic.entity.UserChannel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.ComplChannelConfigService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.impl.UserChannelsServiceImpl;
import com.ag.generic.service.impl.UserLoginServiceImpl;
import com.ag.notification.client.NotificationSendControllerProxy;
import com.ag.notification.client.NotificationSendModel;
import com.ag.notification.client.ResponseModel;

@Component
public class SendNotifciation {

	@Autowired
	ComplChannelConfigService complChannelConfigService;

	@Autowired
	UserChannelsServiceImpl userChannelsService;

	@Autowired
	UserLoginServiceImpl userLoginService;

	@Autowired
	UtilService utilService;

	public void doTask(String category, String type, String subType, SendNotificationModel sdm, int userId) {

		new Thread(new Runnable() {
			public void run() {
				AgLogger.logDebug("", " ASYNC SCH .....");
				UserLogin usr = userLoginService.validetUserid(userId);
				doSentNotification(category, type, subType, sdm, userId, usr.getCorpId());
				AgLogger.logDebug("", " ASYNC SCH ..... END");
			}
		}).start();
	}

	public void doTaskUnRegUser(String category, String type, String subType, SendNotificationModel sdm, String mobile,
			String email, int userId, String corpId) {

		new Thread(new Runnable() {
			public void run() {
				AgLogger.logDebug("", " ASYNC UN REG SCH .....");
				doSentNotificationUnRegUser(category, type, subType, sdm, mobile, email, userId, corpId);
				AgLogger.logDebug("", " ASYNC UN REG SCH ..... END");
			}
		}).start();
	}

	public void doSentNotification(String category, String type, String subType, SendNotificationModel sdm, int userId,
			String corpId) {

		ComplChannelConfig cfd = new ComplChannelConfig();
		cfd.setCategory(category);
		cfd.setType(type);
		cfd.setSubType(subType);
		cfd.setCorpId(corpId);
		List<ComplChannelConfig> lst = complChannelConfigService.fetchByOtherParams(cfd);
		List<UserChannel> channelList = userChannelsService.fetchAllByID(sdm.getReciverId());
		AgLogger.logDebug("userchannelList Size:", channelList.size() + "..............@@@@@@");
		for (UserChannel usc : channelList) {
			ComplChannelConfig txt = null;
			switch (usc.getChannel()) {
			case "EMAIL":
				txt = getText(lst, "EMAIL");
				if (txt != null) {
					String tmk = generateStringText(txt.getText(), sdm);
					AgLogger.logDebug("", "EMAIL" + " | " + tmk + "|" + txt.getSubject());
					AgLogger.logInfo("SENDING EMAIL TO " + usc.getValue());
					savetoService("EMAIL", usc.getValue(), "", "", "", tmk, "", "1", txt.getSubject(), userId, corpId);
				}
				break;
			case "SMS":
				txt = getText(lst, "SMS");
				if (txt != null) {
					String tmk = generateStringText(txt.getText(), sdm);
					AgLogger.logDebug("", "SMS | " + tmk);
					AgLogger.logInfo("SENDING SMS TO " + usc.getValue());
					savetoService("SMS", "", "", "", "", tmk, usc.getValue(), "1", txt.getSubject(), userId, corpId);

				}
				break;
			default:
				txt = null;
				break;
			}
		}

	}

	public void doSentNotificationUnRegUser(String category, String type, String subType, SendNotificationModel sdm,
			String mobileId, String emailId, int userId, String corpId) {

		ComplChannelConfig cfd = new ComplChannelConfig();
		cfd.setCategory(category);
		cfd.setType(type);
		cfd.setSubType(subType);
		cfd.setCorpId(corpId);
		List<ComplChannelConfig> lst = complChannelConfigService.fetchByOtherParams(cfd);
		ComplChannelConfig txt = null;
		// email force send
		txt = getText(lst, "EMAIL");
		if (txt != null) {
			String tmk = generateStringText(txt.getText(), sdm);
			AgLogger.logInfo(tmk + "|" + txt.getSubject());
			savetoService("EMAIL", emailId, "", "", "", tmk, "", "1", txt.getSubject(), userId, corpId);
		}

		// SMS FORCE SEND
		txt = getText(lst, "SMS");
		if (txt != null) {
			String tmk = generateStringText(txt.getText(), sdm);
			AgLogger.logInfo(tmk);
			savetoService("SMS", "", "", "", "", tmk, mobileId, "1", txt.getSubject(), userId, corpId);
		}

	}

	private ComplChannelConfig getText(List<ComplChannelConfig> lst, String channel) {

		ComplChannelConfig cmd = null;

		for (ComplChannelConfig c : lst) {
			if (c.getChannel().equals(channel)) {
				cmd = c;
				return cmd;
			} else if (c.getChannel().equals(channel)) {
				cmd = c;
				return cmd;
			} else if (c.getChannel().equals(channel)) {
				cmd = c;
				return cmd;
			} else if (c.getChannel().equals(channel)) {
				cmd = c;
				return cmd;
			}

		}

		return cmd;
	}

	public String generateStringText(String text, SendNotificationModel sdm) {
		String tmk = text;
		tmk = tmk.replaceAll("@Customer", (sdm.getMerchantName() != null) ? sdm.getMerchantName() : "Customer");
		tmk = tmk.replaceAll("@USERID", (sdm.getUserName() != null) ? sdm.getUserName() : "USERNAME");
		tmk = tmk.replaceAll("@PASS", (sdm.getPass() != null) ? sdm.getPass() : "PASS");
		tmk = tmk.replaceAll("@COMPLAINTNUM", (sdm.getComplaintNum() != null) ? sdm.getComplaintNum() : "0");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh:mm:s a ");
		tmk = tmk.replaceAll("@DATEPARAM", (sdm.getClosureDate() != null) ? sdf.format(sdm.getClosureDate()) : "-");
		tmk = tmk.replaceAll("@RESOLUTION", (sdm.getResolution() != null) ? sdm.getResolution() : "N/A");
		tmk = tmk.replaceAll("@CLOSEBY", (sdm.getClosedBy() != null) ? sdm.getClosedBy() : "SYSTEM");
		tmk = tmk.replaceAll("@OTP", (sdm.getOtp() != null) ? sdm.getOtp() : "");

		return tmk;
	}

	private void savetoService(String channel, String emailId, String imageFile_Path, String mid, String tid,
			String value, String msdisn, String priorty, String subject, int userId, String corpId) {
		try {
			String EmailSMSNotif = AppProp.getProperty("sendingEmailSMSNotification.type");
			if (EmailSMSNotif.equals("2")) {
				NotificationSendModel m = new NotificationSendModel();
				m.setChannel(channel);
				m.setEmailId(emailId);
				m.setImageFile_Path(imageFile_Path);
				m.setMid(mid);
				m.setTid(tid);
				m.setValue(value);
				m.setMsdisn(msdisn);
				m.setPriority(priorty);
				m.setEmailSubject(subject);
				m.setUserId(userId); // after discussion
				NotificationSendControllerProxy pxt = new NotificationSendControllerProxy();
				ResponseModel rs = pxt.saveNotification(m);
				AgLogger.logInfo(rs.getCode());
			} else {
				if (msdisn != null && !msdisn.isEmpty()) {
					utilService.doSendSmsOnly(msdisn, value, corpId);
				}
				if (emailId != null && !emailId.isEmpty()) {
					utilService.doSendEmailOnly(emailId, subject, value, "0", corpId);
				}

			}
		} catch (Exception e) {
			AgLogger.logerror(null, " EXCEPTION  ", e);
		}
	}

	public ResponseModel doSentTestingEmailNotification(String category, String type, String subType,
			SendNotificationModel sdm) {
		ResponseModel responseModel = new ResponseModel();
		ComplChannelConfig cfd = new ComplChannelConfig();
		cfd.setCategory(category);
		cfd.setType(type);
		cfd.setSubType(subType);
		List<ComplChannelConfig> lst = complChannelConfigService.fetchByOtherParams(cfd);
		UserLogin user = userLoginService.validetUserid(sdm.getReciverId());
		ComplChannelConfig txt = null;
		if (!Objects.isNull(user.getEmail()) && !user.getEmail().isEmpty()) {
			txt = getText(lst, "EMAIL");
			if (txt != null) {
				String tmk = generateStringText(txt.getText(), sdm);
				AgLogger.logInfo(tmk + "|" + txt.getSubject());
				responseModel = savetoTestEmailService("EMAIL", user.getEmail(), tmk, "1", txt.getSubject());
			}
		} else {
			responseModel.setCode("9991");
			responseModel.setMessage("Email not Found.");
		}
		return responseModel;
	}

	public ResponseModel doSentTestingSmsNotification(String category, String type, String subType,
			SendNotificationModel sdm, int userId) {
		ResponseModel responseModel = new ResponseModel();
		ComplChannelConfig cfd = new ComplChannelConfig();
		cfd.setCategory(category);
		cfd.setType(type);
		cfd.setSubType(subType);
		List<ComplChannelConfig> lst = complChannelConfigService.fetchByOtherParams(cfd);
		UserLogin user = userLoginService.validetUserid(sdm.getReciverId());
		ComplChannelConfig txt = null;
		txt = getText(lst, "SMS");
		if (!Objects.isNull(user.getMsisdn()) && !user.getMsisdn().isEmpty()) {
			if (txt != null) {
				String tmk = generateStringText(txt.getText(), sdm);
				AgLogger.logInfo(tmk);
				responseModel = savetoTestSmsService("SMS", tmk, user.getMsisdn(), "1", txt.getSubject(), userId);
			}
		} else {
			responseModel.setCode("9992");
			responseModel.setMessage("MSISDN not Found.");
		}
		return responseModel;
	}

	private ResponseModel savetoTestSmsService(String channel, String value, String msdisn, String priorty,
			String subject, int userId) {
		ResponseModel rs = new ResponseModel();
		try {
			NotificationSendModel m = new NotificationSendModel();
			m.setChannel(channel);
			m.setEmailId(null);
			m.setImageFile_Path(null);
			m.setMid(null);
			m.setTid(null);
			m.setValue(value);
			m.setMsdisn(msdisn);
			m.setPriority(priorty);
			m.setEmailSubject(subject);
			m.setUserId(userId);
			NotificationSendControllerProxy pxt = new NotificationSendControllerProxy();
			rs = pxt.testSmsService(m);
			AgLogger.logInfo(rs.getCode());
		} catch (Exception e) {
			rs.setCode("9999");
			rs.setMessage("FAILED");
			AgLogger.logerror(null, " EXCEPTION  ", e);
		}
		return rs;
	}

	private ResponseModel savetoTestEmailService(String channel, String emailId, String value, String priorty,
			String subject) {
		ResponseModel rs = new ResponseModel();
		try {
			NotificationSendModel m = new NotificationSendModel();
			m.setChannel(channel);
			m.setEmailId(emailId);
			m.setImageFile_Path(null);
			m.setMid(null);
			m.setTid(null);
			m.setValue(value);
			m.setMsdisn(null);
			m.setPriority(priorty);
			m.setEmailSubject(subject);
			m.setUserId(0);
			NotificationSendControllerProxy pxt = new NotificationSendControllerProxy();
			rs = pxt.testEmailService(m);
			AgLogger.logInfo(rs.getCode());
		} catch (Exception e) {
			rs.setCode("9999");
			rs.setMessage("FAILED");
			AgLogger.logerror(null, " EXCEPTION  ", e);
		}

		return rs;
	}

}
