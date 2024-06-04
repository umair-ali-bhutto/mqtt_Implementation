package com.loy.adm.ws.classes;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.entity.FeatureMaster;
import com.ag.loy.adm.service.CorporateMasterService;
import com.ag.loy.adm.service.FeatureMasterService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.loy.adm.ws.classes.WsFeatureConfig")
public class WsFeatureConfig implements Wisher {

	@Autowired
	FeatureMasterService featureMasterService;

	@Autowired
	UtilService utilService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	CorporateMasterService corporateMasterService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			AgLogger.logInfo("reqType : " + reqType);

			switch (reqType) {
			case "FETCH":
				response = fetchRequest(rm);
				break;
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

	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String corpId = Objects.isNull(rm.getAdditionalData().get("corpId")) ? null
					: rm.getAdditionalData().get("corpId").toString().trim();

			UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			String maxId = featureMasterService.fetchMaxId();

			List<FeatureMaster> lst = featureMasterService.getAllFeatures(corpId);

			if (lst.size() != 0) {
				response.setCode("0000");
				response.setMessage("SUCCESS.");
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("canBeAdded", utilService.isEditRights(AppProp.getProperty("loy.product.screen.rights.add"),
						ukl.getGroupCode()));
				obj.put("canBeEdited", utilService.isEditRights(AppProp.getProperty("loy.product.screen.rights.edit"),
						ukl.getGroupCode()));
				obj.put("lstView", lst);
				obj.put("maxId", maxId);

				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Records Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9993");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel initRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String corpId = rm.getCorpId();
			int groupCode = 0;
			groupCode = Objects.isNull(rm.getAdditionalData().get("groupCode")) ? groupCode
					: Integer.parseInt(rm.getAdditionalData().get("groupCode").toString());

			List<CorporateMaster> lst = corporateMasterService.fetchAllList(groupCode, corpId);
			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("corporates", lst);

				response.setCode("0000");
				response.setMessage("SUCCESS.");
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Corporated Found.");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9992");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel process(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {

			String featureAddedlist = null;
			if (rm.getAdditionalData().containsKey("featureAddedlist")) {
				featureAddedlist = rm.getAdditionalData().get("featureAddedlist").toString();
			}

			String featureEditedlist = null;
			if (rm.getAdditionalData().containsKey("featureEditedlist")) {
				featureEditedlist = rm.getAdditionalData().get("featureEditedlist").toString();
			}

//			String featureRemlist = null;
//			if (rm.getAdditionalData().containsKey("featureRemlist")) {
//				featureRemlist = rm.getAdditionalData().get("featureRemlist").toString();
//			}
//
//			if (featureRemlist != null) {
//				Type listType = new TypeToken<List<FeatureMaster>>() {
//				}.getType();
//				List<FeatureMaster> removeList = new Gson().fromJson(featureRemlist, listType);
//				System.out.println("REM SIZE: " + removeList.size());
//
//			}
			if (featureEditedlist != null) {
				Type listType = new TypeToken<List<FeatureMaster>>() {
				}.getType();
				List<FeatureMaster> editList = new Gson().fromJson(featureEditedlist, listType);
				AgLogger.logInfo("EDIT SIZE: " + editList.size());
				for (FeatureMaster p : editList) {
					p.setUpdBy(rm.getUserid());
					p.setUpdOn(new Date());
					featureMasterService.updateFeatureMaster(p);
				}

			}

			if (featureAddedlist != null) {
				Type listType = new TypeToken<List<FeatureMaster>>() {
				}.getType();
				List<FeatureMaster> addList = new Gson().fromJson(featureAddedlist, listType);
				AgLogger.logInfo("ADD SIZE: " + addList.size());
				for (FeatureMaster p : addList) {
					p.setCrBy(rm.getUserid());
					p.setCrOn(new Date());
					p.setMappedTo("M");
					p.setMappedId("00");
					p.setFeatureType("01");
					featureMasterService.saveFeature(p);
				}

			}

			response.setCode("0000");
			response.setMessage("Successfully Added/Updated Features.");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9991");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}
