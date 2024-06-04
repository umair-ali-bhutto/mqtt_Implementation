package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserChannel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserChannelsService;
import com.ag.generic.service.UserGroupService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.FedRates;
import com.ag.mportal.services.FedRateService;

@Component("com.generic.ws.classes.WsEditUser")
public class WsEditUser implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UserGroupService userGroupService;

	@Autowired
	UserSettingService userSettingService;
	
	@Autowired
	UserChannelsService userChannelService;

	@Autowired
	FedRateService fedRateService;

	@Autowired
	WsResetPassword wsResetPassword;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String loggedUserCode = Objects.isNull(rm.getAdditionalData().get("loggedUserCode")) ? null
					: rm.getAdditionalData().get("loggedUserCode").toString();

			String userCode = Objects.isNull(rm.getAdditionalData().get("userCode")) ? null
					: rm.getAdditionalData().get("userCode").toString();
			String groupCode = Objects.isNull(rm.getAdditionalData().get("groupCode")) ? null
					: rm.getAdditionalData().get("groupCode").toString();
			String province = Objects.isNull(rm.getAdditionalData().get("province")) ? null
					: rm.getAdditionalData().get("province").toString();
			String mdrOnUs = Objects.isNull(rm.getAdditionalData().get("mdrOnUs")) ? "0.0"
					: rm.getAdditionalData().get("mdrOnUs").toString();
			String mdrOffUs = Objects.isNull(rm.getAdditionalData().get("mdrOffUs")) ? "0.0"
					: rm.getAdditionalData().get("mdrOffUs").toString();
			String userStatus = Objects.isNull(rm.getAdditionalData().get("userStatus")) ? null
					: rm.getAdditionalData().get("userStatus").toString();

			String email = Objects.isNull(rm.getAdditionalData().get("email")) ? null
					: rm.getAdditionalData().get("email").toString();
			String cityName = Objects.isNull(rm.getAdditionalData().get("cityName")) ? null
					: rm.getAdditionalData().get("cityName").toString();
			String regionName = Objects.isNull(rm.getAdditionalData().get("regionName")) ? null
					: rm.getAdditionalData().get("regionName").toString();
			String cnic = Objects.isNull(rm.getAdditionalData().get("cnic")) ? null
					: rm.getAdditionalData().get("cnic").toString();
			String mobile = Objects.isNull(rm.getAdditionalData().get("mobileNumber")) ? null
					: rm.getAdditionalData().get("mobileNumber").toString();
			String address = Objects.isNull(rm.getAdditionalData().get("address")) ? null
					: rm.getAdditionalData().get("address").toString();

			if (Objects.isNull(userCode) || userCode.isEmpty()) {
				response.setCode("0001");
				response.setMessage("Required field is missing.");
				AgLogger.logInfo(rm.getUserid(), "USER CODE NOT FOUND: " + userCode);
			} else {
				UserLogin user = userLoginService.validetUserWithoutStatus(userCode, rm.getCorpId());
				if (Objects.isNull(user)) {
					response.setCode("0002");
					response.setMessage("User not found.");
					AgLogger.logInfo(rm.getUserid(), "USER NOT FOUND: " + userCode);
				} else {
					String tempMobile = user.getMsisdn();
					String tempEmail = user.getEmail();
					
					
					
					boolean isUserMerchant = false;
					List<Integer> lstGroupCodeNonMerchants = getMerchantGroupCodes();
					for (Integer merchantGroupCode : lstGroupCodeNonMerchants) {
						if (user.getGroupCode() == merchantGroupCode) {
							isUserMerchant = true;
							break;
						}
					}
					if (!isUserMerchant) {
						// Its Non Merchant Group
						if (groupCode != null) {
							int finGroupCode = 0;
							try {
								Double d = Double.parseDouble(groupCode);
								finGroupCode = d.intValue();
							} catch (Exception e) {
								finGroupCode = Integer.parseInt(groupCode);
							}

							user.setGroupCode(finGroupCode);
						}
						user.setEmail(email);
						user.setCity(cityName);
						user.setCnic(cnic);
						user.setMsisdn(mobile);
						user.setUpdBy(loggedUserCode);
						user.setRegion(regionName);
						int k = (int) Double.parseDouble(userStatus);
						user.setIsActive(k);
						if (!Objects.isNull(address)) {
							address = address.replaceAll(",", "||");
							user.setAdditionalData(address);
						}
						user.setUpdOn(new Timestamp(new java.util.Date().getTime()));
						userLoginService.updateUser(user);
						
						
						response.setCode("0000");
						response.setMessage("User Edited Successfully.");
						
						
						
						
						

						UserLogin uml = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
						HashMap<Object, Object> obj = new HashMap<Object, Object>();
						obj.put("user", uml);
						response.setData(obj);

					} else {
						// Its Merchant Group
						UserSetting userSettingMdrOnUs = userSettingService.fetchSettingByIdName(user.getUserId(),
								"MDR_ON_US");
						userSettingMdrOnUs.setIsActive(0);
						userSettingService.updateProp(userSettingMdrOnUs);

						UserSetting userSettingMdrOfUs = userSettingService.fetchSettingByIdName(user.getUserId(),
								"MDR_OFF_US");
						userSettingMdrOfUs.setIsActive(0);
						userSettingService.updateProp(userSettingMdrOfUs);

						UserSetting userSettingProvince = userSettingService.fetchSettingByIdName(user.getUserId(),
								"PROVINCE");
						userSettingProvince.setIsActive(0);
						userSettingService.updateProp(userSettingProvince);

						UserSetting userSettingfedDrate = userSettingService.fetchSettingByIdName(user.getUserId(),
								"FED_RATES");
						userSettingfedDrate.setIsActive(0);
						userSettingService.updateProp(userSettingfedDrate);

						List<UserSetting> lstUserSettings = new ArrayList<UserSetting>();
						lstUserSettings.add(UtilAccess.createUserSettingObj(mdrOnUs, "MDR_ON_US", user.getUserId(),
								rm.getUserid()));
						lstUserSettings.add(UtilAccess.createUserSettingObj(mdrOffUs, "MDR_OFF_US", user.getUserId(),
								rm.getUserid()));
						lstUserSettings.add(UtilAccess.createUserSettingObj(province, "PROVINCE", user.getUserId(),
								rm.getUserid()));
						FedRates fedRates = fedRateService.retrieveRateByProvince(province);
						lstUserSettings.add(UtilAccess.createUserSettingObj(fedRates.getRateValue() + "", "FED_RATES",
								user.getUserId(), rm.getUserid()));
						userSettingService.insertLstUserSettings(lstUserSettings);

						// Status
						int k = (int) Double.parseDouble(userStatus);
						user.setIsActive(k);
						user.setMsisdn(mobile);
						user.setEmail(email);
						user.setUpdOn(new Timestamp(new java.util.Date().getTime()));
						user.setUpdBy(loggedUserCode);
						userLoginService.updateUser(user);
						
						
						if (tempMobile != mobile) {
							List<UserChannel> lst = userChannelService.fetchAllByID(user.getUserId());
							for(UserChannel c: lst) {
								if(c.getChannel().equalsIgnoreCase("SMS")) {
									c.setValue(mobile);
									userChannelService.update(c);
									
								}
							}
						} 

						if (tempEmail != email) {
							List<UserChannel> lst = userChannelService.fetchAllByID(user.getUserId());
							for(UserChannel c: lst) {
								if(c.getChannel().equalsIgnoreCase("EMAIL")) {
									c.setValue(email);
									userChannelService.update(c);
									
								}
							}
						} 
						response.setCode("0000");
						response.setMessage("User Edited Successfully.");
						
						

					}

				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			AgLogger.logerror(getClass(), "EXCEPTION IN  EDIT USER: ", ex);
			response.setCode("9995");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	public List<Integer> getMerchantGroupCodes() {
		List<Integer> ids = new ArrayList<Integer>();
		String value = AppProp.getProperty("defult.merchant.group");
		if (value.contains(",")) {
			String[] splt = value.split("\\,");
			for (String sm : splt) {
				ids.add(Integer.parseInt(sm));
			}
		} else {
			ids.add(Integer.parseInt(value));
		}

		return ids;
	}

}