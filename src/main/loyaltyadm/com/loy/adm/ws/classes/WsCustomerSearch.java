package com.loy.adm.ws.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.loy.adm.entity.AccountMaster;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.db.proc.DBProceduresLoyaltyEngine;
import com.ag.generic.model.RequestModel;
import com.ag.loy.adm.service.AccountMasterService;
import com.ag.loy.adm.service.CorporateMasterService;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.loy.adm.entity.CustomerMaster;
import com.ag.loy.adm.service.CustomerMasterService;
import com.ag.loy.cust.service.LoyCustDashboardService;
import com.ag.metro.model.GenericLovModel;
import com.ag.loy.adm.entity.CustomerUserMaster;
import com.ag.loy.adm.entity.UserStatusMaster;
import com.ag.loy.adm.service.CustomerUserMasterService;
import com.ag.loy.adm.service.LoyaltyAdminService;
import com.ag.loy.adm.service.UserStatusService;

@Component("com.loy.adm.ws.classes.WsCustomerSearch")
public class WsCustomerSearch implements Wisher {

	@Autowired
	CorporateMasterService corporateMasterService;

	@Autowired
	CustomerMasterService customerMasterService;

	@Autowired
	LoyCustDashboardService loyCustDashboardService;

	@Autowired
	AccountMasterService accountMasterService;

	@Autowired
	CustomerUserMasterService customerUserMasterService;

	@Autowired
	UserStatusService userStatusService;

	@Autowired
	LoyaltyAdminService adminService;



	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			String loginType = (rm.getAdditionalData().containsKey("loginType"))
					? rm.getAdditionalData().get("loginType").toString()
					: "N/A";

			switch (reqType) {
			case "FETCH":
				response = fetchRequest(rm);
				break;
			case "INIT":
				if (loginType.equals("ADMIN") || loginType == "ADMIN") {
					response = initRequest(rm);
				} else {
					response = initRequestCustomer(rm);
				}

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
				response.setMessage("No Corporated Found.");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel initRequestCustomer(RequestModel rm) {
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
			List<CustomerUserMaster> lstCM = customerUserMasterService.fetchAllByUserCode(userCode);

			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("corporates", lst);
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

			String cardNumber = Objects.isNull(rm.getAdditionalData().get("cardNumber")) ? null
					: rm.getAdditionalData().get("cardNumber").toString().trim();

			String name = Objects.isNull(rm.getAdditionalData().get("name")) ? null
					: rm.getAdditionalData().get("name").toString().trim();

			String cnic = Objects.isNull(rm.getAdditionalData().get("cnic")) ? null
					: rm.getAdditionalData().get("cnic").toString().trim();

			String corpId = Objects.isNull(rm.getAdditionalData().get("corpId")) ? null
					: rm.getAdditionalData().get("corpId").toString();
			
			String userCode = Objects.isNull(rm.getAdditionalData().get("userCode")) ? null
					: rm.getAdditionalData().get("userCode").toString();

			if (cardNumber != null || name != null || cnic != null) {

				String[] record = adminService.isRecordExist(cardNumber, name, cnic, corpId);

				if (record[0].equals("0000")) {

					List<UserStatusMaster> lstUSM = userStatusService.fetchByCorpId(corpId);
					List<GenericLovModel> lstGLM = adminService.fetchCustomerTypes(corpId);

					//String cid = adminService.getCidFromAccountUser(corpId, userCode);
					String[] bal = DBProceduresLoyaltyEngine.getAccountBalancePoints(corpId, record[1], record[3],
							UtilAccess.rrn());

					// AccountMaster am = accountMasterService.fetchById(corpId, record[4]);
					CustomerMaster cm = customerMasterService.findAllByCidCorpId(record[1], corpId);
					List<CustomerUserMaster> lst = customerUserMasterService.findAllByCardNoCorpId(record[3], corpId);

					HashMap<Object, Object> okj = new HashMap<Object, Object>();
					okj.put("nameoncard", record[2]);
					// okj.put("balance", am);
					okj.put("balance", bal[1]);
					okj.put("valueinpkr", bal[2]);
					okj.put("statuslist", lstUSM);
					okj.put("customerMaster", cm);
					okj.put("customerUserMaster", lst);
					okj.put("customerTypes", lstGLM);
					
					

					response.setCode("0000");
					response.setMessage("SUCCESS");
					response.setData(okj);

				} else {
					response.setCode("0002");
					response.setMessage("No Record Found.");
				}
			} else {
				response.setCode("0001");
				response.setMessage("Please Select One Filter Except Corporate ID.");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}
