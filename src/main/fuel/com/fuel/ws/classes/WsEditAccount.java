package com.fuel.ws.classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.entity.MasFleetCoInfo;
import com.ag.fuel.model.KeyValueModel;
import com.ag.fuel.util.AGFuelUtil;
import com.ag.generic.entity.ConfigCity;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.service.impl.ConfigCityServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.services.BuildControlConfigService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.fuel.ws.classes.WsEditAccount")
public class WsEditAccount implements Wisher {

	@Autowired
	UserScreenService screenService;
	
	@Autowired
	ConfigCityServiceImpl configCityServiceImpl;
	
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

			List<ConfigCity> lstCity = configCityServiceImpl.findAll(corpid);
			List<KeyValueModel> lst = new ArrayList<KeyValueModel>();

			if (lstCity.size() != 0) {
				KeyValueModel keyValue = null;
				for (ConfigCity l : lstCity) {
					keyValue = new KeyValueModel();
					keyValue.setKey(l.getCode());
					keyValue.setValue(l.getName());
					lst.add(keyValue);
				}
			}
			
			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			String[] responseProc = AGFuelUtil.fuelAccountProcess(rm.getCorpId(), "ACCOUNT_EDIT", "LOV",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), "");

			FuelProcModelDetails mdl = new Gson().fromJson(responseProc[2], FuelProcModelDetails.class);
			
			ArrayList<KeyValueModel> account = new ArrayList<KeyValueModel>();
			ArrayList<KeyValueModel> entity = new ArrayList<KeyValueModel>();
			
			Type accountList = new TypeToken<List<KeyValueModel>>() {
			}.getType();
			account = new Gson().fromJson(new Gson().toJson(mdl.getData().get("account")), accountList);

			Type entityList = new TypeToken<List<KeyValueModel>>() {
			}.getType();
			entity = new Gson().fromJson(new Gson().toJson(mdl.getData().get("entity")), entityList);

			if (responseProc[0].equals("00")) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("lst", lst);
				o.put("entity", entity);
				o.put("account", account);
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

			String data = (rm.getAdditionalData().containsKey("accountNumber")) ? rm.getAdditionalData().get("accountNumber").toString()
					: null;

			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			String[] responseProc = AGFuelUtil.fuelGetData(rm.getCorpId(), "ACCOUNT_EDIT", "GET_DATA",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), data);
			
			String companyname = "";
			String legalname = "";
			String companynameoncard = "";
			String entity = "";
			String natureofbusiness = "";
			String add1 = "";
			String add2 = "";
			String add3 = "";
			String city = "";
			String tel1 = "";
			String tel2 = "";
			String fax = "";
			String mobile = "";
			String email = "";
			String contperson = "";
			String contdesig = "";
			String ntn = "";
			String strn = "";
			String authname = "";
			String authdesig = "";
			String servicecharges = "";
			String duedate = "";
			String billingfrequency = "";

			if (responseProc[2].contains("data")) {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					JsonNode jsonNode = objectMapper.readTree(responseProc[2]);
					companyname = jsonNode.get("data").get("companyname").asText();
					legalname = jsonNode.get("data").get("legalname").asText();
					companynameoncard = jsonNode.get("data").get("companynameoncard").asText();
					entity = jsonNode.get("data").get("entity").asText();
					natureofbusiness = jsonNode.get("data").get("natureofbusiness").asText();
					
					add1 = jsonNode.get("data").get("add1").asText();
					add2 = jsonNode.get("data").get("add2").asText();
					add3 = jsonNode.get("data").get("add3").asText();
					city = jsonNode.get("data").get("city").asText();
					tel1 = jsonNode.get("data").get("tel1").asText();
					
					tel2 = jsonNode.get("data").get("tel2").asText();
					fax = jsonNode.get("data").get("fax").asText();
					mobile = jsonNode.get("data").get("mobile").asText();
					email = jsonNode.get("data").get("email").asText();
					contperson = jsonNode.get("data").get("contperson").asText();
					
					contdesig = jsonNode.get("data").get("contdesig").asText();
					ntn = jsonNode.get("data").get("ntn").asText();
					strn = jsonNode.get("data").get("strn").asText();
					authname = jsonNode.get("data").get("authname").asText();
					authdesig = jsonNode.get("data").get("authdesig").asText();
					servicecharges = jsonNode.get("data").get("servicecharges").asText();
					duedate = jsonNode.get("data").get("duedate").asText();
					billingfrequency = jsonNode.get("data").get("billingfrequency").asText();
					
					response.setCode("0000");
					response.setMessage("Success.");

				} catch (Exception e) {
					e.printStackTrace();
				}
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("companyname", companyname);
				o.put("legalname", legalname);
				o.put("companynameoncard", companynameoncard);
				o.put("entity", entity);
				o.put("natureofbusiness", natureofbusiness);
				
				o.put("add1", add1);
				o.put("add2", add2);
				o.put("add3", add3);
				o.put("city", city);
				o.put("tel1", tel1);
				
				o.put("tel2", tel2);
				o.put("fax", fax);
				o.put("mobile", mobile);
				o.put("email", email);
				o.put("contperson", contperson);
				
				o.put("contdesig", contdesig);
				o.put("ntn", ntn);
				o.put("strn", strn);
				o.put("authname", authname);
				o.put("authdesig", authdesig);
				o.put("servicecharges", servicecharges);
				o.put("duedate", duedate);
				o.put("billingfrequency", billingfrequency);
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
