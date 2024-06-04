package com.ag.pos.loyalty.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.ComplCategory;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.ComplaintsWebService;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.ComplCategoryService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.LovService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.UtilAccess;
import com.google.gson.Gson;

@RestController
public class LoyaltyViewComplaints {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	LovService lovServiceImpl;

	@Autowired
	ComplCategoryService complCategoryService;

	@Autowired
	ComplaintsService complaintsService;

	@PostMapping({ "/LoyaltyViewComplaints" })
	public ResponseModel doProcessFileAuth(@RequestBody JSONObject requestService, HttpServletRequest request) {
		return doProcessData(requestService, request);
	}

	public ResponseModel doProcessData(JSONObject requestService, HttpServletRequest request) {
		ResponseModel response = new ResponseModel();
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		try {
			UserLogin user = userLoginService.validateUserPassword(requestService.get("userName").toString(),
					requestService.get("password").toString());
			if (user != null) {

				List<LovDetail> listLoveDetails = lovServiceImpl.fetchLovsDetailsAll(user.getCorpId());
				List<LovMaster> listLovMaster = lovServiceImpl.fetchLovs(user.getCorpId());
				HashMap<Integer, Object> userListAll = userLoginService.fetchAllUsersId(user.getCorpId());
				Date frDate = null, tDate = null;
				List<Complaint> compLogs = null;
				ComplCategory cs = new ComplCategory();
				cs.setId(2);

				String mid = requestService.get("mid").toString();
				String tid = requestService.get("tid").toString();
				ComplCategory ctd = complCategoryService.fetchByID(cs);
				UserLogin merchant = userLoginService.fetchUserByMid(mid, user.getCorpId());

				Integer complaintId = merchant.getUserId();

				Complaint complaint = complaintsService.fetchComplaintById(complaintId);
				if (!Objects.isNull(complaint)) {
					compLogs = new ArrayList<Complaint>();
					compLogs.add(complaint);
				}

				compLogs = complaintsService.fetchAllComplaints(mid, tid, frDate, tDate, ctd.getCode(),
						merchant.getUserId() + "", merchant.getUserId() + "");

				if (compLogs.size() != 0) {
					List<ComplaintsWebService> lstComp = new ArrayList<ComplaintsWebService>();
					for (Complaint com : compLogs) {
						ComplaintsWebService mdl = new ComplaintsWebService();
						mdl.setId(com.getId());
						mdl.setMid(com.getMid());
						mdl.setTid(com.getTid());
						mdl.setComplaintSub_Type(com.getComplaintSubType());
						mdl.setComplaintType(com.getComplaintType());
						mdl.setType(getTypeCompl(com.getType(), listLovMaster));
						mdl.setCategory(com.getCategory());
						mdl.setModel(com.getModel());
						mdl.setSubType(
								getSubTypeCompl(com.getComplaintSubType(), com.getComplaintType(), listLoveDetails));
						mdl.setSerialNumber(com.getSerialNumber());
						mdl.setMaker(com.getMaker());
						mdl.setStatus(com.getStatus());
						mdl.setEntryBy(com.getEntryBy());
						mdl.setEntryDate(com.getEntryDate());
						mdl.setLastUpdated(com.getLastUpdated());
						mdl.setClosureDate(com.getClosureDate());
						mdl.setClosedBy(getAssignedTo(
								Integer.parseInt((com.getClosedBy() != null) ? com.getClosedBy() : "0"), userListAll));
						mdl.setAssignedDate(com.getAssignedDate());
						mdl.setAssignedTo(getAssignedTo(
								Integer.parseInt((com.getAssignedTo() != null) ? com.getAssignedTo() : "0"),
								userListAll));
						mdl.setAssignedAddress(com.getAssignedAddress());
						mdl.setComplaintDesc(com.getComplaintDesc());
						mdl.setCanClosable(UtilAccess.allowableToClose(user, com.getStatus(),
								(com.getAssignedTo() != null) ? com.getAssignedTo() : "0"));
						mdl.setCanbeAssigned(UtilAccess.allowableToAsign(user, com.getStatus(),
								(com.getAssignedTo() != null) ? com.getAssignedTo() : "0"));
						mdl.setRemarks(com.getResolution());
						lstComp.add(mdl);
					}

					HashMap<Object, Object> mk = new HashMap<Object, Object>();
					mk.put("complaints", lstComp);

					response.setCode("0000");
					response.setMessage("SUCCESS");
					response.setData(mk);

				} else {
					response.setCode("0001");
					response.setMessage("No Records Found.");

				}

			} else {
				response.setCode("9991");
				response.setMessage("Invalid Credentials.");
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setTid("N/A");
			adt.setEntryDate(time);
			adt.setRequest(requestService.toString());
			adt.setResponse(new Gson().toJson(response));
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("Loyalty View Complaints");
			adt.setMid("N/A");
			adt.setSerialNum("N/A");
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return response;
	}

	public static String getSubTypeCompl(String subtypeid, String type, List<LovDetail> listLoveDetails) {
		for (LovDetail lo : listLoveDetails) {
			if (lo.getId().equals(subtypeid) && lo.getLovId().equals(type)) {
				return lo.getValue();
			}
		}
		return "N/A";
	}

	public static String getTypeCompl(String typeid, List<LovMaster> lovMaster) {
		try {
			for (LovMaster m : lovMaster) {
				if (m.getId().equals(typeid)) {
					return m.getValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "N/A";
	}

	public static String getAssignedTo(int id, HashMap<Integer, Object> userListAll) {

		try {
			UserLogin usl = (UserLogin) userListAll.get(id);
			if (usl != null) {
				return usl.getUserName();
			}
		} catch (Exception e) {

		}
		return "N/A";
	}

}
