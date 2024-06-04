package com.fuel.ws.classes;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.util.AGFuelUtil;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.FileUploader;
import com.ag.mportal.entity.FileUploaderDetails;
import com.ag.mportal.services.FileUploaderDetailsService;
import com.ag.mportal.services.FileUploaderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("com.fuel.ws.classes.WsProductApprover")
public class WsProductApprover implements Wisher {

	@Autowired
	UserScreenService screenService;

	@Autowired
	FileUploaderDetailsService fileUploaderDetailsService;

	@Autowired
	FileUploaderService fileUploaderService;

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
			case "APPROVE":
				response = approveRequest(rm);
				break;
			case "REJECT":
				response = rejectRequest(rm);
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
			FileUploader fup = fileUploaderService.fetctByTypeStatus("PRODUCT_PRICE", rm.getCorpId());
			if (fup != null) {
				long id = fup.getId();

				List<FileUploaderDetails> lstFiles = fileUploaderDetailsService
						.fetchListFileIdStatus(Long.toString(id));

				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("data", lstFiles);
				o.put("fname", fup.getFileName());
				o.put("fid", id);
				o.put("tRec", fup.getTotalRec());
				o.put("tSRec", fup.getSuccessRec());
				o.put("crOn", fup.getCrOn());
				o.put("crBy", fup.getCrBy());
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Success.");

			} else {
				response.setCode("1111");
				response.setMessage("No Data Found.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	ResponseModel approveRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String fileId = (rm.getAdditionalData().containsKey("fileId"))
					? rm.getAdditionalData().get("fileId").toString()
					: "N/A";
			String date = (rm.getAdditionalData().containsKey("date"))
					? rm.getAdditionalData().get("date").toString()
					: "N/A";
			String time = (rm.getAdditionalData().containsKey("time"))
					? rm.getAdditionalData().get("time").toString()
					: "N/A";
			String[] responseProc = AGFuelUtil.fuelApproveProductData(rm.getCorpId(), fileId, date, time);
			if (responseProc[2].contains("data")) {
				JSONObject onj = (JSONObject) JSONValue.parse(responseProc[2]);				
				JSONArray onj2 = (JSONArray) onj.get("data");
				FileUploader fup = fileUploaderService.fetctByTypeStatus("PRODUCT_PRICE", rm.getCorpId());
				fup.setApprBy(rm.getUserid());
				fup.setApprOn(new Timestamp(new java.util.Date().getTime()));
				fup.setStatus("APPROVED");
				fileUploaderService.update(fup);
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("data", onj2);
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Record Apprved.");
			}else {
				response.setCode("1111");
				response.setMessage("No Data Found For Download.");
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

	ResponseModel rejectRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			FileUploader fup = fileUploaderService.fetctByTypeStatus("PRODUCT_PRICE", rm.getCorpId());
			fup.setRejOn(new Timestamp(new java.util.Date().getTime()));
			fup.setRejBy(rm.getUserid());
			fup.setStatus("REJECTED");
			response.setCode("0000");
			response.setMessage("File Rejected.");
		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}
