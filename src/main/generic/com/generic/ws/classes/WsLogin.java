package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.main.qr.EmvQrModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.Captcha;
import com.ag.generic.entity.ComplChannelConfig;
import com.ag.generic.entity.ConfigCity;
import com.ag.generic.entity.ConfigRegion;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.model.GroupDeclarModel;
import com.ag.generic.model.LDAPResponseModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.ScreenDeclarModel;
import com.ag.generic.model.WsMenuModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.CaptchaService;
import com.ag.generic.service.ComplChannelConfigService;
import com.ag.generic.service.ConfigCityService;
import com.ag.generic.service.ConfigPropertiesService;
import com.ag.generic.service.ConfigRegionService;
import com.ag.generic.service.LovService;
import com.ag.generic.service.UserGroupService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.JwtTokenUtil;
import com.ag.generic.util.LdapUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.entity.FedRates;
import com.ag.mportal.entity.QrConfig;
import com.ag.mportal.model.AngularMenuModel;
import com.ag.mportal.services.ChainMerchantsService;
import com.ag.mportal.services.FedRateService;
import com.ag.mportal.services.QrConfigService;
import com.mportal.ws.classes.WsFieldOfcLogin;

@Component("com.generic.ws.classes.WsLogin")
public class WsLogin implements Wisher {

	@Autowired
	UserLoginService userLoginService;
	@Autowired
	UserGroupService userGroupService;
	@Autowired
	UserScreenService userScreenService;
	@Autowired
	ComplChannelConfigService complChannelConfigService;
	@Autowired
	UserSettingService userSettingService;
	@Autowired
	LovService lovService;
	@Autowired
	ConfigCityService cityService;
	@Autowired
	ConfigRegionService regionService;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	@Autowired
	ChainMerchantsService chainMerchantsService;
	@Autowired
	FedRateService fedRatesService;
	@Autowired
	QrConfigService qrConfigService;
	@Autowired
	ConfigPropertiesService configPropertiesService;

	@Autowired
	UtilService utilService;

	@Autowired
	CaptchaService captchaService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String UserCode = rm.getUserid();
			UserLogin user = new UserLogin();
			AgLogger.logDebug("1", UserCode + "|" + rm.getCorpId());

			boolean isCaptcha = false;

			if (rm.getChannel().equalsIgnoreCase("portal")) {
				isCaptcha = true;
			}

			if (isCaptcha) {
				// Captcha VAlidateion here
				Captcha cpt = captchaService.validateCaptcha(rm.getAdditionalData().containsKey("cptId")
						? Integer.parseInt(rm.getAdditionalData().get("cptId").toString())
						: 0, rm.getImei());

				String captAns = rm.getAdditionalData().containsKey("captAns")
						? rm.getAdditionalData().get("captAns").toString()
						: "N/A";

				if (cpt != null) {
					if (cpt.getCaptchaAns().equals(captAns)) {
						user = userLoginService.validetUser(UserCode, rm.getCorpId());
						if (user != null) {
							cpt.setIsActive(0);
							captchaService.updateCaptcha(cpt);
							loginUser(user, response, rm);

						} else {
							cpt.setIsActive(0);
							captchaService.updateCaptcha(cpt);
							AgLogger.logInfo(UserCode, "Portal User Not Found in DB..");
							response.setCode("0001");
							response.setMessage("Cannot Log In, Invalid Credentials.");
						}
					} else {
						cpt.setIsActive(0);
						captchaService.updateCaptcha(cpt);
						AgLogger.logInfo(UserCode, "Portal User Catpcha Mimatched......");
						response.setCode("0001");
						response.setMessage("Cannot Log In, Invalid Credentials.");
					}

				} else {
					AgLogger.logInfo(UserCode, "Portal User Catpcha Is Null....");
					response.setCode("0001");
					response.setMessage("Cannot Log In, Invalid Credentials.");
				}

			} else {

				user = userLoginService.validetUser(UserCode, rm.getCorpId());
				if (user != null) {
					loginUser(user, response, rm);

				} else {
					AgLogger.logInfo(UserCode, "Mobile User Not Found in DB..");
					response.setCode("0001");
					response.setMessage("User Not Found.");
				}
			}

		} catch (Exception ex) {
			response.setCode("9995");
			response.setMessage("Something Went Wrong, Please try again.");

			AgLogger.logerror(getClass(), "doProcess ...EXCEPTION  :  " + ex, ex);
			ex.printStackTrace();
		}
		return response;

	}

	HashMap<Object, Object> getOthersData(UserLogin usr, String channel) {
		HashMap<Object, Object> mp = new HashMap<Object, Object>();
		List<GroupDeclarModel> selectProvinceItem;
		try {

			if (!channel.equalsIgnoreCase("android") && !channel.equalsIgnoreCase("ios")) {
				mp.put("user", usr);
				List<ConfigCity> lstCity = cityService.findAll(usr.getCorpId());
				List<ConfigRegion> lstRegion = regionService.findAll(usr.getCorpId());
				List<LovMaster> lstLov = lovService.fetchLovs(usr.getCorpId());
				List<LovDetail> listLovDetails = lovService.fetchLovsDetailsAll(usr.getCorpId());
				List<ComplChannelConfig> listOfChannel = complChannelConfigService.fetchAllReq();
				List<String> strChannelList = new ArrayList<String>();
				List<FedRates> listFedRates = fedRatesService.retrieveAll();
				selectProvinceItem = UtilAccess.fillProvince(listFedRates);
				List<GroupDeclarModel> lstGroupNonMerchants = userGroupService
						.getNonMerchantUserGroups(usr.getCorpId());

				List<UserLogin> assignedUsers = userLoginService.findUserByGroupAll(usr);

				for (ComplChannelConfig c : listOfChannel) {
					strChannelList.add(c.getChannel());
				}

				List<String> listWithoutDuplicates = strChannelList.stream().distinct().collect(Collectors.toList());

				listOfChannel = new ArrayList<ComplChannelConfig>();

				for (String c : listWithoutDuplicates) {
					int i = 1;
					listOfChannel.add(new ComplChannelConfig(i, c));
					i++;
				}

				mp.put("city", lstCity);
				mp.put("region", lstRegion);
				mp.put("lov", lstLov);
				mp.put("lovDetails", listLovDetails);
				mp.put("listOfChannel", listOfChannel);
				mp.put("ProvinceList", selectProvinceItem);
				mp.put("nonMerchantUserGroups", lstGroupNonMerchants);
				mp.put("merchantUserGroups", lstGroupNonMerchants);
				mp.put("AssignedUsersList", assignedUsers);

				List<GroupDeclarModel> grps = userGroupService.getUserGroupsAllConverted(usr.getCorpId());
				mp.put("groups", grps);

				List<ScreenDeclarModel> screens = userScreenService.getUserScreensAllConverted(usr.getCorpId());
				mp.put("screens", screens);

				List<WsMenuModel> m = MenuCtrl(usr.getGroupCode(), usr.getCorpId());

				List<AngularMenuModel> MainModelList = new ArrayList<AngularMenuModel>();

				for (WsMenuModel k : m) {
					if (k.getParentId() == 0) {
						AngularMenuModel MainModel = new AngularMenuModel();
						ArrayList<String> RouterLink = new ArrayList<String>();
						MainModel.setIcon(k.getIcon());
						MainModel.setLabel(k.getMenuName());
						RouterLink.add(k.getRouterLink());
						MainModel.setRouterLink(RouterLink);
						ArrayList<AngularMenuModel> SubModelList = new ArrayList<AngularMenuModel>();
						for (WsMenuModel n : m) {
							if ((n.getParentId() != 0) && n.getParentId() == k.getId()) {
								ArrayList<String> RouterLinkSub = new ArrayList<String>();
								AngularMenuModel SubModel = new AngularMenuModel();
								SubModel.setIcon(n.getIcon());
								SubModel.setLabel(n.getMenuName());
								RouterLinkSub.add(n.getRouterLink());
								SubModel.setRouterLink(RouterLinkSub);
								SubModelList.add(SubModel);
							}
						}
						if (SubModelList.size() != 0) {
							MainModel.setItem(SubModelList);
							MainModelList.add(MainModel);
						}
					}
				}

				AngularMenuModel finalMenuList = new AngularMenuModel();
				finalMenuList.setData(MainModelList);
				mp.put("menu", finalMenuList);
				mp.put("menuSubsegments", userScreenService.screenRights(usr.getGroupCode(), usr.getCorpId(), 1));
				mp.put("menuSize", MainModelList.size());
				UserSetting us = userSettingService.fetchSettingByIdName(usr.getUserId(),
						UtilAccess.userSettingsDefaultTid);
				if (us != null) {
					mp.put("defaultTid", us.getPropValue());
				} else {
					mp.put("defaultTid", "");

				}
				List<UserSetting> lstUserSetting = userSettingService.fetchSettingByUserLoginId(usr.getUserId());
				if (lstUserSetting.size() != 0) {
					HashMap<String, String> mapUserSetting = UtilAccess.lstToMapUserSetting(lstUserSetting);

					mp.put("mdrOffus",
							!Objects.isNull(mapUserSetting.get("MDR_OFF_US"))
									? Double.valueOf(mapUserSetting.get("MDR_OFF_US"))
									: "N/A");
					mp.put("mdrOnus",
							!Objects.isNull(mapUserSetting.get("MDR_ON_US"))
									? Double.valueOf(mapUserSetting.get("MDR_ON_US"))
									: "N/A");
					mp.put("fedRate",
							!Objects.isNull(mapUserSetting.get("FED_RATES"))
									? Double.valueOf(mapUserSetting.get("FED_RATES"))
									: "N/A");

					mp.put("province",
							!Objects.isNull(mapUserSetting.get("PROVINCE")) ? mapUserSetting.get("PROVINCE") : "N/A");

				} else {
					mp.put("mdrOffus", "N/A");
					mp.put("mdrOnus", "N/A");
					mp.put("fedRate", "N/A");
					mp.put("province", "N/A");
				}

				List<String> listMerchantOption = fetchMerchantOptions(usr);

				if (listMerchantOption.size() > 0) {
					mp.put("listMerchantOption", listMerchantOption);
				}

			} else {

				mp.put("user", usr);
				List<LovMaster> lstLov = lovService.fetchLovs(usr.getCorpId());
				List<LovDetail> listLovDetails = lovService.fetchLovsDetailsAll(usr.getCorpId());
				mp.put("lov", lstLov);
				mp.put("lovDetails", listLovDetails);

				List<String> lstTids = getListOfTids(usr.getMid());
				if (lstTids != null) {
					mp.put("Tid", lstTids);
				} else {
					mp.put("Tid", "N/A");
				}

				UserSetting us = userSettingService.fetchSettingByIdName(usr.getUserId(),
						UtilAccess.userSettingsDefaultTid);
				if (us != null) {
					mp.put("defaultTid", us.getPropValue());
				} else {
					mp.put("defaultTid", "");

				}

				HashMap<Object, Object> qrConfig = getQrConfig(usr.getMid(), usr.getCorpId());
				mp.put("qrConfig", qrConfig.get("qrConfig"));
				mp.put("qrExists", qrConfig.get("qrExists"));
				mp.put("qrMsg", qrConfig.get("qrMsg"));
				mp.put("refundVisible", AppProp.getProperty("refund.visible"));
				mp.put("cashOutVisible", AppProp.getProperty("cashout.visible"));
				mp.put("menuSize", "999999");

			}

		} catch (Exception e) {
			AgLogger.logerror(getClass(), "GetOthersData...Exception:" + e, e);
			e.printStackTrace();
		}
		return mp;
	}

	public List<WsMenuModel> MenuCtrl(int userGroupCode, String corpId) {

		List<WsMenuModel> menus = null;

		try {

			menus = userScreenService.screenRights(userGroupCode, corpId, 0);

		} catch (Exception e) {

			AgLogger.logerror(WsFieldOfcLogin.class, " MENU ERROR" + e, e);

		}

		return menus;

	}

	public List<String> getListOfTids(String mid) {
		List<String> lst = new ArrayList<String>();
		List<String> tdsList = utilService.getTidByMid(mid);
		if (tdsList != null) {
			for (String tds : tdsList) {
				lst.add(tds);
			}
		}
		List<String> newList = lst.stream().distinct().collect(Collectors.toList());
		return newList;
	}

	public List<String> fetchMerchantOptions(UserLogin usl) {
		List<String> skl = new ArrayList<String>();
		if (usl.getMid().equalsIgnoreCase("all")) {
			skl.add("All");
			List<ChainMerchant> cmh = chainMerchantsService.fetchAll();
			for (ChainMerchant c : cmh) {
				skl.add(c.getMid());
			}

			// adding non registered MIDS.
			List<String> nonRegistered = chainMerchantsService.fetchAllNonRegistered();
			for (String mid : nonRegistered) {
				skl.add(mid);
			}

		} else {
			List<ChainMerchant> cmh = chainMerchantsService.fetchAllByID(usl.getMid());
			for (ChainMerchant c : cmh) {
				skl.add(String.valueOf(c.getMid()));
			}

		}
		List<String> listWithoutDuplicates = skl.stream().distinct().collect(Collectors.toList());
		if (listWithoutDuplicates.size() == 0) {
			listWithoutDuplicates.add(usl.getUserCode());
		}
		return listWithoutDuplicates;

	}

	public void updateLoginInformation(UserLogin user, String token, String imei) {
		user.setToken(token);
		user.setTokenGenerationTime(new Timestamp(jwtTokenUtil.getIssuedAtDateFromToken(token).getTime()));
		user.setLastLogin(new Timestamp(new Date().getTime()));
		user.setImeiUuid(imei);
		userLoginService.updateUser(user);
	}

	public HashMap<Object, Object> getQrConfig(String mid, String corpId) {
		HashMap<Object, Object> mp = new HashMap<Object, Object>();
		int qrExists = 0;
		List<QrConfig> qrConfig = qrConfigService.SelectAllByMid(corpId, mid);

		if (qrConfig.size() != 0) {
			EmvQrModel mdl = new EmvQrModel();
			for (QrConfig qrc : qrConfig) {
				mdl.setAmex(qrc.getPropKey());
				switch (qrc.getPropKey()) {
				case "amex":
					mdl.setAmex(qrc.getPropValue());
					continue;
				case "amex2":
					mdl.setAmex2(qrc.getPropValue());
					continue;
				case "countryCode":
					mdl.setCountryCode(qrc.getPropValue());
					continue;
				case "customPan":
					mdl.setCustomPan(qrc.getPropValue());
					continue;
				case "discover":
					mdl.setDiscover(qrc.getPropValue());
					continue;
				case "discover2":
					mdl.setDiscover2(qrc.getPropValue());
					continue;
				case "emvCo":
					mdl.setEmvCo(qrc.getPropValue());
					continue;
				case "emvCo2":
					mdl.setEmvCo2(qrc.getPropValue());
					continue;
				case "jcb":
					mdl.setJcb(qrc.getPropValue());
					continue;
				case "jcb2":
					mdl.setJcb2(qrc.getPropValue());
					continue;
				case "masterCard":
					mdl.setMasterCard(qrc.getPropValue());
					continue;
				case "masterCard2":
					mdl.setMasterCard2(qrc.getPropValue());
					continue;
				case "merchantCategoryCode":
					mdl.setMerchantCategoryCode(qrc.getPropValue());
					continue;
				case "merchantCity":
					mdl.setMerchantCity(qrc.getPropValue());
					continue;
				case "merchantName":
					mdl.setMerchantName(qrc.getPropValue());
					continue;
				case "payloadFormatIndicator":
					mdl.setPayloadFormatIndicator(qrc.getPropValue());
					continue;
				case "pointOfInitiationMethod":
					mdl.setPointOfInitiationMethod(qrc.getPropValue());
					continue;
				case "transactionAmount":
					mdl.setTransactionAmount(qrc.getPropValue());
					continue;
				case "transactionCurrency":
					mdl.setTransactionCurrency(qrc.getPropValue());
					continue;
				case "upi":
					mdl.setUpi(qrc.getPropValue());
					continue;
				case "upi2":
					mdl.setUpi2(qrc.getPropValue());
					continue;
				case "visaCardPan":
					mdl.setVisaCardPan(qrc.getPropValue());
					continue;
				case "visaCardPan2":
					mdl.setVisaCardPan2(qrc.getPropValue());
					continue;
				default:
					break;

				}

			}
			qrExists = 1;
			mdl.setResponseCode("0000");
			mp.put("qrConfig", mdl);
			mp.put("qrExists", qrExists);
			mp.put("qrMsg", AppProp.getProperty("QRNotExistMsg"));
		} else {
			qrExists = 0;
			mp.put("qrExists", qrExists);
			mp.put("qrMsg", AppProp.getProperty("QRNotExistMsg"));

		}
		return mp;

	}

	public ResponseModel loginUser(UserLogin user, ResponseModel response, RequestModel rm) {
		String UserCode = rm.getUserid();
		String imei = rm.getImei();

		String playerId = "";

		if (!Objects.isNull(rm.getAdditionalData())) {
			if (rm.getAdditionalData().containsKey("playerId")) {
				playerId = rm.getAdditionalData().get("playerId").toString();
				utilService.setPlayerIdsPushNotificationModel(user.getUserId(), playerId, rm.getChannel());
			}

		}

		if (user.getUserType().equals("internal")) {
			if (UtilAccess.md5Java(rm.getPassword()).equals(user.getPassword())) {
				response.setCode("0000");
				response.setMessage("SUCCESS");
				Map<Object, Object> obMap = getOthersData(user, rm.getChannel());

				// validation for Menu Size
				int menuSize = 99999;
				try {
					menuSize = (obMap.containsKey("menuSize")) ? Integer.parseInt(obMap.get("menuSize").toString()) : 0;
				} catch (Exception e) {

				}

				if (menuSize != 0) {
					String token = jwtTokenUtil.generateToken(user.getUserCode(), user.getUserId());
					updateLoginInformation(user, token, imei);
					obMap.put("token_auth", token);
					response.setData(obMap);
				} else {
					AgLogger.logInfo(UserCode, "No Menu Found.....");
					response.setCode("0001");
					response.setMessage("No Any Menu Group Assigned, Please contact administrator.");
				}

			} else {
				AgLogger.logInfo(UserCode, "Password Mistamched from db its internal user...");
				response.setCode("0003");
				response.setMessage("Cannot Log In, Invalid Credentials.");
			}
		} else {
			LDAPResponseModel rmkl = new LdapUtil().getLdapLogin(UserCode, rm.getPassword());
			if (rmkl.getCode().equals("0000")) {
				response.setCode("0000");
				response.setMessage("SUCCESS");
				Map<Object, Object> obMap = getOthersData(user, rm.getChannel());
				response.setData(obMap);

				// validation for Menu Size
				int menuSize = 99999;
				try {
					menuSize = (obMap.containsKey("menuSize")) ? Integer.parseInt(obMap.get("menuSize").toString()) : 0;
				} catch (Exception e) {

				}

				if (menuSize != 0) {
					String token = jwtTokenUtil.generateToken(user.getUserCode(), user.getUserId());
					updateLoginInformation(user, token, imei);
					obMap.put("token_auth", token);
					response.setData(obMap);
				} else {
					AgLogger.logInfo(UserCode, "No Menu Found.....");
					response.setCode("0001");
					response.setMessage("Menu Configuration Pending, Please contact administrator.");
				}

			} else {
				AgLogger.logInfo(UserCode, "LDAP LOGIN FAILED....");
				response.setCode("0003");
				response.setMessage("Cannot Log In, Invalid Credentials.");
			}

		}

		return response;

	}

}
