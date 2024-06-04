package com.ag.config;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.BroadcastMsg;
import com.ag.generic.entity.BroadcastMsgDetail;
import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.entity.ComplAssignmentSetup;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.ConfigCorporate;
import com.ag.generic.entity.PlayersIdsPushNotification;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.DataWrapper;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.BroadCastMessageDetailService;
import com.ag.generic.service.BroadCastMessageService;
import com.ag.generic.service.ConfigCorporateService;
import com.ag.generic.service.PlayerIdsPushNotificationService;
import com.ag.generic.service.impl.ComplAssignmentsServiceImpl;
import com.ag.generic.service.impl.ComplAssignmentsSetupServiceImpl;
import com.ag.generic.service.impl.ComplaintsServiceImpl;
import com.ag.generic.service.impl.UserLoginServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;

@Component
public class AssignmentSchedular {

	@Autowired
	UserLoginServiceImpl userLoginService;

	@Autowired
	ComplaintsServiceImpl complaintsService;

	@Autowired
	ComplAssignmentsSetupServiceImpl complAssignmentsSetupService;

	@Autowired
	ComplAssignmentsServiceImpl complAssignmentsServiceImpl;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	PlayerIdsPushNotificationService playerIdsPushNotificationService;

	@Autowired
	BroadCastMessageService broadCastMessageService;

	@Autowired
	BroadCastMessageDetailService broadCastMessageDetailService;

	@Autowired
	ConfigCorporateService configCorporateService;

	@Autowired
	SendNotifciation sendNotifciation;

	@Value("${assignment.schedular}")
	public String assignmentSchedular;

	@Value("${broadcast.schedular}")
	public String broadcastSchedular;

	@Scheduled(cron = "*/5 * * * * ?")
	@Transactional
	public void doProcessAssignment() {
		if (assignmentSchedular.equalsIgnoreCase("true")) {
			try {
				AgLogger.logDebug("", "@@@ ASSIGNMENT SCHEDULAR STARTED.");
				List<Complaint> lst = complaintsService.fetchAllComplaintOnlyNew();
				AgLogger.logInfo("", "ASSIGNMENT SCHEDULAR SIZE." + lst.size());
				if (lst.size() != 0) {

					for (Complaint copm : lst) {
						UserLogin lmk = userLoginService.validetUserid(Integer.parseInt(copm.getEntryBy()));
						if (lmk != null) {
							String defaultCorpId = lmk.getCorpId();
							HashMap<String, Object> allMerchantMap = userLoginService.fetchAllUsers(defaultCorpId);
							List<UserLogin> allAdminUsers = userLoginService.fetchAllAdmins(defaultCorpId);

							int usID = 0;
							ComplAssignmentSetup c = null;
							ComplAssignmentSetup cl = new ComplAssignmentSetup();
							cl.setCategory(copm.getCategory());
							cl.setType(copm.getType());
							cl.setSubType(copm.getSubType());
							c = complAssignmentsSetupService.fetchByOtherParams(cl.getCategory(), cl.getType(),
									cl.getSubType(), 0);
							AgLogger.logInfo("", copm.getId() + "");
							if (c != null) {
								usID = getUserId(c.getComplCategoryType(), copm.getMid(), allAdminUsers, allMerchantMap,
										c, defaultCorpId, copm.getEntryBy());

								if (usID != 0 && c != null) {
									UserLogin usl = userLoginService.validetUserid(usID);
									if (usl != null) {
										String dbText = AppProp.getProperty("user.dashboard.comp.text");
										DataWrapper dwp = new DataWrapper(copm.getDescription(), c.getCategory(),
												c.getType(), c.getSubType(), usl.getUserName(), dbText);
										ComplAssignment ctds = new ComplAssignment();
										ctds.setCategory(c.getCategory());
										ctds.setCompId(copm.getId());
										ctds.setDescription(dwp.getDashBoardText());
										ctds.setEntryBy("assignment_schedular_upd");
										AgLogger.logInfo("",
												new Timestamp(new java.util.Date().getTime()) + ".............");
										ctds.setEntryDate(new Timestamp(new java.util.Date().getTime()));
										ctds.setMid(copm.getMid());
										ctds.setPriority(c.getPriority() + "");
										ctds.setScreenId(c.getScreenId());
										ctds.setSubType(c.getSubType());
										ctds.setType(c.getType());
										ctds.setUserId(usID);
										ctds.setClassName(c.getClassName());
										complAssignmentsServiceImpl.insert(ctds);
										copm.setStatus(UtilAccess.complStatusAssigned);
										copm.setAssignedTo(usID + "");
										copm.setAssignedDate(new Timestamp(new java.util.Date().getTime()));
										copm.setLastUpdated(new Timestamp(new java.util.Date().getTime()));
										complaintsService.updateComplaint(copm);

										// Start - Send Notification
										SendNotificationModel sdm = new SendNotificationModel();
										sdm.setAccountOpeningDate("N/A");
										sdm.setClosedBy("N/A");
										sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
										sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
										sdm.setComplaintNum(String.valueOf(copm.getId()));
										sdm.setMerchantName(usl.getUserName());
										sdm.setPass("N/A");
										sdm.setResolution("N/A");

										sdm.setUserName(usl.getUserName());
										sdm.setReciverId(usl.getUserId());
										sendNotifciation.doTask("998", copm.getType(), copm.getSubType(), sdm, usID);
										// End - Send Notification

									} else {
										AgLogger.logInfo("", "USER NOT FOUND");
									}
								} else {
									AgLogger.logInfo("@@@", "USID OR COMPLAINT ASSIGNMENT SETUP NOT FOUND");
								}
							} else {
								AgLogger.logInfo("!!!!",
										"Data not found on complAssignmentsSetup for category: " + cl.getCategory()
												+ " type: " + cl.getType() + " subtype: " + cl.getSubType() + " level: "
												+ 0);
							}

						} else {
							AgLogger.logInfo("", "COMPLAINT USER NOT FOUND IN DB " + copm.getEntryBy());
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);
			}

			AgLogger.logDebug("", "@@@ ASSIGNMENT SCHEDULAR ENDED.");
		}
	}

	// need to change it to corpid wise
	@Scheduled(cron = "*/5 * * * * ?")
	@Transactional
	public void doProcessBoardCast() {
		if (broadcastSchedular.equalsIgnoreCase("true")) {
			List<PlayersIdsPushNotification> lstid = null;
			try {
				AgLogger.logDebug("", "@@@ BROADCAST SCHEDULAR STARTED.");
				lstid = playerIdsPushNotificationService.searchAll();
				List<BroadcastMsg> msgList = broadCastMessageService.searchBroadCastMessage();
				AgLogger.logInfo("", "BRD MESSAGES SIZE  " + msgList.size());
				if (msgList.size() != 0) {
					for (BroadcastMsg m : msgList) {
						if (!m.getSentTo().equals("ALL")) {
							PlayersIdsPushNotification appId = fetchAppid(Integer.parseInt(m.getSentTo()), lstid);
							if (appId != null) {
								UserLogin user = userLoginService.validetUserid(appId.getUserLoginId());
								ConfigCorporate corporate = configCorporateService.fetchByCorpId(user.getCorpId());

								String[] b = doSentNotification(m.getMessage(), m.getMessageTitle(),
										appId.getPlayerId(), m.getContent(), appId.getChannel(),
										corporate.getPackageName());
								if (b[0].equals("0000")) {
									m.setIsSent("Y");
									broadCastMessageService.updateBroadCastMessagById(m);
									BroadcastMsgDetail bdm = new BroadcastMsgDetail();
									bdm.setEntryDate(new Timestamp(new java.util.Date().getTime()));
									bdm.setMessageId(m.getId());
									bdm.setReadDate(null);
									bdm.setSendDate(new Timestamp(new java.util.Date().getTime()));
									bdm.setUserLoginId(m.getSentTo());
									broadCastMessageDetailService.save(bdm);
								} else {
									m.setIsSent("E");
								}
								m.setMsg(b[1]);
							}

						} else {
							AgLogger.logInfo("Broadcast Sending To All.");
							List<PlayersIdsPushNotification> fetchall = playerIdsPushNotificationService.searchAll();
							for (PlayersIdsPushNotification ftd : fetchall) {
								UserLogin user = userLoginService.validetUserid(ftd.getUserLoginId());
								ConfigCorporate corporate = configCorporateService.fetchByCorpId(user.getCorpId());
								String[] b = doSentNotification(m.getMessage(), m.getMessageTitle(), ftd.getPlayerId(),
										m.getContent(), ftd.getChannel(), corporate.getPackageName());
								if (b[0].equals("0000")) {
									m.setIsSent("Y");
									broadCastMessageService.updateBroadCastMessagById(m);
									BroadcastMsgDetail bdm = new BroadcastMsgDetail();
									bdm.setEntryDate(new Timestamp(new java.util.Date().getTime()));
									bdm.setMessageId(m.getId());
									bdm.setReadDate(null);
									bdm.setSendDate(new Timestamp(new java.util.Date().getTime()));
									bdm.setUserLoginId(m.getSentTo());
									broadCastMessageDetailService.save(bdm);
								} else {
									m.setIsSent("E");
								}
								m.setMsg(b[1]);
							}
						}
						m.setSendDate(new Timestamp(new java.util.Date().getTime()));
						broadCastMessageService.updateBroadCastMessagById(m);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				AgLogger.logerror(getClass(), "EXCEPTION  IN BROADCAST", e);
			}

			AgLogger.logDebug("", "@@@ BROADCAST SCHEDULAR ENDED.");
		}
	}

	// Assignment Util Work
	public int getUserId(String complCatType, String copmMid, List<UserLogin> allAdminsList,
			HashMap<String, Object> allMerchantMap, ComplAssignmentSetup c, String defaultCorpId,
			String complaintInitiatedBy) {
		int usID = 0;

		String vCode = "0.0";

		if (complCatType.equalsIgnoreCase("GENERAL")) {
			usID = c.getUserId();
			vCode = "1.0";
			if (usID == 0) {
				vCode = "1.0.1";
				UserLogin usl = userLoginService.validetUser(copmMid, defaultCorpId);
				usID = usl.getUserId();
			}
		} else if (complCatType.equalsIgnoreCase("REGION")) {
			vCode = "2.0";
			UserLogin merchantUser = fetchMerchant(copmMid, allMerchantMap, defaultCorpId);
			if (merchantUser != null) {
				vCode = "2.0.1";
				UserLogin adminUser = getUserAssigedByRegion(allAdminsList, merchantUser.getRegion());
				if (adminUser != null) {
					vCode = "2.0.1.1";
					usID = adminUser.getUserId();
				} else {
					vCode = "2.0.1.2";
					usID = Integer.parseInt(AppProp.getProperty("super.user.id"));
				}

			} else {
				vCode = "2.0.2";
				usID = Integer.parseInt(AppProp.getProperty("super.user.id"));
			}
		}

		else if (complCatType.equalsIgnoreCase("CITY")) {
			vCode = "3.0";
			UserLogin merchantUser = fetchMerchant(copmMid, allMerchantMap, defaultCorpId);
			if (merchantUser != null) {
				vCode = "3.0.1";
				UserLogin adminUser = getUserAssigedByCityandRegion(allAdminsList, merchantUser.getRegion(),
						merchantUser.getCity());
				if (adminUser != null) {
					vCode = "3.0.1.1";
					usID = adminUser.getUserId();
				} else {
					vCode = "3.0.1.2";
					usID = Integer.parseInt(AppProp.getProperty("super.user.id"));
				}

			} else {
				vCode = "3.0.2";
				usID = Integer.parseInt(AppProp.getProperty("super.user.id"));
			}
		} else if (complCatType.equalsIgnoreCase("SELF")) {
			vCode = "4.0";
			usID = Integer.parseInt(complaintInitiatedBy);

		} else {
			AgLogger.logInfo("", " NO ANY ASSIGNMENT DETAILS FOUND WITH :" + copmMid + "|" + c.getComplCategoryType());
		}
		AgLogger.logInfo("", " ASSIGNMENT TO :" + usID + " with complaint ID " + copmMid + "|"
				+ c.getComplCategoryType() + "|" + vCode);

		return usID;
	}

	private UserLogin getUserAssigedByRegion(List<UserLogin> uslList, String value) {
		UserLogin us = null;
		for (UserLogin u : uslList) {
			if (u.getRegion().equals(value)) {
				return u;
			}
		}
		return us;
	}

	private UserLogin getUserAssigedByCityandRegion(List<UserLogin> uslList, String value, String cityValue) {
		UserLogin us = null;
		for (UserLogin u : uslList) {
			if (u.getRegion().equals(value) && u.getCity().equals(cityValue)) {
				return u;
			}
		}
		return us;
	}

	private UserLogin fetchMerchant(String mid, HashMap<String, Object> allUsersMap, String defaultCorpId) {
		UserLogin user = null;
		if (allUsersMap.get(mid) != null) {
			user = (UserLogin) allUsersMap.get(mid);
		} else {
			user = userLoginService.fetchUserByMid(mid, defaultCorpId);
		}

		return user;

	}

	private PlayersIdsPushNotification fetchAppid(int userId, List<PlayersIdsPushNotification> lstid) {
		if (lstid != null) {
			for (PlayersIdsPushNotification a : lstid) {
				if (a.getUserLoginId() == userId) {
					return a;
				}
			}
		}
		return null;
	}

	private String[] doSentNotification(String content, String heading, String appId, String contentJson,
			String channel, String packageName) {
		String[] isSent = new String[2];
		String jsonResponse;
		isSent[0] = "0000";
		isSent[1] = "SUCCESS";
		try {

			URL url = new URL(AppProp.getProperty("one.signal.url"));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Authorization", AppProp.getProperty("one.signal.auth"));
			con.setRequestMethod("POST");

			JSONObject oCOntent = new JSONObject();
			oCOntent.put("en", content);

			JSONObject oHeading = new JSONObject();
			oHeading.put("en", heading);

			JSONArray ar = new JSONArray();
			ar.put(appId);
			JSONObject jcontentJson = null;
			if (contentJson != null) {
				jcontentJson = new JSONObject(contentJson);
			}

			JSONObject obj = new JSONObject();

			if (channel.equalsIgnoreCase("ios")) {
				obj.put("app_id", AppProp.getProperty("os." + packageName + ".ios"));
			} else {
				obj.put("app_id", AppProp.getProperty("os." + packageName + ".android"));
			}

			obj.put("include_player_ids", ar);
			if (jcontentJson != null) {
				obj.put("data", jcontentJson);
			}
			obj.put("contents", oCOntent);
			obj.put("headings", oHeading);

			AgLogger.logDebug("", "APP MSG SENDING URL " + url);
			AgLogger.logDebug("", "APP MSG SENDING REQ :" + obj.toString());

			byte[] sendBytes = obj.toString().getBytes("UTF-8");
			con.setFixedLengthStreamingMode(sendBytes.length);

			OutputStream outputStream = con.getOutputStream();
			outputStream.write(sendBytes);

			int httpResponse = con.getResponseCode();
			AgLogger.logDebug("", "httpResponse: " + httpResponse);

			if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
				Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				isSent[0] = "0000";
				isSent[1] = jsonResponse;
				scanner.close();
			} else {
				Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				scanner.close();
				isSent[0] = "0001";
				isSent[1] = jsonResponse;
			}
			AgLogger.logInfo("", "APP MSG SENDING RES :" + jsonResponse);
		} catch (Exception e) {
			isSent[0] = "0001";
			isSent[1] = e.getMessage();
			e.printStackTrace();
		}

		return isSent;
	}

}
