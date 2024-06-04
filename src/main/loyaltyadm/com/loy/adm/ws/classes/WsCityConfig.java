package com.loy.adm.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CityMaster;
import com.ag.loy.adm.service.CityMasterService;

@Component("com.loy.adm.ws.classes.WsCityConfig")
public class WsCityConfig implements Wisher {

	@Autowired
	CityMasterService cityMasterService;

	@Autowired
	UtilService utilService;

	@Autowired
	UserLoginService userLoginService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		rm.setCorpId("00003");
		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {
			case "INIT":
				response = initRequest(rm);
				break;
			case "PROCESS":
				response = process(rm);
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

			UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			List<CityMaster> lst = cityMasterService.fetchAllByCorpId(rm.getCorpId());

			if (lst.size() != 0) {
				response.setCode("0000");
				response.setMessage("SUCCESS.");
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("canBeAdded", utilService.isEditRights(AppProp.getProperty("loy.city.screen.rights.add"),
						ukl.getGroupCode()));
				obj.put("canBeEdited", utilService.isEditRights(AppProp.getProperty("loy.city.screen.rights.edit"),
						ukl.getGroupCode()));
				obj.put("lstView", lst);
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Records Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel process(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			response.setCode("0000");
			response.setMessage("SUCCESS.");
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}
