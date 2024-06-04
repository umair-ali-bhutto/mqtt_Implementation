package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserChannel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.ComplChannelConfigService;
import com.ag.generic.service.ConfigCityService;
import com.ag.generic.service.ConfigCountryService;
import com.ag.generic.service.ConfigRegionService;
import com.ag.generic.service.UserChannelsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PasswordGenerator;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;

@Component("com.generic.ws.classes.WsAddNewUser")
public class WsAddNewUser implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	ComplChannelConfigService complChannelConfigService;

	@Autowired
	UserChannelsService userChannelsService;

	@Autowired
	ConfigRegionService configRegionService;

	@Autowired
	ConfigCityService configCityService;

	@Autowired
	ConfigCountryService configCountryService;

	@Autowired
	SendNotifciation sendNotifciation;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			UserLogin user = null;
			String userCode = rm.getAdditionalData().get("userCode").toString();
			String userAmaName = rm.getAdditionalData().get("userName").toString();
			String userCn = rm.getAdditionalData().get("userMobile").toString();
			String userEmail = rm.getAdditionalData().get("userEmail").toString();
			String userCnic = rm.getAdditionalData().get("userCnic").toString();
			String selectedCityStr = rm.getAdditionalData().get("selectedCityStr").toString();
			String userGroup = rm.getAdditionalData().get("userGroupId").toString();
			String adminId = rm.getUserid().toString();

			if (!Objects.isNull(userCode) && !userCode.isEmpty()) {
				user = userLoginService.validetUser(userCode, rm.getCorpId());
			}

			if (Objects.isNull(user)) {

				String cellNumberRegex = "^0([3]{1})([0123456]{1})([0-9]{8})$";
				String cityNameInsert = "";
				String regionNameInsert = "";
				String countryNameInsert = "";

				if (userCode == null || userCode.length() == 0) {
					response.setCode("0001");
					response.setMessage("Enter User ID.");
					return response;
				}

				if (userAmaName == null || userAmaName.isEmpty()) {
					response.setCode("0001");
					response.setMessage("Enter User Name.");
					return response;
				}

				if (userCn == null || userCn.length() == 0
						|| !java.util.regex.Pattern.matches(cellNumberRegex, userCn)) {
					response.setCode("0001");
					response.setMessage("Enter valid Msisdn.");
					return response;
				}
				if (userEmail == null || userEmail.length() == 0 || !validate(userEmail)) {
					response.setCode("0001");
					response.setMessage("Enter valid Email.");
					return response;
				}
				if (userCnic == null || userCnic.length() != 13) {
					response.setCode("0001");
					response.setMessage("Enter valid CNIC.");
					return response;
				}
				if (selectedCityStr == null || selectedCityStr.isEmpty()) {
					response.setCode("0001");
					response.setMessage("Select City.");
					return response;
				} else {
					String[] stringSplit = selectedCityStr.split("-");
					cityNameInsert = stringSplit[0];
					countryNameInsert = stringSplit[1];
					regionNameInsert = stringSplit[2];
					if (regionNameInsert == null || regionNameInsert.isEmpty()) {
						response.setCode("0001");
						response.setMessage("Region is not selected.");
						return response;
					}
				}

				if (userGroup == null || userGroup.isEmpty()) {
					response.setCode("0001");
					response.setMessage("Group is not selected.");
					return response;
				}

				try {

					UserLogin validateUser = null;
					if (Objects.equals(userGroup, "26")) {
						validateUser = userLoginService.validetUserByGroup(regionNameInsert, cityNameInsert, null,
								userGroup);
					} else if (Objects.equals(userGroup, "30")) {
						validateUser = userLoginService.validetUserByGroup(null, null, countryNameInsert, userGroup);
					}

					if (!Objects.isNull(validateUser)) {
						response.setCode("0001");
						response.setMessage("User already exist with selected group.");
						return response;
					}

					String[] result = createNewUserProcess(rm, userCode, userAmaName, userCn, userEmail, userGroup,
							cityNameInsert, regionNameInsert, countryNameInsert, userCnic, adminId, rm.getCorpId());
					String[] resp = new String[2];
					AgLogger.logInfo(rm.getUserid(), "message" + result[0] + "|" + result[1]);

					if (result[0].equals("0000")) {
						resp[0] = "0000";
						resp[1] = "SUCCESS";

						response.setCode("0000");
						response.setMessage("User Created Successfully.");

					} else {
						resp[0] = "1111";
						resp[1] = "FAIL";
						response.setCode("0000");
						response.setMessage("User Not Created Successfully.");
					}
					AgLogger.logInfo(rm.getUserid(), "Response" + resp[0] + "|" + resp[1]);
				} catch (Exception e) {
					e.printStackTrace();
					response.setCode("0001");
					response.setMessage("Please enter valid data.");
					return response;
				}

			} else {
				response.setCode("0001");
				response.setMessage("User Exists " + userCode + " Choose another User ID");
				return response;
			}

		} catch (Exception ex) {

			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	public String[] createNewUserProcess(RequestModel rm, String userID, String userName, String requestMobile,
			String requestEmail, String groupId, String city, String region, String country, String cnic,
			String adminId, String corpId) {
		String[] resp = new String[2];
		resp[0] = "0000";
		resp[1] = "SUCCESS";
		String password;
		try {
			password = PasswordGenerator.generateStrongPassword();

			int m = saveUser(userID, userName, requestMobile, requestEmail, groupId, city, region, country, cnic,
					password, adminId, corpId, rm.getUserid());

			AgLogger.logInfo(rm.getUserid(), userID + ": USER CREATE IN DB WITH ID" + m);

			UserLogin user = userLoginService.validetUser(userID, rm.getCorpId());

			doInsertDefaultChannelDetails(m, requestMobile, requestEmail, userID);
			AgLogger.logInfo(rm.getUserid(), userID + ": USER INSERT CHANNEL WITH LOGIN ID" + m);

			SendNotificationModel sdm = new SendNotificationModel();

			sdm.setPass(user.getTempPass());
			sdm.setUserName(user.getUserCode());
			sdm.setMerchantName(user.getUserName());
			sdm.setReciverId(user.getUserId());
			sdm.setAccountOpeningDate(user.getCrOn().toString());

			sdm.setClosedBy(null);
			sdm.setRequestComplDate(null);
			sdm.setClosureDate(null);
			sdm.setComplaintNum(null);
			sdm.setResolution(null);

			sendNotifciation.doTask("999", "0001", "00003", sdm, user.getUserId());

		} catch (Exception e) {
			AgLogger.logInfo(rm.getUserid(), userID + ": FAILED USER INSERTION in DB.");
			resp[0] = "1111";
			resp[1] = "FAILED";
		} finally {
			password = "";
		}

		return resp;
	}

	public boolean validate(String emailStr) {
		java.util.regex.Pattern VALID_EMAIL_ADDRESS_REGEX = java.util.regex.Pattern
				.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", java.util.regex.Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	int saveUser(String useriD, String userName, String requestMobile, String requestEmail, String groupId, String city,
			String region, String country, String cnic, String password, String adminId, String coorpId,
			String userIds) {
		UserLogin usMDetails = null;
		usMDetails = userLoginService.validetUser(useriD, coorpId);
		int kl = 0;
		if (Objects.isNull(usMDetails)) {
			UserLogin ursl = new UserLogin();
			ursl.setCrBy(userIds + "");
			ursl.setCrOn(new Timestamp(new java.util.Date().getTime()));
			ursl.setIsActive(1);
			ursl.setUserCode(useriD);
			ursl.setGroupCode(Integer.parseInt(groupId));
			ursl.setUserName(userName);
			ursl.setFirstName(userName);
			ursl.setUserType("internal");
			ursl.setMid("ALL");
			ursl.setForceLogin(1);
			ursl.setEmail(requestEmail);
			ursl.setCnic(cnic);
			ursl.setMsisdn(requestMobile);
			ursl.setTempPass(password);
			ursl.setPassword(UtilAccess.md5Java(password));
			ursl.setCity(city);
			ursl.setRegion(region);
			ursl.setCorpId(coorpId);
			ursl.setLoyaltyCorpId(coorpId);
			ursl.setIsReg(0);
			ursl.setAdditionalData("N/A || N/A || N/A || N/A");
			kl = userLoginService.insertUser(ursl);
		}
		return kl;
	}

	void doInsertDefaultChannelDetails(int userLoginId, String requestMobile, String requestEmail, String requestMid) {
		List<String> jkl = complChannelConfigService.fetchAll();
		for (String ck : jkl) {
			UserChannel clm = new UserChannel();
			clm.setChannel(ck);
			clm.setIsActive("Y");
			clm.setUserLoginId(userLoginId);
			if (ck.equalsIgnoreCase("email")) {
				clm.setValue(requestEmail);
			} else {
				clm.setValue(requestMobile);
			}
			userChannelsService.insert(clm);
		}

	}

}