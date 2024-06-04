package com.loy.adm.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.AwardMasters;
import com.ag.loy.adm.service.AwardMastersService;
import com.ag.mportal.model.DisplayModel;

@Component("com.loy.adm.ws.classes.WsAwardConfig")
public class WsAwardConfig implements Wisher {

	@Autowired
	AwardMastersService awardMastersService;
	
	@Autowired
	UserLoginService loginService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {
			case "FETCH":
				response = fetchRequest(rm);
				break;
			case "INIT":
				response = initRequest(rm);
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
			List<DisplayModel> awardTypeModel = new ArrayList<DisplayModel>();

			DisplayModel dms = new DisplayModel();
			dms.setKey("P");
			dms.setValue("P");
			awardTypeModel.add(dms);
			
			List<UserLogin> lst = new ArrayList<UserLogin>();
			lst = loginService.fetchAll();
			

			HashMap<Object, Object> o = new HashMap<Object, Object>();
			o.put("awardType", awardTypeModel);
			o.put("users", lst);

			response.setCode("0000");
			response.setMessage("SUCCESS");
			response.setData(o);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String corpId = (rm.getAdditionalData().containsKey("corpId"))
					? rm.getAdditionalData().get("corpId").toString()
					: "N/A";
			List<DisplayModel> awardTypeModel = new ArrayList<DisplayModel>();
			List<AwardMasters> mstr = awardMastersService.fetchAllRecords(corpId);
			if (mstr != null) {
				if (mstr.size() != 0) {
					DisplayModel dms;
					dms = new DisplayModel();
					dms.setKey("P");
					dms.setValue("P");
					awardTypeModel.add(dms);
					HashMap<Object, Object> o = new HashMap<Object, Object>();
					o.put("awardList", mstr);
					o.put("awardType", awardTypeModel);
					response.setCode("0000");
					response.setMessage("SUCCESS");
					response.setData(o);

				} else {
					response.setCode("0002");
					response.setMessage("No Awards List Found.");
				}
			} else {
				response.setCode("0001");
				response.setMessage("No Awards List Found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

}
