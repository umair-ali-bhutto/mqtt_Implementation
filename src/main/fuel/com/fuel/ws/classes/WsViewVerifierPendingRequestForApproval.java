package com.fuel.ws.classes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.Wisher;

import com.ag.generic.util.AgLogger;

@Component("com.fuel.ws.classes.WsViewVerifierPendingRequestForApproval")
public class WsViewVerifierPendingRequestForApproval implements Wisher {

	@Autowired
	MakerCheckerDataService makerCheckerData;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String groupCode = rm.getAdditionalData().get("groupCode") == null ? null
					: rm.getAdditionalData().get("groupCode").toString();

				List<MakerCheckerData> lst = makerCheckerData.fetchVerifierbyGroupCode(groupCode);
				if (lst.size() != 0) {
					HashMap<Object, Object> o = new HashMap<Object, Object>();
					List<MakerCheckerData> lstTemp = new ArrayList<MakerCheckerData>();
					for (MakerCheckerData m : lst) {
						lstTemp.add(m);
					}
					o.put("lst", lstTemp);
					response.setData(o);
					response.setCode("0000");
					response.setMessage("Success.");
				} else {
					response.setCode("0002");
					response.setMessage("No Pending Records.");
				}
				

			
		} catch (Exception e) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
