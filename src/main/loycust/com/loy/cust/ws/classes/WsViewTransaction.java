package com.loy.cust.ws.classes;


import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.loy.adm.entity.AccountMaster;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.entity.CustomerUserMaster;
import com.ag.loy.adm.entity.VwTxnDetails;
import com.ag.loy.adm.service.AccountMasterService;
import com.ag.loy.adm.service.CorporateMasterService;
import com.ag.loy.adm.service.CustomerUserMasterService;
import com.ag.loy.adm.service.VwTxnDetailsService;

@Component("com.loy.cust.ws.classes.WsViewTransaction")
public class WsViewTransaction implements Wisher {
	
	@Autowired
	VwTxnDetailsService detailsService;
	
	@Autowired
	AccountMasterService accountMasterService;
	
	@Autowired
	CustomerUserMasterService customerUserMasterService;
	
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
			
			String userCode = (rm.getAdditionalData().containsKey("userCode"))
					? rm.getAdditionalData().get("userCode").toString()
					: "N/A";

			List<CorporateMaster> lst = corporateMasterService.fetchAllList(groupCode, corpId);
			
			List<AccountMaster> lstAM = accountMasterService.fetchAllByUserCode(userCode);
			
			List<CustomerUserMaster> lstCM = customerUserMasterService.fetchAllByUserCode(userCode);

			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("corporates", lst);
				obj.put("accountids", lstAM);
				obj.put("cardnumbers", lstCM);
				
				response.setCode("0000");
				response.setMessage("SUCCESS.");
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Corporated Found.");

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

			String userId = Objects.isNull(rm.getAdditionalData().get("userId")) ? null
					: rm.getAdditionalData().get("userId").toString().trim();

			String devBatch = Objects.isNull(rm.getAdditionalData().get("devBatch")) ? null
					: rm.getAdditionalData().get("devBatch").toString().trim();

			String accountId = Objects.isNull(rm.getAdditionalData().get("accountId")) ? null
					: rm.getAdditionalData().get("accountId").toString().trim();

			String frDate = Objects.isNull(rm.getAdditionalData().get("frDate")) ? null
					: rm.getAdditionalData().get("frDate").toString().trim();

			String toDate = Objects.isNull(rm.getAdditionalData().get("toDate")) ? null
					: rm.getAdditionalData().get("toDate").toString().trim();

			String customerId = Objects.isNull(rm.getAdditionalData().get("customerId")) ? null
					: rm.getAdditionalData().get("customerId").toString().trim();

			String corporateId = Objects.isNull(rm.getAdditionalData().get("corporateId")) ? null
					: rm.getAdditionalData().get("corporateId").toString().trim();

			String invoice = Objects.isNull(rm.getAdditionalData().get("invoice")) ? null
					: rm.getAdditionalData().get("invoice").toString().trim();

			String cardNumber = Objects.isNull(rm.getAdditionalData().get("cardNumber")) ? null
					: rm.getAdditionalData().get("cardNumber").toString().trim();

			String rrn = Objects.isNull(rm.getAdditionalData().get("rrn")) ? null
					: rm.getAdditionalData().get("rrn").toString().trim();

			String terminalId = Objects.isNull(rm.getAdditionalData().get("terminalId")) ? null
					: rm.getAdditionalData().get("terminalId").toString().trim();

			if (!Objects.isNull(frDate)) {
				if (Objects.isNull(toDate)) {
					response.setCode("0001");
					response.setMessage("Please select date range correctly.");
					return response;
				}
			} else if (!Objects.isNull(toDate)) {
				if (Objects.isNull(frDate)) {
					response.setCode("0001");
					response.setMessage("Please select date range correctly.");
					return response;
				}
			}

			List<VwTxnDetails> lst = detailsService.fetchByParams(userId, devBatch, accountId, frDate, toDate,
					customerId, corporateId, invoice, cardNumber, rrn, terminalId);
		

			if (Objects.isNull(lst) || lst.isEmpty()) {
				return UtilAccess.generateResponse("8888", "No Record Found.");
			} else {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				map.put("lstViewTransaction", lst);
				String[] blob = detailsService.downloadReport(lst, rm);
				if (!Objects.isNull(blob)) {
					map.put("blob", blob[0]);
					map.put("fileName", blob[1]);
				}
				return UtilAccess.generateResponse("0000", "SUCCESS", map);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
}