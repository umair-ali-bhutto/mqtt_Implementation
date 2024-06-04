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
import com.ag.fuel.model.ProductPriceViewModel;
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

@Component("com.fuel.ws.classes.WsViewProductPrice")
public class WsViewProductPrice implements Wisher {

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
			String[] responseProc = AGFuelUtil.fuelAccountProcess(rm.getCorpId(), "PRODUCT_PRICE", "LOV",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), "");

			FuelProcModelDetails mdl = new Gson().fromJson(responseProc[2], FuelProcModelDetails.class);
			
			ArrayList<KeyValueModel> dealer = new ArrayList<KeyValueModel>();
			
			Type companyList = new TypeToken<List<KeyValueModel>>() {
			}.getType();
			dealer = new Gson().fromJson(new Gson().toJson(mdl.getData().get("dealer")), companyList);
	
			

			if (dealer.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("product", dealer);
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

			String[] responseProc = AGFuelUtil.fuelGetData(rm.getCorpId(), "PRODUCT_PRICE", "GET_DATA",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), data);
			List<ProductPriceViewModel> li = new ArrayList<ProductPriceViewModel>();
			 Type listcardList = new TypeToken<List<ProductPriceViewModel>>() {
				}.getType();
				li = new Gson().fromJson(responseProc[2], listcardList);
			
			if(responseProc[2] !=null) {
				
				 HashMap<Object, Object> o = new HashMap<Object, Object>();
					o.put("lst", li);
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
