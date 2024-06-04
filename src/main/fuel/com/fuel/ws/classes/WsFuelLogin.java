package com.fuel.ws.classes;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.main.qr.EmvQrModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.entity.FuelProfilePicture;
import com.ag.fuel.model.KeyValueModel;
import com.ag.fuel.services.FuelProfilePictureService;
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
import com.ag.generic.service.ComplChannelConfigService;
import com.ag.generic.service.ConfigCityService;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.fuel.ws.classes.WsFuelLogin")
public class WsFuelLogin implements Wisher {

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
	UtilService utilService;
	@Autowired
	FuelProfilePictureService fuelProfilePictureService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String UserCode = rm.getUserid();
			UserLogin user = new UserLogin();
			user = userLoginService.validetUser(UserCode, rm.getCorpId());
			if (user != null) {
				int defaultFuelGroup = Integer.parseInt(AppProp.getProperty("fuel.group.code"));
				AgLogger.logInfo(defaultFuelGroup + "|" + user.getGroupCode());
				if (user.getGroupCode() == defaultFuelGroup) {
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

			AgLogger.logInfo("CHANNEL " + channel);

			List<GroupDeclarModel> lstGroupNonMerchants = userGroupService.getNonMerchantUserGroups(usr.getCorpId());

			mp.put("user", usr);
			List<LovMaster> lstLov = lovService.fetchLovs(usr.getCorpId());
			List<LovDetail> listLovDetails = lovService.fetchLovsDetailsAll(usr.getCorpId());
			mp.put("lov", lstLov);
			mp.put("lovDetails", listLovDetails);
			mp.put("merchantUserGroups", lstGroupNonMerchants);

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
			mp.put("tapNPayEnabledPinAmount", AppProp.getProperty("tap.n.pay.pin.enabled"));
			mp.put("tapNPayEnabled", AppProp.getProperty("tap.n.pay.enabled"));
			mp.put("menuSize", "999999");

			HashMap<Object, Object> obJamp = fetchDetails(usr);

			ArrayList<KeyValueModel> cardList = (ArrayList<KeyValueModel>) obJamp.get("cardList");
			ArrayList<KeyValueModel> dealerList = (ArrayList<KeyValueModel>) obJamp.get("dealerList");

			AgLogger.logInfo("List: " + cardList.toString());
			AgLogger.logInfo("Card: " + AppProp.getProperty("fuel.complaint.card.related"));
			AgLogger.logInfo("Dealer: " + AppProp.getProperty("fuel.complaint.dealer.related"));
			mp.put("cardRelated", AppProp.getProperty("fuel.complaint.card.related"));
			mp.put("cardLovList", cardList);
			mp.put("delearLovList", dealerList);
			mp.put("dealerRelated", AppProp.getProperty("fuel.complaint.dealer.related"));

			// PROFILE PICTURE @UMAIR.ALI
			FuelProfilePicture mdl = fuelProfilePictureService.findByUserId(usr.getUserId());
			byte[] fileBytes = null;
			if (mdl != null) {
				fileBytes = mdl.getProfilePic();
			} else {
				// Default Pic Path Should Be With Complete File Name
				String filePath = AppProp.getProperty("fuel.app.default.profile.pic");
				Path path = Paths.get(filePath);
				fileBytes = Files.readAllBytes(path);
				AgLogger.logInfo("Default Profile Picture Used.");
			}

			String base64String = Base64.getEncoder().encodeToString(fileBytes);

			mp.put("profilePic", base64String);

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

			AgLogger.logerror(WsFuelLogin.class, " MENU ERROR" + e, e);

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
			AgLogger.logInfo(rm.getPassword() + "|" + UtilAccess.md5Java(rm.getPassword()) + "|" + user.getPassword());
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
					response.setCode("0001");
					response.setMessage("No Any Menu Group Assigned, Please contact administrator.");
				}

			} else {
				response.setCode("0003");
				response.setMessage("Cannot Log In, Invalid Credentials.");
			}

		}

		return response;

	}

	private HashMap<Object, Object> fetchDetails(UserLogin usr) {
		String responseProc = DBProceduresFuel.fuelAppProcess(usr.getCorpId(), "fetch_dealers_cards", usr.getUserId(),
				usr.getUserCode(), null, usr.getGroupCode(), null);

		HashMap<Object, Object> resp = new HashMap<Object, Object>();
		ArrayList<KeyValueModel> cardList = new ArrayList<KeyValueModel>();
		ArrayList<KeyValueModel> dealerList = new ArrayList<KeyValueModel>();

		if (responseProc.equals("9999")) {
			resp.put("cardList", cardList);
			resp.put("dealerList", dealerList);

		} else {

			FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);

			if (mdl.getCode().equals("0000")) {

				Type listcardList = new TypeToken<List<KeyValueModel>>() {
				}.getType();
				cardList = new Gson().fromJson(new Gson().toJson(mdl.getData().get("cards")), listcardList);

				Type listdealerList = new TypeToken<List<KeyValueModel>>() {
				}.getType();
				dealerList = new Gson().fromJson(new Gson().toJson(mdl.getData().get("dealers")), listdealerList);

				resp.put("cardList", cardList);
				resp.put("dealerList", dealerList);
			} else {
				resp.put("cardList", cardList);
				resp.put("dealerList", dealerList);

			}

		}

		return resp;

	}
}
