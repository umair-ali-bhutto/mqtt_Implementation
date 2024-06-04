package com.mportal.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.MerConfDetails;
import com.ag.mportal.entity.MerConfMaster;
import com.ag.mportal.services.MerConfDetailsService;
import com.ag.mportal.services.MerConfMasterService;

@Component("com.mportal.ws.classes.WsFetchDonationBinRanges")
public class WsFetchDonationBinRanges implements Wisher {

	@Autowired
	MerConfMasterService merConfMasterService;

	@Autowired
	MerConfDetailsService merConfDetailsService;

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
				response = InitRequest(rm);
				break;
			case "MASTER":
				response = MasterRequest(rm);
				break;
			case "DETAILS":
				response = DetailsRequest(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel InitRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String defaultBins = AppProp.getProperty("hb.default.donation.card.ranges");
			
			if(!defaultBins.equals("N/A")) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("defaultBins", defaultBins);

				response.setCode("0000");
				response.setMessage("Success.");
				response.setData(obj);
			}
			else {
				response.setCode("0001");
				response.setMessage("Default Bins Not Found.");
			}
				
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9993");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
	
	ResponseModel MasterRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String mid = rm.getAdditionalData().get("mid") == null ? null
					: rm.getAdditionalData().get("mid").toString();

			String tid = rm.getAdditionalData().get("tid") == null ? null
					: rm.getAdditionalData().get("tid").toString();

			List<MerConfMaster> lst = new ArrayList<MerConfMaster>();

			lst = merConfMasterService.fetchDataByMidTid(mid, tid);

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

	ResponseModel DetailsRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String recId = rm.getAdditionalData().get("recId").toString();

			AgLogger.logInfo("RECORD ID: " + Long.parseLong(recId));

			List<MerConfDetails> lst = new ArrayList<MerConfDetails>();

			lst = merConfDetailsService.fetchAllByRecId(Long.parseLong(recId));

			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("lst", lst);

				response.setCode("0000");
				response.setMessage("Success.");
				response.setData(obj);
			} else {
				response.setCode("0002");
				response.setMessage("No Record Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9992");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
	
	

}
