package com.fuel.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.model.KeyValueModel;
import com.ag.generic.entity.ConfigCity;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.service.impl.ConfigCityServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.services.BuildControlConfigService;

@Component("com.fuel.ws.classes.WsCompanyProfileCreation")
public class WsCompanyProfileCreation implements Wisher {

	@Autowired
	UserScreenService screenService;
	
	@Autowired
	ConfigCityServiceImpl configCityServiceImpl;

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
			
			List<ConfigCity> lstCity =  configCityServiceImpl.findAll(corpid);
			List<KeyValueModel> lst = new ArrayList<KeyValueModel>();
			
			if(lstCity.size()!=0) {				
				KeyValueModel keyValue = null;				
				for(ConfigCity l: lstCity) {
					keyValue = new KeyValueModel();
					keyValue.setKey(l.getCode());
					keyValue.setValue(l.getName());
					lst.add(keyValue);
				}				
			}
			

			if (lst.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("lst", lst);
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
