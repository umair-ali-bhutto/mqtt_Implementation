package com.mportal.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.BuildControlConfig;
import com.ag.mportal.entity.MerConfDetails;
import com.ag.mportal.entity.MerConfMaster;
import com.ag.mportal.model.ViewOfflineSaleModel;
import com.ag.mportal.services.BuildControlConfigService;
import com.ag.mportal.services.MerConfDetailsService;
import com.ag.mportal.services.MerConfMasterService;

@Component("com.mportal.ws.classes.WsFetchBuildController")
public class WsFetchBuildController implements Wisher {

	@Autowired
	BuildControlConfigService buildControlConfigService;
	
	@Autowired
	MerConfMasterService masterService;
	
	@Autowired
	MerConfDetailsService detailsService;

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
			String model = (rm.getAdditionalData().containsKey("model"))
					? rm.getAdditionalData().get("model").toString()
					: "N/A";

			List<BuildControlConfig> lst = new ArrayList<BuildControlConfig>();
			lst = buildControlConfigService.fetchAllByModel(model);

			if (lst.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("lst", lst);
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
			String mid = rm.getAdditionalData().get("mid") == null ? null
					: rm.getAdditionalData().get("mid").toString();

			String tid = rm.getAdditionalData().get("tid") == null ? null
					: rm.getAdditionalData().get("tid").toString();

			AgLogger.logInfo("MID " + mid + " TID" + tid);

			List<MerConfMaster> lst = masterService.fetchAllByMidTid(mid, tid);
			

			if (lst.size() != 0) {
				response.setCode("0000");
				response.setMessage("Success.");
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("lst", lst);
				response.setData(obj);
			} else {
				response.setCode("0002");
				response.setMessage("No Record Found.");
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
			String id = (rm.getAdditionalData().containsKey("id"))
					? rm.getAdditionalData().get("id").toString()
					: "N/A";
			
			String model = (rm.getAdditionalData().containsKey("model"))
					? rm.getAdditionalData().get("model").toString()
					: "N/A";
			
			
			
			Long idLong=Long.parseLong(id);
			
			MerConfMaster fetchMaster = masterService.fetchAllById(idLong);
			

			List<MerConfDetails> lst = new ArrayList<MerConfDetails>();
			lst = detailsService.fetchAllById(idLong);
			
			List<BuildControlConfig> finalLst = new ArrayList<BuildControlConfig>();
			finalLst = detailsService.fetchRecById(idLong);
			
			List<BuildControlConfig> list = new ArrayList<BuildControlConfig>();
			list = buildControlConfigService.fetchAllByModel(model);
			

			if (lst.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("lst", lst);
				o.put("fetchMaster", fetchMaster);
				o.put("finalLst", finalLst);
				o.put("list", list);
				
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

}
