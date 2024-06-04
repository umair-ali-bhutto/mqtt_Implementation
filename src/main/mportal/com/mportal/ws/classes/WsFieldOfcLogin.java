package com.mportal.ws.classes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplChannelConfig;
import com.ag.generic.entity.ConfigCity;
import com.ag.generic.entity.ConfigRegion;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.model.GroupDeclarModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.ScreenDeclarModel;
import com.ag.generic.model.WsMenuModel;
import com.ag.generic.prop.AppProp;
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
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.entity.FedRates;
import com.ag.mportal.model.AngularMenuModel;
import com.ag.mportal.services.ChainMerchantsService;
import com.ag.mportal.services.FedRateService;
import com.ag.mportal.services.QrConfigService;

@Component("com.mportal.ws.classes.WsFieldOfcLogin")
public class WsFieldOfcLogin implements Wisher {

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

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String UserCode = rm.getUserid();
			UserLogin user = new UserLogin();
			user = userLoginService.validetUser(UserCode, rm.getCorpId());

			if (user != null) {
				int defaultMerchantGrup = Integer.parseInt(AppProp.getProperty("field.group.code"));
				if (user.getGroupCode() == defaultMerchantGrup) {
					loginUser(user, response, rm);
				} else {
					response.setCode("0003");
					response.setMessage("Invalid Credentials.");
				}
			} else {
				response.setCode("0001");
				response.setMessage("Invalid Credentials.");
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

			if (!channel.equalsIgnoreCase("android")) {
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
				mp.put("menuSize", MainModelList.size());
				UserSetting us = userSettingService.fetchSettingByIdName(usr.getUserId(),
						UtilAccess.userSettingsDefaultTid);
				if (us != null) {
					mp.put("defaultTid", us.getPropValue());
				} else {
					mp.put("defaultTid", "");

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

	public ResponseModel loginUser(UserLogin user, ResponseModel response, RequestModel rm) {
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
					response.setCode("0001");
					response.setMessage("No Any Menu Group Assigned, Please contact administrator.");
				}

			} else {
				response.setCode("0003");
				response.setMessage("Cannot Log In, Invalid Credentials.");
			}
		} else {

			response.setCode("0004");
			response.setMessage("Cannot Log In, Not Allowed.");

		}

		return response;

	}

}
