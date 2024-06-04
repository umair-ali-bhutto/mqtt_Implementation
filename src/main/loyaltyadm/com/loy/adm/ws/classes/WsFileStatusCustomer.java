package com.loy.adm.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.entity.LoyCustomerFile;
import com.ag.loy.adm.service.CorporateMasterService;
import com.ag.loy.adm.service.LoyCustomerFileService;

@Component("com.loy.adm.ws.classes.WsFileStatusCustomer")
public class WsFileStatusCustomer implements Wisher {

	@Autowired
	LoyCustomerFileService customerFileService;

	@Autowired
	CorporateMasterService corporateMasterService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			AgLogger.logInfo("reqType : " + reqType);

			switch (reqType) {
			case "INIT":
				response = initRequest(rm);
				break;
			case "FETCH":
				response = fetchRequest(rm);
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
			response.setCode("9991");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		String corpId = Objects.isNull(rm.getAdditionalData().get("corpId")) ? null
				: rm.getAdditionalData().get("corpId").toString().trim();

		try {

			List<LoyCustomerFile> lst = new ArrayList<LoyCustomerFile>();
			lst = customerFileService.fetchAllByCorpId(corpId);

			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("lst", lst);

				response.setCode("0000");
				response.setMessage("Success.");
				response.setData(obj);

			} else {
				response.setCode("0001");
				response.setMessage("No Record Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9991");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}