package com.fuel.ws.classes;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.generic.entity.UserChannel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.ComplChannelConfigService;
import com.ag.generic.service.UserChannelsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PasswordGenerator;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;
import com.google.gson.Gson;

@Component("com.fuel.ws.classes.WsUserRegister")
public class WsUserRegister implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UserChannelsService userChannelsService;

	@Autowired
	SendNotifciation sendNotifciation;

	@Autowired
	ComplChannelConfigService complChannelConfigService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {

		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED Additional Data: " + rm.getAdditionalData());

		try {

			String userCode = rm.getAdditionalData().get("UserCode").toString();
			String firstName = rm.getAdditionalData().get("FirstName").toString();
			String lastName = rm.getAdditionalData().get("LastName").toString();
			String email = rm.getAdditionalData().get("Email").toString();
			String msisdn = rm.getAdditionalData().get("Mobile").toString();

			firstName = firstName.toUpperCase();
			lastName = lastName.toUpperCase();

			String mPattern = "^0([3]{1})([0123456]{1})([0-9]{8})$";

			if (userCode == null || userCode.length() == 0) {
				return UtilAccess.generateResponse("8888", "UserID cant not be null.");
			} else if (firstName == null || firstName.length() == 0) {
				return UtilAccess.generateResponse("8888", "UserName can not be null.");
			} else if (lastName == null || lastName.length() == 0) {
				return UtilAccess.generateResponse("8888", "MSISDN can not be null.");
			}

			else if (email == null || email.length() == 0) {
				return UtilAccess.generateResponse("8888", "EMAIL can not be null.");
			} else if (!Pattern.matches(mPattern, msisdn)) {
				return UtilAccess.generateResponse("8888", "Please enter Valid Mobile Number.");
			} else if (!validate(email)) {
				return UtilAccess.generateResponse("8888", "Please enter Valid Email ID.");
			}

			else {
				if (userCode.length() != 0 && userCode.length() != 0 && firstName.length() != 0
						&& lastName.length() != 0 && email.length() != 0 && msisdn.length() != 0) {

					String defaultGroupCodeUser = AppProp.getProperty("fuel.group.code");

					String passwordGenerated = PasswordGenerator.generateStrongPassword();
					AgLogger.logInfo(userCode + ": DONE");

					int m = saveUser(userCode, firstName, lastName, msisdn, email, defaultGroupCodeUser, rm.getCorpId(),
							passwordGenerated);
					AgLogger.logInfo(userCode + ": USER CREATE IN DB WITH ID" + m);

					if (m != 0) {
						doInsertDefaultChannelDetails(m, msisdn, email);
						AgLogger.logInfo(userCode + ": USER INSERT CHANNEL WITH LOGIN ID" + m);

						SendNotificationModel sdm = new SendNotificationModel();
						UserLogin ukl = userLoginService.validetUserid(m);
						sdm.setAccountOpeningDate("N/A");
						sdm.setClosedBy("N/A");
						sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
						sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
						sdm.setComplaintNum("N/A");
						sdm.setMerchantName(firstName + " " + lastName);
						sdm.setPass(passwordGenerated);
						sdm.setResolution("N/A");
						sdm.setUserName(userCode);
						sdm.setReciverId(m);
						sendNotifciation.doTask("001", "0002", "00001", sdm, ukl.getUserId());

						String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "link_card",
								ukl.getUserId(), ukl.getUserCode(), null, ukl.getGroupCode(), null);

						if (responseProc.equals("9999")) {
							AgLogger.logInfo("Link Card Failed Deactivated User with userId:" + ukl.getUserId());
							ukl.setIsActive(0);
							userLoginService.updateUser(ukl);
							return UtilAccess.generateResponse("8881", "User Not Registered Successfully.");
						} else {
							FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);
							if (mdl.getCode().equals("0000")) {
								AgLogger.logInfo("Card Added Successfully.");
								return UtilAccess.generateResponse("0000", "User Registered Successfully.");
							} else {
								AgLogger.logInfo("Link Card Issue Deactivated User with userId:" + ukl.getUserId());
								ukl.setIsActive(0);
								userLoginService.updateUser(ukl);
								return UtilAccess.generateResponse(mdl.getCode(), mdl.getMessage());

							}

						}

					} else {
						return UtilAccess.generateResponse("8888", "User Not Registered Successfully.");
					}

				} else {
					return UtilAccess.generateResponse("8888", "Please enter valid data.");
				}
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return UtilAccess.generateResponse("0000", "User Registered Successfully.");
	}

	static boolean validate(String emailStr) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	void doInsertDefaultChannelDetails(int userLoginId, String requestMobile, String requestEmail) {
		List<String> jkl = complChannelConfigService.fetchAll();
		for (String ck : jkl) {
			UserChannel clm = new UserChannel();
			clm.setChannel(ck);
			clm.setIsActive("Y");
			clm.setUserLoginId(userLoginId);

			if (ck.equals("EMAIL")) {
				clm.setValue(requestEmail);
			} else {
				clm.setValue(requestMobile);
			}
			userChannelsService.insert(clm);
		}

	}

	int saveUser(String userCode, String fName, String lName, String mobile, String email, String groupCode,
			String corpId, String pwd) {

		int kl = 0;
		UserLogin ursl = new UserLogin();
		ursl.setCrBy("0");
		ursl.setCrOn(new Timestamp(new java.util.Date().getTime()));
		ursl.setIsActive(1);
		ursl.setUserCode(userCode);
		ursl.setFirstName(fName);
		ursl.setLastName(lName);

		ursl.setGroupCode(Integer.parseInt(groupCode));
		ursl.setUserName(fName + " " + lName);
		ursl.setUserType("internal");
		ursl.setMid("All");

		ursl.setForceLogin(1);
		ursl.setEmail(email);
		ursl.setMsisdn(mobile);
		ursl.setTempPass(pwd);
		ursl.setPassword(UtilAccess.md5Java(pwd));
		ursl.setCorpId(corpId);
		ursl.setIsReg(0);
		ursl.setAdditionalData("N/A || N/A || N/A || N/A");
		kl = userLoginService.insertUser(ursl);

//		ChainMerchant smk = new ChainMerchant();
//		smk.setAddress("");
//		smk.setChainMerchantMid(requestMid);
//		smk.setCity(usMDetails.getCity());
//		smk.setEntryBy(loginID);
//		smk.setEntryDate(new Timestamp(new java.util.Date().getTime()));
//		smk.setIsChainMerchant(flagChain);
//		smk.setMid(requestMid);
//		smk.setName(userName);
//		smk.setOffice(additionalData[2]);
//		smk.setUserLoginId(kl);
//		smk.setUserName(requestMid);
//		chainMerchantsService.insert(smk);

		return kl;
	}

}
