package com.fuel.ws.classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.model.KeyValueModel;
import com.ag.fuel.util.AGFuelUtil;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.services.BuildControlConfigService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.fuel.ws.classes.WsEditCardLimit")
public class WsEditCardLimit implements Wisher {

	@Autowired
	UserScreenService screenService;
	
	@Autowired
	UserLoginService userLoginService;

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

			String[] responseProc = AGFuelUtil.fuelAccountProcess(rm.getCorpId(), "CARD_LIMIT_EDIT", "LOV",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), "");

			FuelProcModelDetails mdl = new Gson().fromJson(responseProc[2], FuelProcModelDetails.class);
			
			ArrayList<KeyValueModel> card = new ArrayList<KeyValueModel>();
			
			Type cardlst = new TypeToken<List<KeyValueModel>>() {
			}.getType();
			card = new Gson().fromJson(new Gson().toJson(mdl.getData().get("cardlimit")), cardlst);
			
			

			if (card.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("card", card);
				o.put("screenId", (screen!=null)?screen.getScreenId():0);
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

			String[] responseProc = AGFuelUtil.fuelGetData(rm.getCorpId(), "CARD_LIMIT_EDIT", "GET_DATA",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), data);
			
			FuelProcModelDetails mdl = new Gson().fromJson(responseProc[2], FuelProcModelDetails.class);
			
			ArrayList<KeyValueModel> productlist = new ArrayList<KeyValueModel>();
			
			Type prdlst = new TypeToken<List<KeyValueModel>>() {
			}.getType();
			productlist = new Gson().fromJson(new Gson().toJson(mdl.getData().get("productlist")), prdlst);
			
			String dailyfrequency = "";
			String weeklyfrequency = "";
			String limit_type = "";
			String txnlimit = "";
			String dailylimit = "";
			String weeklylimit = "";
			String monthlylimit = "";
			String nonfuel = "";
			String product = "";
			if (responseProc[2].contains("data")) {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					JsonNode jsonNode = objectMapper.readTree(responseProc[2]);
					dailyfrequency = jsonNode.get("data").get("dailyfrequency").asText();
					weeklyfrequency = jsonNode.get("data").get("weeklyfrequency").asText();
					limit_type = jsonNode.get("data").get("limit_type").asText();
					txnlimit = jsonNode.get("data").get("txnlimit").asText();
					dailylimit = jsonNode.get("data").get("dailylimit").asText();					
					weeklylimit = jsonNode.get("data").get("weeklylimit").asText();
					monthlylimit = jsonNode.get("data").get("monthlylimit").asText();
					nonfuel = jsonNode.get("data").get("nonfuel").asText();
					product = jsonNode.get("data").get("product").asText();
					response.setCode("0000");
					response.setMessage("Success.");

				} catch (Exception e) {
					e.printStackTrace();
				}
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("dailyfrequency", dailyfrequency);
				o.put("weeklyfrequency", weeklyfrequency);
				o.put("limit_type", limit_type);
				o.put("txnlimit", txnlimit);
				o.put("dailylimit", dailylimit);				
				o.put("weeklylimit", weeklylimit);
				o.put("monthlylimit", monthlylimit);
				o.put("nonfuel", nonfuel);
				o.put("product", productlist);
				o.put("productSelected", product);
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

		try {
			response.setCode("0000");
			response.setMessage("Success.");
		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}
