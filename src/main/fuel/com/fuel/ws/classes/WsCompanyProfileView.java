package com.fuel.ws.classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.fuel.ws.classes.WsCompanyProfileView")
public class WsCompanyProfileView implements Wisher {

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

			String[] responseProc = AGFuelUtil.fuelAccountProcess(rm.getCorpId(), "COMPANY_PROFILE_VIEW", "LOV",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), "");

			FuelProcModelDetails mdl = new Gson().fromJson(responseProc[2], FuelProcModelDetails.class);
			
			ArrayList<KeyValueModel> company = new ArrayList<KeyValueModel>();
			
			Type companyList = new TypeToken<List<KeyValueModel>>() {
			}.getType();
			company = new Gson().fromJson(new Gson().toJson(mdl.getData().get("company")), companyList);
			
			

			if (company.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("company", company);
				o.put("screenId", screen.getScreenId());
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
			
			String data = (rm.getAdditionalData().containsKey("data"))
					? rm.getAdditionalData().get("data").toString()
					: null;
			
			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			String[] responseProc = AGFuelUtil.fuelGetData(rm.getCorpId(), "COMPANY_PROFILE_VIEW", "GET_DATA",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), data);
			
			String companycode= "";
			String companydescription= "";
			String companynameoncard= "";
			String companyaddress1= "";
			String companyaddress2= "";
			String city= "";
			String emailaddress= "";
			String ntn= "";
			String salestaxreg= "";
			String cont_person= "";
			String cont_desig= "";
			
			if(responseProc[2].contains("data")) {
				ObjectMapper objectMapper = new ObjectMapper();
				 try {
			            JsonNode jsonNode = objectMapper.readTree(responseProc[2]);
			            companycode = jsonNode.get("data").get("companycode").asText();
			            companydescription = jsonNode.get("data").get("companydescription").asText();
			            companynameoncard = jsonNode.get("data").get("companynameoncard").asText();
			            companyaddress1 = jsonNode.get("data").get("companyaddress1").asText();
			            companyaddress2 = jsonNode.get("data").get("companyaddress2").asText();
			            city = jsonNode.get("data").get("city").asText();
			            emailaddress = jsonNode.get("data").get("emailaddress").asText();
			            ntn = jsonNode.get("data").get("ntn").asText();
			            salestaxreg = jsonNode.get("data").get("salestaxreg").asText();
			            cont_person = jsonNode.get("data").get("cont_person").asText();
			            cont_desig = jsonNode.get("data").get("cont_desig").asText();
			            response.setCode("0000");
						response.setMessage("Success.");

			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				 HashMap<Object, Object> o = new HashMap<Object, Object>();
					o.put("companycode", companycode);
					o.put("companydescription", companydescription);
					o.put("companynameoncard", companynameoncard);
					o.put("companyaddress1", companyaddress1);
					o.put("companyaddress2", companyaddress2);
					o.put("city", city);
					o.put("emailaddress", emailaddress);
					o.put("ntn", ntn);
					o.put("salestaxreg", salestaxreg);
					o.put("contPerson", cont_person);
					o.put("contDesig", cont_desig);
					response.setData(o);
					response.setCode("0000");
					response.setMessage("Success.");
				 
			}else {
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
