package com.fuel.ws.classes;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.model.ChangePinServiceModel;
import com.ag.fuel.model.KeyValueModel;
import com.ag.fuel.util.AGFuelUtil;
import com.ag.generic.entity.OtpLogging;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.SendNotifciation;
import com.ag.mportal.services.BuildControlConfigService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.fuel.ws.classes.WsCardTxnResetPin")
public class WsCardTxnResetPin implements Wisher {

	@Autowired
	UserScreenService screenService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UtilService utilService;

	@Autowired
	SendNotifciation sendNotifciation;

	@Autowired
	OTPLoggingService loggingService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {
			case "INIT":
				response = initRequest(rm);
				break;
			case "FETCH":
				response = fetchRequest(rm);
				break;
			case "PROCESS":
				response = processRequest(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	ResponseModel initRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {

			String route = (rm.getAdditionalData().containsKey("route"))
					? rm.getAdditionalData().get("route").toString()
					: null;

			String corpid = rm.getCorpId();

			UserScreen screen = screenService.fetchScreenIdByRoute(route, corpid);

			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			String[] responseProc = AGFuelUtil.fuelAccountProcess(rm.getCorpId(), "CARD_PIN_RESET", "LOV",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), "");

			FuelProcModelDetails mdl = new Gson().fromJson(responseProc[2], FuelProcModelDetails.class);

			ArrayList<KeyValueModel> card = new ArrayList<KeyValueModel>();

			Type cardlst = new TypeToken<List<KeyValueModel>>() {
			}.getType();
			card = new Gson().fromJson(new Gson().toJson(mdl.getData().get("cardpin")), cardlst);

			if (card.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("card", card);
				o.put("screenId", (screen != null) ? screen.getScreenId() : 0);
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Success.");
			} else {
				response.setCode("0001");
				response.setMessage("No Data Found.");
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {

			String data = (rm.getAdditionalData().containsKey("data")) ? rm.getAdditionalData().get("data").toString()
					: null;

			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			String[] responseProc = AGFuelUtil.fuelGetData(rm.getCorpId(), "CARD_PIN_RESET", "GET_DATA",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), data);
			String company = "";
			String account = "";
			String cardholdername = "";
			String cardstatus = "";
			String cardexpiry = "";
			String mobile = "";
			String email = "";

			if (responseProc[2].contains("data")) {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					JsonNode jsonNode = objectMapper.readTree(responseProc[2]);
					company = jsonNode.get("data").get("company").asText();
					account = jsonNode.get("data").get("account").asText();
					cardholdername = jsonNode.get("data").get("cardholdername").asText();
					cardstatus = jsonNode.get("data").get("cardstatus").asText();
					cardexpiry = jsonNode.get("data").get("cardexpiry").asText();
					mobile = jsonNode.get("data").get("mobile").asText();
					email = jsonNode.get("data").get("email").asText();
					response.setCode("0000");
					response.setMessage("Success.");

				} catch (Exception e) {
					e.printStackTrace();
				}
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("company", company);
				o.put("account", account);
				o.put("expiry", cardexpiry);
				o.put("cardName", cardholdername);
				o.put("currentStatus", cardstatus);
				o.put("mobile", mobile);
				o.put("email", email);
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Success.");

			} else {
				response.setCode("9999");
				response.setMessage("No Data Found.");
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	ResponseModel processRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		try {
			String cardNo = (rm.getAdditionalData().containsKey("data")) ? rm.getAdditionalData().get("data").toString()
					: null;
			String pinChangeURL = AppProp.getProperty("changePinUrl") + "card=" + cardNo + "&pin=0000&t=002";
			AgLogger.logInfo(pinChangeURL);
			String respData = new HttpUtil().doGet(pinChangeURL);
			AgLogger.logInfo("Response Data: " + respData);

			ChangePinServiceModel tempModel = new Gson().fromJson(respData, ChangePinServiceModel.class);
			if (null != tempModel.getResponce() && tempModel.getResponce().equals("0000")) {

				UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

				SendNotificationModel sdm = new SendNotificationModel();
				sdm.setAccountOpeningDate("N/A");
				sdm.setClosedBy("N/A");
				sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setComplaintNum("N/A");
				sdm.setMerchantName(ukl.getFirstName() + " " + ukl.getLastName());
				sdm.setPass("N/A");
				sdm.setResolution("N/A");
				sdm.setUserName(ukl.getUserCode());
				sdm.setReciverId(ukl.getUserId());
				AgLogger.logInfo("SMS/EMAIL NOT SENT FOR NOW");
				// sendNotifciation.doTask("001", "0002", "00001", sdm, ukl.getUserId());

				utilService.doSendAppNotificationOnly(Integer.parseInt(rm.getUserid()),
						AppProp.getProperty("reset.pin.notif"), AppProp.getProperty("reset.pin.notif"));

				response.setCode("0000");
				response.setMessage("Reset Pin Successful.");
			} else {
				response.setCode("1111");
				response.setMessage("Could Not Reset Pin.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something went wrong.");
		}

		return response;
	}

}
