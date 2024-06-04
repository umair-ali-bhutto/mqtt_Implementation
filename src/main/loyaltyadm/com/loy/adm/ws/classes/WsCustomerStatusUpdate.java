package com.loy.adm.ws.classes;

import java.util.ArrayList;
import com.ag.generic.util.UtilAccess;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresLoyaltyEngine;
import com.ag.db.proc.ProcPerfromTransactionModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.entity.CustomerMaster;
import com.ag.loy.adm.entity.CustomerStatusMaster;
import com.ag.loy.adm.model.CustomKycModel;
import com.ag.loy.adm.service.CorporateMasterService;
import com.ag.loy.adm.service.CustomerMasterService;
import com.ag.loy.adm.service.CustomerStatusMasterService;
import com.ag.loy.adm.service.LoyaltyErrorMappingService;

@Component("com.loy.adm.ws.classes.WsCustomerStatusUpdate")
public class WsCustomerStatusUpdate implements Wisher {

	@Autowired
	CorporateMasterService corporateMasterService;

	@Autowired
	CustomerMasterService customerMasterService;

	@Autowired
	CustomerStatusMasterService customerStatusMasterService;

	@Autowired
	LoyaltyErrorMappingService errorMappingService;

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
			case "UPDATE":
				response = updateRequest(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}
		} catch (Exception ex) {
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
				response.setMessage("No Corporate Found.");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel updateRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {

			String customerId = Objects.isNull(rm.getAdditionalData().get("customerId")) ? null
					: rm.getAdditionalData().get("customerId").toString().trim();// 8097370000000012 |3
			String corpId = Objects.isNull(rm.getAdditionalData().get("corpId")) ? null
					: rm.getAdditionalData().get("corpId").toString().trim();

			String Newstatus = Objects.isNull(rm.getAdditionalData().get("status")) ? null
					: rm.getAdditionalData().get("status").toString().trim();
			if (customerId != null && Newstatus != null) {
				ProcPerfromTransactionModel responseProc = DBProceduresLoyaltyEngine.processProcUpdateCustStatus(corpId,
						customerId, Newstatus, UtilAccess.rrn());
				if (responseProc.getpRespCode().equals("0000")) {
					response.setCode("0000");
					response.setMessage("Customer Status Updated Successfully.");
				} else {
					response.setCode(responseProc.getpRespCode());
					String message = errorMappingService.fetchErrorMessage(responseProc.getpRespCode());
					response.setMessage(message);
				}
			} else {
				response.setCode("0001");
				response.setMessage("Invalid Customer ID or Status.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String customerId = Objects.isNull(rm.getAdditionalData().get("customerId")) ? null
					: rm.getAdditionalData().get("customerId").toString().trim();
			String corpId = Objects.isNull(rm.getAdditionalData().get("corpId")) ? null
					: rm.getAdditionalData().get("corpId").toString().trim();

			if (customerId != null) {
				CustomerMaster cm = customerMasterService.findAllByCidCorpId(customerId, corpId);
				if (!Objects.isNull(cm)) {
					List<CustomerStatusMaster> lst = customerStatusMasterService.fetchAllByCorpIdCusttype(corpId,
							cm.getCusttype());
					HashMap<Object, Object> okj = new HashMap<Object, Object>();
					okj.put("customerMaster", cm);
					okj.put("customerStatus", lst);
					response.setCode("0000");
					response.setData(okj);
				} else {
					response.setCode("0001");
					response.setMessage("No Customer Found.");
				}
			} else {
				response.setCode("0001");
				response.setMessage("Invalid Customer ID.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
}
